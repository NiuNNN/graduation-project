package niuniu.javaweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import niuniu.javaweb.mapper.CheckOutMapper;
import niuniu.javaweb.mapper.HouseMapper;
import niuniu.javaweb.mapper.OrderMapper;
import niuniu.javaweb.mapper.RentMapper;
import niuniu.javaweb.pojo.CheckOut;
import niuniu.javaweb.pojo.Order;
import niuniu.javaweb.pojo.Rent;
import niuniu.javaweb.service.CheckOutService;
import niuniu.javaweb.utils.DateUtil;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.utils.tools.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service.impl
 * @date 2023/4/19 18:23
 */
@Service

public class CheckOutServiceImpl extends ServiceImpl<CheckOutMapper, CheckOut> implements CheckOutService {
    @Autowired
    CheckOutMapper checkOutMapper;

    @Autowired
    RentMapper rentMapper;

    @Autowired
    HouseMapper houseMapper;

    @Autowired
    OrderMapper orderMapper;


    /**
     * 申请退房
     *
     * @param userId
     * @param message
     * @return
     */
    @Override
    public CommonResult insertCheckOut(Integer userId, String message) {
        QueryWrapper<Rent> queryWrapper = new QueryWrapper<Rent>();
        queryWrapper.eq("user_id", userId);
        Rent rent = rentMapper.selectOne(queryWrapper);
        if (checkOutMapper.insertCheckOut(userId, rent.getRentId(), message) <= 0) return CommonResult.failed();
//        System.out.println(rent);
        return CommonResult.success();
    }

    /**
     * 获取申请退房状态
     *
     * @param userId
     * @return
     */
    @Override
    public CommonResult getCheckOutState(Integer userId) {
        return CommonResult.success(checkOutMapper.getCheckOutState(userId));
    }

    /**
     * 取消申请
     *
     * @param checkOut
     * @return
     */
    @Override
    public CommonResult cancelCheckOut(CheckOut checkOut) {
        System.out.println(checkOut);
        return CommonResult.success(checkOutMapper.updateById(checkOut));
    }

    /**
     * 退房处理
     *
     * @param checkOut
     * @return
     */
    @Override
    @Transactional
    public CommonResult deleteCheckOut(CheckOut checkOut) throws ParseException {
        /**
         * 先查询有误欠费
         * 判断提交时间是否是另一个月的第三天前
         * 不是返回需要提交相应的信息
         * 是给予退房 修改house表 rent表
         *
         */
        if (orderMapper.selectNoOrder(checkOut.getRentId()) > 0) {
            checkOut.setRemark("存在欠费记录，请缴清再申请");
            checkOutMapper.updateCheckOut(checkOut);
            return CommonResult.failed("存在欠费记录");
        } else {
            if (DateUtil.checkOrderTime(checkOut.getStart())) {
                String total = orderMapper.selectTotal(checkOut.getRentId());
                Order order = new Order();
                order.setRentId(checkOut.getRentId());
                order.setTotal(total);
                order.setOutTradeNo(OrderUtil.getOrderNo());
                order.setType("退款");
                orderMapper.generateOrder(order);
                checkOut.setRemark("退房成功,请来前台领回押金");
                checkOutMapper.updateCheckOut(checkOut);
                rentMapper.logout(checkOut.getRentId());
                Rent rent = rentMapper.selectById(checkOut.getRentId());
                houseMapper.changeState(rent.getHouseId(), "空闲");
            }
            checkOut.setRemark("该月账单还未缴纳");
            checkOutMapper.updateCheckOut(checkOut);
            return CommonResult.failed("该月账单还未生成");
        }


    }
}

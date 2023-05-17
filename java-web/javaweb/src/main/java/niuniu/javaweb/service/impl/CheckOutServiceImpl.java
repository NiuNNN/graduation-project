package niuniu.javaweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import niuniu.javaweb.mapper.*;
import niuniu.javaweb.pojo.CheckOut;
import niuniu.javaweb.pojo.Order;
import niuniu.javaweb.pojo.Rent;
import niuniu.javaweb.pojo.User;
import niuniu.javaweb.service.CheckOutService;
import niuniu.javaweb.utils.DateUtil;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.utils.tools.OrderUtil;
import niuniu.javaweb.vo.CheckOutVO;
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
    ContractMapper contractMapper;

    @Autowired
    HouseMapper houseMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    UserMapper userMapper;


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
        return CommonResult.success(checkOutMapper.cancelCheckout(checkOut));
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
         * 判断提交时间是否是这个月的第三天前
         * 不是返回需要提交相应的信息
         * 是给予退房 修改house表 rent表
         *
         */

        //是否存在欠款
        if (orderMapper.selectNoOrder(checkOut.getRentId()) > 0) {
            checkOut.setRemark("存在欠费记录，请缴清再申请!");
            checkOut.setState(2);
            checkOutMapper.updateCheckOut(checkOut);
            return CommonResult.failed("存在欠费记录...");
        } else {
            //判断申请时间
            if (DateUtil.checkOrderTime(checkOut.getStart())) {
                //符合这个月的前三天
                //先判断合同时间
                handleCheckOut(checkOut);
                return CommonResult.success();
            } else {
                //查询是否存在该月的账单记录
                if (orderMapper.checkOrder(checkOut.getRentId(), checkOut.getStart())) {
                    //存在账单 给予退房
                    handleCheckOut(checkOut);
                    return CommonResult.success();
                } else {
                    checkOut.setRemark("该月账单还未缴纳!");
                    checkOut.setState(2);
                    checkOutMapper.updateCheckOut(checkOut);
                    return CommonResult.failed("该月账单还未生成...");
                }

            }
        }
    }

    /**
     * 退款处理
     *
     * @param checkOut
     */
    public void handleCheckOut(CheckOut checkOut) {
        if (contractMapper.checkSign(checkOut.getRentId(), checkOut.getStart())) {
            checkOut.setRemark("退房成功");
        } else {
            //租满合同时间退还押金
            String total = orderMapper.selectTotal(checkOut.getRentId());
            //填写order表
            Order order = new Order();
            order.setRentId(checkOut.getRentId());
            order.setTotal(total);
            order.setOutTradeNo(OrderUtil.getOrderNo());
            order.setType("退款");
            orderMapper.generateOrder(order);
            checkOut.setRemark("退房成功,请来前台领回押金!");
        }
        //修改checkout表
        checkOut.setState(1);
        checkOutMapper.updateCheckOut(checkOut);
        //修改rent表
        rentMapper.logout(checkOut.getRentId());
        Rent rent = rentMapper.selectById(checkOut.getRentId());
        //修改house表
        houseMapper.changeState(rent.getHouseId(), "空闲");
    }

    /**
     * 获取全部申请信息
     *
     * @param time
     * @param houseName
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public IPage<CheckOutVO> getAllCheckOut(String time, String houseName, int currentPage, int pageSize) {
        Page<CheckOutVO> page = new Page<>(currentPage, pageSize);
        QueryWrapper<CheckOutVO> queryWrapper = new QueryWrapper<>();
        return checkOutMapper.getAllCheckOut(time, houseName, page, queryWrapper);
    }

    /**
     * 获取退房详细信息
     *
     * @param checkoutId
     * @return
     */
    @Override
    public CommonResult getCheckOutDetail(Integer checkoutId) {
        CheckOutVO checkOutDetail = checkOutMapper.getCheckOutDetail(checkoutId);
        if (checkOutDetail.getUserId() != null) {
            User user = userMapper.selectById(checkOutDetail.getUserId());
            checkOutDetail.setName(user.getName());
        }
        return CommonResult.success(checkOutDetail);
    }
}

package niuniu.javaweb.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import niuniu.javaweb.pojo.Order;
import niuniu.javaweb.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.mapper
 * @date 2023/4/9 15:37
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 生成订单
     *
     * @param order
     * @return
     */
    int generateOrder(Order order);

    /**
     * 支付通过支付宝
     *
     * @param order
     * @return
     */
    int updateOrder(Order order);

    /**
     * 支付通过现金
     *
     * @param order
     * @return
     */
    int updateOrderByCash(Order order);

    /**
     * 插入支付宝订单号
     *
     * @param outTradeNo
     * @param tradeNo
     * @return
     */
    int insertTradeNo(String outTradeNo, String tradeNo);

    /**
     * 判断是否缴费
     *
     * @param order
     * @return
     */
    int judgeOrder(Order order);

    /**
     * 获取订单信息
     *
     * @param rentId
     * @return
     */
    Order getOrderByRentId(Integer rentId);

    /**
     * 按需获取全部订单
     *
     * @param houseName
     * @param time
     * @param page
     * @param queryWrapper
     * @return
     */
    IPage<OrderVO> getOrderPage(@Param("houseName") String houseName, @Param("time") String time, @Param("page") Page<OrderVO> page, @Param(Constants.WRAPPER) QueryWrapper<OrderVO> queryWrapper);

    /**
     * 获取付款时的杂费金额
     *
     * @param orderId
     * @return
     */
    String getMisPrice(Integer orderId);

    /**
     * 查询有没有欠费记录
     *
     * @param rentId
     * @return
     */
    int selectNoOrder(Integer rentId);

    /**
     * 获取押金
     *
     * @param rentId
     * @return
     */
    String selectTotal(Integer rentId);

    /**
     * 获取用户账单
     *
     * @param userId
     * @return
     */
    List<Order> getUserOrder(Integer userId);

    /**
     * 获取用户详细账单
     *
     * @param rentId
     * @return
     */
    OrderVO getUserOrderDetail(Integer rentId);

    /**
     * 判断是否存在该月账单
     *
     * @param rentId
     * @param time
     * @return
     */
    boolean checkOrder(Integer rentId, String time);
}

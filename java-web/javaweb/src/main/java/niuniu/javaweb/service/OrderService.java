package niuniu.javaweb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import niuniu.javaweb.pojo.Order;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.vo.OrderVO;
import niuniu.javaweb.vo.PaymentVo;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service
 * @date 2023/4/9 15:37
 */
public interface OrderService {

    /**
     * 生成订单
     *
     * @param order
     * @return
     */
    CommonResult generateOrder(Order order) throws ParseException;

    /**
     * 支付通过现金
     *
     * @param order
     * @return
     */
    CommonResult payByCash(Order order);

    /**
     * 支付通过支付宝
     *
     * @param order
     * @return
     */
    CommonResult payByAlipay(Order order, PaymentVo paymentVo) throws Exception;

    /**
     * 移动端支付宝支付
     *
     * @param order
     * @param paymentVo
     * @return
     * @throws Exception
     */
    CommonResult pay(Order order, PaymentVo paymentVo) throws Exception;

    /**
     * 支付回调
     *
     * @param request
     */
    void callback(HttpServletRequest request);

    /**
     * 判断是否缴费
     *
     * @param order
     * @return
     */
    CommonResult judgePay(Order order);

    /**
     * 获取订单信息
     *
     * @param rentId
     * @return
     */
    CommonResult getOrderByRentId(Integer rentId);

    /**
     * 按需获取订单信息
     *
     * @param currentPage
     * @param pageSize
     * @param houseName
     * @param time
     * @return
     */
    IPage<OrderVO> getAllOrder(int currentPage, int pageSize, String houseName, String time);

    /**
     * 获取用户账单信息
     *
     * @param userId
     * @return
     */
    CommonResult getUserOrder(Integer userId);

    /**
     * 获取用户账单详情
     *
     * @param orderId
     * @return
     */
    CommonResult getUserOrderDetail(Integer orderId);
}

package niuniu.javaweb.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import niuniu.javaweb.pojo.Order;
import niuniu.javaweb.service.OrderService;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.vo.OrderVO;
import niuniu.javaweb.vo.PaymentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.controller
 * @date 2023/4/9 15:37
 */
@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("generateOrder")
    public CommonResult generateOrder(Order order) throws ParseException {
        return orderService.generateOrder(order);
    }

    /**
     * 支付通过现金
     *
     * @param order
     * @return
     */
    @PostMapping("payByCash")
    public CommonResult payByCash(Order order) {
        return orderService.payByCash(order);
    }

    /**
     * 支付通过支付宝
     *
     * @param order
     * @return
     */
    @PostMapping("payByAlipay")
    public CommonResult payByAlipay(Order order, PaymentVo paymentVo) throws Exception {
        return orderService.payByAlipay(order, paymentVo);
    }

    /**
     * 移动端支付宝支付
     */
    @PostMapping(value = "pay")
    public CommonResult pay(Order order, PaymentVo paymentVo) throws Exception {
        return orderService.pay(order, paymentVo);
    }

    /**
     * 判断是否缴费
     *
     * @param order
     * @return
     */
    @GetMapping("judgePay")
    public CommonResult judgePay(Order order) {
        return orderService.judgePay(order);
    }


    /**
     * 支付成功的回调
     */
    @PostMapping("/callback")
    public void fallback(HttpServletRequest request) {
        orderService.callback(request);
    }

    /**
     * 获取订单信息
     *
     * @param rentId
     * @return
     */
    @GetMapping("getOrderByRentId")
    public CommonResult getOrderByRentId(Integer rentId) {
        return orderService.getOrderByRentId(rentId);
    }

    /**
     * 获取全部订单
     *
     * @param currentPage
     * @param pageSize
     * @param houseName
     * @param time
     * @return
     */
    @GetMapping("/getAllOrder/{currentPage}/{pageSize}")
    public CommonResult getAllOrder(@PathVariable int currentPage, @PathVariable int pageSize, String houseName, String time) {
        IPage<OrderVO> page = orderService.getAllOrder(currentPage, pageSize, houseName, time);
        //如果当前页码大于了总页码值，那么重新执行查询操作，使用最大页码值作为当前页码值
        if (currentPage > page.getPages()) {
            page = orderService.getAllOrder((int) page.getPages(), pageSize, houseName, time);
        }
        return CommonResult.success(page);
    }

    /**
     * 获取用户账单信息
     *
     * @param userId
     * @return
     */
    @GetMapping("getUserOrder")
    public CommonResult getUserOrder(Integer userId) {
        return orderService.getUserOrder(userId);
    }

    /**
     * 获取用户账单信息详情
     *
     * @param orderId
     * @return
     */
    @GetMapping("getUserOrderDetail")
    public CommonResult getUserOrderDetail(Integer orderId) {
        return orderService.getUserOrderDetail(orderId);
    }
}

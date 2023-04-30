package niuniu.javaweb.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.alipay.easysdk.payment.wap.models.AlipayTradeWapPayResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import niuniu.javaweb.mapper.CostMapper;
import niuniu.javaweb.mapper.OrderMapper;
import niuniu.javaweb.pojo.Order;
import niuniu.javaweb.service.BasicService;
import niuniu.javaweb.service.OrderService;
import niuniu.javaweb.utils.DateUtil;
import niuniu.javaweb.utils.StringUtils;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.utils.tools.OrderUtil;
import niuniu.javaweb.vo.OrderVO;
import niuniu.javaweb.vo.PaymentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service.impl
 * @date 2023/4/9 15:37
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    CostMapper costMapper;

    @Autowired
    BasicService basicService;

    //支付成功后要跳转的页面
    @Value("${alipay.returnUrl}")
    private String returnUrl;

    @Value("${alipay.quitUrl}")
    private String quitUrl;

    /**
     * 生成订单
     *
     * @param order
     * @return
     */
    @Override
    public CommonResult generateOrder(Order order) throws ParseException {
        String misPrice = basicService.getBasicByRentId(order.getRentId(), DateUtil.getCurrentMonth());
        order.setTotal(order.getTotal().replaceAll(",", ""));
        order.setOutTradeNo(OrderUtil.getOrderNo());
        order.setMisPrice(misPrice);
        orderMapper.generateOrder(order);
        return CommonResult.success(order);
    }

    /**
     * 支付通过现金
     *
     * @param order
     * @return
     */
    @Override
    public CommonResult payByCash(Order order) {
        return orderMapper.updateOrderByCash(order) > 0 ? CommonResult.success() : CommonResult.failed();
    }

    /**
     * 支付通过支付宝
     *
     * @param order
     * @return
     */
    @Override
    public CommonResult payByAlipay(Order order, PaymentVo paymentVo) throws Exception {
        System.out.println(order);
        System.out.println(paymentVo);
        AlipayTradePagePayResponse pay = Factory.Payment.Page().pay(paymentVo.getSubject(), order.getOutTradeNo(), paymentVo.getTotal().toString(), returnUrl);
        orderMapper.updateOrder(order);
        return CommonResult.success(pay.body);
    }

    /**
     * 移动端支付宝支付
     *
     * @param order
     * @param paymentVo
     * @return
     * @throws Exception
     */
    @Override
    public CommonResult pay(Order order, PaymentVo paymentVo) throws Exception {
        System.out.println(order);
        System.out.println(paymentVo);
        AlipayTradeWapPayResponse pay = Factory.Payment
                //选择网页支付平台
                .Wap()
                //调用支付方法，设置订单名称、我们自己系统中的订单号、金额、回调页面
                .pay(paymentVo.getSubject(), order.getOutTradeNo(), paymentVo.getTotal().toString(), quitUrl + order.getOrderId(), quitUrl + order.getOrderId());
        orderMapper.updateOrder(order);
        return CommonResult.success(pay.body);
    }


    /**
     * 判断是否缴费
     *
     * @param order
     * @return
     */
    @Override
    public CommonResult judgePay(Order order) {
        return CommonResult.success(orderMapper.judgeOrder(order));
    }

    /**
     * 支付回调
     *
     * @param request
     */
    @Override
    public void callback(HttpServletRequest request) {
        System.out.println("支付成功回调！");
        int im = 0;
        im++;
        //测试请求次数
        System.out.println("次数:" + im);
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号
        String outTradeNo = request.getParameter("out_trade_no");
        System.out.println("商户订单号: " + outTradeNo);
        //支付宝交易号
        String tradeNo = request.getParameter("trade_no");
        System.out.println("支付宝交易号: " + tradeNo);
        //支付宝交易金额
        String totalAmount = request.getParameter("total_amount");
        System.out.println("支付宝交易金额: " + totalAmount);
        //交易状态
        String tradeStatus = request.getParameter("trade_status");
        System.out.println("交易状态: " + tradeStatus);
        System.out.println("params: " + JSONObject.toJSONString(params));
        if (tradeStatus.equals("TRADE_SUCCESS")) {
            orderMapper.insertTradeNo(outTradeNo, tradeNo);
        }

    }

    /**
     * 获取订单信息
     *
     * @param rentId
     * @return
     */
    @Override
    public CommonResult getOrderByRentId(Integer rentId) {
        return CommonResult.success(orderMapper.getOrderByRentId(rentId));
    }

    /**
     * 按需获取订单信息
     *
     * @param currentPage
     * @param pageSize
     * @param houseName
     * @param time
     * @return
     */
    @Override
    public IPage<OrderVO> getAllOrder(int currentPage, int pageSize, String houseName, String time) {
        Page<OrderVO> page = new Page<>(currentPage, pageSize);
        QueryWrapper<OrderVO> queryWrapper = new QueryWrapper<>();
        return orderMapper.getOrderPage(houseName, time, page, queryWrapper);
    }

    /**
     * 获取用户账单信息
     *
     * @param userId
     * @return
     */
    @Override
    public CommonResult getUserOrder(Integer userId) {
        List<Order> userOrder = orderMapper.getUserOrder(userId);
        if (userOrder.size() > 0) {
            for (Order order : userOrder) {
                if (order.getState().equals("0")) {
                    order.setState("待缴费");
                } else {
                    order.setState("已缴费");
                }
            }
        }
        return CommonResult.success(userOrder);
    }

    /**
     * 获取用户账单详情
     *
     * @param orderId
     * @return
     */
    @Override
    public CommonResult getUserOrderDetail(Integer orderId) {
        HashMap<String, Object> map = new HashMap<>();
        OrderVO userOrderDetail = orderMapper.getUserOrderDetail(orderId);
        map.put("house", userOrderDetail);
        //获取用户杂费信息
        if (!StringUtils.isEmpty(userOrderDetail.getMisPrice())) {
            CommonResult orderMis = basicService.getOrderMis(orderId);
            map.put("mis", orderMis.getData());
        }
        //获取用户水电信息
        if (userOrderDetail.getType().equals("租金")) {
            map.put("message", costMapper.getCostByRentId(userOrderDetail.getRentId(), null));
        }
        return CommonResult.success(map);
    }
}

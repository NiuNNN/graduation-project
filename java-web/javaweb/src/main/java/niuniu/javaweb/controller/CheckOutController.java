package niuniu.javaweb.controller;

import niuniu.javaweb.pojo.CheckOut;
import niuniu.javaweb.service.CheckOutService;
import niuniu.javaweb.utils.result.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.controller
 * @date 2023/4/19 18:24
 */
@RestController
public class CheckOutController {

    @Autowired
    CheckOutService checkOutService;

    /**
     * 申请退房
     *
     * @param userId
     * @param message
     * @return
     */
    @GetMapping("insertCheckOut")
    public CommonResult insertCheckOut(Integer userId, String message) {
        return checkOutService.insertCheckOut(userId, message);
    }

    /**
     * 获取用户申请退房状态
     *
     * @param userId
     * @return
     */
    @GetMapping("getCheckOutState")
    public CommonResult getCheckOutState(Integer userId) {
        return checkOutService.getCheckOutState(userId);
    }

    /**
     * 退房处理
     *
     * @param checkOut
     * @return
     */
    @GetMapping("deleteCheckOut")
    public CommonResult deleteCheckOut(CheckOut checkOut) throws ParseException {
        return checkOutService.deleteCheckOut(checkOut);
    }

    /**
     * 取消申请
     *
     * @param checkOut
     * @return
     */
    @GetMapping("cancelCheckOut")
    public CommonResult cancelCheckOut(CheckOut checkOut) {
        return checkOutService.cancelCheckOut(checkOut);
    }
}

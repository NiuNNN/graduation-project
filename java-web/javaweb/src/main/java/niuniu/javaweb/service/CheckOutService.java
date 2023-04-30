package niuniu.javaweb.service;

import niuniu.javaweb.pojo.CheckOut;
import niuniu.javaweb.utils.result.CommonResult;

import java.text.ParseException;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service
 * @date 2023/4/19 18:23
 */
public interface CheckOutService {
    /**
     * 申请退房
     *
     * @param userId
     * @param message
     * @return
     */
    CommonResult insertCheckOut(Integer userId, String message);

    /**
     * 获取申请退房状态
     *
     * @param userId
     * @return
     */
    CommonResult getCheckOutState(Integer userId);

    /**
     * 退房处理
     *
     * @param checkOut
     * @return
     */
    CommonResult deleteCheckOut(CheckOut checkOut) throws ParseException;

    /**
     * 取消申请
     *
     * @param checkOut
     * @return
     */
    CommonResult cancelCheckOut(CheckOut checkOut);
}

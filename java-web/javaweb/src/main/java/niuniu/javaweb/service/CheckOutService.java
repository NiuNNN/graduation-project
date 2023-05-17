package niuniu.javaweb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import niuniu.javaweb.pojo.CheckOut;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.vo.CheckOutVO;

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

    /**
     * 获取全部申请信息
     *
     * @param time
     * @param houseName
     * @param currentPage
     * @param pageSize
     * @return
     */
    IPage<CheckOutVO> getAllCheckOut(String time, String houseName, int currentPage, int pageSize);

    /**
     * 获取退房详细信息
     *
     * @param checkoutId
     * @return
     */
    CommonResult getCheckOutDetail(Integer checkoutId);

    /**
     * 押金退还提醒
     *
     * @param houseName
     * @return
     */
    CommonResult judgeDepositByHouseName(String houseName);

    /**
     * 退房处理
     *
     * @param houseName
     * @param userId
     * @return
     */
    CommonResult insertCheckOutByStaff(String houseName, Integer userId) throws ParseException;
}

package niuniu.javaweb.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import niuniu.javaweb.pojo.CheckOut;
import niuniu.javaweb.service.CheckOutService;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.vo.CheckOutVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    /**
     * 获取全部申请信息
     *
     * @param currentPage
     * @param pageSize
     * @param time
     * @param houseName
     * @return
     */
    @GetMapping("getAllCheckOut/{currentPage}/{pageSize}")
    public CommonResult getAllCheckOut(@PathVariable int currentPage, @PathVariable int pageSize, String time, String houseName) {
        IPage<CheckOutVO> page = checkOutService.getAllCheckOut(time, houseName, currentPage, pageSize);
        //如果当前页码大于了总页码值，那么重新执行查询操作，使用最大页码值作为当前页码值
        if (currentPage > page.getPages()) {
            page = checkOutService.getAllCheckOut(time, houseName, (int) page.getPages(), pageSize);
        }
        return CommonResult.success(page);
    }

    /**
     * 获取退房详细信息
     *
     * @param checkoutId
     * @return
     */
    @GetMapping("getCheckOutDetail")
    public CommonResult getCheckOutDetail(Integer checkoutId) {
        return checkOutService.getCheckOutDetail(checkoutId);
    }

    /**
     * 押金退还提醒
     *
     * @param houseName
     * @return
     */
    @GetMapping("judgeDepositByHouseName")
    public CommonResult judgeDepositByHouseName(String houseName) {
        return checkOutService.judgeDepositByHouseName(houseName);
    }

    /**
     * 退房申请
     *
     * @param houseName
     * @param userId
     * @return
     */
    @PostMapping("handleCheckOut")
    public CommonResult handleCheckOut(String houseName, Integer userId) throws ParseException {
        return checkOutService.insertCheckOutByStaff(houseName, userId);
    }
}

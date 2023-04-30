package niuniu.javaweb.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import niuniu.javaweb.pojo.Basic;
import niuniu.javaweb.service.BasicService;
import niuniu.javaweb.utils.result.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.controller
 * @date 2023/4/2 11:59
 */
@RestController
public class BasicController {
    @Autowired
    BasicService basicService;

    /**
     * 新建收费标准
     *
     * @param basic
     * @return
     */
    @PostMapping("/insertBasic")
    public CommonResult insertBasic(Basic basic) {
        return basicService.insertBasic(basic);
    }

    /**
     * 获取全部收费标准
     *
     * @param currentPage
     * @param pageSize
     * @return
     */

    @GetMapping("/getAllBasic/{currentPage}/{pageSize}")
    public CommonResult getAllBasic(@PathVariable int currentPage, @PathVariable int pageSize) {
        IPage<Basic> page = basicService.getAllBasic(currentPage, pageSize);
        //如果当前页码大于了总页码值，那么重新执行查询操作，使用最大页码值作为当前页码值
        if (currentPage > page.getPages()) {
            page = basicService.getAllBasic((int) page.getPages(), pageSize);
        }
        return CommonResult.success(page);
    }

    /**
     * 获取除了水电以外的杂费
     *
     * @return
     */
    @GetMapping("/getBasic")
    public CommonResult getBasic() {
        return basicService.getBasic();
    }

    /**
     * 获取用户杂费
     *
     * @param userId
     * @return
     */
    @GetMapping("/getBasicByUserId")
    public CommonResult getBasicByUserId(Integer userId) {
        return basicService.getBasicByUserId(userId);
    }

    /**
     * 改变杂费状态
     *
     * @param miscellaneousId
     * @param state
     * @return
     */
    @GetMapping("/changeMiscellaneousState")
    public CommonResult changeMiscellaneousState(Integer miscellaneousId, Integer state) {
        return basicService.changeMiscellaneousState(miscellaneousId, state);
    }

    /**
     * 获取未选择的杂费信息
     *
     * @param rentId
     * @return
     */
    @GetMapping("/getNoBasicByRentId")
    public CommonResult getNoBasicByRentId(Integer rentId) {
        return basicService.getNoBasicByRentId(rentId);
    }

    /**
     * 插入新的杂费信息
     *
     * @param rentId
     * @param baseId
     * @return
     */
    @GetMapping("/insertMiscellaneous")
    public CommonResult insertMiscellaneous(Integer rentId, Integer baseId) {
        return basicService.insertMiscellaneous(rentId, baseId);
    }

    /**
     * 删除基本杂费
     *
     * @param baseId
     * @return
     */
    @GetMapping("/deleteBasic")
    public CommonResult deleteBasic(Integer baseId) {
        return basicService.deleteBasic(baseId);
    }

    /**
     * 更新收费信息
     *
     * @param basic
     * @return
     */
    @PostMapping("/updateBasic")
    public CommonResult updateBasic(Basic basic) {
        return basicService.updateBasic(basic);
    }

    /**
     * 获取付款时对应的杂费
     *
     * @param orderId
     * @return
     */
    @GetMapping("/getOrderMis")
    public CommonResult getOrderMis(Integer orderId) {
        return basicService.getOrderMis(orderId);
    }
}

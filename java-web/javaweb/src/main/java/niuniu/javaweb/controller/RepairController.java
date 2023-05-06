package niuniu.javaweb.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import niuniu.javaweb.pojo.Repair;
import niuniu.javaweb.service.RepairService;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.vo.RepairVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.controller
 * @date 2023/4/25 16:13
 */
@RestController
public class RepairController {
    @Autowired
    RepairService repairService;

    /**
     * 插入维修记录
     *
     * @param repair
     * @return
     */
    @PostMapping("insertRepair")
    public CommonResult insertRepair(Repair repair) {
        return repairService.insertRepair(repair);
    }

    /**
     * 修改维修信息
     *
     * @param repair
     * @return
     */
    @GetMapping("changeRepair")
    public CommonResult changeRepair(Repair repair) {
        return repairService.changeRepair(repair);
    }

    /**
     * 删除维修信息
     *
     * @param repair
     * @return
     */
    @GetMapping("deleteRepair")
    public CommonResult deleteRepair(Repair repair) {
        return repairService.deleteRepair(repair);
    }

    /**
     * 获取全部维修信息
     *
     * @param currentPage
     * @param pageSize
     * @param houseName
     * @param report
     * @return
     */
    @GetMapping("getAllRepair/{currentPage}/{pageSize}")
    public CommonResult getAllRepair(@PathVariable int currentPage, @PathVariable int pageSize, String houseName, String report) {
        IPage<RepairVO> page = repairService.getAllRepair(currentPage, pageSize, houseName, report);
        //如果当前页码大于了总页码值，那么重新执行查询操作，使用最大页码值作为当前页码值
        if (currentPage > page.getPages()) {
            page = repairService.getAllRepair((int) page.getPages(), pageSize, houseName, report);
        }
        return CommonResult.success(page);
    }

    /**
     * 获取用户维修信息
     *
     * @param userId
     * @return
     */
    @GetMapping("getRepairByUserId")
    public CommonResult getRepairByUserId(Integer userId) {
        return repairService.getRepairByUserId(userId);
    }

    /**
     * 获取维修详细信息
     *
     * @param repairId
     * @return
     */
    @GetMapping("getRepairByRepairId")
    public CommonResult getRepairByRepairId(Integer repairId) {
        return repairService.getRepairByRepairId(repairId);
    }

    /**
     * 完成维修
     *
     * @param repair
     * @return
     */
    @GetMapping("completeRepair")
    public CommonResult completeRepair(Repair repair) {
        return repairService.completeRepair(repair);
    }
}

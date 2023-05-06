package niuniu.javaweb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import niuniu.javaweb.pojo.Repair;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.vo.RepairVO;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service
 * @date 2023/4/25 16:11
 */
public interface RepairService {
    /**
     * 插入维修记录
     *
     * @param repair
     * @return
     */
    CommonResult insertRepair(Repair repair);

    /**
     * 改变维修信息
     *
     * @param repair
     * @return
     */
    CommonResult changeRepair(Repair repair);

    /**
     * 删除维修信息
     *
     * @param repair
     * @return
     */
    CommonResult deleteRepair(Repair repair);

    /**
     * 获取全部维修信息
     *
     * @param currentPage
     * @param pageSize
     * @param houseName
     * @param report
     * @return
     */
    IPage<RepairVO> getAllRepair(int currentPage, int pageSize, String houseName, String report);

    /**
     * 获取用户维修信息
     *
     * @param userId
     * @return
     */
    CommonResult getRepairByUserId(Integer userId);

    /**
     * 获取维修详细信息
     *
     * @param repairId
     * @return
     */
    CommonResult getRepairByRepairId(Integer repairId);


    /**
     * 完成维修
     *
     * @param repair
     * @return
     */
    CommonResult completeRepair(Repair repair);
}

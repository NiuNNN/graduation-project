package niuniu.javaweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import niuniu.javaweb.mapper.RepairMapper;
import niuniu.javaweb.pojo.Repair;
import niuniu.javaweb.service.RepairService;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.vo.RepairVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service.impl
 * @date 2023/4/25 16:12
 */
@Service
public class RepairServiceImpl extends ServiceImpl<RepairMapper, Repair> implements RepairService {

    @Autowired
    RepairMapper repairMapper;

    /**
     * 插入维修记录
     *
     * @param repair
     * @return
     */
    @Override
    public CommonResult insertRepair(Repair repair) {
        return repairMapper.insertRepair(repair) > 0 ? CommonResult.success() : CommonResult.failed();
    }

    /**
     * 改变维修信息
     *
     * @param repair
     * @return
     */
    @Override
    public CommonResult changeRepair(Repair repair) {
        return repairMapper.changeRepair(repair) > 0 ? CommonResult.success() : CommonResult.failed();
    }

    /**
     * 删除维修信息
     *
     * @param repair
     * @return
     */
    @Override
    public CommonResult deleteRepair(Repair repair) {
        if (repair.getState().equals("待处理")) {
            repairMapper.deleteById(repair);
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 获取全部维修信息
     *
     * @param currentPage
     * @param pageSize
     * @param houseId
     * @return
     */
    @Override
    public IPage<RepairVO> getAllRepair(int currentPage, int pageSize, Integer houseId) {
        Page<RepairVO> page = new Page<>(currentPage, pageSize);
        QueryWrapper<RepairVO> queryWrapper = new QueryWrapper<>();
        return repairMapper.getAllRepair(houseId, page, queryWrapper);
    }

    /**
     * 获取用户维修信息
     *
     * @param userId
     * @return
     */
    @Override
    public CommonResult getRepairByUserId(Integer userId) {
        List<Repair> repairList = repairMapper.getRepairByUserId(userId);
        for (Repair repair : repairList) {
            if (repair.getState().equals("0")) {
                repair.setState("待处理");
            } else {
                repair.setState("已维修");
            }
        }
        return CommonResult.success(repairList);
    }
}

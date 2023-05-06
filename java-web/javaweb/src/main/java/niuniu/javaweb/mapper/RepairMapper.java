package niuniu.javaweb.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import niuniu.javaweb.pojo.Repair;
import niuniu.javaweb.vo.RepairVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.mapper
 * @date 2023/4/25 16:11
 */
@Mapper
public interface RepairMapper extends BaseMapper<Repair> {

    /**
     * 插入维修记录
     *
     * @param repair
     * @return
     */
    int insertRepair(Repair repair);

    /**
     * 修改维修信息
     *
     * @param repair
     * @return
     */
    int changeRepair(Repair repair);

    /**
     * 获取全部维修信息
     *
     * @param houseName
     * @param report
     * @param page
     * @param wrapper
     * @return
     */
    IPage<RepairVO> getAllRepair(@Param("houseName") String houseName, @Param("report") String report, @Param("page") Page<RepairVO> page, @Param(Constants.WRAPPER) Wrapper<RepairVO> wrapper);

    /**
     * 获取用户维修信息
     *
     * @param userId
     * @return
     */
    List<Repair> getRepairByUserId(Integer userId);

    /**
     * 获取维系信息
     *
     * @param repairId
     * @return
     */
    RepairVO getRepairByRepairId(Integer repairId);

    /**
     * 完成维修
     *
     * @param repair
     * @return
     */
    int completeRepair(Repair repair);
}

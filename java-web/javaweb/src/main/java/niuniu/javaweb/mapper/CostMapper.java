package niuniu.javaweb.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import niuniu.javaweb.pojo.Cost;
import niuniu.javaweb.vo.CostVo;
import niuniu.javaweb.vo.ElectricInExcelVO;
import niuniu.javaweb.vo.WaterInExcelVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.mapper
 * @date 2023/4/13 23:17
 */
@Mapper
public interface CostMapper extends BaseMapper<Cost> {

    /**
     * 获取该月的用电账单
     *
     * @return
     */
    List<ElectricInExcelVO> getMonthElectric();

    /**
     * 获取该月的水费账单
     *
     * @return
     */
    List<WaterInExcelVO> getMonthWater();

    /**
     * 插入该月新的用量信息
     *
     * @param rentId
     * @return
     */
    int insertNewCost(Integer rentId);

    /**
     * 获取该月没有的用量订单编号
     *
     * @return
     */
    List<CostVo> noCostMonth();

    /**
     * 插入用电信息
     *
     * @param electricInExcelVO
     * @return
     */
    int updateElectric(ElectricInExcelVO electricInExcelVO);

    /**
     * 插入用水信息
     *
     * @param waterInExcelVO
     * @return
     */
    int updateWater(WaterInExcelVO waterInExcelVO);

    /**
     * 按需查询用电情况
     *
     * @param houseName
     * @param date
     * @param page
     * @param wrapper
     * @return
     */
    IPage<CostVo> getAllCost(@Param("houseName") String houseName, @Param("date") String date, @Param("page") Page<CostVo> page, @Param(Constants.WRAPPER) Wrapper<CostVo> wrapper);


    /**
     * 更新新的用电信息
     *
     * @param cost
     * @return
     */
    int updateElectricByCostId(Cost cost);

    /**
     * 更新用水信息
     *
     * @param cost
     * @return
     */
    int updateWaterByCostId(Cost cost);

    /**
     * 完善cost
     *
     * @param costVo
     * @return
     */
    int updateByCostVO(CostVo costVo);


    /**
     * 判断是否有该月信息
     *
     * @param time
     * @return
     */
    int judgeHasCost(String time);

    /**
     * 判断该月是否有信息为空
     *
     * @param time
     * @return
     */
    int judgeHasNum(String time);

    /**
     * 获取账单信息
     *
     * @param time
     * @return
     */
    List<CostVo> getCostVOByTime(String time);

    /**
     * @param rentId
     * @param date
     * @return
     */
    Cost getCostByRentId(Integer rentId, String date);

}

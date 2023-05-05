package niuniu.javaweb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import niuniu.javaweb.pojo.Cost;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.vo.CostVo;
import niuniu.javaweb.vo.ElectricInExcelVO;
import niuniu.javaweb.vo.WaterInExcelVO;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service
 * @date 2023/4/13 23:16
 */
public interface CostService {
    /**
     * 获取该月的用电excel模板
     *
     * @return
     */
    void sheetElectricExcel();

    /**
     * 获取该月的用水excel模板
     */

    void sheetWaterExcel();

    /**
     * 上传文件 用电表
     *
     * @param excel
     * @return
     */
    CommonResult getElectricExcel(MultipartFile excel);

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    CommonResult uploadFile(MultipartFile file);

    /**
     * 判断excel
     *
     * @param electricInExcelVOS
     * @return
     */
    CommonResult judgeElectric(List<ElectricInExcelVO> electricInExcelVOS);

    /**
     * 替换源电费数据
     *
     * @param electricInExcelVOS
     * @return
     */
    CommonResult toggleElectric(List<ElectricInExcelVO> electricInExcelVOS);

    /**
     * 按需查询使用信息
     *
     * @param currentPage
     * @param pageSize
     * @param costVoO
     * @return
     */
    IPage<CostVo> getAllCost(int currentPage, int pageSize, CostVo costVoO);

    /**
     * 更新用电信息
     *
     * @param cost
     * @return
     */
    CommonResult updateElectricByCostId(Cost cost);

    /**
     * 上传水费
     *
     * @param excel
     * @return
     */
    CommonResult getWaterExcel(MultipartFile excel);

    /**
     * 存储水费
     *
     * @param waterInExcelVOS
     * @return
     */
    CommonResult judgeWater(List<WaterInExcelVO> waterInExcelVOS);

    /**
     * 替换源水费数据
     *
     * @param waterInExcelVOS
     * @return
     */
    CommonResult toggleWater(List<WaterInExcelVO> waterInExcelVOS);

    /**
     * 更新用水信息
     *
     * @param cost
     * @return
     */
    CommonResult updateWaterByCostId(Cost cost);

    /**
     * 删除帐单
     *
     * @param costId
     * @return
     */
    CommonResult deleteCost(Integer costId);

    /**
     * 生成单一用户报表
     *
     * @param costVo
     */
    void generatePersonCost(CostVo costVo) throws ParseException;

    /**
     * 按需生成全部账单
     *
     * @param time
     */
    void generateALlCost(String time) throws ParseException;

    /**
     * 判断是否满足条件一键生成
     *
     * @param time
     * @return
     */
    CommonResult judgeCost(String time);

    /**
     * 获得水电信息
     *
     * @param rentId
     * @param time
     * @return
     */
    CommonResult getCostByRentId(Integer rentId, String time);

    /**
     * 获取用户图表
     *
     * @param userId
     * @return
     */
    CommonResult getUserChart(Integer userId);
}

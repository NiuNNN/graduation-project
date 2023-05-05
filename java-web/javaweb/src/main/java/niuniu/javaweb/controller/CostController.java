package niuniu.javaweb.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import niuniu.javaweb.pojo.Cost;
import niuniu.javaweb.service.CostService;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.vo.CostVo;
import niuniu.javaweb.vo.ElectricInExcelVO;
import niuniu.javaweb.vo.WaterInExcelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.controller
 * @date 2023/4/13 23:19
 */
@RestController
public class CostController {
    @Autowired
    CostService costService;

    /**
     * 获取该月用电excel模板
     */
    @PostMapping("sheetElectricExcel")
    public void sheetElectricExcel() {
        try {
            costService.sheetElectricExcel();
        } catch (Exception e) {
            throw new RuntimeException("文件下载失败");
        }
    }

    /**
     * 获取该月的用水excel模板
     */
    @PostMapping("sheetWaterExcel")
    public void sheetWaterExcel() {
        try {
            costService.sheetWaterExcel();
        } catch (Exception e) {
            throw new RuntimeException("文件下载失败");
        }
    }

    /**
     * 上传电费
     *
     * @param excel
     * @return
     */
    @PostMapping("/getElectricExcel")
    public CommonResult getElectricExcel(@RequestParam("file") MultipartFile excel) {
        return costService.getElectricExcel(excel);
    }

    /**
     * 上传水费
     *
     * @param excel
     * @return
     */
    @PostMapping("/getWaterExcel")
    public CommonResult getWaterExcel(@RequestParam("file") MultipartFile excel) {
        return costService.getWaterExcel(excel);
    }

    /**
     * 存储电费
     *
     * @param listStr
     * @return
     */
    @PostMapping("/judgeElectric")
    public CommonResult judgeElectric(String listStr) {
        List<ElectricInExcelVO> electricInExcelVOS = JSON.parseArray(listStr, ElectricInExcelVO.class);
        return costService.judgeElectric(electricInExcelVOS);
    }

    /**
     * 存储电费
     *
     * @param listStr
     * @return
     */
    @PostMapping("/judgeWater")
    public CommonResult judgeWater(String listStr) {
        List<WaterInExcelVO> waterInExcelVOS = JSON.parseArray(listStr, WaterInExcelVO.class);
        return costService.judgeWater(waterInExcelVOS);
    }

    /**
     * 替换源电费数据
     *
     * @param listStr
     * @return
     */
    @PostMapping("/toggleElectric")
    public CommonResult toggleElectric(String listStr) {
        List<ElectricInExcelVO> electricInExcelVOS = JSON.parseArray(listStr, ElectricInExcelVO.class);
        return costService.toggleElectric(electricInExcelVOS);
    }

    /**
     * 替换源水费数据
     *
     * @param listStr
     * @return
     */
    @PostMapping("/toggleWater")
    public CommonResult toggleWater(String listStr) {
        List<WaterInExcelVO> waterInExcelVOS = JSON.parseArray(listStr, WaterInExcelVO.class);
        return costService.toggleWater(waterInExcelVOS);
    }

    /**
     * 按需获取使用信息
     *
     * @param currentPage
     * @param pageSize
     * @param costVo
     * @return
     */
    @GetMapping("/getAllCost/{currentPage}/{pageSize}")
    public CommonResult getAllCost(@PathVariable int currentPage, @PathVariable int pageSize, CostVo costVo) {
        IPage<CostVo> page = costService.getAllCost(currentPage, pageSize, costVo);
        //如果当前页码大于了总页码值，那么重新执行查询操作，使用最大页码值作为当前页码值
        if (currentPage > page.getPages()) {
            page = costService.getAllCost((int) page.getPages(), pageSize, costVo);
        }
        return CommonResult.success(page);
    }


    /**
     * 更新用电信息
     *
     * @param cost
     * @return
     */
    @GetMapping("updateElectricByCostId")
    public CommonResult updateElectricByCostId(Cost cost) {
        return costService.updateElectricByCostId(cost);
    }

    /**
     * 更新用水信息
     *
     * @param cost
     * @return
     */
    @GetMapping("updateWaterByCostId")
    public CommonResult updateWaterByCostId(Cost cost) {
        return costService.updateWaterByCostId(cost);
    }

    /**
     * 删除账单
     *
     * @param costId
     * @return
     */
    @GetMapping("deleteCost")
    public CommonResult deleteCost(Integer costId) {
        return costService.deleteCost(costId);
    }

    /**
     * 导出单个的excel
     *
     * @param costVo
     * @return
     * @throws ParseException
     */
    @PostMapping("generatePersonCost")
    public void generatePersonCost(CostVo costVo) throws ParseException {
//        System.out.println(costVo);
        costService.generatePersonCost(costVo);
    }

    /**
     * 生成全部账单
     *
     * @param time
     */
    @PostMapping("generateAllCost")
    public void generateAllCost(String time) throws ParseException {
        costService.generateALlCost(time);
    }

    /**
     * 判断是否满足条件一键生成
     *
     * @param time
     * @return
     */
    @GetMapping("judgeCost")
    public CommonResult judgeCost(String time) {
        return costService.judgeCost(time);
    }

    /**
     * 获取水电信息
     *
     * @param rentId
     * @param time
     * @return
     */
    @GetMapping("getCostByRentId")
    public CommonResult getCostByRentId(Integer rentId, String time) {
        return costService.getCostByRentId(rentId, time);
    }

    /**
     * 获取用户图表
     *
     * @param userId
     * @return
     */
    @GetMapping("getUserChart")
    public CommonResult getUserChart(Integer userId) {
        return costService.getUserChart(userId);
    }
}

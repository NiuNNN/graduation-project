package niuniu.javaweb.controller;

import niuniu.javaweb.service.FinancialService;
import niuniu.javaweb.utils.result.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.controller
 * @date 2023/5/26 21:44
 */
@RestController
public class FinancialController {
    @Autowired
    FinancialService financialService;

    /**
     * 获取财务图表信息
     *
     * @return
     */
    @GetMapping("getFinancialChart")
    public CommonResult getFinancialChart() throws ParseException {
        return financialService.getFinancialChart();
    }

    /**
     * 获取全部财务信息
     *
     * @param date
     * @return
     */
    @GetMapping("getAllFinancial")
    public CommonResult getAllFinancial(String date) {
        return financialService.getAllFinancial(date);
    }

    /**
     * 导出财务execl
     *
     * @param list
     */
    @PostMapping("getFinancialExcel")
    public void getFinancialExcel(String list) {
        financialService.getFinancialExcel(list);
    }

    /**
     * 获取财务详情信息
     *
     * @param date
     * @return
     */
    @GetMapping("getFinancialDetail")
    public CommonResult getFinancialDetail(String date) {
        return financialService.getFinancialDetail(date);
    }

    /**
     * 判断是否给予生成报表
     *
     * @param date
     * @return
     */
    @GetMapping("judgeGenerateFinancial")
    public CommonResult judgeGenerateFinancial(String date) {
        return financialService.judgeGenerateFinancial(date);
    }

    /**
     * 生成财务信息
     *
     * @param date
     * @param water
     * @param electric
     * @return
     */
    @PostMapping("generateFinancial")
    public CommonResult generateFinancial(String date, String water, String electric) {
        return financialService.generateFinancial(date, water, electric);
    }
}

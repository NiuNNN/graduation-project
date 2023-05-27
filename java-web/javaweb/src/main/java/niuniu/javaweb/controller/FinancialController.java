package niuniu.javaweb.controller;

import niuniu.javaweb.service.FinancialService;
import niuniu.javaweb.utils.result.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}

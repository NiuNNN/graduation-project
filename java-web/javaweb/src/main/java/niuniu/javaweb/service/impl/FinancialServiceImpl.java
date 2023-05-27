package niuniu.javaweb.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import niuniu.javaweb.mapper.BasicMapper;
import niuniu.javaweb.mapper.FinancialMapper;
import niuniu.javaweb.pojo.Financial;
import niuniu.javaweb.service.FinancialService;
import niuniu.javaweb.utils.DateUtil;
import niuniu.javaweb.utils.excel.ExcelUtil;
import niuniu.javaweb.utils.result.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service.impl
 * @date 2023/4/17 9:24
 */
@Service
public class FinancialServiceImpl extends ServiceImpl<FinancialMapper, Financial> implements FinancialService {
    @Autowired
    FinancialMapper financialMapper;

    @Autowired
    BasicMapper basicMapper;

    /**
     * 判断是否有该月信息
     *
     * @param date
     * @return
     */
    @Override
    public CommonResult judgeFinancialByDate(String date) {
        System.out.println("进入到judgeFinancialByDate");
        System.out.println("【date】" + date);
        return financialMapper.judgeFinancialByDate(date, "") > 0 ? CommonResult.success() : CommonResult.failed();
    }

    /**
     * 更新房屋财务数据
     *
     * @param financial
     * @return
     */
    @Override
    @CacheEvict(cacheNames = "getFinancialChart", beforeInvocation = true)
    public CommonResult updateHouseFinancial(Financial financial) {
        System.out.println("进入到updateHouseFinancial");
        CommonResult commonResult = judgeFinancialByDate(financial.getDate());
        System.out.println("【commonResult.getCode()】" + commonResult.getCode());
        System.out.println("【commonResult.getCode() == 500】" + (commonResult.getCode() == 500));
        if (commonResult.getCode() == 500) {
            System.out.println("进入创建");
            financialMapper.insertFinancial();
            System.out.println("创建成功");
        }
        return financialMapper.updateFinancial(financial) > 0 ? CommonResult.success() : CommonResult.failed();
    }


    /**
     * 获取财务图表信息
     *
     * @return
     */
    @Override
    @Cacheable(cacheNames = "getFinancialChart")
    public CommonResult getFinancialChart() throws ParseException {
        String[][] strings = new String[9][13];
        strings[0] = new String[]{"Financial", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        String target[] = new String[]{"支出水费", "支出电费", "支出薪水", "支出杂费", "收入水费", "收入电费", "收入杂费", "收入租金"};
        String outWater[] = new String[12];
        String outElectric[] = new String[12];
        String outSalary[] = new String[12];
        String outMis[] = new String[12];
        String inWater[] = new String[12];
        String inElectric[] = new String[12];
        String inMis[] = new String[12];
        String inRent[] = new String[12];

        List<Financial> financialChart = financialMapper.getFinancialChart();
        if (financialChart.size() == 12) {
            for (int i = 0; i < financialChart.size(); i++) {
                outWater[i] = financialChart.get(i).getOutWater();
                outElectric[i] = financialChart.get(i).getOutElectric();
                outSalary[i] = financialChart.get(i).getOutSalary();
                outMis[i] = financialChart.get(i).getOutMis();
                inWater[i] = financialChart.get(i).getInWater();
                inMis[i] = financialChart.get(i).getInMis();
                inRent[i] = financialChart.get(i).getInRent();
            }
        } else {
            Financial financial = new Financial();
            financial.setOutWater("0");
            financial.setOutElectric("0");
            financial.setOutSalary("0");
            financial.setOutMis("0");
            financial.setInWater("0");
            financial.setInElectric("0");
            financial.setInMis("0");
            financial.setInRent("0");
            for (int i = 0; i < 12; i++) {
                Financial financial1 = financial;
                for (int j = 0; j < financialChart.size(); j++) {
                    if (DateUtil.compareMonth1(financialChart.get(j).getDate(), DateUtil.getYear() + "-" + DateUtil.getMonth(i))) {
                        financial1 = financialChart.get(j);
                    }

                }
                outWater[i] = financial1.getOutWater();
                outElectric[i] = financial1.getOutElectric();
                outSalary[i] = financial1.getOutSalary();
                outMis[i] = financial1.getOutMis();
                inWater[i] = financial1.getInWater();
                inElectric[i] = financial1.getInElectric();
                inMis[i] = financial1.getInMis();
                inRent[i] = financial1.getInRent();
            }
        }

        strings[1][0] = "支出水费";
        strings[2][0] = "支出电费";
        strings[3][0] = "支出薪水";
        strings[4][0] = "支出杂费";
        strings[5][0] = "收入水费";
        strings[6][0] = "收入电费";
        strings[7][0] = "收入杂费";
        strings[8][0] = "收入租金";

        for (int i = 1; i < 13; i++) {
            strings[1][i] = outWater[i - 1];
            strings[2][i] = outElectric[i - 1];
            strings[3][i] = outSalary[i - 1];
            strings[4][i] = outMis[i - 1];
            strings[5][i] = inWater[i - 1];
            strings[6][i] = inElectric[i - 1];
            strings[7][i] = inMis[i - 1];
            strings[8][i] = inRent[i - 1];
        }
/**
 * ['Financial', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
 *             ['支出水费', '1000', '2000', 1000, 1000, 1000, 1000],
 *             ['支出电费', '5000', 5000, 5000, 5000, 5000, 5000],
 *             ['支出薪水', '6000', 6000, 6000, 6000, 6000, 6000],
 *             ['支出杂费', 200, 200, 200, 200, 200, 200],
 *             ['收入水费', 3000, 3000, 3000, 3000, 3000, 3000],
 *             ['收入电费', 10000, 10000, 10000, 10000, 10000, 10000],
 *             ['收入杂费', 6000, 6000, 6000, 6000, 6000, 6000],
 *             ['收入租金', 12000, 12000, 12000, 12000, 12000, 12000]
 */
        return CommonResult.success(strings);
    }

    /**
     * 获取全部财务信息
     *
     * @param date
     * @return
     */
    @Override
    public CommonResult getAllFinancial(String date) {
        return CommonResult.success(financialMapper.getAllFinancial(date));
    }

    /**
     * 导出excel表
     *
     * @param list
     */
    @Override
    public void getFinancialExcel(String list) {
        List<Financial> financials = JSON.parseArray(list, Financial.class);
        ExcelUtil.excelLockExport(Financial.class, "财务账单", financials, "财务账单");
    }
}

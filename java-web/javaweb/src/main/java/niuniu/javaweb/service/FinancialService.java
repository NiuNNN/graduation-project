package niuniu.javaweb.service;

import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.vo.FinancialVO;

import java.text.ParseException;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service
 * @date 2023/4/17 9:24
 */
public interface FinancialService {
    /**
     * 判断是否有该月信息
     *
     * @param date
     * @return
     */
    CommonResult judgeFinancialByDate(String date);

    /**
     * 更新房屋财务数据
     *
     * @param financial
     * @return
     */
    CommonResult updateHouseFinancial(FinancialVO financial);

    /**
     * 获取财务图表信息
     *
     * @return
     */
    CommonResult getFinancialChart() throws ParseException;

    /**
     * 获取全部财务信息
     *
     * @param date
     * @return
     */
    CommonResult getAllFinancial(String date);


    /**
     * 导出excel表
     *
     * @param list
     */
    void getFinancialExcel(String list);

    /**
     * 返回财务信息
     *
     * @param date
     * @return
     */
    CommonResult getFinancialDetail(String date);

    /**
     * 判断是否给予生成报表
     *
     * @param date
     * @return
     */
    CommonResult judgeGenerateFinancial(String date);

    /**
     * 生成财务信息
     *
     * @param date
     * @param water
     * @param electric
     * @param userId
     * @return
     */
    CommonResult generateFinancial(String date, String water, String electric, Integer userId);
}

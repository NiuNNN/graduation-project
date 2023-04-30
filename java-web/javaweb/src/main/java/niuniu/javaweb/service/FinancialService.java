package niuniu.javaweb.service;

import niuniu.javaweb.pojo.Financial;
import niuniu.javaweb.utils.result.CommonResult;

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
    CommonResult updateHouseFinancial(Financial financial);
}

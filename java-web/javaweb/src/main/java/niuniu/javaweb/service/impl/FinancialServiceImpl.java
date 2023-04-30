package niuniu.javaweb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import niuniu.javaweb.mapper.BasicMapper;
import niuniu.javaweb.mapper.FinancialMapper;
import niuniu.javaweb.pojo.Financial;
import niuniu.javaweb.service.FinancialService;
import niuniu.javaweb.utils.result.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

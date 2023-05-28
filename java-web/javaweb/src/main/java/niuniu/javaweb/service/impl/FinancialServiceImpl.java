package niuniu.javaweb.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import niuniu.javaweb.mapper.*;
import niuniu.javaweb.pojo.*;
import niuniu.javaweb.service.FinancialService;
import niuniu.javaweb.utils.DateUtil;
import niuniu.javaweb.utils.excel.ExcelUtil;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.vo.CostVo;
import niuniu.javaweb.vo.FinancialVO;
import niuniu.javaweb.vo.StaffPayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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

    @Autowired
    CostMapper costMapper;

    @Autowired
    HouseMapper houseMapper;

    @Autowired
    StaffPayMapper staffPayMapper;

    @Autowired
    RepairMapper repairMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    RentMapper rentMapper;

    @Autowired
    UserMapper userMapper;

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
    @CacheEvict(cacheNames = "getFinancialChart", allEntries = true)
    public CommonResult updateHouseFinancial(FinancialVO financial) {
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
        List<FinancialVO> allFinancial = financialMapper.getAllFinancial(date);
        for (FinancialVO financialVO : allFinancial) {
            if (financialVO.getState() == 0) {
                financialVO.setStatus("待审核");
            } else {
                String[] split = financialVO.getRemark().split(",");
                financialVO.setName(split[0]);
                financialVO.setTime(split[1]);
                financialVO.setStatus("已审核");
            }
        }
        return CommonResult.success(allFinancial);
    }

    /**
     * 导出excel表
     *
     * @param list
     */
    @Override
    public void getFinancialExcel(String list) {
        List<FinancialVO> financials = JSON.parseArray(list, FinancialVO.class);
        ExcelUtil.excelLockExport(FinancialVO.class, "财务账单", financials, "财务账单");
    }

    /**
     * 返回财务信息
     *
     * @param date
     * @return
     */
    @Override
    public CommonResult getFinancialDetail(String date) {
        //房间收入
        List<CostVo> costVos = costMapper.getCostVOByTime(date);
        List<CostVo> costVoList = new ArrayList<>();
        HashMap<String, List<Object>> hashMap = new HashMap<>();
//        System.out.println(costVos.size());
        for (CostVo costVo : costVos) {
            CostVo costVOByCostId = costMapper.getCostVOByCostId(costVo);
            costVOByCostId.setHousePrice(houseMapper.getHousePriceByCostId(costVo.getCostId()).replaceAll(",", ""));
            costVoList.add(costVOByCostId);
        }
        //薪水支出
        List<StaffPayVO> staffPays = staffPayMapper.selectSalaryPayByTime(date);
        //其他支出
        //维修支出
        List<Repair> repairList = repairMapper.selectRepairPrice(date);
        //退款押金
        List<Order> orders = orderMapper.selectCheckOut(date);
        List<Order> orderList = new ArrayList<>();
        if (repairList.size() > 0) {
            Order order = new Order();
            for (Repair repair : repairList) {
                order.setType("维修");
                order.setTotal(repair.getPrice());
                order.setTime(repair.getFix());
            }
            orderList.add(order);
        }
        if (orders.size() > 0) {
            for (Order order : orders) {
                orderList.add(order);
            }
        }
        hashMap.put("houseTable", Collections.singletonList(costVoList));
        hashMap.put("salaryTable", Collections.singletonList(staffPays));
        hashMap.put("misTable", Collections.singletonList(orderList));
        return CommonResult.success(hashMap);
    }

    /**
     * 判断是否给予生成报表
     *
     * @param date
     * @return
     */
    @Override
    public CommonResult judgeGenerateFinancial(String date) {
        //先判断是否有未录入的信息
        if (costMapper.judgeHasCost(date) < rentMapper.selectRentCount())
            return CommonResult.failed("存在未录入的房间信息，请上传信息后报表...");
            //判断是否有未缴费的住户
        else if (costMapper.judgeHasNum(date) > 0) {
            return CommonResult.failed("存在未处理的房间信息，请完善信息后报表...");
        } else if (orderMapper.selectOrderCount(date) > 0) return CommonResult.failed("存在未缴费用户，请督促后报表...");
        else {
            return CommonResult.success();
        }
    }

    /**
     * 生成财务信息
     *
     * @param date
     * @param water
     * @param electric
     * @param userId
     * @return
     */
    @Override
    @CacheEvict(cacheNames = "getFinancialChart", allEntries = true)
    public CommonResult generateFinancial(String date, String water, String electric, Integer userId) {
        Float outSalary = 0.0f;
        Float outMis = 0.0f;
        //获取该月财务信息 进行计算
        FinancialVO financial = financialMapper.getAllFinancial(date).get(0);

        //获取水电支出信息
        Cost sumCost = costMapper.getSumCost(date);
        Float outWater = Float.valueOf(sumCost.getNumWater()) * Float.valueOf(water);
        Float outElectric = Float.valueOf(sumCost.getNumElectric()) * Float.valueOf(electric);

        //盈利
        Float proWater = Float.valueOf(financial.getInWater()) - outWater;
        Float proElectric = Float.valueOf(financial.getInElectric()) - outElectric;

        financial.setOutWater(String.valueOf(outWater));
        financial.setOutElectric(String.valueOf(outElectric));
        financial.setProWater(String.valueOf(proWater));
        financial.setProElectric(String.valueOf(proElectric));

        //获取薪水信息
        List<StaffPayVO> staffPays = staffPayMapper.selectSalaryPayByTime(date);
        for (StaffPayVO staffPayVO : staffPays) {
            outSalary += Float.valueOf(staffPayVO.getPrice());
        }
        financial.setOutSalary(String.valueOf(outSalary));

        //获取退回押金信息
        List<Order> orders = orderMapper.selectCheckOut(date);
        for (Order order : orders) {
            outMis += Float.valueOf(order.getTotal());
        }
        //获取维修信息
        List<Repair> repairList = repairMapper.selectRepairPrice(date);
        for (Repair repair : repairList) {
            outMis += Float.valueOf(repair.getPrice());
        }
        financial.setOutMis(String.valueOf(outMis));

        User user = userMapper.selectById(userId);

        financial.setRemark(user.getName() + "," + DateUtil.getNowTime());
        Float realTotal = outWater + outElectric + outSalary + outMis;
        Float finalTotal = Float.valueOf(financial.getAdvanceTotal()) - realTotal;
        financial.setRealTotal(String.valueOf(realTotal));
        financial.setFinalTotal(String.valueOf(finalTotal));
        financial.setState(1);
        System.out.println(financial);
        return financialMapper.updateFinancial(financial) > 0 ? CommonResult.success() : CommonResult.failed();
    }
}

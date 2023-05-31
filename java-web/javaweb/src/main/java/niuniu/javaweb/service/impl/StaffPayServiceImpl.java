package niuniu.javaweb.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import niuniu.javaweb.mapper.SalaryMapper;
import niuniu.javaweb.mapper.StaffPayMapper;
import niuniu.javaweb.mapper.UserMapper;
import niuniu.javaweb.pojo.Salary;
import niuniu.javaweb.pojo.StaffPay;
import niuniu.javaweb.service.StaffPayService;
import niuniu.javaweb.utils.DateUtil;
import niuniu.javaweb.utils.excel.ExcelUtil;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.utils.tools.OrderUtil;
import niuniu.javaweb.vo.RoleVO;
import niuniu.javaweb.vo.StaffPayVO;
import niuniu.javaweb.vo.StaffVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service.impl
 * @date 2023/5/24 10:00
 */
@Service
public class StaffPayServiceImpl extends ServiceImpl<StaffPayMapper, StaffPay> implements StaffPayService {

    @Autowired
    StaffPayMapper staffPayMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    SalaryMapper salaryMapper;

    /**
     * 员工离职
     *
     * @param userId
     */
    @Override
    @Transactional
    public CommonResult leaveSalary(Integer userId) throws ParseException {
        changeSalaryState();
        return CommonResult.success(generateSalary(userId, null));
    }

    /**
     * 生成员工薪水
     *
     * @param userId
     * @param nowTime
     * @return
     * @throws ParseException
     */
    @Transactional
    public StaffPayVO generateSalary(Integer userId, String nowTime) throws ParseException {
        /**
         * 判断staff表中是否存在该员工薪水信息
         */
        StaffPay has = staffPayMapper.selectHasSalary(userId);
//        System.out.println(staffPay);
        StaffVO user = userMapper.getStaff(userId).get(0);
        String time = "";
        if (has != null) {
            time = has.getTime();
        } else {
            time = user.getAddtime();
        }
        Long differ = DateUtil.differDay(time);

        Float basePrice = 0.0f;

        Float elsePrice = 0.0f;

        String remark = "";

        StaffPayVO staffPayVO = new StaffPayVO();


        /**
         * 判断相差天数
         * 如果>=1 则给予发放工资
         */
        if (differ >= 1) {
            /**
             * 判断是否是实习期
             */
            String[] mon = DateUtil.getMon(user.getAddtime(), 6);
            String targetTime = mon[0] + "-" + mon[1] + "-" + mon[2];
            List<Salary> userSalary = salaryMapper.getUserSalary(userId);
            System.out.println("differ" + differ);
            int month = (int) Math.ceil(differ / 30);//向上取整
            int day = (int) (differ - month * 30);
            if (DateUtil.differDay(targetTime) >= 0) {
                System.out.println("正式工资");
                basePrice = getActualPrice(userSalary.get(0)) * month + (getActualPrice(userSalary.get(0)) / 30) * day;
            } else {
                System.out.println("实习工资");
                basePrice = getActualPrice(userSalary.get(1)) * month + (getActualPrice(userSalary.get(1)) / 30) * day;
            }
            if (differ >= 30) {
                /**
                 * 添加其他薪水
                 */
                int i = 0;
                for (Salary salary : userSalary) {
                    if (salary.getState() == 3) {
                        if (i != 0) {
                            remark += "_";
                        }
                        Float actualPrice = getActualPrice(salary);
                        elsePrice += actualPrice * month;
                        remark += salary.getSalaryId() + "_" + actualPrice;
                        i++;
                    }
                }
            }
//            System.out.println("basePrice:" + basePrice);
//            System.out.println("elsePrice:" + elsePrice);
//            System.out.println("price:" + (basePrice + elsePrice));
            /**
             * 存入数据库
             */
            staffPayVO.setUserId(userId);
            staffPayVO.setOutTradeNo(OrderUtil.getOrderNo());
            staffPayVO.setPrice(String.valueOf(basePrice + elsePrice));
            staffPayVO.setBasePrice(String.valueOf(basePrice));
            staffPayVO.setElsePrice(String.valueOf(elsePrice));
            staffPayVO.setRemark(remark);
            staffPayVO.setUsername(user.getUsername());
            staffPayVO.setName(user.getName());
            staffPayVO.setRoleName(user.getRoleName());
            staffPayVO.setTime(nowTime);
            staffPayMapper.insertStaffPay(staffPayVO);
        }
        return staffPayVO;
    }

    /**
     * 获取真实的薪水
     *
     * @param salary
     * @return
     * @throws ParseException
     */
    @Transactional
    public Float getActualPrice(Salary salary) throws ParseException {
        if (salary.getOperation().equals("1")) {
            System.out.println("operation");
            return Float.valueOf(salary.getPrice());
        } else {
            if (!DateUtil.compareMonth(salary.getTime(), DateUtil.getNowTime())) {
                System.out.println("新值");
                return Float.valueOf(salary.getPrice());
            } else {
                System.out.println("旧值");
                return Float.valueOf(salary.getOldPrice());
            }
        }
    }

    /**
     * 修改职位薪水状态 判断后把3转换为1,2转换为0
     */
    private void changeSalaryState() throws ParseException {
        List<RoleVO> changeSalary = salaryMapper.getChangeSalary();
        if (!changeSalary.isEmpty()) {
            String time = DateUtil.getNowTime();
            for (RoleVO roleVO : changeSalary) {
                //判断时间
                if (!DateUtil.compareMonth(roleVO.getTime(), time)) {
                    if (roleVO.getState() == 2) {
                        roleVO.setState(0);

                    } else if (roleVO.getState() == 3) {
                        roleVO.setState(1);
                    }
                    System.out.println(roleVO);
                    salaryMapper.updateRoleSalary(roleVO.getRoleId(), roleVO.getSalaryId(), roleVO.getState());
                }
            }
        }
    }

    /**
     * 按需获取员工薪水信息
     *
     * @param time
     * @param name
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public IPage<StaffPayVO> selectSalaryPay(String time, String name, int currentPage, int pageSize) {
        Page<StaffPayVO> page = new Page<>(currentPage, pageSize);
        QueryWrapper<StaffPayVO> queryWrapper = new QueryWrapper<>();
        return staffPayMapper.selectSalaryPay(time, name, page, queryWrapper);
    }

    /**
     * 导出员工薪水状况
     *
     * @param list
     */
    @Override
    public void generateStaffExcel(String list) {
        List<StaffPayVO> staffPayVOS = JSONObject.parseArray(list, StaffPayVO.class);
        System.out.println(staffPayVOS.size());
        ExcelUtil.excelLockExport(StaffPayVO.class, "员工薪水表", staffPayVOS, "薪水表");
    }

    /**
     * 生成全部员工薪水并导出
     */
    @Override
    public void generateStaffSalary() throws ParseException {
        List<StaffVO> staff = userMapper.getStaff(null);
        List<StaffPayVO> staffPayVOS = new ArrayList<>();
        changeSalaryState();
        if (!staff.isEmpty()) {
            for (StaffVO user : staff) {
                StaffPayVO staffPayVO = generateSalary(user.getUserId(), DateUtil.getNowTime());
                if (staffPayVO != null) {
                    staffPayVOS.add(staffPayVO);
                }
            }
        }
        ExcelUtil.excelLockExport(StaffPayVO.class, "员工薪水表", staffPayVOS, "薪水表");
    }

    /**
     * 判断是否有未生成的员工薪水
     *
     * @return
     */
    @Override
    public CommonResult judgeGenerateStaff() {
        return userMapper.getStaff(null).size() > staffPayMapper.judgeGenerateStaff() ? CommonResult.success() : CommonResult.failed("没有待生成薪水的员工...");
    }

}

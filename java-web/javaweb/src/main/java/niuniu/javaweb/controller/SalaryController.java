package niuniu.javaweb.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import niuniu.javaweb.pojo.Salary;
import niuniu.javaweb.service.SalaryService;
import niuniu.javaweb.service.StaffPayService;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.vo.StaffPayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.controller
 * @date 2023/5/19 9:38
 */
@RestController
public class SalaryController {
    @Autowired
    private SalaryService salaryService;

    @Autowired
    StaffPayService staffPayService;

    /**
     * 获取全部薪水分页表示
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/getAllSalary/{currentPage}/{pageSize}")
    public CommonResult getAllSalary(@PathVariable int currentPage, @PathVariable int pageSize) {
        IPage<Salary> page = salaryService.getAllSalary(currentPage, pageSize);
        //如果当前页码大于了总页码值，那么重新执行查询操作，使用最大页码值作为当前页码值
        if (currentPage > page.getPages()) {
            page = salaryService.getAllSalary((int) page.getPages(), pageSize);
        }
        return CommonResult.success(page);
    }

    /**
     * 添加薪水
     *
     * @param salary
     * @return
     */
    @PostMapping("/insertSalary")
    public CommonResult insertSalary(Salary salary) {
        return salaryService.insertSalary(salary);
    }

    /**
     * 修改薪水
     *
     * @param salary
     * @return
     */
    @PostMapping("/updateSalary")
    public CommonResult updateSalary(Salary salary) {
        return salaryService.updateSalary(salary);
    }

    /**
     * 删除薪水标准
     *
     * @param salaryId
     * @return
     */
    @GetMapping("/deleteSalary")
    public CommonResult deleteSalary(Integer salaryId) {
        return salaryService.deleteSalary(salaryId);
    }

    /**
     * 获取工资按map返回
     *
     * @return
     */
    @GetMapping("/getSalaryMap")
    public CommonResult getSalaryMap() {
        return salaryService.getSalaryMap();
    }

    /**
     * 按需获取员工薪水信息
     *
     * @param currentPage
     * @param pageSize
     * @param time
     * @param name
     * @return
     */
    @GetMapping("selectSalaryPay/{currentPage}/{pageSize}")
    public CommonResult selectSalaryPay(@PathVariable int currentPage, @PathVariable int pageSize, String time, String name) {
        IPage<StaffPayVO> page = staffPayService.selectSalaryPay(time, name, currentPage, pageSize);
        //如果当前页码大于了总页码值，那么重新执行查询操作，使用最大页码值作为当前页码值
        if (currentPage > page.getPages()) {
            page = staffPayService.selectSalaryPay(time, name, (int) page.getPages(), pageSize);
        }
        return CommonResult.success(page);
    }

    /**
     * 导出员工薪水状况
     *
     * @param list
     * @throws ParseException
     */
    @PostMapping("generateStaffSalary")
    public void generateStaffSalary(String list) throws ParseException {
        System.out.println(list);
        staffPayService.generateStaffSalary(list);
    }
}

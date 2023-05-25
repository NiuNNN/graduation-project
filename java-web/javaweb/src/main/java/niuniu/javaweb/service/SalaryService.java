package niuniu.javaweb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import niuniu.javaweb.pojo.Salary;
import niuniu.javaweb.utils.result.CommonResult;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service
 * @date 2023/5/19 9:39
 */
public interface SalaryService {

    /**
     * 获取薪水分页展示
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    IPage<Salary> getAllSalary(int currentPage, int pageSize);

    /**
     * 添加薪水
     *
     * @param salary
     * @return
     */
    CommonResult insertSalary(Salary salary);

    /**
     * 修改薪水
     *
     * @param salary
     * @return
     */
    CommonResult updateSalary(Salary salary);

    /**
     * 删除薪水
     *
     * @param salaryId
     * @return
     */
    CommonResult deleteSalary(Integer salaryId);

    /**
     * 获取工资按map返回
     *
     * @return
     */
    CommonResult getSalaryMap();
    

}

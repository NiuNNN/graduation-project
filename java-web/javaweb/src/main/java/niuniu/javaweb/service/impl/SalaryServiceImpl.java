package niuniu.javaweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import niuniu.javaweb.mapper.SalaryMapper;
import niuniu.javaweb.pojo.Salary;
import niuniu.javaweb.service.SalaryService;
import niuniu.javaweb.utils.result.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service.impl
 * @date 2023/5/19 9:39
 */
@Service
public class SalaryServiceImpl extends ServiceImpl<SalaryMapper, Salary> implements SalaryService {

    @Autowired
    private SalaryMapper salaryMapper;

    /**
     * 获取薪水分页展示
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public IPage<Salary> getAllSalary(int currentPage, int pageSize) {
        Page<Salary> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Salary> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("state", 0);
        return salaryMapper.selectPage(page, queryWrapper);
    }

    /**
     * 添加薪水
     *
     * @param salary
     * @return
     */
    @Override
    public CommonResult insertSalary(Salary salary) {
        QueryWrapper<Salary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("salary_name", salary.getSalaryName()).ne("state", 0);
        if (salaryMapper.selectCount(queryWrapper) > 0) return CommonResult.failed("存在该项薪水,请重新填写...");
        return salaryMapper.insert(salary) > 0 ? CommonResult.success() : CommonResult.failed();
    }

    /**
     * 修改薪水
     *
     * @param salary
     * @return
     */
    @Override
    public CommonResult updateSalary(Salary salary) {
        Salary salary1 = salaryMapper.selectById(salary.getSalaryId());
        if (!salary1.getSalaryName().equals(salary.getSalaryName())) {
            QueryWrapper<Salary> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("salary_name", salary.getSalaryName()).ne("state", 0);
            if (salaryMapper.selectCount(queryWrapper) > 0) return CommonResult.failed("存在该项薪水,请重新填写...");
        }
        if (!salary1.getPrice().equals(salary.getPrice())) {
            salary.setOldPrice(salary1.getPrice());
        }
        return salaryMapper.updateSalary(salary) > 0 ? CommonResult.success() : CommonResult.failed();
    }

    /**
     * 删除薪水
     *
     * @param salaryId
     * @return
     */
    @Override
    public CommonResult deleteSalary(Integer salaryId) {
        //首先先判断是否被使用
        if (salaryMapper.selectSalaryCount(salaryId) > 0) return CommonResult.failed("该薪水项被使用中，无法删除...");
        else {
            salaryMapper.deleteSalary(salaryId);
            return CommonResult.success();
        }
    }
}

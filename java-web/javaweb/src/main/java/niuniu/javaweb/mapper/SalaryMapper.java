package niuniu.javaweb.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import niuniu.javaweb.pojo.Salary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.mapper
 * @date 2023/5/19 9:39
 */
@Mapper
public interface SalaryMapper extends BaseMapper<Salary> {

    /**
     * 插入一条记录
     *
     * @param entity 实体对象
     */
    @Override
    int insert(Salary entity);

    /**
     * 修改薪水
     *
     * @param salary
     * @return
     */
    int updateSalary(Salary salary);

    /**
     * 查询该薪水是否被使用
     *
     * @param salaryId
     * @return
     */
    int selectSalaryCount(Integer salaryId);

    /**
     * 删除薪水
     *
     * @param salaryId
     * @return
     */
    int deleteSalary(Integer salaryId);

    /**
     * 获取薪水信息分页显示
     *
     * @param page
     * @param wrapper
     * @return
     */
    IPage<Salary> getAllSalary(@Param("page") Page<Salary> page, @Param(Constants.WRAPPER) Wrapper<Salary> wrapper);
}

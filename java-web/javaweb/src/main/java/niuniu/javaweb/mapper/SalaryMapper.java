package niuniu.javaweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import niuniu.javaweb.pojo.Salary;
import org.apache.ibatis.annotations.Mapper;

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
}

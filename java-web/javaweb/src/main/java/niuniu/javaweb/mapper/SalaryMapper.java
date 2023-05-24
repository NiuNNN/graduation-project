package niuniu.javaweb.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import niuniu.javaweb.pojo.Salary;
import niuniu.javaweb.vo.RoleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 根据状态获取薪水
     *
     * @param state
     * @return
     */
    List<Salary> getSalaryByState(Integer state);

    /**
     * 插入职工薪水
     *
     * @param salaryId
     * @param roleId
     * @return
     */
    int insertRoleSalary(Integer salaryId, Integer roleId, Integer state);

    /**
     * 修改状态
     *
     * @param roleId
     * @param salaryId
     * @param state
     * @return
     */
    int updateRoleSalary(Integer roleId, Integer salaryId, Integer state);

    /**
     * 获取职工是否存在该条薪水记录
     *
     * @param roleId
     * @param salaryId
     * @return
     */
    int selectHasRoleSalary(Integer roleId, Integer salaryId);

    /**
     * 获取用户薪水
     *
     * @param userId
     * @return
     */
    List<Salary> getUserSalary(Integer userId);

    /**
     * 获取待修改状态的员工薪水
     *
     * @return
     */
    List<RoleVO> getChangeSalary();
}

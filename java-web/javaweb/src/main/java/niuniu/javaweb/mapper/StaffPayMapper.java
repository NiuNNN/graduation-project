package niuniu.javaweb.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import niuniu.javaweb.pojo.StaffPay;
import niuniu.javaweb.vo.StaffPayVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.mapper
 * @date 2023/5/24 9:59
 */
@Mapper
public interface StaffPayMapper extends BaseMapper<StaffPay> {
    /**
     * 判断是否存在该员工信息
     *
     * @param userId
     * @return
     */
    StaffPay selectHasSalary(Integer userId);

    /**
     * 存入员工工资信息
     *
     * @param staffPay
     * @return
     */
    int insertStaffPay(StaffPay staffPay);

    /**
     * 按需获取员工薪水信息
     *
     * @param time
     * @param name
     * @param page
     * @param wrapper
     * @return
     */
    IPage<StaffPayVO> selectSalaryPay(@Param("time") String time, @Param("name") String name, @Param("page") Page<StaffPayVO> page, @Param(Constants.WRAPPER) Wrapper<StaffPayVO> wrapper);
}

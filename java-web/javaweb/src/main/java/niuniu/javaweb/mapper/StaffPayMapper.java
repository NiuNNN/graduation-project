package niuniu.javaweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import niuniu.javaweb.pojo.StaffPay;
import org.apache.ibatis.annotations.Mapper;

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
}

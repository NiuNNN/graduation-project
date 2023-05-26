package niuniu.javaweb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.vo.StaffPayVO;

import java.text.ParseException;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service
 * @date 2023/5/24 9:59
 */
public interface StaffPayService {
    /**
     * 员工离职
     *
     * @param userId
     */
    CommonResult leaveSalary(Integer userId) throws ParseException;

    /**
     * 按需获取员工薪水信息
     *
     * @param time
     * @param name
     * @param currentPage
     * @param pageSize
     * @return
     */
    IPage<StaffPayVO> selectSalaryPay(String time, String name, int currentPage, int pageSize);

    /**
     * 导出员工薪水状况
     *
     * @param list
     */
    void generateStaffExcel(String list);

    /**
     * 生成全部员工薪水并导出
     */
    void generateStaffSalary() throws ParseException;

    /**
     * 判断是否有未生成的员工薪水
     *
     * @return
     */
    CommonResult judgeGenerateStaff();
}

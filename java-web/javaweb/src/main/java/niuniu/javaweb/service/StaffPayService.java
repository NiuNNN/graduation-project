package niuniu.javaweb.service;

import niuniu.javaweb.utils.result.CommonResult;

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
    
}

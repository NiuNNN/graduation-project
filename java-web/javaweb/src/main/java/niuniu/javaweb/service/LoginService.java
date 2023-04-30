package niuniu.javaweb.service;

import niuniu.javaweb.pojo.User;
import niuniu.javaweb.utils.result.CommonResult;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service
 * @date 2023/2/9 10:30
 */
public interface LoginService {

    /**
     * 后台用户登录
     *
     * @param user
     * @return
     */
    CommonResult StaffLogin(User user);

    /**
     * 用户登录
     */
    CommonResult UserLogin(User user);

    /**
     * 用户登出
     *
     * @return
     */
    CommonResult loginOut();
}

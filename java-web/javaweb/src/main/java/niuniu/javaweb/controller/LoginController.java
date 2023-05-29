package niuniu.javaweb.controller;

import niuniu.javaweb.pojo.User;
import niuniu.javaweb.service.LoginService;
import niuniu.javaweb.utils.result.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.controller
 * @date 2023/2/9 11:10
 */
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    /**
     * 员工登录
     *
     * @param user
     * @return
     */

    @PostMapping("/staff/login")
    public CommonResult loginStaff(User user) {
        return loginService.StaffLogin(user);
    }

    /**
     * 员工退出
     *
     * @return
     */
    @GetMapping("/staff/logout")
    public CommonResult loginStaffOut() {
        return loginService.loginOut();
    }


    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    @PostMapping("/user/login")
    public CommonResult loginUser(User user, HttpServletRequest httpServletRequest) throws Exception {
        System.out.println(user);
        System.out.println(httpServletRequest);
        return loginService.UserLogin(user, httpServletRequest);
    }

    /**
     * 用户退出
     *
     * @return
     */
    @GetMapping("/user/logout")
    public CommonResult loginUserOut() {
        System.out.println("退出");
        return loginService.loginOut();
    }
}

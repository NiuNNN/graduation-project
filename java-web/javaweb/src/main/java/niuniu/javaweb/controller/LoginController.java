package niuniu.javaweb.controller;

import niuniu.javaweb.pojo.User;
import niuniu.javaweb.service.LoginService;
import niuniu.javaweb.utils.result.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 员工
     *
     * @param user
     * @return
     */

    @PostMapping("/staff/login")
    public CommonResult loginStaff(User user) {
        return loginService.StaffLogin(user);
    }

    @GetMapping("/staff/logout")
    public CommonResult loginStaffOut() {
        return loginService.loginOut();
    }


    /**
     * 用户
     *
     * @param user
     * @return
     */
    @PostMapping("/user/login")
    public CommonResult loginUser(User user) {
        return loginService.UserLogin(user);
    }

    @GetMapping("/user/logout")
    public CommonResult loginUserOut() {
        System.out.println("退出");
        return loginService.loginOut();
    }
}

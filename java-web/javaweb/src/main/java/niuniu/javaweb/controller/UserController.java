package niuniu.javaweb.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import niuniu.javaweb.pojo.User;
import niuniu.javaweb.service.UserService;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.controller
 * @date 2023/2/10 21:24
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 获取用户信息
     *
     * @param user 用户编号
     * @return
     */
    @GetMapping("/getUser")
//    @PreAuthorize("hasAnyRole('/user!get')")
    public CommonResult getUser(User user) {
        return CommonResult.success(userService.getUser(user.getUsername()));
    }

    /**
     * 上传用户头像
     *
     * @param file     头像
     * @param username 用户编号
     * @return
     */
    @PostMapping("/uploadAvatar")
    public CommonResult uploadAvatar(@RequestParam("file") MultipartFile file, @RequestParam("username") String username) {
        return CommonResult.success(userService.uploadAvatar(file, username));
    }

    /**
     * 获取用户目录
     *
     * @param user
     * @return
     */
    @GetMapping("/getMenu")
    public CommonResult getMenu(User user) {
        return CommonResult.success(userService.getMenuList(user.getUsername()));
    }

    /**
     * 创建新用户账号
     *
     * @param roleId
     * @return
     * @throws Exception
     */
    @GetMapping("/createUserName")
    public CommonResult createUserName(Integer roleId) throws Exception {
        return userService.createUserName(roleId);
    }

    /**
     * 校验身份证
     *
     * @param file
     * @param direction
     * @return
     */
    @PostMapping("/validateIDCard")
    public CommonResult validateIDCard(@RequestParam("file") MultipartFile file, @RequestParam("direction") String direction) {
        return userService.validateIDCard(file, direction);
    }

    /**
     * 确认信息
     *
     * @param font
     * @param back
     * @param username
     * @param phone
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    @PostMapping("/confirmMsg")
    public CommonResult confirmMsg(@RequestParam("back") MultipartFile font, @RequestParam("font") MultipartFile back, String username, String phone) throws URISyntaxException, IOException {
        return userService.confirmMsg(font, back, username, phone);
    }

    /**
     * 创建用户
     *
     * @param font
     * @param back
     * @param user
     * @return
     */
    @PostMapping("/insertUser")
    public CommonResult insertUser(@RequestParam("back") MultipartFile font, @RequestParam("font") MultipartFile back, String user) {
        return userService.insertUser(font, back, user);
    }

    /**
     * 按需获取住户信息
     *
     * @param currentPage
     * @param pageSize
     * @param username
     * @param name
     * @param houseName
     * @param state
     * @return
     */
    @GetMapping("/getUserPage/{currentPage}/{pageSize}")
    public CommonResult getUserPage(@PathVariable int currentPage, @PathVariable int pageSize, String username, String name, String houseName, String state, String orderState) {
//        System.out.println("username" + username);
//        System.out.println("name" + name);
//        System.out.println("state" + state);
//        System.out.println("orderState" + orderState);
        IPage<UserVO> page = userService.getUserPage(username, name, houseName, currentPage, pageSize, state, orderState);
        //如果当前页码大于了总页码值，那么重新执行查询操作，使用最大页码值作为当前页码值
        if (currentPage > page.getPages()) {
            page = userService.getUserPage(username, name, houseName, (int) page.getPages(), pageSize, state, orderState);
        }
        return CommonResult.success(page);
    }

    /**
     * 按需获取没有获取房间的用户
     *
     * @param currentPage
     * @param pageSize
     * @param username
     * @param name
     * @return
     */
    @GetMapping("/getNoHouseUserPage/{currentPage}/{pageSize}")
    public CommonResult getNoHouseUserPage(@PathVariable int currentPage, @PathVariable int pageSize, String username, String name) {
        System.out.println("username" + username);
        System.out.println("name" + name);
        IPage<UserVO> page = userService.getNoHouseUserPage(username, name, currentPage, pageSize);
        //如果当前页码大于了总页码值，那么重新执行查询操作，使用最大页码值作为当前页码值
        if (currentPage > page.getPages()) {
            page = userService.getNoHouseUserPage(username, name, (int) page.getPages(), pageSize);
        }
        return CommonResult.success(page);
    }

    /**
     * 更新用户信息
     *
     * @param font
     * @param back
     * @param username
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    @PostMapping("/updateUserByIDCard")
    public CommonResult updateUserByIDCard(@RequestParam("back") MultipartFile font, @RequestParam("font") MultipartFile back, String username) throws URISyntaxException, IOException {
        return userService.updateUserByIDCard(font, back, username);
    }

    /**
     * 修改用户密码或手机
     *
     * @param user
     * @return
     */
    @PostMapping("/updateUser")
    public CommonResult updateUser(User user) {
        return userService.updateUser(user);
    }

    /**
     * 修改用户状态
     *
     * @param userId
     * @param state
     * @return
     */
    @GetMapping("changeUserState")
    public CommonResult changeUserState(Integer userId, Integer state) {
        return userService.changeUserState(userId, state);
    }

    /**
     * 校验密码
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("validatePassword")
    public CommonResult validatePassword(String username, String password) {
        return userService.validatePassword(username, password);
    }

    /**
     * 获取用户名字
     *
     * @param userId
     * @return
     */
    @GetMapping("getName/{userId}")
    public CommonResult getName(@PathVariable("userId") Integer userId) {
        return userService.getName(userId);
    }

    /**
     * 删除住户
     *
     * @param userId
     * @return
     */
    @GetMapping("deleteUser/{userId}")
    public CommonResult deleteUser(@PathVariable("userId") Integer userId) {
        return userService.deleteUser(userId);
    }
}

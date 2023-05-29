package niuniu.javaweb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import niuniu.javaweb.pojo.User;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.vo.MenuListVO;
import niuniu.javaweb.vo.StaffVO;
import niuniu.javaweb.vo.UserVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service
 * @date 2023/2/7 21:56
 */
public interface UserService {

    /**
     * 获取用户信息
     *
     * @param username 用户编号
     * @return
     */
    User getUser(String username);

    /**
     * 更新头像信息
     *
     * @param avatar   头像
     * @param username 用户编号
     * @return
     */
    CommonResult uploadAvatar(MultipartFile avatar, String username);

    /**
     * 获取用户权限
     *
     * @param username
     * @return
     */
    MenuListVO getMenuList(String username);

    /**
     * 创建新用户账号
     *
     * @param roleId
     * @return
     * @throws Exception
     */
    CommonResult createUserName(Integer roleId) throws Exception;

    /**
     * 校验身份证是否可靠
     *
     * @param file
     * @param direction
     * @return
     */
    CommonResult validateIDCard(MultipartFile file, String direction);

    /**
     * 确认信息
     *
     * @param font
     * @param back
     * @return
     */
    CommonResult confirmMsg(MultipartFile font, MultipartFile back) throws URISyntaxException, IOException;

    /**
     * 创建用户
     *
     * @param font
     * @param back
     * @param jsonUser
     * @param roleId
     * @return
     */
    CommonResult insertUser(MultipartFile font, MultipartFile back, String jsonUser, Integer roleId);

    /**
     * 按需查询全部用户信息
     *
     * @param username
     * @param name
     * @param houseName
     * @param currentPage
     * @param pageSize
     * @param state
     * @param orderState
     * @return
     */
    IPage<UserVO> getUserPage(String username, String name, String houseName, int currentPage, int pageSize, String state, String orderState);

    /**
     * 查询没有住房的用户
     *
     * @param username
     * @param name
     * @param currentPage
     * @param pageSize
     * @return
     */
    IPage<UserVO> getNoHouseUserPage(String username, String name, int currentPage, int pageSize);

    /**
     * 修改用户信息
     *
     * @param font
     * @param back
     * @param username
     * @return
     */
    CommonResult updateUserByIDCard(MultipartFile font, MultipartFile back, String username) throws URISyntaxException, IOException;

    /**
     * 更新用户手机/密码
     *
     * @param user
     * @return
     */
    CommonResult updateUser(User user);

    /**
     * 修改用户状态
     *
     * @param userId
     * @param state
     * @return
     */
    CommonResult changeUserState(Integer userId, Integer state);

    /**
     * 校验密码
     *
     * @param username
     * @return
     */
    CommonResult validatePassword(String username, String password);

    /**
     * 获取用户名字
     *
     * @param userId
     * @return
     */
    CommonResult getName(Integer userId);

    /**
     * 删除住户
     *
     * @param userId
     * @return
     */
    CommonResult deleteUser(Integer userId);

    /**
     * 获取员工信息
     *
     * @param username
     * @param name
     * @param roleId
     * @param state
     * @param currentPage
     * @param pageSize
     * @return
     */
    IPage<StaffVO> getStaffPage(String username, String name, Integer roleId, Integer state, int currentPage, int pageSize);

    /**
     * 忘记密码
     *
     * @param user
     * @return
     */
    CommonResult forgetPassword(User user);
}

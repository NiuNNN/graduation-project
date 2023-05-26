package niuniu.javaweb.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import niuniu.javaweb.pojo.User;
import niuniu.javaweb.vo.StaffVO;
import niuniu.javaweb.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.mapper
 * @date 2023/2/7 21:51
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 创建用户
     *
     * @param user 用户信息
     * @return
     */
    int createUser(User user);

    /**
     * 更新头像
     *
     * @param username
     * @param avatar
     * @return
     */
    int updateAvatar(String username, String avatar);

    /**
     * 获取用户id
     *
     * @param username 用户编号
     * @return
     */
    Integer getUserId(String username);

    /**
     * 按需查询全部用户信息
     *
     * @param username
     * @param name
     * @param houseName
     * @param state
     * @param page
     * @param wrapper
     * @return
     */

    IPage<UserVO> getUserPage(@Param("username") String username, @Param("name") String name, @Param("houseName") String houseName, @Param("state") String state, @Param("orderState") String orderState, @Param("page") Page<UserVO> page, @Param(Constants.WRAPPER) Wrapper<UserVO> wrapper);

    /**
     * 按需查询没有住房的用户
     *
     * @param username
     * @param name
     * @param page
     * @param wrapper
     * @return
     */
    IPage<UserVO> getNoHouseUserPage(@Param("username") String username, @Param("name") String name, @Param("page") Page<UserVO> page, @Param(Constants.WRAPPER) Wrapper<UserVO> wrapper);

    /**
     * 更新用户信息通过身份证
     *
     * @param user
     * @return
     */
    int updateUserByIDCard(User user);

    /**
     * 修改用户手机/密码
     *
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 修改用户状态
     *
     * @param userId
     * @param state
     * @return
     */
    int changeUserState(Integer userId, Integer state);

    /**
     * 校验密码
     *
     * @param username
     * @return
     */
    String validatePassword(String username);

    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    int deleteUser(Integer userId);

    /**
     * 获取员工信息
     *
     * @param username
     * @param name
     * @param state
     * @param roleId
     * @param page
     * @param wrapper
     * @return
     */
    IPage<StaffVO> getStaffPage(@Param("username") String username, @Param("name") String name, @Param("state") Integer state, @Param("roleId") Integer roleId, @Param("page") Page<StaffVO> page, @Param(Constants.WRAPPER) Wrapper<StaffVO> wrapper);

    /**
     * 获取除管理员以外的在职员工
     *
     * @param userId
     * @return
     */
    List<StaffVO> getStaff(Integer userId);
}

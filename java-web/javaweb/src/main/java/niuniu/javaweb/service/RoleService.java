package niuniu.javaweb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import niuniu.javaweb.pojo.Role;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.vo.RoleVO;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service
 * @date 2023/3/24 18:28
 */
public interface RoleService {

    /**
     * 获取全部职位
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    IPage<RoleVO> getAllRole(int currentPage, int pageSize);

    /**
     * 按职务获取已选择权限
     *
     * @param roleId
     * @return
     */
    CommonResult selectPermsByRoleId(Integer roleId);

    /**
     * 获取职务未有权限
     *
     * @param roleId
     * @return
     */
    CommonResult selectElsePermsByRoleId(Integer roleId);

    /**
     * 赋权限
     *
     * @param roleId
     * @param keys
     * @return
     */
    CommonResult updatePermsByRoleId(Integer roleId, String keys);

    /**
     * 移除权限
     *
     * @param roleId
     * @param keys
     * @return
     */
    CommonResult deletePermsByRoleId(Integer roleId, String keys);

    /**
     * 修改职务状态
     *
     * @param roleId
     * @param state
     * @return
     */
    CommonResult changeRoleState(Integer roleId, Integer state);

    /**
     * 新建职务
     *
     * @param role
     * @param arrList
     * @return
     */
    CommonResult insertRole(Role role, String arrList);

    /**
     * 修改职务信息
     *
     * @param role
     * @param base
     * @param probation
     * @param arrList
     * @return
     */
    CommonResult updateRole(Role role, Integer base, Integer probation, String arrList);

    /**
     * 获取职位对应的薪水
     *
     * @param roleId
     * @return
     */
    CommonResult getRoleSalary(Integer roleId);
}

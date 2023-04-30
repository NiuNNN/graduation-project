package niuniu.javaweb.service;

import niuniu.javaweb.pojo.Role;
import niuniu.javaweb.utils.result.CommonResult;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service
 * @date 2023/3/24 18:28
 */
public interface RoleService {

    /**
     * 获取全部职位
     *
     * @return
     */
    CommonResult getAllRole();

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
     * @return
     */
    CommonResult insertRole(Role role);

    /**
     * 修改职务信息
     *
     * @param role
     * @return
     */
    CommonResult updateRole(Role role);
}

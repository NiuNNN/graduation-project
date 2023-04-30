package niuniu.javaweb.controller;

import niuniu.javaweb.pojo.Role;
import niuniu.javaweb.service.RoleService;
import niuniu.javaweb.utils.result.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.controller
 * @date 2023/3/24 18:27
 */
@RestController
public class RoleController {

    @Autowired
    RoleService roleService;

    /**
     * 获取全部权限
     *
     * @return
     */
    @GetMapping("/getAllRole")
    public CommonResult getAllRole() {
        return roleService.getAllRole();
    }

    /**
     * 获取已有权限
     *
     * @param roleId
     * @return
     */
    @GetMapping("/selectPermsByRoleId/{roleId}")
    public CommonResult selectPermsByRoleId(@PathVariable Integer roleId) {
        return roleService.selectPermsByRoleId(roleId);
    }

    /**
     * 获取未授权权限
     *
     * @param roleId
     * @return
     */
    @GetMapping("/selectElsePermsByRoleId/{roleId}")
    public CommonResult selectElsePermsByRoleId(@PathVariable Integer roleId) {
        return roleService.selectElsePermsByRoleId(roleId);
    }

    /**
     * 添加权限
     *
     * @param roleId
     * @param keys
     * @return
     */
    @PostMapping("updatePermsByRoleId")
    public CommonResult updatePermsByRoleId(Integer roleId, String keys) {
        return roleService.updatePermsByRoleId(roleId, keys);
    }

    /**
     * 移除权限
     *
     * @param roleId
     * @param keys
     * @return
     */
    @PostMapping("deletePermsByRoleId")
    public CommonResult deletePermsByRoleId(Integer roleId, String keys) {
        return roleService.deletePermsByRoleId(roleId, keys);
    }

    /**
     * 修改职务状态
     *
     * @param roleId
     * @param state
     * @return
     */
    @GetMapping("changeRoleState/{roleId}")
    public CommonResult changeRoleState(@PathVariable Integer roleId, Integer state) {
        return roleService.changeRoleState(roleId, state);
    }

    /**
     * 新建职务
     *
     * @param role
     * @return
     */
    @PostMapping("insertRole")
    public CommonResult insertRole(Role role) {
        return roleService.insertRole(role);
    }

    /**
     * 修改职务信息
     *
     * @param role
     * @return
     */
    @PostMapping("updateRole")
    public CommonResult updateRole(Role role) {
        return roleService.updateRole(role);
    }
}

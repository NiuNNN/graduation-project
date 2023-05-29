package niuniu.javaweb.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import niuniu.javaweb.pojo.Role;
import niuniu.javaweb.service.RoleService;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

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
     * 获取职位
     *
     * @return
     */
    @GetMapping("/getRole")
    public CommonResult getRole() {
        return roleService.getRole();
    }

    /**
     * 获取全部职位并分页展示
     *
     * @return
     */
    @GetMapping("/getAllRole/{currentPage}/{pageSize}")
    public CommonResult getAllRole(@PathVariable int currentPage, @PathVariable int pageSize) {
        IPage<RoleVO> page = roleService.getAllRole(currentPage, pageSize);
        //如果当前页码大于了总页码值，那么重新执行查询操作，使用最大页码值作为当前页码值
        if (currentPage > page.getPages()) {
            page = roleService.getAllRole((int) page.getPages(), pageSize);
        }
        return CommonResult.success(page);
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
    public CommonResult insertRole(Role role, String arrList) {
        return roleService.insertRole(role, arrList);
    }

    /**
     * 修改职务信息
     *
     * @param role
     * @param base
     * @param probation
     * @param arrList
     * @return
     */
    @PostMapping("updateRole")
    public CommonResult updateRole(Role role, Integer base, Integer probation, String arrList) {
        return roleService.updateRole(role, base, probation, arrList);
    }

    /**
     * 获取职位所对应的薪水
     *
     * @param roleId
     * @return
     */
    @GetMapping("getRoleSalary")
    public CommonResult getRoleSalary(Integer roleId) {
        return roleService.getRoleSalary(roleId);
    }

    /**
     * 获取用户职位薪水
     *
     * @param roleId
     * @return
     */
    @GetMapping("getUserSalary")
    public CommonResult getUserSalary(Integer roleId) {
        return roleService.getUserSalary(roleId);
    }

    /**
     * 员工离职
     *
     * @param userId
     * @return
     */
    @GetMapping("/leaveRole")
    public CommonResult leaveRole(Integer userId) throws ParseException {
        return roleService.leaveRole(userId);
    }

    /**
     * 获取职位名称
     *
     * @param userId
     * @return
     */
    @GetMapping("getUserRole")
    public CommonResult getUserRole(Integer userId) {
        return roleService.getUserRole(userId);
    }
}

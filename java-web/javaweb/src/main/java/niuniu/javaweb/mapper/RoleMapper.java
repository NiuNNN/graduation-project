package niuniu.javaweb.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import niuniu.javaweb.pojo.Role;
import niuniu.javaweb.vo.RoleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.mapper
 * @date 2023/3/24 18:07
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * @param page
     * @param wrapper
     * @return
     */
    IPage<RoleVO> getAllRole(@Param("page") Page<RoleVO> page, @Param(Constants.WRAPPER) Wrapper<RoleVO> wrapper);

    /**
     * 修改用户状态
     *
     * @param roleId
     * @param state
     * @return
     */
    int changeRoleState(Integer roleId, Integer state);

    /**
     * 新建职务
     *
     * @param role
     * @return
     */
    int insertRole(Role role);

    /**
     * 修改职务信息
     *
     * @param role
     * @return
     */
    int updateRole(Role role);

}

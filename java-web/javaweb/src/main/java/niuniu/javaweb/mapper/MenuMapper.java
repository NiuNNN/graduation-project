package niuniu.javaweb.mapper;

import niuniu.javaweb.vo.MenuVO;
import niuniu.javaweb.vo.TransferVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.mapper
 * @date 2023/2/9 10:34
 */
@Mapper
public interface MenuMapper {
    /**
     * 根据用户账号查询菜单
     *
     * @param username
     * @return
     */
    List<MenuVO> selectPermsByUserName(String username);

    /**
     * 按职务获取已有权限
     *
     * @param roleId
     * @return
     */
    List<TransferVO> selectPermsByRoleId(Integer roleId);

    /**
     * 获取职务未有权限
     *
     * @param roleId
     * @return
     */
    List<TransferVO> selectElsePermsByRoleId(Integer roleId);


    /**
     * 获得全部操作项
     *
     * @return
     */
    @Cacheable(cacheNames = "allOperation")
    List<TransferVO> selectAllOperation();

    /**
     * 获取缺少的操作
     *
     * @param str
     * @return
     */
    List<TransferVO> selectOperationByStr(String[] str);

    /**
     * 查询缺少的父节点元素
     *
     * @param menuId
     * @return
     */
    TransferVO selectByMenuId(String menuId);

    /**
     * 查找结点
     *
     * @param menuId
     * @param roleId
     * @return
     */
    int selectNode(String menuId, Integer roleId);

    /**
     * 插入父节点
     *
     * @param menuId
     * @param roleId
     * @return
     */
    int insertParent(String menuId, Integer roleId);

    /**
     * 查询操作权限
     *
     * @param menuId
     * @param roleId
     * @return
     */
    String selectQuery(String menuId, Integer roleId);

    /**
     * 更新操作权限
     *
     * @param menuId
     * @param roleId
     * @param query
     * @return
     */
    int updateQuery(String menuId, Integer roleId, String query);

    /**
     * 插入操作权限
     *
     * @param menuId
     * @param roleId
     * @param query
     * @return
     */
    int insertQuery(String menuId, Integer roleId, String query);

    /**
     * 删除结点
     *
     * @param menuId
     * @param roleId
     * @return
     */
    int deleteNode(String menuId, Integer roleId);
}

package niuniu.javaweb.utils;

import niuniu.javaweb.vo.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.utils
 * @date 2023/2/7 17:55
 */
public class MenuUtils {

    /**
     * 构建菜单树
     *
     * @param menuVOList
     * @return
     */
    public static MenuListVO buildTree(List<MenuVO> menuVOList) {
        List<MenuVO> topMenuList = new ArrayList<>();

        for (MenuVO menuVO : menuVOList) {
            if (menuVO.getParentId() == null) {
                topMenuList.add(menuVO);
            }
        }
        List<MenuVO> resultMenu = new ArrayList<>();
        List<RouteVO> resultRoute = new ArrayList<>();
        for (MenuVO menuVO : topMenuList) {
            MenuListVO treeChildren = MenuUtils.getTreeChildren(menuVO, menuVOList);

            MenuVO treeChildrenMenuVO = treeChildren.getMenuVO();


            RouteVO treeChildrenRouteVO = treeChildren.getRouteVO();

            resultMenu.add(treeChildrenMenuVO);
            resultRoute.add(treeChildrenRouteVO);
        }
        MenuListVO result = new MenuListVO(resultMenu, resultRoute, null, null);

        return result;
    }

    /**
     * 查找指定父节点的子孩子
     *
     * @param parent
     * @param menuVOList
     * @return
     */
    public static MenuListVO getTreeChildren(MenuVO parent, List<MenuVO> menuVOList) {

        MenuListVO menuListVO = new MenuListVO();

        RouteVO routeVO = new RouteVO();

        routeVO.setLeaf(parent.getLeaf());
        routeVO.setName(parent.getName());
        routeVO.setPath(parent.getPath());
        routeVO.setComponent(parent.getComponent());
        routeVO.setParams(new QueryVO(parent.getQuery()));
        routeVO.setMeta(new MetaVO(parent.getIcon(), parent.getTitle()));

        List<MenuVO> childrenList = new ArrayList<>();

        List<RouteVO> childrenRoute = new ArrayList<>();

        for (MenuVO menuVO : menuVOList) {
            if (parent.getMenuId().equals(menuVO.getParentId())) {
                childrenList.add(MenuUtils.getTreeChildren(menuVO, menuVOList).getMenuVO());
                childrenRoute.add(MenuUtils.getTreeChildren(menuVO, menuVOList).getRouteVO());
            }

        }

        routeVO.setChildren(childrenRoute);

        parent.setChildren(childrenList);

        menuListVO.setMenuVO(parent);
        menuListVO.setRouteVO(routeVO);

        return menuListVO;
    }
}

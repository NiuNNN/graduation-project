package niuniu.javaweb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.vo
 * @date 2023/3/12 10:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteVO implements Serializable {

    private static final long serialVersionUID = -5816193308087536658L;

    /**
     * 判断是否有叶子
     */
    private boolean leaf;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件
     */
    private String component;

    /**
     * 路由信息
     */
    private MetaVO meta;

    /**
     * 路由权限
     */
    private QueryVO params;

    /**
     * 菜单子节点
     */
    private List<RouteVO> children = new ArrayList<>();
}

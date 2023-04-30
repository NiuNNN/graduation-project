package niuniu.javaweb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author NiuNiu666
 * @package com.niuniu.javaweb.vo
 * @date 2022/12/30 11:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuVO implements Serializable {

    private static final long serialVersionUID = 571370848404627586L;
    /**
     * 菜单id
     */
    private Integer menuId;

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
     * 是否有叶子节点
     */
    private Boolean leaf;

    /**
     * 菜单父节点
     */
    private Integer parentId;

    /**
     * 权限标识
     */
    private String perms;


    /**
     * 图标
     */
    private String icon;

    /**
     * 标题
     */
    private String title;

    /**
     * 菜单子节点
     */
    private List<MenuVO> children = new ArrayList<>();

    /**
     * 权限操作
     */
    private String query;
}

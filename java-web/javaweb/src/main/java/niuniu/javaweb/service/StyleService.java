package niuniu.javaweb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import niuniu.javaweb.pojo.Style;
import niuniu.javaweb.utils.result.CommonResult;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service
 * @date 2023/3/19 12:08
 */
public interface StyleService {
    /**
     * 添加房型
     *
     * @param style
     * @return
     */
    CommonResult insertStyle(Style style);

    /**
     * 按需获取房型
     *
     * @param currentPage
     * @param pageSize
     * @param style
     * @return
     */
    IPage<Style> getAllStyle(int currentPage, int pageSize, Style style);

    /**
     * 修改状态操作
     *
     * @param styleId
     * @param state
     * @return
     */
    CommonResult resetStyle(Integer styleId, String state);

    /**
     * 更新房型数据
     *
     * @param style
     * @return
     */
    CommonResult updateStyle(Style style);

    /**
     * 获取下拉框
     *
     * @return
     */
    CommonResult getStyleName(Integer floorId);

    /**
     * 获取房型面积下拉框
     *
     * @param StyleName
     * @param floorId
     * @return
     */
    CommonResult getStyleArea(String StyleName, Integer floorId);

    /**
     * 获取房型阳台信息
     *
     * @param styleName
     * @param floorId
     * @param area
     * @return
     */
    CommonResult getStyleBalcony(String styleName, Integer floorId, String area);

    /**
     * 获取房型剩余数量
     *
     * @param style
     * @param floorId
     * @return
     */
    CommonResult leftStyle(Style style, Integer floorId);

    /**
     * 获取全部房型名称
     *
     * @return
     */
    CommonResult getAllStyleName();
}

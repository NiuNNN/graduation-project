package niuniu.javaweb.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import niuniu.javaweb.pojo.Style;
import niuniu.javaweb.service.StyleService;
import niuniu.javaweb.utils.result.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.controller
 * @date 2023/3/19 12:54
 */
@RestController
public class StyleController {

    @Autowired
    StyleService styleService;

    /**
     * 添加房型
     *
     * @param style
     * @return
     */
    @PostMapping("/insertStyle")
    public CommonResult insertStyle(Style style) {
        return styleService.insertStyle(style);
    }

    /**
     * 获取全部房型
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/getAllStyle/{currentPage}/{pageSize}")
    public CommonResult getAllStyle(@PathVariable int currentPage, @PathVariable int pageSize, Style style) {
        IPage<Style> page = styleService.getAllStyle(currentPage, pageSize, style);
        //如果当前页码大于了总页码值，那么重新执行查询操作，使用最大页码值作为当前页码值
        if (currentPage > page.getPages()) {
            page = styleService.getAllStyle((int) page.getPages(), pageSize, style);
        }
        return CommonResult.success(page);
    }

    /**
     * 房型恢复上架
     *
     * @param styleId
     * @return
     */
    @GetMapping("/resetStyle/{styleId}/{state}")
    public CommonResult resetStyle(@PathVariable Integer styleId, @PathVariable String state) {
        return styleService.resetStyle(styleId, state);
    }

    /**
     * 更新房型数据
     *
     * @param style
     * @return
     */
    @PostMapping("/updateStyle")
    public CommonResult updateStyle(Style style) {
        return styleService.updateStyle(style);
    }

    /**
     * 获取房型名称
     *
     * @param floorId
     * @return
     */
    @GetMapping("/getStyleName/{floorId}")
    public CommonResult getStyleName(@PathVariable Integer floorId) {
        return styleService.getStyleName(floorId);
    }

    /**
     * 获取房型面积
     *
     * @param floorId
     * @param styleName
     * @return
     */
    @GetMapping("/getStyleArea/{floorId}")
    public CommonResult getStyleArea(@PathVariable Integer floorId, String styleName) {
        return styleService.getStyleArea(styleName, floorId);
    }

    /**
     * 获取房型阳台信息
     *
     * @param floorId
     * @param styleName
     * @param area
     * @return
     */
    @GetMapping("/getStyleBalcony/{floorId}")
    public CommonResult getStyleBalcony(@PathVariable Integer floorId, String styleName, String area) {
        return styleService.getStyleBalcony(styleName, floorId, area);
    }

    /**
     * 获取房型剩余数量
     *
     * @param style
     * @param floorId
     * @return
     */
    @GetMapping("/leftStyle/{floorId}")
    public CommonResult leftStyle(Style style, @PathVariable Integer floorId) {
        return styleService.leftStyle(style, floorId);
    }

    /**
     * 获取全部房型名称
     *
     * @return
     */
    @GetMapping("/getAllStyleName")
    public CommonResult getAllStyleName() {
        return styleService.getAllStyleName();
    }
}

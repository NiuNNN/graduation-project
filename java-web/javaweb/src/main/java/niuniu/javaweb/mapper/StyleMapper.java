package niuniu.javaweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import niuniu.javaweb.pojo.Style;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.mapper
 * @date 2023/3/19 12:08
 */
@Mapper
public interface StyleMapper extends BaseMapper<Style> {

    /**
     * 校验是否有存在此类型的房型
     *
     * @param style
     * @return
     */
    int selectSameStyle(Style style);

    /**
     * 修改状态
     *
     * @param styleId
     * @param state
     * @return
     */
    int resetStyle(Integer styleId, String state);

    /**
     * 获取下拉框
     *
     * @return
     */
    List<String> getStyleName(String floor);

    /**
     * 获取房型面积下拉框
     *
     * @param styleName
     * @param floor
     * @return
     */
    List<String> getStyleArea(String styleName, String floor);

    /**
     * 获取房型面积
     *
     * @param styleName
     * @param floor
     * @param area
     * @return
     */
    List<String> getStyleBalcony(String styleName, String floor, String area);

    /**
     * 按需得到房型的剩余数量
     *
     * @param style
     * @return
     */
    Style leftStyle(Style style);

    /**
     * 修改房型的剩余数量
     *
     * @param styleId
     * @return
     */
    int updateAvailable(Integer styleId);

    /**
     * 获取全部房型名称
     *
     * @return
     */
    List<Style> getAllStyleName();

    /**
     * 增加可用
     *
     * @param styleId
     * @return
     */
    int increaseAvailable(Integer styleId);
}

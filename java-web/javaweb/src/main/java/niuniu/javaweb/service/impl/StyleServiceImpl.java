package niuniu.javaweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import niuniu.javaweb.mapper.FloorMapper;
import niuniu.javaweb.mapper.StyleMapper;
import niuniu.javaweb.pojo.Floor;
import niuniu.javaweb.pojo.Style;
import niuniu.javaweb.service.StyleService;
import niuniu.javaweb.utils.result.CommonResult;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service.impl
 * @date 2023/3/19 12:09
 */
@Service
public class StyleServiceImpl extends ServiceImpl<StyleMapper, Style> implements StyleService {

    @Autowired
    StyleMapper styleMapper;

    @Autowired
    FloorMapper floorMapper;

    /**
     * 添加房型
     *
     * @param style
     * @return
     */
    @Override
    @CacheEvict(cacheNames = "allArea", key = "1")
    public CommonResult insertStyle(Style style) {
        /**
         * 首先校验是否存在相同 面积&价格&房型名称&数量 的房间
         * 存在则不给予创建
         * 不存在则校验面积是否足够
         * 更新面积表
         */
        Floor floor = floorMapper.selectById(1);
        Float floorArea = Float.valueOf(floor.getAvailable());
        Float styleArea = Float.valueOf(style.getArea());
        Float styleNum = Float.valueOf(style.getNum());

        if (styleMapper.selectSameStyle(style) == 0) {
            float result = floorArea - styleArea * styleNum;
            if (result >= 0) {
                style.setState("1");
                style.setAvailable(style.getNum());
                styleMapper.insert(style);
                floor.setAvailable(String.valueOf(result));
                floorMapper.updateById(floor);
                return CommonResult.success();
            } else {
                return CommonResult.failed("可用面积不足,请确认后重新录入");
            }
        } else {
            return CommonResult.failed("存在此类型的房型，请重新确认后录入");
        }
    }

    /**
     * 按需获取房型
     *
     * @param currentPage
     * @param pageSize
     * @param style
     * @return
     */
    @Override
    public IPage<Style> getAllStyle(int currentPage, int pageSize, Style style) {
        LambdaQueryWrapper<Style> lambdaQueryWrapper = new LambdaQueryWrapper<Style>();
        lambdaQueryWrapper.like(Strings.isNotEmpty(style.getStyleName()), Style::getStyleName, style.getStyleName());
        lambdaQueryWrapper.like(Strings.isNotEmpty(style.getBalcony()), Style::getBalcony, style.getBalcony());
        lambdaQueryWrapper.like(Strings.isNotEmpty(style.getFloor()), Style::getFloor, style.getFloor());
        IPage page = new Page(currentPage, pageSize);
        return styleMapper.selectPage(page, lambdaQueryWrapper);
    }

    /**
     * 修改状态操作
     *
     * @param styleId
     * @return
     */
    @Override
    @CacheEvict(cacheNames = "allArea", key = "1")
    public CommonResult resetStyle(Integer styleId, String state) {
        /**
         * 判断是否给予上架
         * 下架收回可用面积
         */

        Floor floor = floorMapper.selectById(1);
        Float floorArea = Float.valueOf(floor.getAvailable());

        Style style = styleMapper.selectById(styleId);
        Float styleArea = Float.valueOf(style.getArea());
        Float styleNum = Float.valueOf(style.getNum());


        if (state.equals("0")) {
            float result = floorArea + styleArea * styleNum;
            floor.setAvailable(String.valueOf(result));
            styleMapper.resetStyle(styleId, state);
            floorMapper.updateById(floor);
            return CommonResult.success();
        } else {
            float result = floorArea - styleArea * styleNum;
            if (result >= 0) {
                floor.setAvailable(String.valueOf(result));
                styleMapper.resetStyle(styleId, state);
                floorMapper.updateById(floor);
                return CommonResult.success();
            } else return CommonResult.failed("可用面积不足,请确认后重新录入");
        }

    }

    /**
     * 更新房型数据
     *
     * @param style
     * @return
     */
    @Override
    @CacheEvict(cacheNames = "allArea", key = "1")
    public CommonResult updateStyle(Style style) {
        /**
         * 修改状态
         */
        if (style.getState().equals("正常")) {
            style.setState("1");
        } else {
            style.setState("0");
        }
        /**
         * 获取更新前的数据
         */
        Style baseStyle = styleMapper.selectById(style);


        /**
         * 获取可用面积 计算是否>0
         * 先加上原来的房型总面积 再减去新的房型面积
         */
        Floor floor = floorMapper.selectById(1);
        Float floorArea = Float.valueOf(floor.getAvailable()); //空余面积


        Float styleArea = Float.valueOf(style.getArea());//新的面积
        Float styleNum = Float.valueOf(style.getNum());//新的数量

        Float baseArea = Float.valueOf(baseStyle.getArea());//旧的面积
        Float baseNum = Float.valueOf(baseStyle.getNum());//旧的数量

        Float result = floorArea + (baseArea * baseNum) - (styleArea * styleNum);//计算出新的剩余面积

        /**
         * 判断该房型是否被出租
         * 房型总数量 == 空余房型数量
         */
        if (baseStyle.getNum().equals(baseStyle.getAvailable())) {
            /**
             * 判断房型是否已经存在
             */
            System.out.println(1);
            if (styleMapper.selectSameStyle(style) == 0) {
                /**
                 * 如果修改的是下架的 直接给予修改
                 */
                System.out.println(2);
                if (style.getState().equals("0")) {
                    System.out.println(3);
                    style.setAvailable(style.getNum());
                    styleMapper.updateById(style);
                    return CommonResult.success();
                } else {
                    /**
                     * 判断面积是否充足
                     */
                    System.out.println(4);
                    if (result > 0) {
                        System.out.println(5);
                        floor.setAvailable(String.valueOf(result));
                        style.setAvailable(style.getNum());
                        styleMapper.updateById(style);
                        floorMapper.updateById(floor);
                        return CommonResult.success();
                    } else {
                        System.out.println(6);
                        return CommonResult.failed("可用面积不足,请确认后重新录入");
                    }
                }
            } else {
                System.out.println(7);
                return CommonResult.failed("存在此类型的房型，请重新确认后录入");
            }
        } else {
            /**
             *  如果数量与空余数量不相等 则判断修改的是什么 只允许修改房间数量 且房间数量 > 原来的房间数量 以及房型价格
             */
            System.out.println(8);
            if (baseStyle.getStyleName().equals(style.getStyleName()) && baseStyle.getArea().equals(style.getArea()) && baseStyle.getBalcony().equals(style.getBalcony()) && baseStyle.getFloor().equals(style.getFloor())) {
                /**
                 * 判断修改房间的数量是否符合要求
                 * num-available <= 新的房间数量
                 */
                System.out.println(9);
                float rent = Float.valueOf(baseStyle.getNum()) - Float.valueOf(baseStyle.getAvailable());
                float newAvailable = Float.valueOf(style.getNum()) - rent;
                if (newAvailable >= 0) {
                    System.out.println(10);
                    if (style.getState().equals("0")) {
                        System.out.println(11);
                        style.setAvailable(String.valueOf(newAvailable));
                        styleMapper.updateById(style);
                        return CommonResult.success();
                    } else {
                        /**
                         * 判断面积是否充足
                         */
                        System.out.println(12);
                        if (result > 0) {
                            System.out.println(13);
                            floor.setAvailable(String.valueOf(result));
                            style.setAvailable(String.valueOf(newAvailable));
                            System.out.println(style);
                            styleMapper.updateById(style);
                            floorMapper.updateById(floor);
                            return CommonResult.success();
                        } else {
                            System.out.println(14);
                            return CommonResult.failed("可用面积不足,请确认后重新录入");
                        }
                    }
                } else {
                    System.out.println(15);
                    return CommonResult.failed("修改的房型数量小于在租数量，不给予修改");
                }
            } else {
                System.out.println(16);
                return CommonResult.failed("已有该房型在出租，不允许修改除数量、租金以外的数据，请重新编辑");
            }
        }
    }

    /**
     * 获取下拉框
     *
     * @return
     */
    @Override
    public CommonResult getStyleName(Integer floorId) {
        return CommonResult.success(styleMapper.getStyleName(getFloor(floorId)));
    }

    /**
     * 获取面积下拉框
     *
     * @param StyleName
     * @param floorId
     * @return
     */
    @Override
    public CommonResult getStyleArea(String StyleName, Integer floorId) {
        return CommonResult.success(styleMapper.getStyleArea(StyleName, getFloor(floorId)));
    }

    /**
     * 获取房型阳台信息
     *
     * @param styleName
     * @param floorId
     * @param area
     * @return
     */
    @Override
    public CommonResult getStyleBalcony(String styleName, Integer floorId, String area) {
        return CommonResult.success(styleMapper.getStyleBalcony(styleName, getFloor(floorId), area));
    }

    /**
     * 获取房型剩余数量
     *
     * @param style
     * @param floorId
     * @return
     */
    @Override
    public CommonResult leftStyle(Style style, Integer floorId) {
        style.setFloor(getFloor(floorId));
        return CommonResult.success(styleMapper.leftStyle(style));
    }

    /**
     * 获取全部房型名称
     *
     * @return
     */
    @Override
    @Cacheable(cacheNames = "allStyleName")
    public CommonResult getAllStyleName() {
        return CommonResult.success(styleMapper.getAllStyleName());
    }

    /**
     * 转换为高低楼层
     *
     * @param floorId
     * @return
     */
    public static String getFloor(Integer floorId) {
        String floor = "";
        if (floorId > 6) {
            floor = "高";
        } else {
            floor = "低";
        }
//        System.out.println(floor);
        return floor;
    }

}


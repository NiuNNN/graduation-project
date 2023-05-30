package niuniu.javaweb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import niuniu.javaweb.mapper.FloorMapper;
import niuniu.javaweb.mapper.HouseMapper;
import niuniu.javaweb.mapper.StyleMapper;
import niuniu.javaweb.pojo.Floor;
import niuniu.javaweb.pojo.House;
import niuniu.javaweb.pojo.Style;
import niuniu.javaweb.service.HouseService;
import niuniu.javaweb.utils.StringUtils;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.utils.tools.HouseTools;
import niuniu.javaweb.vo.HouseVO;
import niuniu.javaweb.vo.NumVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service
 * @date 2023/3/21 10:00
 */
@Service
public class HouseServiceImpl extends ServiceImpl<HouseMapper, House> implements HouseService {

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private FloorMapper floorMapper;

    @Autowired
    private StyleMapper styleMapper;

    /**
     * 获取房间号
     *
     * @param floorId
     * @return
     */
    @Override
    @Cacheable(cacheNames = "getHouseNumber", key = "#floorId")
    public CommonResult<Object> getHouseNumber(Integer floorId) {
        String houseNumber = HouseTools.getHouseNumber(houseMapper.getHouseNumber(floorId));
//        System.out.println(houseNumber);
        if (StringUtils.isEmpty(houseNumber)) return CommonResult.failed("此层房间已经饱满，无法添加");
        else {
            String finalNumber = floorId + houseNumber;
//            System.out.println(finalNumber);
            return CommonResult.success(finalNumber);
        }
    }

    /**
     * 创建新的房间
     *
     * @param houseVO
     * @return
     */
    @Override

    @CacheEvict(cacheNames = {"allArea", "getHouseNumber", "houseDetail"}, key = "#houseVO.floorId")
    public CommonResult insertHouse(HouseVO houseVO) {
//        判断是否有空余房间
        Style style = styleMapper.selectById(houseVO.getStyleId());
        if (style.getNum().equals("0")) {
            return CommonResult.failed("该房型没有空余，请重新操作");
        } else {
            Floor floor = floorMapper.selectById(houseVO.getFloorId());
            float available = Float.valueOf(floor.getAvailable()) - Float.valueOf(houseVO.getArea());
            /**
             * 判断面积楼层面积是否足够
             */
            if (available < 0) {
                return CommonResult.failed("楼层面积不足");
            }
            /**
             * 改变该楼层的可用面积
             */

            floorMapper.updateAvailable(houseVO.getFloorId(), String.valueOf(available));
            /**
             * 改变房型数量
             */
            styleMapper.updateAvailable(houseVO.getStyleId());
            /**
             * 创建房间
             */
            houseMapper.insertNewHouse(houseVO);
            return CommonResult.success();
        }
    }

    /**
     * 获取房屋详细信息
     *
     * @param floorId
     * @return
     */
    @Override
    @Cacheable(cacheNames = "houseDetail", key = "#floorId")
    public CommonResult getHouseDetail(Integer floorId) {
        return CommonResult.success(houseMapper.getHouseDetail(floorId));
    }

    /**
     * 查询房间
     *
     * @param styleName
     * @param floor
     * @param balcony
     * @return
     */
    @Override
    public CommonResult getHouse(String styleName, String floor, String balcony) {
        return CommonResult.success(houseMapper.getHouse(styleName, floor, balcony));
    }

    /**
     * 修改房屋信息
     *
     * @param houseId
     * @param state
     * @return
     */
    @Override
    public int changeState(Integer houseId, String state) {
        return houseMapper.changeState(houseId, state);
    }

    /**
     * 删除房间
     *
     * @param houseVO
     * @return
     */
    @Override
    @Transactional
    @CacheEvict(cacheNames = {"allArea", "getHouseNumber", "houseDetail"}, key = "#houseVO.floorId")
    public CommonResult deleteHouse(HouseVO houseVO) {
//        System.out.println(houseVO);
        houseMapper.changeState(houseVO.getHouseId(), "删除");
        Floor floor = floorMapper.selectById(houseVO.getFloorId());
        float available = Float.valueOf(floor.getAvailable()) + Float.valueOf(houseVO.getArea());
        floorMapper.updateAvailable(houseVO.getFloorId(), String.valueOf(available));
        styleMapper.increaseAvailable(houseVO.getStyleId());
        return CommonResult.success();
    }

    /**
     * 获取用户住房信息
     *
     * @param userId
     * @return
     */
    @Override
    public CommonResult getHouseDetailByUserId(Integer userId) {
        return CommonResult.success(houseMapper.getHouseDetailByUserId(userId));
    }

    /**
     * 判断房间是否已租
     *
     * @param houseName
     * @return
     */
    @Override
    public CommonResult judgeHouseState(String houseName) {
        return CommonResult.success(houseMapper.judgeHouseState(houseName));
    }

    /**
     * 获取每层房间数
     *
     * @return
     */
    @Override
    public CommonResult getHouseNum() {
        List<NumVO> numVOList = new ArrayList<>();
        for (int i = 2; i <= 13; i++) {
            NumVO numVO = new NumVO();
            numVO.setFloorId(i);
            numVO.setAllNum(houseMapper.getFloorNum(i, null));
            numVO.setPeaceNum(houseMapper.getFloorNum(i, "已租"));
            numVOList.add(numVO);
        }
        return CommonResult.success(numVOList);
    }
}

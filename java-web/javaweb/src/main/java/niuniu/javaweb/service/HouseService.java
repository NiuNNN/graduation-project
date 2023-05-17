package niuniu.javaweb.service;

import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.vo.HouseVO;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service
 * @date 2023/3/21 10:00
 */
public interface HouseService {

    /**
     * 获取房号
     *
     * @param floorId
     * @return
     */
    CommonResult getHouseNumber(Integer floorId);

    /**
     * 创建新的房间
     *
     * @param houseVO
     * @return
     */
    CommonResult insertHouse(HouseVO houseVO);

    /**
     * 获取房屋详细
     *
     * @param floorId
     * @return
     */
    CommonResult getHouseDetail(Integer floorId);

    /**
     * 查询房间
     *
     * @param styleName
     * @param floor
     * @param balcony
     * @return
     */
    CommonResult getHouse(String styleName, String floor, String balcony);

    /**
     * 修改房屋状态
     *
     * @param houseId
     * @param state
     * @return
     */
    int changeState(Integer houseId, String state);

    /**
     * 删除房间
     *
     * @param houseVO
     * @return
     */
    CommonResult deleteHouse(HouseVO houseVO);

    /**
     * 获取用户住房信息
     *
     * @param userId
     * @return
     */
    CommonResult getHouseDetailByUserId(Integer userId);

    /**
     * 判断房间是否已出租
     *
     * @param houseName
     * @return
     */
    CommonResult judgeHouseState(String houseName);
}

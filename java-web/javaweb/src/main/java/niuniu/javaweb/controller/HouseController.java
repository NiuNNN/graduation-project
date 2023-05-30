package niuniu.javaweb.controller;

import niuniu.javaweb.service.HouseService;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.vo.HouseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.controller
 * @date 2023/3/21 12:39
 */
@RestController
public class HouseController {
    @Autowired
    HouseService houseService;

    /**
     * 获取房号
     *
     * @param floorId
     * @return
     */
    @GetMapping("/getHouseNumber/{floorId}")
    public CommonResult getHouseNumber(@PathVariable Integer floorId) {
        return houseService.getHouseNumber(floorId);
    }

    /**
     * 新建房间
     *
     * @param houseVO
     * @return
     */
    @PostMapping("/insertHouse")
    public CommonResult insertHouse(HouseVO houseVO) {
        return houseService.insertHouse(houseVO);
    }

    /**
     * 获取房间的详细信息
     *
     * @param floorId
     * @return
     */
    @GetMapping("/getHouseDetail/{floorId}")
    public CommonResult getHouseDetail(@PathVariable Integer floorId) {
        return houseService.getHouseDetail(floorId);
    }

    /**
     * 查询房间
     *
     * @param styleName
     * @param floor
     * @param balcony
     * @return
     */
    @GetMapping("/getHouse")
    public CommonResult getHouse(String styleName, String floor, String balcony) {
//        System.out.println(styleName);
//        System.out.println(floor);
//        System.out.println(balcony);
        return houseService.getHouse(styleName, floor, balcony);
    }

    /**
     * 删除房间
     *
     * @param houseVO
     * @return
     */
    @GetMapping("deleteHouse")
    public CommonResult deleteHouse(HouseVO houseVO) {
        return houseService.deleteHouse(houseVO);
    }

    /**
     * 获取用户住房信息
     *
     * @param userId
     * @return
     */
    @GetMapping("getHouseDetailByUserId")
    public CommonResult getHouseDetailByUserId(Integer userId) {
        return houseService.getHouseDetailByUserId(userId);
    }

    /**
     * 判断房间是否已出租
     *
     * @param houseName
     * @return
     */
    @GetMapping("judgeHouseState")
    public CommonResult judgeHouseState(String houseName) {
        return houseService.judgeHouseState(houseName);
    }

    @GetMapping("getHouseNum")
    public CommonResult getHouseNum() {
        return houseService.getHouseNum();
    }
}

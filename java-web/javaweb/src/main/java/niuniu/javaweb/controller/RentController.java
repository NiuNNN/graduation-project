package niuniu.javaweb.controller;

import niuniu.javaweb.pojo.Rent;
import niuniu.javaweb.service.BasicService;
import niuniu.javaweb.service.HouseService;
import niuniu.javaweb.service.RentService;
import niuniu.javaweb.utils.result.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.controller
 * @date 2023/4/6 19:04
 */
@RestController
public class RentController {

    @Autowired
    RentService rentService;

    @Autowired
    BasicService basicService;

    @Autowired
    HouseService houseService;

    /**
     * 插入杂费 修改房屋信息
     *
     * @param rent
     * @param basicList
     * @return
     */
    @PostMapping("insertRent")
    public CommonResult insertRent(Rent rent, String basicList) {
        rent.setDeposit(rent.getDeposit().replaceAll(",", ""));
        Integer rentId = rentService.insertRent(rent);
        if (rentId <= 0) {
            return CommonResult.failed();
        }
        if (houseService.changeState(rent.getHouseId(), "已租") <= 0) {
            return CommonResult.failed();
        }
        return basicService.insertMiscellaneous(rentId, basicList);
    }

    /**
     * 修改房间信息
     *
     * @param rent
     * @param oldHouseId
     * @return
     */
    @PostMapping("changeRent")
    @Transactional
    public CommonResult changeRent(Rent rent, Integer oldHouseId) {
        rent.setDeposit(rent.getDeposit().replaceAll(",", ""));
        if (houseService.changeState(rent.getHouseId(), "已租") <= 0) {
            return CommonResult.failed();
        }
        if (houseService.changeState(oldHouseId, "空闲") <= 0) {
            return CommonResult.failed();
        }
        return rentService.changeRent(rent);
    }

    /**
     * 获取rentId通过userId
     *
     * @param userId
     * @return
     */
    @GetMapping("getRentIdByUserId")
    public CommonResult getRentIdByUserId(Integer userId) {
        return rentService.getRentIdByUserId(userId);
    }
}

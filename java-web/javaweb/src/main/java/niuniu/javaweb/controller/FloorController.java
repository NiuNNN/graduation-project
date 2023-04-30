package niuniu.javaweb.controller;

import niuniu.javaweb.service.FloorService;
import niuniu.javaweb.utils.result.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.controller
 * @date 2023/3/20 16:08
 */
@RestController
public class FloorController {
    @Autowired
    FloorService floorService;

    /**
     * 获取面积大小
     *
     * @return
     */
    @GetMapping("/getArea/{floorId}")
    public CommonResult getArea(@PathVariable Integer floorId) {
        return floorService.getArea(floorId);
    }

    /**
     * 获取楼层数
     *
     * @return
     */
    @GetMapping("/getFloor")
    public CommonResult getFloor() {
        return floorService.getFloor();
    }
}

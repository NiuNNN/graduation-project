package niuniu.javaweb.service;

import niuniu.javaweb.utils.result.CommonResult;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service
 * @date 2023/3/20 16:05
 */
public interface FloorService {
    /**
     * 获取楼层面积
     *
     * @param floorId
     * @return
     */
    CommonResult getArea(Integer floorId);

    /**
     * 获取楼层数
     *
     * @return
     */
    CommonResult getFloor();
}

package niuniu.javaweb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import niuniu.javaweb.mapper.FloorMapper;
import niuniu.javaweb.pojo.Floor;
import niuniu.javaweb.service.FloorService;
import niuniu.javaweb.utils.result.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service.impl
 * @date 2023/3/20 16:05
 */
@Service
public class FloorServiceImpl extends ServiceImpl<FloorMapper, Floor> implements FloorService {
    @Autowired
    FloorMapper floorMapper;

    /**
     * 获取面积
     *
     * @return
     */
    @Override
    @Cacheable(cacheNames = "allArea", key = "#floorId")
    public CommonResult getArea(Integer floorId) {
        return CommonResult.success(floorMapper.selectById(floorId));
    }

    /**
     * 获取楼层数
     *
     * @return
     */
    @Override
    @Cacheable(cacheNames = "allFloor")
    public CommonResult getFloor() {
        return CommonResult.success(floorMapper.getFloor());
    }
}

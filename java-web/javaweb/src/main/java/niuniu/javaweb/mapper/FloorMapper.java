package niuniu.javaweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import niuniu.javaweb.pojo.Floor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.mapper
 * @date 2023/3/20 16:04
 */
@Mapper
public interface FloorMapper extends BaseMapper<Floor> {
    /**
     * 修改楼层的可用面积
     *
     * @param floorId
     * @param available
     * @return
     */
    int updateAvailable(Integer floorId, String available);

    /**
     * 获取楼层数
     *
     * @return
     */
    List<Floor> getFloor();
}

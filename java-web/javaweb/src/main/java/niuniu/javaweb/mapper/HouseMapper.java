package niuniu.javaweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import niuniu.javaweb.pojo.House;
import niuniu.javaweb.vo.CostVo;
import niuniu.javaweb.vo.HouseVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.mapper
 * @date 2023/3/21 9:59
 */
@Mapper
public interface HouseMapper extends BaseMapper<House> {

    /**
     * 创建新的房间
     *
     * @param houseVO
     * @return
     */
    int insertNewHouse(HouseVO houseVO);

    /**
     * 获取房屋的详细信息
     *
     * @param floorId
     * @return
     */
    List<HouseVO> getHouseDetail(Integer floorId);

    /**
     * 根据楼层获取房号
     *
     * @param floorId
     * @return
     */
    String[] getHouseNumber(Integer floorId);

    /**
     * 查询房间
     *
     * @param styleName
     * @param floor
     * @param balcony
     * @return
     */
    List<HouseVO> getHouse(String styleName, String floor, String balcony);

    /**
     * 修改房屋状态
     *
     * @param houseId
     * @param state
     * @return
     */
    int changeState(Integer houseId, String state);

    /**
     * 获取全部房间名称
     *
     * @return
     */
    List<CostVo> getAllHouseNameForCost();

    /**
     * 获取房间价格
     *
     * @param costId
     * @return
     */
    String getHousePriceByCostId(Integer costId);

    /**
     * 获取用户房屋信息
     *
     * @param userId
     * @return
     */
    HouseVO getHouseDetailByUserId(Integer userId);

    /**
     * 查询房间是否为空
     *
     * @param houseName
     * @return
     */
    int judgeHouseState(String houseName);

    /**
     * 获取房间数量
     *
     * @return
     */
    int getNum();
}


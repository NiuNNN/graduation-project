package niuniu.javaweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import niuniu.javaweb.pojo.Rent;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.mapper
 * @date 2023/4/6 19:00
 */
@Mapper
public interface RentMapper extends BaseMapper<Rent> {
    /**
     * 插入新的居住信息
     *
     * @param rent
     * @return
     */
    int insertRent(Rent rent);

    /**
     * 修改房间信息
     *
     * @param rent
     * @return
     */
    int changeRent(Rent rent);

    /**
     * 修改总价
     *
     * @param rentId
     * @param deposit
     * @return
     */
    int updateDeposit(Integer rentId, String deposit);

    /**
     * 退房操作
     *
     * @param rentId
     * @return
     */
    int logout(Integer rentId);

    Integer getRentIdByHouseName(String houseName);
}

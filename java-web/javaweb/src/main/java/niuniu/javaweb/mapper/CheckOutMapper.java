package niuniu.javaweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import niuniu.javaweb.pojo.CheckOut;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.mapper
 * @date 2023/4/19 18:23
 */
@Mapper
public interface CheckOutMapper extends BaseMapper<CheckOut> {

    /**
     * 退房申请
     *
     * @param userId
     * @param rentId
     * @param message
     * @return
     */
    int insertCheckOut(Integer userId, Integer rentId, String message);

    /**
     * 处理退房
     *
     * @param checkOut
     * @return
     */
    int updateCheckOut(CheckOut checkOut);

    CheckOut getCheckOutState(Integer userId);
}

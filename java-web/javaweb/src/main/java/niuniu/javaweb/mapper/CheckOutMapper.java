package niuniu.javaweb.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import niuniu.javaweb.pojo.CheckOut;
import niuniu.javaweb.vo.CheckOutVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 获取全部
     *
     * @param time
     * @param houseName
     * @param page
     * @param wrapper
     * @return
     */
    IPage<CheckOutVO> getAllCheckOut(@Param("time") String time, @Param("houseName") String houseName, @Param("page") Page<CheckOutVO> page, @Param(Constants.WRAPPER) Wrapper<CheckOutVO> wrapper);

    /**
     * 撤销申请
     *
     * @param checkOut
     * @return
     */
    int cancelCheckout(CheckOut checkOut);

    /**
     * 获取退房详细信息
     *
     * @param checkoutId
     * @return
     */
    CheckOutVO getCheckOutDetail(Integer checkoutId);
}

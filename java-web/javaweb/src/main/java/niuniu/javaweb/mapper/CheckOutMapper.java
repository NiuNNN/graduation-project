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

import java.util.List;

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
     * @param checkOut
     * @return
     */
    int insertCheckOut(CheckOut checkOut);

    /**
     * 处理退房
     *
     * @param checkOut
     * @return
     */
    int updateCheckOut(CheckOut checkOut);

    /**
     * 获取退房状态
     *
     * @param userId
     * @return
     */
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

    /**
     * 判断是否有对应未处理的房间
     *
     * @param houseName
     * @return
     */
    List<CheckOut> judgeHasCheckOut(String houseName);

    /**
     * 获取申请退房数量
     *
     * @return
     */
    int getNum();
}

package niuniu.javaweb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import niuniu.javaweb.pojo.Basic;
import niuniu.javaweb.utils.result.CommonResult;

import java.text.ParseException;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service
 * @date 2023/4/2 11:53
 */
public interface BasicService {
    /**
     * 新建收费标准
     *
     * @param basic
     * @return
     */
    CommonResult insertBasic(Basic basic);

    /**
     * 获取全部收费标准
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    IPage<Basic> getAllBasic(int currentPage, int pageSize);

    /**
     * 获取除了水电以外的杂费
     *
     * @return
     */
    CommonResult getBasic();

    /**
     * 插入用户杂费
     *
     * @param rentId
     * @param basicList
     * @return
     */
    CommonResult insertMiscellaneous(Integer rentId, String basicList);

    /**
     * 插入杂费
     *
     * @param rentId
     * @param baseId
     * @return
     */
    CommonResult insertMiscellaneous(Integer rentId, Integer baseId);

    /**
     * 获取用户杂费
     *
     * @param userId
     * @return
     */
    CommonResult getBasicByUserId(Integer userId);

    /**
     * 改变杂费状态
     *
     * @param miscellaneousId
     * @param state
     * @return
     */
    CommonResult changeMiscellaneousState(Integer miscellaneousId, Integer state);

    /**
     * 获取未选择的杂费信息
     *
     * @param rentId
     * @return
     */
    CommonResult getNoBasicByRentId(Integer rentId);

    /**
     * 删除基本杂费
     *
     * @param baseId
     * @return
     */
    CommonResult deleteBasic(Integer baseId);

    /**
     * 更新收费信息
     *
     * @param basic
     * @return
     */
    CommonResult updateBasic(Basic basic);

    /**
     * 对用户该月的杂费进行切片存储
     *
     * @param rentId
     * @param nowTime
     * @return
     */
    String getBasicByRentId(Integer rentId, String nowTime) throws ParseException;

    /**
     * 获取基本收费
     *
     * @param basic
     * @param nowTime
     * @return
     */
    String getActuallyPrice(Basic basic, String nowTime) throws ParseException;

    /**
     * 获取付款时对应的杂费
     *
     * @param orderId
     * @return
     */
    CommonResult getOrderMis(Integer orderId);
}

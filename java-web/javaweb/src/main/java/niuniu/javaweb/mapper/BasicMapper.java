package niuniu.javaweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import niuniu.javaweb.pojo.Basic;
import niuniu.javaweb.vo.BasicVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.mapper
 * @date 2023/4/2 11:53
 */
@Mapper
public interface BasicMapper extends BaseMapper<Basic> {

    /**
     * 新建收费标准
     *
     * @param basic
     * @return
     */
    @Override
    int insert(Basic basic);

    /**
     * 获取除水电以外的收费标准
     *
     * @return
     */
    List<Basic> getBasic();

    /**
     * 插入杂费
     *
     * @param rentId
     * @param baseId
     * @return
     */
    int insertMiscellaneous(Integer rentId, String baseId);

    /**
     * 获取住户的杂费
     *
     * @param userId
     * @return
     */
    List<BasicVO> getBasicByUserId(Integer userId);

    /**
     * 改变杂费状态
     * 0：取消  1：在使用 2 ：直至下月取消
     *
     * @param miscellaneousId
     * @param state
     * @return
     */
    int changeMiscellaneousState(Integer miscellaneousId, Integer state);

    /**
     * 获取未选择的杂费信息
     *
     * @param rentId
     * @return
     */
    List<Basic> getNoBasicByRentId(Integer rentId);

    /**
     * 删除基本杂费
     *
     * @param baseId
     * @return
     */
    int deleteBasic(Integer baseId);

    /**
     * 删除用户杂费
     *
     * @param baseId
     * @return
     */
    int deleteMiscellaneous(Integer baseId);


    /**
     * 更新基本杂费信息
     *
     * @param basic
     * @return
     */
    int updateBasic(Basic basic);


    /**
     * 修改状态 把2状态的置为0
     *
     * @param rentId
     * @return
     */
    int changeMiscellaneousStateByRentId(Integer rentId);

    /**
     * 修改basic 状态为2的置为0
     *
     * @return
     */
    int changeBasicState();

    /**
     * 获取用户的杂费信息进行切片存储
     *
     * @param rentId
     * @return
     */
    List<Basic> getBasicByRentId(Integer rentId);
}
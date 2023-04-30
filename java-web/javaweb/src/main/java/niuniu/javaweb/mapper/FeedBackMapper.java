package niuniu.javaweb.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import niuniu.javaweb.pojo.FeedBack;
import niuniu.javaweb.vo.FeedBackVO;
import niuniu.javaweb.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.mapper
 * @date 2023/4/24 21:41
 */
@Mapper
public interface FeedBackMapper extends BaseMapper<FeedBack> {
    /**
     * 存入用户反馈
     *
     * @param feedBack
     * @return
     */
    int insertFeedBack(FeedBack feedBack);

    /**
     * 获取用户反馈
     *
     * @param page
     * @param wrapper
     * @return
     */
    IPage<FeedBackVO> getFeedBack(@Param("page") Page<FeedBackVO> page, @Param(Constants.WRAPPER) Wrapper<UserVO> wrapper);

    /**
     * 已阅
     *
     * @param feedbackId
     * @return
     */
    int checkFeedBack(Integer feedbackId);
}

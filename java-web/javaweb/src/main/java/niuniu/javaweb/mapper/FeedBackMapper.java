package niuniu.javaweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import niuniu.javaweb.pojo.FeedBack;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
     * @return
     */
    List<FeedBack> getFeedBack();

    /**
     * 已阅
     *
     * @param feedbackId
     * @return
     */
    int checkFeedBack(Integer feedbackId);
}

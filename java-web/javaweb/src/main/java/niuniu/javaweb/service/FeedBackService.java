package niuniu.javaweb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import niuniu.javaweb.pojo.FeedBack;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.vo.FeedBackVO;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service
 * @date 2023/4/24 21:42
 */
public interface FeedBackService {
    /**
     * 存入用户反馈
     *
     * @param feedBack
     * @return
     */
    CommonResult insertFeedBack(FeedBack feedBack);

    /**
     * 获取用户反馈
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    IPage<FeedBackVO> getFeedBack(int currentPage, int pageSize);

    /**
     * 已读反馈
     *
     * @param feedbackId
     * @return
     */
    CommonResult checkFeedBack(Integer feedbackId);
}

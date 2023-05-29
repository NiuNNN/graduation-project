package niuniu.javaweb.service;

import niuniu.javaweb.pojo.FeedBack;
import niuniu.javaweb.utils.result.CommonResult;

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
     * @return
     */
    CommonResult getFeedBack();

    /**
     * 已读反馈
     *
     * @param feedbackId
     * @return
     */
    CommonResult checkFeedBack(Integer feedbackId);

    /**
     * 获取数值
     *
     * @return
     */
    CommonResult getNum();
}

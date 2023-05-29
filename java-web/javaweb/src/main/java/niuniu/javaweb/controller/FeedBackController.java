package niuniu.javaweb.controller;

import niuniu.javaweb.pojo.FeedBack;
import niuniu.javaweb.service.FeedBackService;
import niuniu.javaweb.utils.result.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.controller
 * @date 2023/4/24 21:43
 */
@RestController
public class FeedBackController {
    @Autowired
    FeedBackService feedBackService;

    /**
     * 存入用户反馈
     *
     * @param feedBack
     * @return
     */
    @PostMapping("/insertFeedBack")
    public CommonResult insertFeedBack(FeedBack feedBack) {
        return feedBackService.insertFeedBack(feedBack);
    }

    /**
     * 获取用户反馈
     *
     * @return
     */
    @GetMapping("/getFeedBack")
    public CommonResult getFeedBack() {
        return feedBackService.getFeedBack();
    }

    /**
     * 已读反馈
     *
     * @param feedbackId
     * @return
     */
    @GetMapping("/checkFeedBack")
    public CommonResult checkFeedBack(Integer feedbackId) {
        return feedBackService.checkFeedBack(feedbackId);
    }

    /**
     * 获取数值
     *
     * @return
     */
    @GetMapping("getNum")
    public CommonResult getNum() {
        return feedBackService.getNum();
    }
}

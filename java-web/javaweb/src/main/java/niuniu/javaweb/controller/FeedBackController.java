package niuniu.javaweb.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import niuniu.javaweb.pojo.FeedBack;
import niuniu.javaweb.service.FeedBackService;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.vo.FeedBackVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/getFeedBack/{currentPage}/{pageSize}")
    public CommonResult getFeedBack(@PathVariable int currentPage, @PathVariable int pageSize) {
        IPage<FeedBackVO> page = feedBackService.getFeedBack(currentPage, pageSize);
        //如果当前页码大于了总页码值，那么重新执行查询操作，使用最大页码值作为当前页码值
        if (currentPage > page.getPages()) {
            page = feedBackService.getFeedBack((int) page.getPages(), pageSize);
        }
        return CommonResult.success(page);
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
}

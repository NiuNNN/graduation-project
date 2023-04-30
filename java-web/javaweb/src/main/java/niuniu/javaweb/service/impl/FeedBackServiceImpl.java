package niuniu.javaweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import niuniu.javaweb.mapper.FeedBackMapper;
import niuniu.javaweb.pojo.FeedBack;
import niuniu.javaweb.service.FeedBackService;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.vo.FeedBackVO;
import niuniu.javaweb.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service.impl
 * @date 2023/4/24 21:42
 */
@Service
public class FeedBackServiceImpl extends ServiceImpl<FeedBackMapper, FeedBack> implements FeedBackService {
    @Autowired
    FeedBackMapper feedBackMapper;

    /**
     * 存入用户反馈
     *
     * @param feedBack
     * @return
     */
    @Override
    public CommonResult insertFeedBack(FeedBack feedBack) {
        return feedBackMapper.insertFeedBack(feedBack) > 0 ? CommonResult.success() : CommonResult.failed();
    }

    /**
     * 获取用户反馈
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public IPage<FeedBackVO> getFeedBack(int currentPage, int pageSize) {
        Page<FeedBackVO> page = new Page<>(currentPage, pageSize);
        QueryWrapper<UserVO> queryWrapper = new QueryWrapper<>();
        return feedBackMapper.getFeedBack(page, queryWrapper);
    }

    /**
     * 已读反馈
     *
     * @param feedbackId
     * @return
     */
    @Override
    public CommonResult checkFeedBack(Integer feedbackId) {
        return feedBackMapper.checkFeedBack(feedbackId) > 0 ? CommonResult.success() : CommonResult.failed();
    }
}

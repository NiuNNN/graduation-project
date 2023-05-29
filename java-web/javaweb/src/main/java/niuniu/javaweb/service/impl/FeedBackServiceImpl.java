package niuniu.javaweb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import niuniu.javaweb.mapper.*;
import niuniu.javaweb.pojo.FeedBack;
import niuniu.javaweb.service.FeedBackService;
import niuniu.javaweb.utils.result.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service.impl
 * @date 2023/4/24 21:42
 */
@Service
public class FeedBackServiceImpl extends ServiceImpl<FeedBackMapper, FeedBack> implements FeedBackService {
    @Autowired
    FeedBackMapper feedBackMapper;

    @Autowired
    HouseMapper houseMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RepairMapper repairMapper;

    @Autowired
    CheckOutMapper checkOutMapper;

    @Autowired
    IpMapper ipMapper;

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
     * @return
     */
    @Override
    public CommonResult getFeedBack() {
        return CommonResult.success(feedBackMapper.getFeedBack());
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

    /**
     * 获取数值
     *
     * @return
     */
    @Override
    public CommonResult getNum() {
        HashMap<String, Integer> map = new HashMap<>();
        //房间数量
        map.put("houseNum", houseMapper.getNum());
        //住户数量
        map.put("userNum", userMapper.getNum());
        //退房数量
        map.put("outNum", checkOutMapper.getNum());
        //维修数量
        map.put("repairNum", repairMapper.getNum());
        //获取今天访问量
        map.put("ipNum", ipMapper.getNum());
        return CommonResult.success(map);
    }
}

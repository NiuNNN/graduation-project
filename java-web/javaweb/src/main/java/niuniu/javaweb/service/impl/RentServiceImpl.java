package niuniu.javaweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import niuniu.javaweb.mapper.RentMapper;
import niuniu.javaweb.pojo.Rent;
import niuniu.javaweb.service.RentService;
import niuniu.javaweb.utils.result.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service.impl
 * @date 2023/4/6 19:01
 */
@Service
public class RentServiceImpl extends ServiceImpl<RentMapper, Rent> implements RentService {

    @Autowired
    RentMapper rentMapper;

    /**
     * 存入居住信息
     *
     * @param rent
     * @return
     */
    @Override
    public Integer insertRent(Rent rent) {
        rentMapper.insertRent(rent);
        return rent.getRentId();
    }

    /**
     * 修改房间信息
     *
     * @param rent
     * @return
     */
    @Override
    public CommonResult changeRent(Rent rent) {
        return rentMapper.changeRent(rent) > 0 ? CommonResult.success() : CommonResult.failed();
    }

    /**
     * 获取rentId通过userId
     *
     * @param userId
     * @return
     */
    @Override
    public CommonResult getRentIdByUserId(Integer userId) {
        QueryWrapper<Rent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return CommonResult.success(rentMapper.selectOne(queryWrapper));
    }
}

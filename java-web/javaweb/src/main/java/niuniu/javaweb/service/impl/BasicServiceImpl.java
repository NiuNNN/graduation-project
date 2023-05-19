package niuniu.javaweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import niuniu.javaweb.mapper.BasicMapper;
import niuniu.javaweb.mapper.OrderMapper;
import niuniu.javaweb.pojo.Basic;
import niuniu.javaweb.service.BasicService;
import niuniu.javaweb.utils.DateUtil;
import niuniu.javaweb.utils.StringUtils;
import niuniu.javaweb.utils.result.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service.impl
 * @date 2023/4/2 11:53
 */
@Service
public class BasicServiceImpl extends ServiceImpl<BasicMapper, Basic> implements BasicService {
    @Autowired
    BasicMapper basicMapper;

    @Autowired
    OrderMapper orderMapper;

    /**
     * 新建收费标注
     *
     * @param basic
     * @return
     */
    @Override
    @CacheEvict(cacheNames = "getBasic")
    public CommonResult insertBasic(Basic basic) {
        QueryWrapper<Basic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("base_name", basic.getBaseName()).ne("state", 0);
        if (basicMapper.selectCount(queryWrapper) > 0) return CommonResult.failed("存在该项收费标准,请重新填写...");
        return basicMapper.insert(basic) > 0 ? CommonResult.success() : CommonResult.failed();
    }

    /**
     * 获取全部收费标准
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public IPage<Basic> getAllBasic(int currentPage, int pageSize) {
        Page<Basic> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Basic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", 1);
        return basicMapper.selectPage(page, queryWrapper);
    }

    /**
     * 获取除了水电以外的杂费
     *
     * @return
     */
    @Override
    @Cacheable(cacheNames = "getBasic")
    public CommonResult getBasic() {
        return CommonResult.success(basicMapper.getBasic());
    }

    /**
     * 插入用户杂费
     *
     * @param rentId
     * @param basicList
     * @return
     */
    @Override
    public CommonResult insertMiscellaneous(Integer rentId, String basicList) {
        if (basicList.length() > 0) {
            String[] strings = basicList.split(",");
            for (int i = 0; i < strings.length; i++) {
                basicMapper.insertMiscellaneous(rentId, strings[i]);
            }
        }
        return CommonResult.success(rentId);
    }

    /**
     * 插入杂费
     *
     * @param rentId
     * @param baseId
     * @return
     */
    @Override
    public CommonResult insertMiscellaneous(Integer rentId, Integer baseId) {
        return basicMapper.insertMiscellaneous(rentId, String.valueOf(baseId)) > 0 ? CommonResult.success() : CommonResult.failed();
    }

    /**
     * 获取用户杂费
     *
     * @param userId
     * @return
     */
    @Override
    public CommonResult getBasicByUserId(Integer userId) {
        return CommonResult.success(basicMapper.getBasicByUserId(userId));
    }

    /**
     * 改变杂费状态
     *
     * @param miscellaneousId
     * @param state
     * @return
     */
    @Override
    public CommonResult changeMiscellaneousState(Integer miscellaneousId, Integer state) {
        return basicMapper.changeMiscellaneousState(miscellaneousId, state) > 0 ? CommonResult.success() : CommonResult.failed();
    }

    /**
     * 获取未选择的杂费信息
     *
     * @param rentId
     * @return
     */
    @Override
    public CommonResult getNoBasicByRentId(Integer rentId) {
        return CommonResult.success(basicMapper.getNoBasicByRentId(rentId));
    }

    /**
     * 删除基本杂费
     *
     * @param baseId
     * @return
     */
    @Override
    @Transactional
    public CommonResult deleteBasic(Integer baseId) {
        System.out.println("【进入到这里】");
        if (baseId > 2) {
            System.out.println(baseId);
            int i = basicMapper.deleteBasic(baseId);
            System.out.println(i);
            basicMapper.deleteMiscellaneous(baseId);
            return CommonResult.success();

        } else {
            return CommonResult.failed("不允许删除此项收费");
        }
    }

    /**
     * 更新收费信息
     *
     * @param basic
     * @return
     */
    @Override
    public CommonResult updateBasic(Basic basic) {
        Basic basic1 = basicMapper.selectById(basic.getBaseId());
        if (!basic1.getPrice().equals(basic.getPrice())) {
            basic.setOldPrice(basic1.getPrice());
        }
        return basicMapper.updateBasic(basic) > 0 ? CommonResult.success() : CommonResult.failed();
    }

    /**
     * 对用户该月的杂费进行切片存储
     *
     * @param rentId
     * @param nowTime
     * @return
     */
    @Override
    public String getBasicByRentId(Integer rentId, String nowTime) throws ParseException {
        List<Basic> basicList = basicMapper.getBasicByRentId(rentId);
        String misPrice = "";
        if (basicList.size() > 0) {
            for (int i = 0; i < basicList.size(); i++) {
                if (i != 0) {
                    misPrice += "_";
                }
                misPrice += basicList.get(i).getBaseId() + "_";
                misPrice += getActuallyPrice(basicList.get(i), nowTime);
            }
        }
        return misPrice;
    }

    /**
     * 获取基本收费
     *
     * @param basic
     * @param nowTime
     * @return
     */
    @Override
    public String getActuallyPrice(Basic basic, String nowTime) throws ParseException {
        /**
         * 判断是否是及时生效 是 取新值
         * 否 判断base时间和账单时间 是 取旧值
         * 否 取新值
         */
        if (basic.getOperation().equals("1")) {
            return basic.getPrice();
        } else {
            if (DateUtil.compareMonth(basic.getTime(), nowTime)) {
                return basic.getOldPrice();
            } else {
                return basic.getPrice();
            }
        }
    }

    /**
     * 获取付款时对应的杂费
     *
     * @param orderId
     * @return
     */
    @Override
    public CommonResult getOrderMis(Integer orderId) {
        String misPrice = orderMapper.getMisPrice(orderId);
        if (StringUtils.isEmpty(misPrice)) return CommonResult.success();
        List<Basic> basicList = new ArrayList<>();
        String[] arr = misPrice.split("_");
        for (String s : arr) {
            System.out.println(s);
        }
        for (int i = 0; i < arr.length; i += 2) {
            Basic basic = basicMapper.selectById(arr[i]);
            basic.setRemark("每月" + basic.getBaseName() + "收费￥" + arr[i + 1] + "元");
            basic.setPrice(arr[i + 1]);
            basicList.add(basic);
        }
        return CommonResult.success(basicList);
    }
}

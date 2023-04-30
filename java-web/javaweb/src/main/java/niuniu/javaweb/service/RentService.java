package niuniu.javaweb.service;

import niuniu.javaweb.pojo.Rent;
import niuniu.javaweb.utils.result.CommonResult;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service
 * @date 2023/4/6 19:01
 */
public interface RentService {
    /**
     * /插入新的租住信息
     *
     * @param rent
     * @return
     */
    Integer insertRent(Rent rent);

    /**
     * 修改房间信息
     *
     * @param rent
     * @return
     */
    CommonResult changeRent(Rent rent);

    /**
     * 获取rentId通过userId
     *
     * @param userId
     * @return
     */
    CommonResult getRentIdByUserId(Integer userId);
}

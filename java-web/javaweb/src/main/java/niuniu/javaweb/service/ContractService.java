package niuniu.javaweb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import niuniu.javaweb.pojo.Contract;
import niuniu.javaweb.pojo.Style;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.vo.RentContractVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service
 * @date 2023/3/31 12:14
 */
public interface ContractService {

    /**
     * 获取合同模板
     *
     * @return
     */
    CommonResult getBaseContract();

    /**
     * 获取全部合同
     *
     * @param currentPage
     * @param pageSize
     * @param contract
     * @return
     */
    IPage<Style> selectContract(int currentPage, int pageSize, Contract contract);

    /**
     * 填写合同
     *
     * @param rentContractVo
     * @return
     */
    CommonResult writeRentContract(RentContractVo rentContractVo) throws IOException;

    /**
     * 插入签名
     *
     * @param file
     * @param rentContractVoStr
     * @param url
     * @return
     * @throws IOException
     */
    CommonResult writeSign(MultipartFile file, String rentContractVoStr, String url) throws IOException;

    /**
     * 修改原来的合同状态
     *
     * @param userId
     * @return
     */
    CommonResult changeStateByUserId(Integer userId);

    /**
     * 获取用户合同信息
     *
     * @param userId
     * @return
     */
    CommonResult getContractByUserId(Integer userId);
}

package niuniu.javaweb.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import niuniu.javaweb.pojo.Contract;
import niuniu.javaweb.pojo.Style;
import niuniu.javaweb.service.ContractService;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.vo.RentContractVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.controller
 * @date 2023/3/31 12:19
 */
@RestController
public class ContractController {

    @Autowired
    ContractService contractService;

    /**
     * 获取合同模板
     *
     * @return
     */
    @GetMapping("/getBaseContract")
    public CommonResult getBaseContract() {
        return contractService.getBaseContract();
    }

    /**
     * 按需获取合同
     *
     * @param currentPage
     * @param pageSize
     * @param contract
     * @return
     */
    @GetMapping("/selectContract/{currentPage}/{pageSize}")
    public CommonResult selectContract(@PathVariable int currentPage, @PathVariable int pageSize, Contract contract) {
        IPage<Style> page = contractService.selectContract(currentPage, pageSize, contract);
        //如果当前页码大于了总页码值，那么重新执行查询操作，使用最大页码值作为当前页码值
        if (currentPage > page.getPages()) {
            page = contractService.selectContract((int) page.getPages(), pageSize, contract);
        }
        return CommonResult.success(page);
    }

    /**
     * 填写合同
     *
     * @param rentContractVo
     * @return
     */
    @PostMapping("/writeRentContract")
    public CommonResult writeRentContract(RentContractVo rentContractVo) throws IOException {
        return contractService.writeRentContract(rentContractVo);
    }

    /**
     * 生成合同
     *
     * @param file
     * @param rentContractVoStr
     * @param url
     * @return
     * @throws IOException
     */
    @PostMapping("/writeSign")
    public CommonResult writeSign(@RequestParam("file") MultipartFile file, @RequestParam("param") String rentContractVoStr, @RequestParam("url") String url) throws

            IOException {
        return contractService.writeSign(file, rentContractVoStr, url);
    }

    /**
     * 修改合同状态
     *
     * @param userId
     * @return
     */
    @GetMapping("changeContractStateByUserId")
    public CommonResult changeStateByUserId(Integer userId) {
        return contractService.changeStateByUserId(userId);
    }

    /**
     * 获取用户合同信息
     *
     * @param userId
     * @return
     */
    @GetMapping("getContractByUserId")
    public CommonResult getContractByUserId(Integer userId) {
        return contractService.getContractByUserId(userId);
    }
}

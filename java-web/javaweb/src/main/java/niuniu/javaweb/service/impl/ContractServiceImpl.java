package niuniu.javaweb.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import niuniu.javaweb.mapper.ContractMapper;
import niuniu.javaweb.mapper.RoleMapper;
import niuniu.javaweb.pojo.Contract;
import niuniu.javaweb.pojo.Role;
import niuniu.javaweb.pojo.Style;
import niuniu.javaweb.service.ContractService;
import niuniu.javaweb.utils.CapitalNumber;
import niuniu.javaweb.utils.DateUtil;
import niuniu.javaweb.utils.FileUtil;
import niuniu.javaweb.utils.UUIDUtils;
import niuniu.javaweb.utils.itext.PdfTempPrintUtil;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.vo.RentContractVo;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.HashMap;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service.impl
 * @date 2023/3/31 12:14
 */
@Service
public class ContractServiceImpl extends ServiceImpl<ContractMapper, Contract> implements ContractService {

    //生成文件路径
    private static String DEST_PATH;

    @Value("${pdf.DEST_PATH}")
    public void setDestPath(String destPath) {
        this.DEST_PATH = destPath;
    }

    @Autowired
    ContractMapper contractMapper;

    @Autowired
    RoleMapper roleMapper;


    /**
     * 获取合同模板
     *
     * @return
     */
    @Override
    public CommonResult getBaseContract() {
        return CommonResult.success(contractMapper.selectBaseContract());
    }

    /**
     * 获取合同
     *
     * @param currentPage
     * @param pageSize
     * @param contract
     * @return
     */
    @Override
    public IPage<Style> selectContract(int currentPage, int pageSize, Contract contract) {
        LambdaQueryWrapper<Contract> lambdaQueryWrapper = new LambdaQueryWrapper<Contract>();
        if (contract.getKindId() > 0)
            lambdaQueryWrapper.like(Strings.isNotEmpty(contract.getKindId().toString()), Contract::getKindId, contract.getKindId());
        lambdaQueryWrapper.like(Strings.isNotEmpty(contract.getState().toString()), Contract::getState, contract.getState());
        IPage page = new Page(currentPage, pageSize);
        return contractMapper.selectPage(page, lambdaQueryWrapper);
    }

    /**
     * 填写合同
     *
     * @param rentContractVo
     * @return
     */
    @Override
    public CommonResult writeRentContract(RentContractVo rentContractVo) throws IOException {
//        System.out.println(rentContractVo);
        HashMap<String, String> map = putRentMap(rentContractVo);
        String s = UUIDUtils.generateShortUuid();
        map.put("Text1", "【" + "    " + "】");
        return CommonResult.success(PdfTempPrintUtil.generateContract("租赁合同模板.pdf", "draft" + "_" + s, map, null));
    }

    /**
     * 填写劳动合同
     *
     * @param rentContractVo
     * @return
     */
    @Override
    public CommonResult writeWorkContract(RentContractVo rentContractVo) throws ParseException, IOException {
        HashMap<String, String> map = putWorkMap(rentContractVo);
        String s = UUIDUtils.generateShortUuid();
        map.put("fill_1", "【" + "    " + "】");
        return CommonResult.success(PdfTempPrintUtil.generateContract("劳动合同模板.pdf", "draft" + "_" + s, map, null));
    }

    /**
     * 插入签名
     *
     * @param file
     * @param rentContractVoStr
     * @param url
     * @return
     * @throws IOException
     */
    @Override
    @Transactional
    public CommonResult writeRentSign(MultipartFile file, String rentContractVoStr, String url) throws IOException {
//        System.out.println(fileFullPath);
        RentContractVo rentContractVo = JSON.parseObject(rentContractVoStr, RentContractVo.class);
//        System.out.println(rentContractVo);
        String s = UUIDUtils.generateShortUuid();
        HashMap<String, String> map = putRentMap(rentContractVo);
        map.put("Text1", "【" + s + "】");
        url = DEST_PATH + "\\" + url;
        CommonResult commonResult = FileUtil.uploadFile(file);
        Object fileFullPath = commonResult.getData();
        String path = PdfTempPrintUtil.generateContract("租赁合同模板.pdf", DateUtil.getCurrentTime() + "_" + rentContractVo.getUsername() + "_" + rentContractVo.getName(), map, (String) fileFullPath);
//        System.out.println(file);
        FileUtil.deleteFile(new File(url));
        FileUtil.deleteFile((new File((String) fileFullPath)));

        //存入合同信息
        contractMapper.insertContract(new Contract(null, 1, rentContractVo.getUserId(), null, null, path));
        return CommonResult.success(path);
    }

    /**
     * 插入签名
     *
     * @param file
     * @param rentContractVoStr
     * @param url
     * @return
     * @throws IOException
     */
    @Override
    public CommonResult writeWorkSign(MultipartFile file, String rentContractVoStr, String url) throws IOException, ParseException {
        //        System.out.println(fileFullPath);
        RentContractVo rentContractVo = JSON.parseObject(rentContractVoStr, RentContractVo.class);
//        System.out.println(rentContractVo);
        String s = UUIDUtils.generateShortUuid();
        HashMap<String, String> map = putWorkMap(rentContractVo);
        map.put("fill_1", "【" + s + "】");
        url = DEST_PATH + "\\" + url;
        CommonResult commonResult = FileUtil.uploadFile(file);
        Object fileFullPath = commonResult.getData();
        String path = PdfTempPrintUtil.generateContract("劳动合同模板.pdf", DateUtil.getCurrentTime() + "_" + rentContractVo.getUsername() + "_" + rentContractVo.getName(), map, (String) fileFullPath);
//        System.out.println(file);
        FileUtil.deleteFile(new File(url));
        FileUtil.deleteFile((new File((String) fileFullPath)));

        //存入合同信息
        contractMapper.insertContract(new Contract(null, 2, rentContractVo.getUserId(), null, null, path));
        return CommonResult.success(path);
    }

    /**
     * 把信息存入map中
     *
     * @param rentContractVo
     * @return
     */
    private HashMap<String, String> putRentMap(RentContractVo rentContractVo) {
        HashMap<String, String> map = new HashMap<>();
        ZonedDateTime now = ZonedDateTime.now();
        map.put("Text2", rentContractVo.getName());
        map.put("Text3", rentContractVo.getArea());
        map.put("Text4", rentContractVo.getPriceArea());
        map.put("Text5", rentContractVo.getPriceElse());
        map.put("Text6", CapitalNumber.getChineseMoneyStringForDoubleVal(Double.parseDouble(rentContractVo.getPrice().replace(",", ""))));
        map.put("Text7", rentContractVo.getPrice());
        map.put("Text8", String.valueOf(now.getYear()));
        map.put("Text9", String.valueOf(now.getMonthValue()));
        map.put("Text10", String.valueOf(now.getDayOfMonth()));
        map.put("Text11", String.valueOf(now.getYear() + 1));
        map.put("Text12", String.valueOf(now.getMonthValue()));
        map.put("Text13", String.valueOf(now.getDayOfMonth()));
        map.put("Text15", rentContractVo.getName());
        map.put("Text16", rentContractVo.getIdcard());
        map.put("Text17", rentContractVo.getAddress());
        map.put("Text18", rentContractVo.getPhone());
        map.put("Text19", String.valueOf(now.getYear()));
        map.put("Text20", String.valueOf(now.getMonthValue()));
        map.put("Text21", String.valueOf(now.getDayOfMonth()));
        return map;
    }

    private HashMap<String, String> putWorkMap(RentContractVo rentContractVo) throws ParseException {
        HashMap<String, String> map = new HashMap<>();
        ZonedDateTime now = ZonedDateTime.now();
        Role role = roleMapper.selectById(rentContractVo.getRoleId());
        map.put("fill_2", rentContractVo.getName());
        map.put("fill_3", String.valueOf(now.getYear()));
        map.put("fill_4", String.valueOf(now.getMonthValue()));
        map.put("fill_5", String.valueOf(now.getDayOfMonth()));
        map.put("fill_6", String.valueOf(now.getYear() + 1));
        map.put("fill_7", String.valueOf(now.getMonthValue()));
        map.put("fill_8", String.valueOf(now.getDayOfMonth()));
        String[] mon = DateUtil.getMon(DateUtil.getNowTime(), 6);
        map.put("fill_9", mon[0]);
        map.put("fill_10", mon[1]);
        map.put("fill_11", mon[2]);
        map.put("fill_12", role.getRoleName());
        map.put("fill_13", role.getRemark());
        map.put("fill_14", rentContractVo.getPrice());
        map.put("fill_15", rentContractVo.getPriceElse());
        map.put("fill_17", rentContractVo.getName());
        map.put("fill_18", rentContractVo.getIdcard());
        map.put("fill_19", rentContractVo.getAddress());
        map.put("fill_20", rentContractVo.getPhone());
        map.put("fill_21", String.valueOf(now.getYear()));
        map.put("fill_22", String.valueOf(now.getMonthValue()));
        map.put("fill_23", String.valueOf(now.getDayOfMonth()));
        return map;
    }


    /**
     * 修改原来的合同状态
     *
     * @param userId
     * @return
     */
    @Override
    public CommonResult changeStateByUserId(Integer userId) {
        return contractMapper.changeStateByUserId(userId) > 0 ? CommonResult.success() : CommonResult.success();
    }

    /**
     * 获取用户合同信息
     *
     * @param userId
     * @return
     */
    @Override
    public CommonResult getContractByUserId(Integer userId) {
        QueryWrapper<Contract> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", 1).eq("user_id", userId);
        return CommonResult.success(contractMapper.selectOne(queryWrapper));
    }
}

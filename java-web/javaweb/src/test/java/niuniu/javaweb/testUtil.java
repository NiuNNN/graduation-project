package niuniu.javaweb;

import niuniu.javaweb.mapper.ContractMapper;
import niuniu.javaweb.mapper.HouseMapper;
import niuniu.javaweb.mapper.MenuMapper;
import niuniu.javaweb.mapper.UserMapper;
import niuniu.javaweb.pojo.User;
import niuniu.javaweb.service.ContractService;
import niuniu.javaweb.service.HouseService;
import niuniu.javaweb.utils.*;
import niuniu.javaweb.utils.itext.PdfTempPrintUtil;
import niuniu.javaweb.utils.tools.HouseTools;
import niuniu.javaweb.utils.tools.SeqTools;
import niuniu.javaweb.vo.TransferVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;

import static niuniu.javaweb.utils.CapitalNumber.getChineseMoneyStringForBigDecimal;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb
 * @date 2023/3/18 8:26
 */
@SpringBootTest
public class testUtil {
    @Autowired
    HouseService houseService;

    @Autowired
    HouseMapper houseMapper;

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    ContractMapper contractMapper;

    @Autowired
    ContractService contractService;

    @Autowired
    UserMapper userMapper;

    @Test
    void testGetChineseMoneyStringForBigDecimal() {
        System.out.println(getChineseMoneyStringForBigDecimal(new BigDecimal(1240.23)));
        System.out.println(getChineseMoneyStringForBigDecimal(new BigDecimal(-1240.23)));
    }

    @Test
    void testGenerateContract() throws IOException {
        //当前时间
        ZonedDateTime now = ZonedDateTime.now();
        String uuid = UUIDUtils.generateShortUuid();
//        System.out.println(uuid);
        HashMap<String, String> map = new HashMap<>();
        map.put("Text1", uuid);
        map.put("Text2", "黄飞鸿");
        map.put("Text3", "安保人员");
        map.put("Text4", "主要负责保障出租屋的治安问题");
        map.put("Text5", "4000.00");
        map.put("Text6", "2500.00");
        map.put("Text8", "黄飞鸿");
        map.put("Text9", "44068000000000");
        map.put("Text10", "黄飞鸿故居");
        map.put("Text11", "13624561230");
        map.put("Text12", String.valueOf(now.getYear()));
        map.put("Text13", String.valueOf(now.getMonthValue()));
        map.put("Text14", String.valueOf(now.getDayOfMonth()));
        String baseContractUrl = contractMapper.getBaseContractUrl(1);
        PdfTempPrintUtil.generateContract(baseContractUrl, uuid, map, null);
    }

    @Test
    void testHouseTools() {
        String[] houseNumber1 = houseMapper.getHouseNumber(2);
        System.out.println(HouseTools.getHouseNumber(houseNumber1));
    }

    @Test
    void testTransferUtils() {
        TransferUtil transferUtil = new TransferUtil();
        List<TransferVO> transferVOS = transferUtil.buildTransferTree(menuMapper.selectElsePermsByRoleId(2));
        System.out.println(transferVOS);
    }

    @Test
    void testSign() throws IOException {
        HashMap<String, String> map = new HashMap<>();
        ZonedDateTime now = ZonedDateTime.now();
        map.put("Text2", "asd");
        map.put("Text3", "123.00");
        map.put("Text4", "123.00");
        map.put("Text5", "123.00");
        map.put("Text6", CapitalNumber.getChineseMoneyStringForDoubleVal(Double.parseDouble("123.00")));
        map.put("Text7", "123.00");
        map.put("Text8", String.valueOf(now.getYear()));
        map.put("Text9", String.valueOf(now.getMonthValue()));
        map.put("Text10", String.valueOf(now.getDayOfMonth()));
        map.put("Text11", String.valueOf(now.getYear() + 1));
        map.put("Text12", String.valueOf(now.getMonthValue()));
        map.put("Text13", String.valueOf(now.getDayOfMonth()));
        map.put("Text15", "asd");
        map.put("Text16", "123");
        map.put("Text17", "123");
        map.put("Text18", "123");
        map.put("Text19", String.valueOf(now.getYear()));
        map.put("Text20", String.valueOf(now.getMonthValue()));
        map.put("Text21", String.valueOf(now.getDayOfMonth()));
        PdfTempPrintUtil.generateContract("租赁合同模板.pdf", "123" + "_" + "罗", map, "E:\\graduation-project\\file\\upload\\Cache\\1680773976927_629415494934500.jpeg");

    }

    @Test
    void testDeleteFile() {
        FileUtil.deleteFile("E:\\graduation-project\\file\\upload\\Contract", "draft");
    }

    @Test
    void testGetCurrentTime() {
        System.out.println(DateUtil.getCurrentTime());
    }

    @Test
    void testCheckOrderTime() {
        System.out.println(DateUtil.checkOrderTime("2023-04-01 20:37:54"));
    }

    @Test
    void testIpUtil() throws Exception {
        //国内ip
        String ip1 = "220.248.12.158";

        String cityInfo1 = IpUtil.getCityInfo(ip1);
        System.out.println(cityInfo1);
        String address1 = IpUtil.getIpPossession(ip1);
        System.out.println(address1);
    }

    @Test
    void createUser() {
        for (int i = 0; i <= 94; i++) {
            String number = null;
            try {
                number = SeqTools.getNumber(String.valueOf(1));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            User user = new User();
            user.setUsername(number);
            user.setRoleId(1);
            user.setPassword("$2a$10$qtlT8SO6M8UoRXDMbsGcWedNKUQrjrsTb7Z4jhYssYi1nAw1Ci.5C");
            user.setName("小");
            user.setPhone("13923654789");
            user.setSex("男");
            user.setNation("汉");
            user.setBirthday("2000-01-01");
            user.setAddress("广东省湛江市麻章区广东海洋大学");
            user.setIdcard("440680000000000000");
            user.setSign("2000-01-01");
            user.setLose("2023-05-31");
            user.setAuthority("佛山市公安局南海分局");
            userMapper.createUser(user);
        }
    }
}

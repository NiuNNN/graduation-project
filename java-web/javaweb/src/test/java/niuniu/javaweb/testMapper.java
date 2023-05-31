package niuniu.javaweb;

import niuniu.javaweb.mapper.*;
import niuniu.javaweb.pojo.Contract;
import niuniu.javaweb.pojo.Report;
import niuniu.javaweb.pojo.User;
import niuniu.javaweb.service.UserService;
import niuniu.javaweb.utils.ArrayUtil;
import niuniu.javaweb.utils.MenuUtils;
import niuniu.javaweb.utils.StringUtils;
import niuniu.javaweb.utils.tools.SeqTools;
import niuniu.javaweb.vo.FinancialVO;
import niuniu.javaweb.vo.TransferVO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb
 * @date 2023/2/7 22:28
 */
@SpringBootTest
public class testMapper {
    @Autowired
    UserService userService;

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ReportMapper reportMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    ContractMapper contractMapper;

    @Autowired
    FinancialMapper financialMapper;


    @Test
    void selectPermsByUserName() {
        System.out.println(MenuUtils.buildTree(menuMapper.selectPermsByUserName("202302100001")));
    }

    @Test
    void getUserId() {
        System.out.println(userMapper.getUserId("202302100001"));
    }

    @Test
    void createUser() {
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

    @Test
    void insertReport() {
        Report report = new Report();

        for (int i = 0; i < 30; i++) {
            report.setUserId(3);
            report.setStem("标题" + i);
            report.setContent("文本内容" + i);
            reportMapper.insertReport(report);
        }
    }

    @Test
    void testGetAllRole() {
//        System.out.println(roleMapper.getAllRole());
    }

    @Test
    void testSelectElseOperationByRoleId() {
        List<TransferVO> transferVOS = menuMapper.selectPermsByRoleId(2);
        List<String> pidList = new ArrayList<>();
        List<TransferVO> target = new ArrayList<>();
        for (TransferVO transferVO : transferVOS) {
            if (!StringUtils.isEmpty(transferVO.getQuery())) {

                transferVO.setId(transferVO.getPid() + "" + transferVO.getMid());

                String s = transferVO.getQuery().replaceAll("[^\\d,]", "");
                if (s.length() == 6) continue;
                else {
                    pidList.add(transferVO.getPid());
                    String[] arr = {"1", "2", "3", "4", "5", "6"};
                    String[] split = s.split("");//[2,3,4,5,6]
                    List<String> compare = ArrayUtil.compare(arr, split);
//                    System.out.println(menuMapper.selectOperationByStr(compare.toArray(new String[]{})));
                    List<TransferVO> children = menuMapper.selectOperationByStr(compare.toArray(new String[]{}));
                    for (TransferVO transferVO1 : children) {
                        transferVO1.setPid(transferVO.getId());
                        transferVO1.setId(transferVO.getId() + "-" + transferVO1.getOid());
                    }
                    transferVO.setChildren(children);
                    target.add(transferVO);
                }
            }
        }
        System.out.println(pidList.stream().distinct().collect(Collectors.toList()));
        System.out.println(target);

    }

    @Test
    void testGetBaseContractUrl() {
        System.out.println(contractMapper.getBaseContractUrl((1)));
    }

    @Test
    void testInsertContract() {
        Contract contract = new Contract();
        contract.setUserId(3);
        contract.setKindId(1);
        contract.setUrl("劳动合同模板.pdf");
        for (int i = 0; i < 20; i++) {
            contractMapper.insertContract(contract);
        }
    }

    @Test
    void judgeFinancialByDate() {
        financialMapper.judgeFinancialByDate("2023-04", "");
    }

    @Test
    void testInsertFinancial() {
        financialMapper.insertFinancial();
    }

    @Test
    void testUpdateFinancial() {
        FinancialVO financial = new FinancialVO();
        financial.setDate("2023-04");
        financial.setInWater("123.00");
        financial.setInElectric("123.00");
        financial.setInMis("123.00");
        financial.setInRent("123.00");
        financial.setAdvanceTotal("123.00");
        financialMapper.updateFinancial(financial);
    }
}

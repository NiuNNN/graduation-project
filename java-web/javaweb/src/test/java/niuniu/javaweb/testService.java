package niuniu.javaweb;

import com.baomidou.mybatisplus.core.metadata.IPage;
import niuniu.javaweb.mapper.BasicMapper;
import niuniu.javaweb.mapper.MenuMapper;
import niuniu.javaweb.mapper.OrderMapper;
import niuniu.javaweb.pojo.Basic;
import niuniu.javaweb.service.*;
import niuniu.javaweb.utils.ArrayUtil;
import niuniu.javaweb.utils.DateUtil;
import niuniu.javaweb.utils.StringUtils;
import niuniu.javaweb.utils.TransferUtil;
import niuniu.javaweb.vo.TransferVO;
import niuniu.javaweb.vo.UserVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb
 * @date 2023/3/21 12:26
 */
@SpringBootTest
public class testService {

    @Autowired
    HouseService houseService;

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    UserService userService;

    @Autowired
    BasicMapper basicMapper;

    @Autowired
    CostService costService;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    StaffPayService staffPayService;

    @Autowired
    FinancialService financialService;

    @Test
    void testGetHouseNumber() {
        houseService.getHouseNumber(2);
    }

    @Test
    void testSelectElsePermsByRoleId() {
        /**
         * 首先获取没有权限的
         */
        List<TransferVO> transferVOS = menuMapper.selectElsePermsByRoleId(5);

        /**
         * 获取没有操作的权限
         */
        List<TransferVO> transferVOS1 = menuMapper.selectPermsByRoleId(5);
        List<String> pidList = new ArrayList<>();
        List<TransferVO> target = new ArrayList<>();
        for (TransferVO transferVO : transferVOS1) {
            if (!StringUtils.isEmpty(transferVO.getQuery())) {

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
                        transferVO1.setId(transferVO.getPid() + "-" + transferVO.getMid() + "-" + transferVO1.getOid());
                    }
                    transferVO.setChildren(children);
                    target.add(transferVO);
                }
            }
        }
        System.out.println(pidList.stream().distinct().collect(Collectors.toList()));
        System.out.println(target);

        /**
         * 判断没有权限的列表里是否存在该子节点的父节点 列表不为空
         */
        if (!transferVOS.isEmpty()) {
            for (int i = 0; i < target.size(); i++) {
                int index = 0;
                for (int j = 0; j > transferVOS.size(); j++) {
                    if (target.get(i).getPid().equals(target.get(j).getId())) index++;
                }
                /**
                 * 如果没有则添加父节点元素
                 */
                if (index == 0) {
                    transferVOS.add(menuMapper.selectByMenuId(target.get(i).getPid()));
                }
            }

        }
        /**
         * 列表为空
         */
        else {
            for (TransferVO transferVO : target) {
                transferVOS.add(menuMapper.selectByMenuId(transferVO.getPid()));
            }
        }

        /**
         * 添加子节点
         */
        for (TransferVO transferVO : target) {
            transferVOS.add(transferVO);
        }

        System.out.println(new TransferUtil().buildTransferTree(transferVOS));
    }

    @Test
    void testSelectPermsByRoleId() {
        List<TransferVO> transferVOS = menuMapper.selectPermsByRoleId(5);
        for (TransferVO transferVO : transferVOS) {
            if (!StringUtils.isEmpty(transferVO.getQuery())) {

                String s = transferVO.getQuery().replaceAll("[^\\d,]", "");
                List<TransferVO> children = menuMapper.selectOperationByStr(s.split(""));
                System.out.println(children);
                for (TransferVO transferVO1 : children) {
                    transferVO1.setPid(transferVO.getId());
                    transferVO1.setId(transferVO.getPid() + "-" + transferVO.getMid() + "-" + transferVO1.getOid());
                }
                transferVO.setChildren(children);
            }
        }
        System.out.println(new TransferUtil().buildTransferTree(transferVOS));

    }

    @Test
    void testUpdatePermsByRoleId() {
        String[] str = {"6-19-1", "6-19-2", "6-19-3", "6-19-4", "6-19-5", "6-19-6"};
        List<String> parse = List.of(str);
        for (String s : parse) {
            if (org.springframework.util.StringUtils.countOccurrencesOf(s, "-") == 2) {
                String[] split = s.replaceAll("[^\\d,]", ",").split(",");
                //父级 split[0]
                System.out.println(split[0]);
                //子级 split[1]
                System.out.println(split[1]);
                //操作 split[2]
                System.out.println(split[2]);
                /**
                 * 先判断父节点是否存在
                 */
                if (menuMapper.selectNode(split[0], 5) == 0) menuMapper.insertParent(split[0], 5);
                /**
                 * 判断子节点是否存在
                 */
                String query = "%#%" + split[2] + "&%%#";
                if (menuMapper.selectNode(split[1], 5) > 0) {
                    /**
                     * 存在取出原来的操作权限并添加
                     */
                    menuMapper.updateQuery(split[1], 5, menuMapper.selectQuery(split[1], 5) + query);
                } else {
                    /**
                     * 不存在直接添加操作权限
                     */
                    menuMapper.insertQuery(split[1], 5, query);
                }
            }
        }
    }

    @Test
    void testDeletePermsByRoleId() {
        String[] str = {"6-19-6"};
        List<String> parse = List.of(str);
        for (String s : parse) {
            if (org.springframework.util.StringUtils.countOccurrencesOf(s, "-") == 2) {
                String[] split = s.replaceAll("[^\\d,]", ",").split(",");

                String[] query = menuMapper.selectQuery(split[1], 5).replaceAll("[^\\d,]", "").split("");

                List<String> list = Arrays.asList(query); //转换为list集合

                List<String> arrayList = new ArrayList<String>(list);

                arrayList.remove(split[2]);

                if (arrayList.isEmpty()) {
                    menuMapper.deleteNode(split[0], 5);
                    menuMapper.deleteNode(split[1], 5);
                } else {
                    String result = "";

                    for (String str1 : arrayList) {
                        result += "%#%" + str1 + "&%%#";
                    }
                    menuMapper.updateQuery(split[1], 5, result);
                }
            }
        }
    }

    @Test
    void testGetUserPage() {
        IPage<UserVO> userPage = userService.getUserPage("", "", "", 1, 1, "1", "1");
        System.out.println(userPage);
    }

    @Test
    void testGetNoHouseUserPage() {
        IPage<UserVO> userPage = userService.getNoHouseUserPage("", "", 1, 1);
        System.out.println(userPage);
    }

    @Test
    void testGetActuallyPrice() throws ParseException {
        Basic basic = basicMapper.selectById(10);
        String nowTime = "2023-06-14";
        if (basic.getOperation().equals("1")) {
            System.out.println(basic.getPrice());
        } else {
            if (DateUtil.compareMonth(basic.getTime(), nowTime)) {
                System.out.println(basic.getOldPrice());
            } else {
                System.out.println(basic.getPrice());
            }
        }
    }

    @Test
    @Transactional
    void testGenerateAll() throws ParseException {
        costService.generateALlCost("2023-04");
    }

    @Test
    void getOrderMis() {
        String misPrice = orderMapper.getMisPrice(137);
//        System.out.println(misPrice);
        String[] arr = misPrice.split("_");
        for (String s : arr) {
            System.out.println(s);
        }
        for (int i = 0; i < arr.length; i += 2) {
            Basic basic = basicMapper.selectById(arr[i]);
            basic.setRemark("每月" + basic.getBaseName() + "收费￥" + arr[i + 1] + "元");
            basic.setPrice(arr[i + 1]);
            System.out.println(basic);
//            basicList.add(basic);
//            System.out.println("【arr[i]】" + arr[i]);
        }
    }

    @Test
    void getUserChart() {
        System.out.println(costService.getUserChart(95));
    }

    @Test
    void leaveSalary() throws ParseException {
        staffPayService.leaveSalary(4);
    }

    @Test
    void getFinancialChart() throws ParseException {
        System.out.println(financialService.getFinancialChart());
    }
}

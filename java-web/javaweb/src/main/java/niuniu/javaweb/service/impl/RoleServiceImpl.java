package niuniu.javaweb.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import niuniu.javaweb.mapper.*;
import niuniu.javaweb.pojo.Role;
import niuniu.javaweb.pojo.Salary;
import niuniu.javaweb.service.RoleService;
import niuniu.javaweb.service.StaffPayService;
import niuniu.javaweb.utils.ArrayUtil;
import niuniu.javaweb.utils.StringUtils;
import niuniu.javaweb.utils.TransferUtil;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.vo.RoleVO;
import niuniu.javaweb.vo.TransferVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service.impl
 * @date 2023/3/24 18:28
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    RoleMapper roleMapper;

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    SalaryMapper salaryMapper;

    @Autowired
    StaffPayService staffPayService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ContractMapper contractMapper;

    /**
     * 获取全部职位
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    @Cacheable(cacheNames = "allRole", key = "#currentPage")
    public IPage<RoleVO> getAllRole(int currentPage, int pageSize) {
        Page<RoleVO> page = new Page<>(currentPage, pageSize);
        QueryWrapper<RoleVO> queryWrapper = new QueryWrapper<>();
        return roleMapper.getAllRole(page, queryWrapper);
    }

    /**
     * 按职务获取已有权限
     *
     * @param roleId
     * @return
     */
    @Override
    @Cacheable(cacheNames = "HasPerms", key = "#roleId")
    public CommonResult selectPermsByRoleId(Integer roleId) {
        List<TransferVO> transferVOS = menuMapper.selectPermsByRoleId(roleId);
        for (TransferVO transferVO : transferVOS) {
            if (!StringUtils.isEmpty(transferVO.getQuery())) {
                String s = transferVO.getQuery().replaceAll("[^\\d,]", "");
                List<TransferVO> children = menuMapper.selectOperationByStr(s.split(""));
                System.out.println(children);
                for (TransferVO transferVO1 : children) {
                    transferVO1.setPid(transferVO.getId());
                    transferVO1.setId(transferVO.getPid() + "-" + transferVO.getMid() + "-" + transferVO1.getOid());
                    if (roleId == 2) transferVO1.setDisabled(true);
                }
                transferVO.setChildren(children);
            }
        }
        return CommonResult.success(new TransferUtil().buildTransferTree(transferVOS));
    }

    /**
     * 获取职务未有权限
     *
     * @param roleId
     * @return
     */
    @Override
    @Cacheable(cacheNames = "ElsePerms", key = "#roleId")
    public CommonResult selectElsePermsByRoleId(Integer roleId) {
        /**
         * 首先获取没有权限的
         */
        List<TransferVO> transferVOS = menuMapper.selectElsePermsByRoleId(roleId);

        /**
         * 获取没有操作的权限
         */
        List<TransferVO> transferVOS1 = menuMapper.selectPermsByRoleId(roleId);
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
//        System.out.println(pidList.stream().distinct().collect(Collectors.toList()));
//        System.out.println(target);

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

        return CommonResult.success(new TransferUtil().buildTransferTree(transferVOS));
    }

    /**
     * 赋权限
     *
     * @param roleId
     * @param keys
     * @return
     */
    @Override
    @CacheEvict(cacheNames = {"HasPerms", "ElsePerms", "userMenu"}, allEntries = true)
    public CommonResult updatePermsByRoleId(Integer roleId, String keys) {
//        System.out.println(roleId);
//        System.out.println(keys);
        List<String> parse = (List<String>) JSON.parse(keys);
        for (String s : parse) {
            if (org.springframework.util.StringUtils.countOccurrencesOf(s, "-") == 2) {
                String[] split = s.replaceAll("[^\\d,]", ",").split(",");
                //父级 split[0]
                System.out.println(0 + "" + split[0]);
                //子级 split[1]
                System.out.println(1 + "" + split[1]);
                //操作 split[2]
                System.out.println(2 + "" + split[2]);
                /**
                 * 先判断父节点是否存在
                 */
                if (menuMapper.selectNode(split[0], roleId) == 0) menuMapper.insertParent(split[0], roleId);
                /**
                 * 判断子节点是否存在
                 */
                String query = "%#%" + split[2] + "&%%#";
                if (menuMapper.selectNode(split[1], roleId) > 0) {
                    /**
                     * 存在取出原来的操作权限并添加
                     */
                    menuMapper.updateQuery(split[1], roleId, menuMapper.selectQuery(split[1], roleId) + query);
                } else {
                    /**
                     * 不存在直接添加操作权限
                     */
                    menuMapper.insertQuery(split[1], roleId, query);
                }
            }
        }
        return CommonResult.success();
    }

    /**
     * 移除权限
     *
     * @param roleId
     * @param keys
     * @return
     */
    @Override
    @CacheEvict(cacheNames = {"HasPerms", "ElsePerms", ",userMenu"}, allEntries = true)
    public CommonResult deletePermsByRoleId(Integer roleId, String keys) {
        List<String> parse = (List<String>) JSON.parse(keys);
        for (String s : parse) {
            if (org.springframework.util.StringUtils.countOccurrencesOf(s, "-") == 2) {
                String[] split = s.replaceAll("[^\\d,]", ",").split(",");

                String[] query = menuMapper.selectQuery(split[1], roleId).replaceAll("[^\\d,]", "").split("");

                List<String> list = Arrays.asList(query); //转换为list集合

                List<String> arrayList = new ArrayList<String>(list);

                arrayList.remove(split[2]);

                if (arrayList.isEmpty()) {
                    menuMapper.deleteNode(split[0], roleId);
                    menuMapper.deleteNode(split[1], roleId);
                } else {
                    String result = "";

                    for (String str1 : arrayList) {
                        result += "%#%" + str1 + "&%%#";
                    }
                    menuMapper.updateQuery(split[1], roleId, result);
                }
            }
        }
        return CommonResult.success();
    }

    /**
     * 修改职务状态
     *
     * @param roleId
     * @param state
     * @return
     */
    @Override
    @CacheEvict(cacheNames = "allRole", allEntries = true)
    public CommonResult changeRoleState(Integer roleId, Integer state) {
        return roleMapper.changeRoleState(roleId, state) > 0 ? CommonResult.success() : CommonResult.failed();
    }

    /**
     * 新建职务
     *
     * @param role
     * @param arrList
     * @return
     */
    @Override
    @Transactional
    @CacheEvict(cacheNames = "allRole", allEntries = true)
    public CommonResult insertRole(Role role, String arrList) {
        roleMapper.insertRole(role);
//        System.out.println(role);
//        System.out.println(JSON.parseArray(arrList));
        List<Integer> list = JSONArray.parseArray(arrList, Integer.class);
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                salaryMapper.insertRoleSalary(list.get(i), role.getRoleId(), 1);
            }
        }
        return CommonResult.success();
    }

    /**
     * 修改职务信息
     *
     * @param role
     * @param base
     * @param probation
     * @param arrList
     * @return
     */
    @Override
    @Transactional
    @CacheEvict(cacheNames = {"allRole", "roleSalary"}, allEntries = true)
    public CommonResult updateRole(Role role, Integer base, Integer probation, String arrList) {
        //获取用户现存的薪水标准 获取的salary_role对象中 state只有 1，3
        List<Salary> roleSalary = roleMapper.getRoleSalary(role.getRoleId());
        //获取新的用户薪水标准
        List<Integer> newList = JSONArray.parseArray(arrList, Integer.class);
        List<Integer> list1 = JSONArray.parseArray(arrList, Integer.class);
        //存放用户旧的薪水标准
        List<Salary> oldList = new ArrayList<>();
        List<Salary> list2 = new ArrayList<>();
        for (Salary salary : roleSalary) {
            if (salary.getState() == 1) {
                if (salary.getSalaryId() != base) {
                    handleUpdate(role, salary, base);
                }
            } else if (salary.getState() == 2) {
                if (salary.getSalaryId() != probation) {
                    handleUpdate(role, salary, probation);
                }
            } else if (salary.getState() == 3) {
                System.out.println(salary);
                oldList.add(salary);
                list2.add(salary);
            }
        }
        handleUpdate(role, newList, oldList, list1, list2);
        //修改职务信息
        roleMapper.updateRole(role);
        return CommonResult.success();
    }

    /**
     * 更新主要薪水
     *
     * @param role
     * @param salary
     * @param target
     */
    public void handleUpdate(Role role, Salary salary, Integer target) {
        /**
         * 先删除后添加
         * 首先先修改原来薪水标准置为 如果该月已经修改过 那就把最新的一次进行删除 如果没有则修改为2
         * 然后确认是否以前存在该条记录 存在则修改状态 不存在则添加
         *
         */
        if (salary.getState() == 3) {
            salaryMapper.updateRoleSalary(role.getRoleId(), salary.getSalaryId(), 0);
        } else {
            salaryMapper.updateRoleSalary(role.getRoleId(), salary.getSalaryId(), 2);
        }
        //判断是否存在该条记录
        if (salaryMapper.selectHasRoleSalary(role.getRoleId(), target) > 0) {
            //存在 修改状态
            salaryMapper.updateRoleSalary(role.getRoleId(), target, 3);
        } else {
            //不存在 进行添加
            salaryMapper.insertRoleSalary(target, role.getRoleId(), 3);
        }
    }

    /**
     * 处理更新其余薪水
     *
     * @param role
     * @param newList
     * @param oldList
     */
    public void handleUpdate(Role role, List<Integer> newList, List<Salary> oldList, List<Integer> list1, List<Salary> list2) {
        for (Integer integer : newList) {
            System.out.println(integer);
            System.out.println("运行到1");
            for (Salary salary : oldList) {
                System.out.println("运行到2");
                //若存在 则不添加也不删除
                if (integer == salary.getSalaryId()) {
                    System.out.println("运行到3");
                    list1.remove(integer); //add
                    list2.remove(salary); //del
                }
            }
        }
        //添加新的
        for (Integer target : list1) {
            //判断是否存在该条记录
            if (salaryMapper.selectHasRoleSalary(role.getRoleId(), target) > 0) {
                //存在 修改状态
                salaryMapper.updateRoleSalary(role.getRoleId(), target, 3);
            } else {
                //不存在 进行添加
                salaryMapper.insertRoleSalary(target, role.getRoleId(), 3);
            }
        }
        //删除旧的
        for (Salary salary : list2) {
            //判断原薪水状态 如果为3 直接设置为0
            if (salary.getState() == 3) {
                salaryMapper.updateRoleSalary(role.getRoleId(), salary.getSalaryId(), 0);
            } else {
                salaryMapper.updateRoleSalary(role.getRoleId(), salary.getSalaryId(), 2);
            }
        }
    }

    /**
     * 获取职位对应的薪水
     *
     * @param roleId
     * @return
     */
    @Override
    @Cacheable(cacheNames = "roleSalary", key = "#roleId")
    public CommonResult getRoleSalary(Integer roleId) {
        HashMap<String, String> hashMap = new HashMap<>();
        List<Salary> roleSalary = roleMapper.getRoleSalary(roleId);
        List<Integer> list = new ArrayList<>();
        for (Salary salary : roleSalary) {
            if (salary.getState() == 1) {
                hashMap.put("base", JSON.toJSONString(salary.getSalaryId()));
            } else if (salary.getState() == 2) {
                hashMap.put("probation", JSON.toJSONString(salary.getSalaryId()));
            } else if (salary.getState() == 3) {
                list.add(salary.getSalaryId());
            }
        }
        hashMap.put("else", JSON.toJSONString(list));
        return CommonResult.success(hashMap);
    }

    /**
     * 获取全部职位
     *
     * @return
     */
    @Override
    public CommonResult getRole() {
        return CommonResult.success(roleMapper.getRole());
    }

    /**
     * 获取用户职位薪水
     *
     * @param roleId
     * @return
     */
    @Override
    public CommonResult getUserSalary(Integer roleId) {
        List<RoleVO> userSalary = roleMapper.getUserSalary(roleId);
        if (!userSalary.isEmpty()) {
            for (RoleVO roleVO : userSalary) {
                if (roleVO.getState() == 1) {
                    roleVO.setSalaryName("正常薪水");
                } else if (roleVO.getState() == 2) {
                    roleVO.setSalaryName("实习薪水");
                }
            }
        }
        return CommonResult.success(userSalary);
    }

    /**
     * 员工离职处理
     *
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public CommonResult leaveRole(Integer userId) throws ParseException {
        /**
         * 1. 生成员工薪水
         * 2. 修改合同 报废合同
         * 3. 修改use表，把user state修改为0 并且填写离职时间
         */
        staffPayService.leaveSalary(userId);
        contractMapper.changeStateByUserId(userId);
        return userMapper.deleteUser(userId) > 0 ? CommonResult.success() : CommonResult.failed();
    }

    /**
     * 获取职位名称
     *
     * @param userId
     * @return
     */
    @Override
    public CommonResult getUserRole(Integer userId) {
        return CommonResult.success(roleMapper.getUserRole(userId));
    }
}

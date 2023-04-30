package niuniu.javaweb.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import niuniu.javaweb.mapper.MenuMapper;
import niuniu.javaweb.mapper.RoleMapper;
import niuniu.javaweb.pojo.Role;
import niuniu.javaweb.service.RoleService;
import niuniu.javaweb.utils.ArrayUtil;
import niuniu.javaweb.utils.StringUtils;
import niuniu.javaweb.utils.TransferUtil;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.vo.TransferVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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

    /**
     * 获取全部职位
     *
     * @return
     */
    @Override
    @Cacheable(cacheNames = "allRole")
    public CommonResult getAllRole() {
        return CommonResult.success(roleMapper.getAllRole());
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
     * @return
     */
    @Override
    @CacheEvict(cacheNames = "allRole", allEntries = true)
    public CommonResult insertRole(Role role) {
        return roleMapper.insertRole(role) > 0 ? CommonResult.success() : CommonResult.failed();
    }

    /**
     * 修改职务信息
     *
     * @param role
     * @return
     */
    @Override
    @CacheEvict(cacheNames = "allRole", allEntries = true)
    public CommonResult updateRole(Role role) {
        return roleMapper.updateRole(role) > 0 ? CommonResult.success() : CommonResult.failed();
    }
}

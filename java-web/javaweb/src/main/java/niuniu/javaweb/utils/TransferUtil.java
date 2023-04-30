package niuniu.javaweb.utils;

import niuniu.javaweb.mapper.MenuMapper;
import niuniu.javaweb.vo.TransferVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.utils
 * @date 2023/3/26 10:20
 */
@Component
public class TransferUtil {

    private static MenuMapper menuMapper;

    @Autowired
    public void setMenuMapper(MenuMapper menuMapper) {
        TransferUtil.menuMapper = menuMapper;
    }

    /**
     * 构建穿梭框
     *
     * @param transferVOList
     * @return
     */
    public List<TransferVO> buildTransferTree(List<TransferVO> transferVOList) {
        List<TransferVO> topMenuList = new ArrayList<>();
        for (TransferVO transferVO : transferVOList) {
            if (transferVO.getPid() == null) {
                topMenuList.add(transferVO);
            }
        }
        List<TransferVO> result = new ArrayList<>();
        for (TransferVO transferVO : topMenuList) {
            TransferVO treeChildren = this.getTransferTreeChildren(transferVO, transferVOList);
            result.add(treeChildren);
        }
        return result;
    }


    /**
     * 查找父节点指定子节点
     *
     * @param parent
     * @param transferVOList
     * @return
     */
    private TransferVO getTransferTreeChildren(TransferVO parent, List<TransferVO> transferVOList) {
        List<TransferVO> childrenList = new ArrayList<>();
        int i = 1;
        for (TransferVO transferVO : transferVOList) {
            if (parent.getId().equals(transferVO.getPid())) {
                transferVO.setId(parent.getId() + "-" + transferVO.getId());
                /**
                 * 添加操作
                 */
                if (transferVO.getChildren().isEmpty()) {
                    List<TransferVO> operation = this.menuMapper.selectAllOperation();
                    List<TransferVO> childrenOperation = new ArrayList<>();
                    for (TransferVO transferVO1 : operation) {
                        transferVO1.setPid(transferVO.getId());
                        transferVO1.setId(transferVO.getId() + "-" + transferVO1.getOid());
                        childrenOperation.add(transferVO1);
                    }
                    transferVO.setChildren(childrenOperation);
                }

                childrenList.add(transferVO);
            }
        }
        if (!childrenList.isEmpty()) parent.setChildren(childrenList);
        return parent;
    }
}

package niuniu.javaweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import niuniu.javaweb.pojo.Contract;
import niuniu.javaweb.vo.ContractVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.mapper
 * @date 2023/3/30 21:49
 */
@Mapper
public interface ContractMapper extends BaseMapper<Contract> {
    /**
     * 获取合同模板的文件地址
     *
     * @param kindId
     * @return
     */
    String getBaseContractUrl(Integer kindId);

    /**
     * 获取合同模板
     *
     * @return
     */
    List<ContractVO> selectBaseContract();

    /**
     * 新建合同
     *
     * @param contract
     * @return
     */
    int insertContract(Contract contract);

    /**
     * 修改原来的合同状态
     *
     * @param userId
     * @return
     */
    int changeStateByUserId(Integer userId);
}

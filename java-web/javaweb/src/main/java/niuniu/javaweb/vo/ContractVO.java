package niuniu.javaweb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.vo
 * @date 2023/3/31 12:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractVO implements Serializable {

    private static final long serialVersionUID = -5951088168812197961L;

    /**
     * 合同种类编号
     */
    private Integer kindId;

    /**
     * 合同号
     */
    private Integer ContractId;

    /**
     * 合同名称 姓名_合同种类
     */
    private String contractName;

    /**
     * 职务名称
     */
    private String roleName;

    /**
     * 合同状态
     */
    private Integer state;

    /**
     * 用户编号
     */
    private Integer userId;

    /**
     * 签署时间
     */
    private String sign;

    /**
     * 获取存储地址
     */
    private String url;

}

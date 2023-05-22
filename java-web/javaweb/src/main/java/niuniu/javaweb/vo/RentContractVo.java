package niuniu.javaweb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.vo
 * @date 2023/4/5 21:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentContractVo implements Serializable {
    /**
     * 合同编号
     */
    private String url;

    /**
     * 名称
     */
    private String name;

    /**
     * 面积
     */
    private String area;

    /**
     * 面积价格
     */
    private String priceArea;

    /**
     * 其余收费
     */
    private String priceElse;

    /**
     * 总共收费
     */
    private String price;

    /**
     * 身份证号码
     */
    private String idcard;

    /**
     * 住址
     */
    private String address;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 用户id
     */
    private Integer userId;
    

    /**
     * 职业id
     */
    private Integer roleId;
}

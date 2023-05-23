package niuniu.javaweb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.vo
 * @date 2023/5/22 21:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -1594404695195563268L;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 职位id
     */
    private Integer roleId;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String name;


    /**
     * 手机
     */
    private String phone;

    /**
     * 种族
     */
    private String nation;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 住址
     */
    private String address;

    /**
     * 身份证
     */
    private String idcard;

    /**
     * 性别
     */
    private String sex;

    /**
     * 签订时间
     */
    private String sign;

    /**
     * 过期时间
     */
    private String lose;

    /**
     * 签订机关
     */
    private String authority;

    /**
     * 录入时间
     */
    private String addtime;

    /**
     * 注销时间
     */
    private String deltime;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 备注
     */
    private String remark;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 职位名称
     */
    private String roleName;

    /**
     * 职业备注
     */
    private String roleRemark;
}

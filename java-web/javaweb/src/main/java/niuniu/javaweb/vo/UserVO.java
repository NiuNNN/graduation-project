package niuniu.javaweb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.vo
 * @date 2023/4/11 9:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO implements Serializable {

    private static final long serialVersionUID = 7413594303073357800L;

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
     * 订单id
     */
    private Integer rentId;

    /**
     * 录入时间
     */
    private String loginRent;

    /**
     * 离开时间
     */
    private String logoutRent;

    /**
     * 总价格
     */
    private String deposit;

    /**
     * 房间id
     */
    private Integer houseId;

    /**
     * 房间号
     */
    private String houseName;

    /**
     * 房型id
     */
    private Integer styleId;

    /**
     * 房型
     */
    private String styleName;

    /**
     * 面积
     */
    private String area;

    /**
     * 阳台
     */
    private String balcony;

    /**
     * 金额
     */
    private String price;
}

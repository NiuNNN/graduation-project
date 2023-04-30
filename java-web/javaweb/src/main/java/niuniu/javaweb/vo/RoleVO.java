package niuniu.javaweb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.vo
 * @date 2023/3/24 17:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVO implements Serializable {

    private static final long serialVersionUID = -6019480107077057353L;
    /**
     * 编号
     */
    private Integer id;

    /**
     * 员工编号
     */
    private Integer roleId;

    /**
     * 职位
     */
    private String roleName;

    /**
     * 职务
     */
    private String remark;

    /**
     * 状态
     */
    private Integer state;
}

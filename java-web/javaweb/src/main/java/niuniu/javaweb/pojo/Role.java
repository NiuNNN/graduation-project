package niuniu.javaweb.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.pojo
 * @date 2023/2/7 18:04
 */
@TableName(value = "role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {

    private static final long serialVersionUID = 545429227690729923L;
    /**
     * 职位id
     */
    @TableId
    private Integer roleId;

    /**
     * 职位名称
     */
    private String roleName;

    /**
     * 职责
     */
    private String remark;
}

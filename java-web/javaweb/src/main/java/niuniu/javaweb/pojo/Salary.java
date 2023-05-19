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
 * @date 2023/4/25 14:22
 */
@TableName(value = "salary")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Salary implements Serializable {

    private static final long serialVersionUID = 4077468185367639457L;

    @TableId
    private Integer salaryId;

    /**
     * 薪水名称
     */
    private String salaryName;

    /**
     * 薪水
     */
    private String price;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 时间
     */
    private String time;

    /**
     * 执行时间
     */
    private String operation;

    /**
     * 旧的薪水
     */
    private String oldPrice;
}

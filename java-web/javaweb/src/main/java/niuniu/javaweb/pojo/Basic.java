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
 * @date 2023/4/2 11:46
 */
@TableName(value = "basic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Basic implements Serializable {

    private static final long serialVersionUID = 1541531651893319235L;

    /**
     * 编号
     */
    @TableId
    private Integer baseId;

    /**
     * 收费名称
     */
    private String baseName;

    /**
     * 收费
     */
    private String price;

    /**
     * 收费详情
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
     * 是否立即生效 否则下一月再生效
     */
    private String operation;

    /**
     * 旧的数据
     */
    private String oldPrice;
}

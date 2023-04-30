package niuniu.javaweb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.vo
 * @date 2023/4/12 18:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicVO implements Serializable {

    private static final long serialVersionUID = -7790049392260118825L;

    /**
     * 编号
     */
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
     * 旧的收费
     */
    private String oldPrice;

    /**
     * 杂费编号
     */
    private Integer miscellaneousId;
}

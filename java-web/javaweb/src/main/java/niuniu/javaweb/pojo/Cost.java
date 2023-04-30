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
 * @date 2023/4/13 22:54
 */
@TableName(value = "cost")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cost implements Serializable {

    private static final long serialVersionUID = -6751109902214500764L;

    /**
     * 收费编号
     */
    @TableId
    private Integer costId;

    /**
     * 住户居住编号
     */
    private Integer rentId;

    /**
     * 年月
     */
    private String date;

    /**
     * 水的使用量
     */
    private String numWater;

    /**
     * 水费
     */
    private String costWater;

    /**
     * 电费使用量
     */
    private String numElectric;

    /**
     * 电费
     */
    private String costElectric;

    /**
     * 杂费
     */
    private String mis;

    /**
     * 总收费
     */
    private String total;

    /**
     * 电费上传时间
     */
    private String uploadElectric;

    /**
     * 水费上传时间
     */
    private String uploadWater;

    /**
     * 状态
     */
    private Integer state;
}

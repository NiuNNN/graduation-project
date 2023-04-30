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
 * @date 2023/4/25 16:06
 */
@TableName(value = "repair")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Repair implements Serializable {

    private static final long serialVersionUID = 8796997580094563611L;

    /**
     * 维修id
     */
    @TableId
    private Integer repairId;

    /**
     * 住房id
     */
    private Integer rentId;

    /**
     * 维修人员
     */
    private Integer userId;

    /**
     * 信息
     */
    private String message;

    /**
     * 上传时间
     */
    private String report;

    /**
     * 处理时间
     */
    private String fix;

    /**
     * 维修细节
     */
    private String remark;

    /**
     * 状态
     */
    private String state;
}

package niuniu.javaweb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.vo
 * @date 2023/4/25 16:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepairVO implements Serializable {

    private static final long serialVersionUID = -7476331654136287201L;
    /**
     * 维修id
     */
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
    private Integer state;

    /**
     * 维修员姓名
     */
    private String name;

    /**
     * 房间编号
     */
    private Integer houseId;

    /**
     * 房间名
     */
    private String houseName;
}

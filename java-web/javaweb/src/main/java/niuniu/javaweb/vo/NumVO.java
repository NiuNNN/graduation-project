package niuniu.javaweb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.vo
 * @date 2023/5/30 10:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NumVO implements Serializable {

    private static final long serialVersionUID = -6342670967020248453L;

    /**
     * 楼层id
     */
    private Integer floorId;

    /**
     * 全部数量
     */
    private Integer allNum;

    /**
     * 单层数量
     */
    private Integer peaceNum;
}

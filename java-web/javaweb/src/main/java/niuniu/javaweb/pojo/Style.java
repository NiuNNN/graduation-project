package niuniu.javaweb.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import niuniu.javaweb.utils.tools.StateSerializer;

import java.io.Serializable;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.pojo
 * @date 2023/3/19 12:03
 */
@TableName(value = "style")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Style implements Serializable {

    private static final long serialVersionUID = -6977364630602220911L;

    /**
     * 房型编号
     */
    @TableId
    private Integer styleId;

    /**
     * 房型名称
     */
    private String styleName;

    /**
     * 房型价格
     */
    private String price;

    /**
     * 房型面积
     */
    private String area;

    /**
     * 房型数量
     */
    private String num;

    /**
     * 阳台
     */
    private String balcony;

    /**
     * 楼层
     */
    private String floor;

    /**
     * 可用数量
     */
    private String available;

    /**
     * 房型状态
     */
    @JsonSerialize(using = StateSerializer.class)
    private String state;
}

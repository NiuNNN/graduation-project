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
 * @date 2023/3/21 9:51
 */
@TableName(value = "house")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class House implements Serializable {

    private static final long serialVersionUID = 5418308955090719885L;

    /**
     * 编号
     */
    @TableId
    private Integer houseId;

    /**
     * 房号
     */
    private String houseName;

    /**
     * 房型编号
     */
    private Integer styleId;

    /**
     * 楼层编号
     */
    private Integer floorId;

    /**
     * 状态
     */
    private String state;

    /**
     * 创建时间
     */
    private String insertTime;
}

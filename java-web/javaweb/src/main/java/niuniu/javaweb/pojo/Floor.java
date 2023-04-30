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
 * @date 2023/3/20 15:47
 */
@TableName(value = "floor")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Floor implements Serializable {

    private static final long serialVersionUID = -2085491132238831058L;

    /**
     * 编号
     */
    @TableId
    private Integer floorId;

    /**
     * 楼层
     */
    private Integer floorName;

    /**
     * 总面积
     */
    private String area;

    /**
     * 可用面积
     */
    private String available;
}

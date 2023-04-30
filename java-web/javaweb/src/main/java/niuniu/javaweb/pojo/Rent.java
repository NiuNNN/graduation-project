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
 * @date 2023/4/6 18:55
 */
@TableName(value = "rent")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rent implements Serializable {

    private static final long serialVersionUID = 6943122899585727350L;

    /**
     * 住户居住编号
     */
    @TableId
    private Integer rentId;

    /**
     * 房屋编号
     */
    private Integer houseId;

    /**
     * 用户编号
     */
    private Integer userId;

    /**
     * 录入时间
     */
    private String login;

    /**
     * 总收费
     */
    private String deposit;

    /**
     * 离开时间
     */
    private String logout;

    /**
     * 判断状态
     */
    private Integer state;
}

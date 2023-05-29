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
 * @date 2023/5/29 16:19
 */
@TableName(value = "ip")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ip implements Serializable {

    private static final long serialVersionUID = -6861897779125764467L;

    /**
     * id
     */
    @TableId
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 具体地址
     */
    private String ipPossession;

    /**
     * 城市
     */
    private String ipCity;

    /**
     * 访问时间
     */
    private String time;
}

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
 * @date 2023/3/31 12:15
 */
@TableName(value = "contract")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contract implements Serializable {

    private static final long serialVersionUID = 2461072354934292120L;

    /**
     * 合同编号
     */
    @TableId
    private Integer contractId;

    /**
     * 合同种类编号
     */
    private Integer kindId;

    /**
     * 用户编号
     */
    private Integer userId;

    /**
     * 签署时间
     */
    private String sign;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 存储路径
     */
    private String url;
}

package niuniu.javaweb.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.vo
 * @date 2023/5/7 11:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckOutVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -1443799500034468136L;

    /**
     * 退房编号
     */
    @TableId
    private Integer checkoutId;

    /**
     * 入住编号
     */
    private Integer rentId;

    /**
     * 处理人id
     */
    private Integer userId;

    /**
     * 申请时间
     */
    private String start;

    /**
     * 处理时间
     */
    private String end;

    /**
     * 留言
     */
    private String message;

    /**
     * 描述
     */
    private String remark;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 房号
     */
    private String houseName;
}

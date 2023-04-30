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
 * @date 2023/4/19 17:57
 */
@TableName(value = "checkout")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckOut implements Serializable {

    private static final long serialVersionUID = 778198395154128063L;

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

}

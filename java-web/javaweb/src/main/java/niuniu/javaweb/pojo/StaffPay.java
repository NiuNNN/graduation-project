package niuniu.javaweb.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.pojo
 * @date 2023/5/24 9:55
 */
@TableName(value = "staff_pay")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffPay implements Serializable {
    @Serial
    private static final long serialVersionUID = 8256733357130385486L;

    @TableId
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 薪水
     */
    private String price;

    /**
     * 其他薪水
     */
    private String elsePrice;

    /**
     * 基本工资
     */
    private String basePrice;

    /**
     * 备注
     */
    private String remark;

    /**
     * 订单编号
     */
    private String outTradeNo;

    /**
     * 录入时间
     */
    private String time;

    /**
     * 状态
     */
    private Integer state;
}

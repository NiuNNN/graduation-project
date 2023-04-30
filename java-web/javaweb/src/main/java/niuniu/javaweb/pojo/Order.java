package niuniu.javaweb.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.pojo
 * @date 2023/4/9 15:30
 */
@TableName(value = "order_pay")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

    private static final long serialVersionUID = -7255392008873980653L;

    /**
     * 押金编号
     */
    @TableId(type = IdType.AUTO)
    private Integer orderId;

    /**
     * 居住编号
     */
    private Integer rentId;

    /**
     * 收款人
     */
    private Integer userId;

    /**
     * 金额
     */
    private String total;

    /**
     * 时间
     */
    private String time;

    /**
     * 操作选择
     */
    private String operation;

    /**
     * 状态
     */
    private String state;

    /**
     * 订单号
     */
    private String outTradeNo;

    /**
     * 支付宝订单号
     */
    private String tradeNo;

    /**
     * 类型
     */
    private String type;

    /**
     * 付款时间
     */
    private String payTime;

    /**
     * 存储杂费缴费时的金额细节
     */
    private String misPrice;
}

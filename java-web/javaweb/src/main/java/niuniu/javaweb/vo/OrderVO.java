package niuniu.javaweb.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.vo
 * @date 2023/4/17 16:20
 */
@HeadRowHeight(value = 15)
@ColumnWidth(value = 18)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVO implements Serializable {

    @ExcelIgnore
    private static final long serialVersionUID = 3061111816177910829L;

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
     * 房号
     */
    private String houseName;

    /**
     * 房费
     */
    private String housePrice;

    /**
     * 标准电费
     */
    private String basicElectric;

    /**
     * 标准水费
     */
    private String basicWater;

    /**
     * 用水量
     */
    private String numWater;

    /**
     * 用电量
     */
    private String numElectric;

    /**
     * 水费
     */
    private String costWater;

    /**
     * 电费
     */
    private String costElectric;

    /**
     * 时间
     */
    private String date;

    /**
     * 杂费
     */
    private String mis;

    /**
     * 收款人
     */
    private String name;

    /**
     * 杂费信息
     */
    private String misPrice;
}

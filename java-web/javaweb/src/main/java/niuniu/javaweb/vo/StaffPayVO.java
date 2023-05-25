package niuniu.javaweb.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.vo
 * @date 2023/5/25 11:56
 */
@HeadRowHeight(value = 15)
@ColumnWidth(value = 18)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffPayVO implements Serializable {

    @ExcelIgnore
    @Serial
    private static final long serialVersionUID = 5231595297648664895L;

    @ExcelIgnore
    private Integer id;

    /**
     * 用户id
     */
    @ExcelIgnore
    private Integer userId;


    /**
     * 用户账号
     */
    @ExcelProperty("员工账号")
    private String username;

    /**
     * 订单编号
     */
    @ExcelProperty("订单编号")
    private String outTradeNo;

    /**
     * 姓名
     */
    @ExcelProperty("姓名")
    private String name;

    /**
     * 职位名称
     */
    @ExcelProperty("职位")
    private String roleName;

    /**
     * 基本工资
     */
    @ExcelProperty("基本工资")
    private String basePrice;

    /**
     * 其他薪水
     */
    @ExcelProperty("福利")
    private String elsePrice;


    /**
     * 薪水
     */
    @ExcelProperty("薪水")
    private String price;

    /**
     * 备注
     */
    @ExcelIgnore
    private String remark;


    /**
     * 录入时间
     */
    @ExcelProperty("发放时间")
    private String time;

}

package niuniu.javaweb.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import niuniu.javaweb.utils.excel.UnLockCell;

import java.io.Serializable;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.vo
 * @date 2023/4/13 23:20
 */
@HeadRowHeight(value = 15)
@ColumnWidth(value = 18)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElectricInExcelVO implements Serializable {

    @ExcelIgnore
    private static final long serialVersionUID = -7997668598914132582L;

    @ExcelProperty("订单号")
    private Integer rentId;

    @ExcelProperty("房号")
    private String houseName;

    @ExcelIgnore
    private Integer costId;

    @ExcelIgnore
    private String cost;

    @ExcelProperty("月份")
    private String time;

    @ExcelProperty("用电量/度")
    @UnLockCell
    private String num;

    @ExcelIgnore
    private Integer state;
}

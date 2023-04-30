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
 * @date 2023/4/13 23:04
 */
@HeadRowHeight(value = 15)
@ColumnWidth(value = 18)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WaterInExcelVO implements Serializable {

    @ExcelIgnore
    private static final long serialVersionUID = 8428840080061211213L;

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

    @UnLockCell
    @ExcelProperty("用水量/方")
    private String num;

    @ExcelIgnore
    private Integer state;
}

package niuniu.javaweb.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.vo
 * @date 2023/5/28 17:42
 */
@HeadRowHeight(value = 15)
@ColumnWidth(value = 18)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialVO implements Serializable {
    @ExcelIgnore
    private static final long serialVersionUID = 2519531432470454117L;

    /**
     * id
     */
    @ExcelProperty("编号")
    @TableId
    private Integer financialId;

    /**
     * 年月
     */
    @ExcelProperty("年月")
    private String date;

    /**
     * 审核人
     */
    @ExcelProperty("审核人")
    private String name;

    /**
     * 审核时间
     */
    @ExcelProperty("审核时间")
    private String time;

    /**
     * 审核状态
     */
    @ExcelProperty("审核状态")
    private String status;

    /**
     * 支出水费
     */
    @ExcelProperty("水费总支出")
    private String outWater;

    /**
     * 支出电费
     */
    @ExcelProperty("电费总支出")
    private String outElectric;

    /**
     * 收入水费
     */
    @ExcelProperty("水费总收入")
    private String inWater;

    /**
     * 收入电费
     */
    @ExcelProperty("电费总收入")
    private String inElectric;

    /**
     * 盈利水费
     */
    @ExcelProperty("水费总盈利")
    private String proWater;

    /**
     * 盈利电费
     */
    @ExcelProperty("电费总盈利")
    private String proElectric;

    /**
     * 支出人工
     */
    @ExcelProperty("薪水总支出")
    private String outSalary;

    /**
     * 杂费收入
     */
    @ExcelProperty("杂费总收入")
    private String inMis;

    /**
     * 杂费支出
     */
    @ExcelProperty("杂费总支出")
    private String outMis;

    /**
     * 租金
     */
    @ExcelProperty("租金总收入")
    private String inRent;

    /**
     * 总收入
     */
    @ExcelProperty("总收入")
    private String advanceTotal;

    /**
     * 最终支出
     */
    @ExcelProperty("总支出")
    private String RealTotal;

    /**
     * 最终获利
     */
    @ExcelProperty("获利")
    private String finalTotal;

    /**
     * 修改时间
     */
    @ExcelIgnore
    private String uploadTime;

    @ExcelIgnore
    private String remark;

    @ExcelIgnore
    private Integer state;

}

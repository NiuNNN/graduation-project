package niuniu.javaweb.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.pojo
 * @date 2023/4/17 9:06
 */
@HeadRowHeight(value = 15)
@ColumnWidth(value = 18)
@TableName(value = "financial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Financial implements Serializable {

    @ExcelIgnore
    private static final long serialVersionUID = 7265305738509975523L;

    /**
     * id
     */
    @ExcelProperty("编号")
    @TableId
    private Integer financialId;

    /**
     * 年月
     */
    @ExcelProperty("月份")
    private String date;

    /**
     * 支出水费
     */
    @ExcelProperty("支出总水费")
    private String outWater;

    /**
     * 支出电费
     */
    @ExcelProperty("支出总电费")
    private String outElectric;

    /**
     * 收入水费
     */
    @ExcelProperty("收入总水费")
    private String inWater;

    /**
     * 收入电费
     */
    @ExcelProperty("收入总电费")
    private String inElectric;

    /**
     * 盈利水费
     */
    @ExcelProperty("盈利总水费")
    private String proWater;

    /**
     * 盈利电费
     */
    @ExcelProperty("盈利总电费")
    private String proElectric;

    /**
     * 支出人工
     */
    @ExcelProperty("人工总支出")
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
    private Integer state;
}

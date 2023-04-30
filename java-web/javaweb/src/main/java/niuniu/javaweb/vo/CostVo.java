package niuniu.javaweb.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.vo
 * @date 2023/4/14 16:00
 */
@HeadRowHeight(value = 15)
@ColumnWidth(value = 18)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CostVo implements Serializable {

    @ExcelIgnore
    private static final long serialVersionUID = 1214932755947187688L;

    @ExcelIgnore
    private Integer costId;

    /**
     * 住户居住编号
     */
    @ExcelProperty("订单号")
    private Integer rentId;

    /**
     * 房号
     */
    @ExcelProperty("房号")
    private String houseName;

    /**
     * 年月
     */
    @ExcelProperty("月份")
    private String date;

    /**
     * 房费
     */
    @ExcelProperty("房费")
    private String housePrice;

    /**
     * 水的使用量
     */
    @ExcelProperty("用水量/方")
    private String numWater;

    /**
     * 水费
     */
    @ExcelProperty("水费/元")
    private String costWater;

    /**
     * 水费上传时间
     */
    @ExcelProperty("水费上传时间")
    private String uploadWater;


    /**
     * 电费使用量
     */
    @ExcelProperty("用电量/度")
    private String numElectric;

    /**
     * 电费
     */
    @ExcelProperty("电费/元")
    private String costElectric;

    /**
     * 电费上传时间
     */
    @ExcelProperty("电费上传时间")
    private String uploadElectric;

    /**
     * 杂费
     */
    @ExcelProperty("杂费")
    private String mis;

    /**
     * 总收费
     */
    @ExcelProperty("总收费")
    private String total;

    /**
     * 状态
     */
    @ExcelIgnore
    private Integer state;

    /**
     * 除水电以外的费用
     */
    @ExcelIgnore
    private String deposit;
}

package niuniu.javaweb.handler;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import niuniu.javaweb.utils.excel.UnLockCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.handler
 * @date 2023/4/14 17:38
 */
public class CellHandler implements CellWriteHandler {

    private static final String PASSWORD = "niuniu666";


    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {
    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
    }

    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, WriteCellData<?> cellData, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
    }

    /**
     * 在完成对单元格的所有操作后调用
     *
     * @param writeSheetHolder
     * @param writeTableHolder
     * @param cellDataList
     * @param cell
     * @param head
     * @param relativeRowIndex
     * @param isHead
     */
    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        String fieldName = head.getFieldName();
        Class<?> clazz = writeSheetHolder.getClazz();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.getName().equals(fieldName)) {
                if (field.isAnnotationPresent(UnLockCell.class) && field.isAnnotationPresent(ExcelProperty.class)) {
//                    System.out.println(fieldName);
                    Map<String, Object> properties = new HashMap<>(1);
                    properties.put(CellUtil.LOCKED, false);
                    CellUtil.setCellStyleProperties(cell, properties);
                }
            }
        }

    }

    @Override
    public int order() {
        return Integer.MAX_VALUE;
    }
}

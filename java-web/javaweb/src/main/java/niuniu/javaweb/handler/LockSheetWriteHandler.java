package niuniu.javaweb.handler;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.handler
 * @date 2023/4/14 17:37
 */
public class LockSheetWriteHandler implements SheetWriteHandler {

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        Sheet sheet = writeSheetHolder.getSheet();
        //锁定工作簿，设置保护密码
        sheet.protectSheet("niuniu666");
        // 锁定单元格不可选中(防止别人直接复制内容到其他excel修改)
        ((SXSSFSheet) writeSheetHolder.getSheet()).lockSelectLockedCells(true);
    }
}



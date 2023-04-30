package niuniu.javaweb.utils.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.handler.WriteHandler;
import niuniu.javaweb.handler.CellHandler;
import niuniu.javaweb.handler.LockSheetWriteHandler;
import niuniu.javaweb.listener.ExcelListener;
import niuniu.javaweb.utils.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.utils.excel
 * @date 2023/2/10 17:44
 */
@Component
public class ExcelUtil<T> {
    // excel文件后缀
    private final static String EXCEL_2003 = "xls";
    private final static String EXCEL_2007 = "xlsx";

    // sheet名字
    public final static String SHEET_NAME = "模板";

    // 校验文件后缀是否为 xls、xlsx
    public static boolean checkExcelExtension(MultipartFile excel) {
        String filename = excel.getOriginalFilename();
        if (StringUtils.isEmpty(filename)) {
            System.out.println("【校验Excel文件后缀】Excel文件名为空");
            return false;
        }
        int index = filename.lastIndexOf(".");
        if (index == -1) {
            System.out.println("【校验Excel文件后缀】Excel文件名中没有点号");
            return false;
        }
        String extension = filename.substring(index + 1);
        return Arrays.asList(EXCEL_2003, EXCEL_2007).contains(extension);
    }

    // 读取excel文件
    public List<T> simpleExcelRead(String filePath, Class<T> clazz) {
        ExcelListener<T> excelListener = new ExcelListener();
        EasyExcel.read(filePath, clazz, excelListener).sheet().doRead();
        List<T> dataList = excelListener.getDataList();
        return dataList;
    }

    // 写Excel文件
    public void simpleExcelWrite(String filePath, Class<T> clazz, List<T> dataList) {
        EasyExcel.write(filePath, clazz).sheet(SHEET_NAME).doWrite(dataList);
    }

    //导出excel指定锁定列
    public static void excelLockExport(Class head, String excelName, List data, String sheetName) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        try {
            // 这里URLEncoder.encode可以防止浏览器端导出excel文件名中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode(excelName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), head)
                    .registerWriteHandler(new WriteHandler() {
                        @Override
                        public int order() {
                            return 0;
                        }
                    })
                    //锁定工作簿
                    .registerWriteHandler(new LockSheetWriteHandler())
                    //指定单元格解锁
                    .registerWriteHandler(new CellHandler())
                    .sheet(sheetName == null ? "Sheet1" : sheetName).doWrite(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("导出数据失败" + e);
        }
    }

}

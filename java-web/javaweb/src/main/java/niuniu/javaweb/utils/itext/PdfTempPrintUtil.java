package niuniu.javaweb.utils.itext;


/**
 * @author NiuNiu666
 * @package niuniu.javaweb.utils.itext
 * @date 2023/3/16 19:04
 */

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.annot.PdfWidgetAnnotation;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import niuniu.javaweb.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于生成合同
 */
@Component
public class PdfTempPrintUtil {


    //生成文件路径
    private static String DEST_PATH;

    //字体样式 仿宋
    private static String FONT_PATH;


    @Value("${pdf.DEST_PATH}")
    public void setDestPath(String destPath) {
        this.DEST_PATH = destPath;
    }

    @Value("${pdf.FONT_PATH}")
    public void setFontPath(String fontPath) {
        this.FONT_PATH = fontPath;
    }

    /**
     * 生成合同pdf文件
     *
     * @param TEMP_PATH
     * @param PdfName
     * @param map
     * @return
     * @throws IOException
     */
    public static String generateContract(String TEMP_PATH, String PdfName, HashMap<String, String> map, String IMAGE) throws IOException {
//        System.out.println(TEMP_PATH);
        return generate(TEMP_PATH, PdfName, map, IMAGE);

    }


    /**
     * 生成合同
     *
     * @param TEMP_PATH
     * @param PdfName
     * @param map
     * @param IMAGE
     * @return
     * @throws IOException
     */
    private static String generate(String TEMP_PATH, String PdfName, HashMap<String, String> map, String IMAGE) throws IOException {
        PdfDocument pdfDocument = new PdfDocument(new PdfReader(DEST_PATH + "\\" + TEMP_PATH), new PdfWriter(DEST_PATH + "\\" + PdfName + ".pdf"));

        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDocument, false);

        //设置文字样式
        PdfFont font = PdfFontFactory.createFont(FONT_PATH);

        //设置文字颜色
        DeviceRgb color = new DeviceRgb(0, 0, 0);


        for (Map.Entry<String, String> entry : map.entrySet()) {
            form.getFormFields().get(entry.getKey()).setValue(entry.getValue()).setColor(color).setFont(font);
        }

        if (!StringUtils.isEmpty(IMAGE)) {
//            System.out.println(123);
            /**
             * 获取具体表单域
             */
            PdfFormField formField = form.getField("image");
            /**
             * 获取控件对象
             */
            List<PdfWidgetAnnotation> widgetAnnotationList = formField.getWidgets();
            PdfWidgetAnnotation widgetAnnotation = widgetAnnotationList.get(0);

            /**
             * 域左下角顶点坐标
             */
            float x1 = widgetAnnotation.getRectangle().getAsNumber(0).floatValue();
            float y1 = widgetAnnotation.getRectangle().getAsNumber(1).floatValue();

            /**
             * 域右上角顶点坐标
             */
            float x2 = widgetAnnotation.getRectangle().getAsNumber(2).floatValue();
            float y2 = widgetAnnotation.getRectangle().getAsNumber(3).floatValue();

            /**
             * 域宽高
             */
            float fieldWidth = x2 - x1;
            float fieldHeight = y2 - y1;

            /**
             * 创建图片对象
             */
            Image img = new Image(ImageDataFactory.create(IMAGE));
            /**
             * 图片自适应
             */
            Image image = img.scaleToFit(fieldWidth, fieldHeight);
            /**
             * 获取图片自适应缩放后的宽高
             */
            float imageScaledWidth = image.getImageScaledWidth();
            float imageScaledHeight = image.getImageScaledHeight();
            /**
             * 计算图片居中位置
             * x坐标 = 域x坐标+（域宽度/2）-（自适应后图片宽度/2）
             * y坐标 = 域y坐标+（域宽度/2）-（自适应后图片高度/2）
             */
            float centerX = x1 + (fieldWidth / 2) - (imageScaledWidth / 2);
            float centerY = y1 + (fieldHeight / 2) - (imageScaledHeight / 2);

            img.setFixedPosition(2, centerX, centerY);
            /**
             * 获取文件对象，将图片添加到文档
             */
            Document document = new Document(pdfDocument);
            document.add(img);

        }
        form.flattenFields();
        pdfDocument.close();
        return PdfName + ".pdf";
    }

}

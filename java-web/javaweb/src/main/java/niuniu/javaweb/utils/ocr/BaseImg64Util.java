package niuniu.javaweb.utils.ocr;

import org.apache.commons.codec.binary.Base64;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.utils.ocr
 * @date 2023/2/10 17:27
 */
public class BaseImg64Util {
    /**
     * 将一张本地图片转化成Base64字符串
     * @param imgPath 本地图片地址
     * @return 图片转化base64后再UrlEncode结果
     */
    public static String getImageStrFromPath(String imgPath) {
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgPath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        Base64 encoder = new Base64();
        // 返回Base64编码过的字节数组字符串
        return encoder.encodeToString(data).replaceAll("\r\n", "").replaceAll("\\+", "%2B");
    }
}

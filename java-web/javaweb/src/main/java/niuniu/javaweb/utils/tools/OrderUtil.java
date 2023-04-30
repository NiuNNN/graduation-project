package niuniu.javaweb.utils.tools;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.utils.tools
 * @date 2023/4/9 18:44
 */
public class OrderUtil {
    /**
     * 根据时间戳生成订单号
     */
    public static String getOrderNo() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        LocalDateTime localDateTime = Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
        return df.format(localDateTime);
    }
}

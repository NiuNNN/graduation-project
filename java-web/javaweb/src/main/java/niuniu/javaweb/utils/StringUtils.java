package niuniu.javaweb.utils;

import org.springframework.stereotype.Component;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.utils
 * @date 2023/2/7 17:55
 */
@Component
public class StringUtils {


    /**
     * 判断字符串是否为空
     *
     * @param param
     * @return
     */
    public static boolean isEmpty(String param) {
        return param == null || "".equals(param.trim());
    }
}




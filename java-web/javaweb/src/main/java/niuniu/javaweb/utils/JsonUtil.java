package niuniu.javaweb.utils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.utils
 * @date 2023/2/7 17:55
 */

public class JsonUtil {
    private static ObjectMapper objectMapper = null;
    static {
        objectMapper = new ObjectMapper();
        //设置 date 格式
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * map to JSON
     * @param param
     * @return
     */
    public static String mapToJson(Map param){
        try {
            return objectMapper.writeValueAsString(param);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("转换异常");
        }
    }

    /**
     * list to JSON
     * @param param
     * @return
     */
    public static String listToJson(List param){
        try {
            return objectMapper.writeValueAsString(param);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("转换异常");
        }
    }

    /**
     * 将JSON to map
     * @param jsonMap
     * @return
     */
    public static Map<String,Object> jsonToMap(String jsonMap){
        try {
            return objectMapper.readValue(jsonMap,Map.class);
        } catch (Exception e) {
            throw new RuntimeException("转换异常");
        }
    }


    /**
     * 将JSON to list
     * @param jsonList
     * @return
     */
    public static List jsonToList(String jsonList){
        try {
            return objectMapper.readValue(jsonList,List.class);
        } catch (Exception e) {
            throw new RuntimeException("转换异常");
        }
    }

    /**
     * @param <T>
     * @return
     */
    public static <T> String objToJson(T t){
        try {
            return objectMapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("转换异常");
        }
    }

    /**
     * json字符串转为对象
     * @param info
     * @param <T>
     * @return
     */
    public static <T> T jsonToObj(String info,Class<T> clazz) {
        try {
            return objectMapper.readValue(info,clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("转换异常");
        }
    }

}

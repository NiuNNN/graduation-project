package niuniu.javaweb.utils.tools;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.utils.tools
 * @date 2023/3/19 17:20
 */
public class StateSerializer extends JsonSerializer<String> {
    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if ("1".equals(s)) {
            jsonGenerator.writeString("正常");
        } else {
            jsonGenerator.writeString("已下架");
        }
    }
}

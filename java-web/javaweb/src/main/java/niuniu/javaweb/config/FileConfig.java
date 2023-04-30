package niuniu.javaweb.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.config
 * @date 2023/2/10 17:53
 */
@Data
@Component
@ConfigurationProperties(prefix = "file")
public class FileConfig implements WebMvcConfigurer {

    // 上传路径
    private String uploadPath;

    //下载路径
    private String downloadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/view/**").addResourceLocations("file:"+uploadPath+"/");

    }
}

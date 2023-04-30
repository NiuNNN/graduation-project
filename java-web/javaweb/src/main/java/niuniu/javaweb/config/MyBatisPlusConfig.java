package niuniu.javaweb.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.config
 * @date 2023/3/19 18:01
 */

@Configuration // 配置类
public class MyBatisPlusConfig {

    @Configuration
    public class MybatisPlusConfig {

        @Bean
        public MybatisPlusInterceptor mybatisPlusInterceptor() {
            MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
            mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
            return mybatisPlusInterceptor;
        }
    }

}
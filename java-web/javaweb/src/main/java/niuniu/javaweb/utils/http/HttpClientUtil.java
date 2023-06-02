package niuniu.javaweb.utils.http;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;

import javax.annotation.PostConstruct;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.utils.http
 * @date 2023/6/2 8:26
 */
public class HttpClientUtil {

    private static RequestConfig requestConfig;

    @PostConstruct
    public void beforeInit() {
        // 配置信息
        HttpClientUtil.requestConfig = RequestConfig.custom()
                // 设置Cookie策略为忽略
                .setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
    }

}

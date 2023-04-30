package niuniu.javaweb.utils.ocr;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.utils.ocr
 * @date 2023/2/10 17:26
 */
@Component
@PropertySource("classpath:config/ocr.yml")
public class AccessTokenUtil {
    @Value("${clientId}")
    private String APIKEY;

    @Value("${clientSecret}")
    private String SecretKEY;

    // 获取Token路径
    @Value("${clientPath}")
    private String PATH;


    public String getAuth() {
        // 获取token地址
        String getAccessTokenUrl = PATH
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + APIKEY
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + SecretKEY;
        try {
            System.out.println(getAccessTokenUrl);
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
//			for (String key : map.keySet()) {
//				System.err.println(key + "--->" + map.get(key));
//			}
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果示例
             */
//			System.err.println("result:" + result);

            JSONObject jsonObject = new JSONObject(result);
            String access_token = jsonObject.getString("access_token");
            return access_token;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }
}

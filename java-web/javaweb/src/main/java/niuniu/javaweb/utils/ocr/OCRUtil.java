package niuniu.javaweb.utils.ocr;

import niuniu.javaweb.pojo.User;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.utils.ocr
 * @date 2023/2/10 17:29
 */
@Component
@PropertySource("classpath:config/ocr.yml")
public class OCRUtil {
    private AccessTokenUtil accessTokenUtil = BeanUtil.getBean(AccessTokenUtil.class);

    private String POST_URL = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard?access_token=" + accessTokenUtil.getAuth();

//必传参数：id_card_side=front -front：身份证含照片的一面 -back：身份证带国徽的一面  自动检测身份证正反面，如果传参指定方向与图片相反，支持正常识别，返回参数image_status字段为"reversed_side"

    /**
     * 识别本地图片的文字
     *
     * @param path 本地图片地址
     * @return 识别结果，为json格式
     * @throws URISyntaxException URI打开异常
     * @throws IOException        io流异常
     */
    public User checkFile(String path, int flag) throws URISyntaxException, IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new NullPointerException("图片不存在");
        }
        String image = BaseImg64Util.getImageStrFromPath(path);

        String param = flag > 0 ? "image=" + image + "&id_card_side=front" : "image=" + image + "&id_card_side=back";
        return post(param);
    }

    /**
     * @param url 图片url
     * @return 识别结果，为json格式
     */
    public User checkUrl(String url) throws IOException, URISyntaxException {
        String param = "url=" + url;
        return post(param);
    }


    /**
     * 通过传递参数：url和image进行文字识别
     *
     * @param param 区分是url还是image识别
     * @return 识别结果
     * @throws URISyntaxException URI打开异常
     * @throws IOException        IO流异常
     */
    private User post(String param) throws URISyntaxException, IOException {
        // 开始搭建post请求
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost();
        URI url = new URI(POST_URL);
        post.setURI(url);
        // 设置请求头，请求头必须为application/x-www-form-urlencoded，因为是传递一个很长的字符串，不能分段发送
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");
        StringEntity entity = new StringEntity(param);
        post.setEntity(entity);
        HttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == 200) {
            String str;
            try {
                str = EntityUtils.toString(response.getEntity());
                return getVOByJson(str);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * 身份证参数转换
     *
     * @param jsonResult
     * @return
     */
    public static User getVOByJson(String jsonResult) {
        User user = new User();
        try {
            JSONObject jsonObject = new JSONObject(jsonResult);
            JSONObject words_result = jsonObject.getJSONObject("words_result");
            Iterator<String> it = words_result.keys();
            while (it.hasNext()) {
                String key = it.next();
//                System.out.println(key);
                JSONObject result = words_result.getJSONObject(key);
                String value = result.getString("words");
                switch (key) {
                    case "姓名":
                        user.setName(value);
                        break;
                    case "民族":
                        user.setNation(value);
                        break;
                    case "住址":
                        user.setAddress(value);
                        break;
                    case "公民身份号码":
                        user.setIdcard(value);
                        break;
                    case "出生":
                        user.setBirthday(value);
                        break;
                    case "性别":
                        user.setSex(value);
                        break;
                    case "失效日期":
                        user.setLose(value);
                        break;
                    case "签发日期":
                        user.setSign(value);
                        break;
                    case "签发机关":
                        user.setAuthority(value);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}

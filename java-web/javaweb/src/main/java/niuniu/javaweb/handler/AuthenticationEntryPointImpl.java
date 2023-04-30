package niuniu.javaweb.handler;

import com.alibaba.fastjson.JSON;
import niuniu.javaweb.utils.WebUtils;
import niuniu.javaweb.utils.result.CommonResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.handler
 * @date 2023/2/7 17:50
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        CommonResult result = CommonResult.validateFailed("用户认证失败，请重新登陆！");

        String jsonString = JSON.toJSONString(result);
        //处理异常
        WebUtils.renderString(response,jsonString);
    }
}

package niuniu.javaweb.handler;

import com.alibaba.fastjson.JSON;
import niuniu.javaweb.utils.WebUtils;
import niuniu.javaweb.utils.result.CommonResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        CommonResult result = CommonResult.failed("用户授权失败，请联系管理员！");

        String jsonString = JSON.toJSONString(result);
        //处理异常
        WebUtils.renderString(response,jsonString);
    }
}
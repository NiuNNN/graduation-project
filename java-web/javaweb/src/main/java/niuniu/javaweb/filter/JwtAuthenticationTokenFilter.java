package niuniu.javaweb.filter;

import niuniu.javaweb.config.redis.RedisCache;
import niuniu.javaweb.pojo.Login;
import niuniu.javaweb.utils.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.filter
 * @date 2023/2/7 17:48
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            //放行
            filterChain.doFilter(request, response);
            return;
        }
        //解析token
        String username;
        try {
            username = JWTUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }
//        System.out.println(username);
        //3.通过解析的userId去redis查询用户信息
        Login login = redisCache.getCacheObject("Login:" + username);
//        System.out.println(login);
        if (Objects.isNull(login)) {
            throw new RuntimeException("用户未登录");
        }

        //4.存入SecurityContextHolder供后续过滤器使用
        //5.存入权限信息
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login, null, login.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //6.放行
        filterChain.doFilter(request, response);
    }
}


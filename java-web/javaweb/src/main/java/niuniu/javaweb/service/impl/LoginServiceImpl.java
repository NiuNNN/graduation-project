package niuniu.javaweb.service.impl;

import niuniu.javaweb.config.redis.RedisCache;
import niuniu.javaweb.pojo.Login;
import niuniu.javaweb.pojo.User;
import niuniu.javaweb.service.LoginService;
import niuniu.javaweb.utils.FileUtil;
import niuniu.javaweb.utils.jwt.JWTUtil;
import niuniu.javaweb.utils.result.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service.impl
 * @date 2023/2/9 11:04
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    private static String DEST_PATH;

    @Value("${pdf.DEST_PATH}")
    public void setDestPath(String destPath) {
        this.DEST_PATH = destPath;
    }

    @Override
    @CacheEvict(cacheNames = "userMenu", key = "#user.username", beforeInvocation = true)
    public CommonResult StaffLogin(User user) {
        redisCache.deleteObject("Login:" + user.getUsername());
        System.out.println(user);
        //调用AuthenticationManager authenticationManager进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        System.out.println(authenticationToken);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        System.out.println("authentication" + authentication);

        //如果认证没通过，给出提示
        if (Objects.isNull(authentication)) {
            throw new RuntimeException("登录失败！");
        }
        //认证通过，使用username生成jwt返回前端，并username为key，用户信息为value存入redis
        Login login = (Login) authentication.getPrincipal();

        System.out.println(login);
        if (login.getUser().getRoleId() != 1) {
            String username = login.getUser().getUsername();
            String jwt = JWTUtil.generateToken(username);
            Map<String, Object> map = new HashMap<>();
            map.put("token", jwt);
            map.put("menu", login);

            /**
             * 设置缓存时间为 3600s
             */
            redisCache.setCacheObject("Login:" + username, login, 86400000, TimeUnit.SECONDS);


            FileUtil.deleteFile(DEST_PATH, "draft");

            return CommonResult.success(map, "登录成功");
        } else {
            return CommonResult.forbidden("没有权限登录");
        }
    }

    @Override
//    @CacheEvict(cacheNames = "Login", key = "#user.username", beforeInvocation = true)
    public CommonResult UserLogin(User user) {
        redisCache.deleteObject("Login:" + user.getUsername());
        System.out.println("运行到此处");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

//        System.out.println("authentication"+authentication);

        //如果认证没通过，给出提示
        if (Objects.isNull(authentication)) {
            throw new RuntimeException("登录失败！");
        }
        //认证通过，使用username生成jwt返回前端，并username为key，用户信息为value存入redis
        Login login = (Login) authentication.getPrincipal();

        System.out.println(login);

        if (login.getUser().getRoleId() == 1) {
            System.out.println(123);
            String username = login.getUser().getUsername();
            String jwt = JWTUtil.generateToken(username);
            Map<String, Object> map = new HashMap<>();
            map.put("token", jwt);
            map.put("menu", login);

            /**
             * 设置缓存时间为 3600s
             */
            redisCache.setCacheObject("Login:" + username, login, 86400000, TimeUnit.SECONDS);

            return CommonResult.success(map, "登录成功");
        } else {
            System.out.println("fail");
            return CommonResult.forbidden("没有权限登录");
        }
    }

    @Override
    public CommonResult loginOut() {
        //获取SecurityContextHolder中的用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Login login = (Login) authentication.getPrincipal();
        String username = login.getUser().getUsername();

        //删除redis中的值
        redisCache.deleteObject("Login:" + username);
        return CommonResult.success(null, "注销成功");
    }
}

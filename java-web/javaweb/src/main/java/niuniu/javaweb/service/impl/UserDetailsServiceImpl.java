package niuniu.javaweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import niuniu.javaweb.mapper.MenuMapper;
import niuniu.javaweb.mapper.UserMapper;
import niuniu.javaweb.pojo.Login;
import niuniu.javaweb.pojo.User;
import niuniu.javaweb.utils.MenuUtils;
import niuniu.javaweb.vo.MenuListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service.impl
 * @date 2023/2/9 10:32
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        System.out.println(1);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        System.out.println(2);
        User user = userMapper.selectOne(queryWrapper);
        System.out.println("运行到此处" + user);
        if (null == user) {
            throw new UsernameNotFoundException("用户名未找到或者密码错误！");
        }
//        System.out.println("查询到的user信息："+user);

        MenuListVO menuListVO = MenuUtils.buildTree(menuMapper.selectPermsByUserName(user.getUsername()));

        System.out.println("查询到的user权限信息");

        return new Login(user, menuListVO);
    }
}

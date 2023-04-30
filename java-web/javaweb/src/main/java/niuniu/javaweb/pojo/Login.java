package niuniu.javaweb.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;
import niuniu.javaweb.utils.StringUtils;
import niuniu.javaweb.vo.MenuListVO;
import niuniu.javaweb.vo.MenuVO;
import niuniu.javaweb.vo.RouteVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.pojo
 * @date 2023/2/7 18:00
 */
@Data
@NoArgsConstructor
public class Login implements UserDetails {
    private User user;

    private List<MenuVO> menuList;

    private List<RouteVO> RouteList;

    @JSONField(serialize = false) //不能序列化SimpleGrantedAuthority包
    private List<SimpleGrantedAuthority> authorities;

    public Login(User user, MenuListVO menuListVO) {
        this.user = user;
        this.menuList = menuListVO.getMenuVOList();
        this.RouteList = menuListVO.getRouteVOList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        System.out.println("获取授权信息");

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (menuList != null && !menuList.isEmpty()) {
            for (MenuVO menuVO : menuList) {
                if (StringUtils.isEmpty(menuVO.getPerms())) {
                    continue;
                }
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(menuVO.getPerms());
                authorities.add(grantedAuthority);
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

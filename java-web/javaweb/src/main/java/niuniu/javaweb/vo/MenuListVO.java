package niuniu.javaweb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.vo
 * @date 2023/3/12 11:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuListVO implements Serializable {

    private static final long serialVersionUID = -2363013104771565594L;
    private List<MenuVO> menuVOList;
    private List<RouteVO> routeVOList;
    private MenuVO menuVO;
    private RouteVO routeVO;
}

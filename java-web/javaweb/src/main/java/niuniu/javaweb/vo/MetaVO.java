package niuniu.javaweb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.vo
 * @date 2023/3/12 10:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetaVO implements Serializable {

    private static final long serialVersionUID = -6356966165046013920L;
    /**
     * 图标
     */
    private String icon;

    /**
     * 标题
     */
    private String title;
}

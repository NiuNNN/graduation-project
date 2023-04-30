package niuniu.javaweb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.vo
 * @date 2023/3/23 18:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryVO implements Serializable {

    private static final long serialVersionUID = 3957570208834171563L;

    /**
     * query路径携带头
     */
    private String type;
}

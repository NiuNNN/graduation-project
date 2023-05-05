package niuniu.javaweb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.vo
 * @date 2023/5/4 11:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 5898836590032163990L;

    private String name;

    private String type;
    
    private String stack;

    private Boolean smooth;

    private ArrayList<String> data;
}

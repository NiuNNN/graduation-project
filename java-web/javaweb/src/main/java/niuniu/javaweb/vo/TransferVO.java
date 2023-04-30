package niuniu.javaweb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.vo
 * @date 2023/3/26 10:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferVO implements Serializable {

    private static final long serialVersionUID = 286076922594535918L;

    private String id;

    private String pid;

    private String mid;

    private String label;

    private String oid;

    private String query;

    private boolean disabled;

    private List<TransferVO> children = new ArrayList<>();
}

package niuniu.javaweb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.vo
 * @date 2023/4/24 21:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedBackVO implements Serializable {

    private static final long serialVersionUID = -6928643200818196030L;

    /**
     * 反馈id
     */
    private Integer feedbackId;

    /**
     * 详细说明
     */
    private String message;

    /**
     * 反馈人
     */
    private Integer userId;

    /**
     * 时间
     */
    private String time;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 用户名称
     */
    private String username;
}

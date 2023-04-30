package niuniu.javaweb.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.pojo
 * @date 2023/4/24 21:38
 */
@TableName(value = "feedback")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedBack implements Serializable {

    private static final long serialVersionUID = 5481865179940922285L;

    /**
     * 反馈id
     */
    @TableId
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
}

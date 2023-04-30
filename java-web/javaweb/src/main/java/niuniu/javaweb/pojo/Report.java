package niuniu.javaweb.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.pojo
 * @date 2023/3/13 16:42
 */
@TableName(value = "report")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report implements Serializable {


    private static final long serialVersionUID = 2802283873976341489L;
    /**
     * 公告id
     */
    @TableId(type = IdType.AUTO)
    private Integer reportId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 公告标题
     */
    private String stem;

    /**
     * 公告信息
     */
    private String content;

    /**
     * 发布时间/保存时间
     */
    private String time;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 浏览量
     */
    private Integer views;
}

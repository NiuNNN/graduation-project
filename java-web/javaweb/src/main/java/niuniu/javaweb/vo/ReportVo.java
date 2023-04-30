package niuniu.javaweb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.vo
 * @date 2023/3/13 23:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportVo implements Serializable {

    private static final long serialVersionUID = 2431430340285603615L;

    /**
     * 公告编号
     */
    private Integer reportId;

    /**
     * 发布时间/保存时间
     */
    private String time;

    /**
     * 发布人
     */
    private String name;

    /**
     * 浏览量
     */
    private Integer views;

    /**
     * 公告标题
     */
    private String stem;

    /**
     * 公告信息
     */
    private String content;

    /**
     * 用户头像
     */
    private String avatar;
}

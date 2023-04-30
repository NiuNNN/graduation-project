package niuniu.javaweb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.vo
 * @date 2023/3/22 10:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseVO implements Serializable {

    private static final long serialVersionUID = -8524919782901169647L;

    /**
     * 房间编号
     */
    private Integer houseId;

    /**
     * 房号
     */
    private String houseName;

    /**
     * 面积
     */
    private String area;

    /**
     * 房型编号
     */
    private Integer styleId;

    /**
     * 房型名称
     */
    private String styleName;

    /**
     * 楼层编号
     */
    private Integer floorId;

    /**
     * 房间状态
     */
    private String state;

    /**
     * 阳台
     */
    private String balcony;

    /**
     * 价格
     */
    private String price;

    /**
     * 入住时间
     */
    private String loginTime;

    /**
     * 创建时间
     */
    private String insertTime;

    /**
     * 用户名字
     */
    private String username;

    /**
     * 详细配置
     */

    /**
     * 面积价格
     */
    private String priceArea;

}

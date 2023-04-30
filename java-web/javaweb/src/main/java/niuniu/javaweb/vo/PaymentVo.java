package niuniu.javaweb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.vo
 * @date 2023/4/9 18:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentVo implements Serializable {

    private static final long serialVersionUID = -272527434625659200L;

    //商品名称
    private String subject;

    //总金额
    private BigDecimal total = BigDecimal.ZERO;
}

package niuniu.javaweb.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.pojo
 * @date 2023/4/25 14:22
 */
@TableName(value = "salary")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Salary implements Serializable {

    private static final long serialVersionUID = 4077468185367639457L;

    private Integer salaryId;

    private String salaryValue;
}

package niuniu.javaweb.utils.excel;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.utils.excel
 * @date 2023/4/14 17:35
 */


import java.lang.annotation.*;

/**
 * 用于标记锁定哪些列不需要锁定
 */
@Target(value = {ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UnLockCell {

}

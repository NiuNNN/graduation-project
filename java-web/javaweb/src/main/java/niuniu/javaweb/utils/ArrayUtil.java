package niuniu.javaweb.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.utils
 * @date 2023/3/27 13:09
 */
public class ArrayUtil {
    public static <T> List<T> compare(T[] t1, T[] t2) {
        List<T> list1 = Arrays.asList(t1);
        List<T> list2 = Arrays.asList(t2);
        List<T> list3 = new ArrayList<>();//用来保存最后结果的集合
        for (T t : t2) {
            if (!list1.contains(t)) {
                list3.add(t);
            }
        }
        for (T t : t1) {
            if (!list2.contains(t)) {
                list3.add(t);
            }
        }
        return list3;
    }

}

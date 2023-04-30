package niuniu.javaweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import niuniu.javaweb.pojo.Financial;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.mapper
 * @date 2023/4/17 9:23
 */
@Mapper
public interface FinancialMapper extends BaseMapper<Financial> {
    /**
     * 判断是否存在该条信息
     *
     * @param date
     * @param state
     * @return
     */
    int judgeFinancialByDate(String date, String state);

    /**
     * 创建财务数据
     *
     * @return
     */
    int insertFinancial();

    /**
     * 更新财务数据
     *
     * @param financial
     * @return
     */
    int updateFinancial(Financial financial);
}

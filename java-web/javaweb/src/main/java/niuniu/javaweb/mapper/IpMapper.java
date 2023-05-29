package niuniu.javaweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import niuniu.javaweb.pojo.Basic;
import niuniu.javaweb.pojo.Ip;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.mapper
 * @date 2023/5/29 16:42
 */
@Mapper
public interface IpMapper extends BaseMapper<Basic> {

    /**
     * 插入新的ip信息
     *
     * @param ip
     * @return
     */
    int insertIp(Ip ip);
}

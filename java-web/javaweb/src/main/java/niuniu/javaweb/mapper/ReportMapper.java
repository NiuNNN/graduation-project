package niuniu.javaweb.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import niuniu.javaweb.pojo.Report;
import niuniu.javaweb.vo.ReportVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.mapper
 * @date 2023/3/13 16:48
 */
@Mapper
public interface ReportMapper extends BaseMapper<Report> {

    /**
     * 存入公告信息
     *
     * @param report
     * @return
     */
    int insertReport(Report report);

    /**
     * 获取全部公告信息
     *
     * @return
     */
    List<Report> getAllReport(int state);

    /**
     * 获取最新的公告信息
     *
     * @return
     */
    List<Report> getNewAllReport();

    /**
     * 改变公告信息状态
     *
     * @param reportId
     * @return
     */
    int changeReport(int reportId, int state);

    /**
     * 更新公告信息
     *
     * @param report
     * @return
     */
    int updateReport(Report report);

    /**
     * 浏览量+1
     *
     * @param reportId
     * @return
     */
    int updatePreview(int reportId);

    /**
     * 获取公告信息
     *
     * @param reportId
     * @param page
     * @param wrapper
     * @return
     */
    IPage<ReportVo> getReportPage(@Param("reportId") Integer reportId, @Param("page") Page<Report> page, @Param(Constants.WRAPPER) Wrapper<ReportVo> wrapper);
}

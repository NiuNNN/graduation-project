package niuniu.javaweb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import niuniu.javaweb.pojo.Report;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.vo.ReportVo;

import java.util.List;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service
 * @date 2023/3/13 16:59
 */
public interface ReportService {

    /**
     * 存入公告信息
     *
     * @param report   公共信息
     * @param username 用户账号
     * @return
     */
    CommonResult insertReport(Report report, String username);

    /**
     * 获取公告信息
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
     * @param state
     * @return
     */
    CommonResult changeReport(int reportId, int state);

    /**
     * 更新公告信息
     *
     * @param report
     * @return
     * @Param username
     */
    CommonResult updateReport(Report report, String username);

    /**
     * 浏览量+1
     *
     * @param reportId
     * @return
     */
    CommonResult updatePreview(int reportId);

    /**
     * 获取公告信息
     *
     * @param currentPage
     * @param pageSize
     * @param reportId
     * @return
     */
    IPage<ReportVo> getReportPage(int currentPage, int pageSize, Integer reportId);
}

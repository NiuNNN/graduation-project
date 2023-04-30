package niuniu.javaweb.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import niuniu.javaweb.pojo.Report;
import niuniu.javaweb.service.ReportService;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.vo.ReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.controller
 * @date 2023/3/13 20:51
 */
@RestController
public class ReportController {

    @Autowired
    ReportService reportService;

    /**
     * 获取全部公告信息
     *
     * @return
     */
    @GetMapping("/getAllReport/{state}")
    public CommonResult getAllReport(@PathVariable int state) {
        return CommonResult.success(reportService.getAllReport(state));
    }

    /**
     * 获取最新公告信息
     *
     * @return
     */
    @GetMapping("getNewAllReport")
    public CommonResult getNewAllReport() {
        return CommonResult.success(reportService.getNewAllReport());
    }

    /**
     * 存入公告
     *
     * @param report
     * @param username
     * @return
     */
    @PostMapping("/insertReport")
    public CommonResult insertReport(Report report, String username) {
        return reportService.insertReport(report, username);
    }

    /**
     * 改变公告状态
     *
     * @param reportId
     * @param state
     * @return
     */
    @GetMapping("/changeReport/{reportId}/{state}")
    public CommonResult changeReport(@PathVariable int reportId, @PathVariable int state) {
        return reportService.changeReport(reportId, state);
    }

    /**
     * 更新公告信息
     *
     * @param report
     * @param username
     * @return
     */
    @PostMapping("/updateReport")
    public CommonResult updateReport(Report report, String username) {
        return reportService.updateReport(report, username);
    }

    /**
     * 浏览量+1
     *
     * @param reportId
     * @return
     */
    @GetMapping("/updatePreview")
    public CommonResult updatePreview(int reportId) {
        return reportService.updatePreview(reportId);
    }

    /**
     * 获取公告信息
     *
     * @param currentPage
     * @param pageSize
     * @param reportId
     * @return
     */
    @GetMapping("/getReportPage/{currentPage}/{pageSize}")
    public CommonResult getReportPage(@PathVariable int currentPage, @PathVariable int pageSize, Integer reportId) {
        IPage<ReportVo> page = reportService.getReportPage(currentPage, pageSize, reportId);
        //如果当前页码大于了总页码值，那么重新执行查询操作，使用最大页码值作为当前页码值
        if (currentPage > page.getPages()) {
            page = reportService.getReportPage((int) page.getPages(), pageSize, reportId);
        }
        return CommonResult.success(page);
    }
}

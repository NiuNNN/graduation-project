package niuniu.javaweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import niuniu.javaweb.mapper.ReportMapper;
import niuniu.javaweb.mapper.UserMapper;
import niuniu.javaweb.pojo.Report;
import niuniu.javaweb.service.ReportService;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.vo.ReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service.impl
 * @date 2023/3/13 16:59
 */
@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements ReportService {

    @Autowired
    ReportMapper reportMapper;

    @Autowired
    UserMapper userMapper;

    /**
     * 存入公告信息
     *
     * @param report   公共信息
     * @param username 用户账号
     * @return
     */
    @Override
    public CommonResult insertReport(Report report, String username) {
        Integer userId = userMapper.getUserId(username);
        report.setUserId(userId);
        return reportMapper.insertReport(report) > 0 ? CommonResult.success() : CommonResult.failed();
    }


    /**
     * 修改公告信息
     *
     * @param reportId
     * @return
     */
    @Override
    public CommonResult changeReport(int reportId, int state) {
        return reportMapper.changeReport(reportId, state) > 0 ? CommonResult.success() : CommonResult.failed();
    }

    /**
     * 更新公告信息
     *
     * @param report
     * @return
     */
    @Override
    public CommonResult updateReport(Report report, String username) {
        Integer userId = userMapper.getUserId(username);
        report.setUserId(userId);
        return reportMapper.updateReport(report) > 0 ? CommonResult.success() : CommonResult.failed();
    }

    /**
     * 浏览量+1
     *
     * @param reportId
     * @return
     */
    @Override
    public CommonResult updatePreview(int reportId) {
        return reportMapper.updatePreview(reportId) > 0 ? CommonResult.success() : CommonResult.failed();
    }

    /**
     * 获取公告信息
     *
     * @param currentPage
     * @param pageSize
     * @param time
     * @param name
     * @param state
     * @param reportId
     * @return
     */
    @Override
    public IPage<ReportVo> getReportPage(int currentPage, int pageSize, String time, String name, Integer state, Integer reportId) {
        Page page = new Page(currentPage, pageSize);
        QueryWrapper<ReportVo> queryWrapper = new QueryWrapper<>();
        return reportMapper.getReportPage(time, name, state, reportId, page, queryWrapper);
    }
}

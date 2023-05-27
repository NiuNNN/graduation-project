package niuniu.javaweb.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itextpdf.styledxmlparser.jsoup.internal.StringUtil;
import niuniu.javaweb.exception.ParamErrorException;
import niuniu.javaweb.mapper.*;
import niuniu.javaweb.pojo.Basic;
import niuniu.javaweb.pojo.Cost;
import niuniu.javaweb.pojo.Financial;
import niuniu.javaweb.pojo.Order;
import niuniu.javaweb.service.BasicService;
import niuniu.javaweb.service.CostService;
import niuniu.javaweb.service.FinancialService;
import niuniu.javaweb.utils.*;
import niuniu.javaweb.utils.excel.ExcelUtil;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.utils.tools.OrderUtil;
import niuniu.javaweb.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service.impl
 * @date 2023/4/13 23:17
 */
@Service
public class CostServiceImpl extends ServiceImpl<CostMapper, Cost> implements CostService {
    @Autowired
    CostMapper costMapper;
    @Autowired
    HouseMapper houseMapper;

    @Autowired
    BasicMapper basicMapper;

    @Autowired
    RentMapper rentMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    FinancialMapper financialMapper;

    @Autowired
    FinancialService financialService;

    @Autowired
    BasicService basicService;

    @Autowired
    ExcelUtil excelUtil;

    private static String WATER_NAME_EXCEL = DateUtil.getCurrentMonth() + "_水费上传专用表";

    private static String ELECTRIC_NAME_EXCEL = DateUtil.getCurrentMonth() + "_电费上传专用表";

    private static String COST_EXCEL = DateUtil.getCurrentMonth() + "_租赁账单";

    /**
     * 获取该月的用水excel模板
     *
     * @return
     */
    @Override
    public void sheetElectricExcel() {
        List<CostVo> costVos = allHouseNameForCost();
        List<ElectricInExcelVO> electricInExcelVOS = new ArrayList<>();
        for (CostVo costVo : costVos) {
            ElectricInExcelVO electricInExcelVO = new ElectricInExcelVO();
            electricInExcelVO.setTime(costVo.getDate());
            electricInExcelVO.setHouseName(costVo.getHouseName());
            electricInExcelVO.setRentId(costVo.getRentId());
//            System.out.println(electricInExcelVO);
            electricInExcelVOS.add(electricInExcelVO);
        }
        ExcelUtil.excelLockExport(ElectricInExcelVO.class, ELECTRIC_NAME_EXCEL, electricInExcelVOS, "电费上传专用表");
    }

    /**
     * 获取该月的用水excel模板
     *
     * @throws IOException
     */
    @Override
    public void sheetWaterExcel() {
        List<CostVo> costVos = allHouseNameForCost();
        List<WaterInExcelVO> waterInExcelVOS = new ArrayList<>();
        for (CostVo costVo : costVos) {
            WaterInExcelVO waterInExcelVO = new WaterInExcelVO();
            waterInExcelVO.setTime(costVo.getDate());
            waterInExcelVO.setHouseName(costVo.getHouseName());
            waterInExcelVO.setRentId(costVo.getRentId());
//            System.out.println(electricInExcelVO);
            waterInExcelVOS.add(waterInExcelVO);
        }
        ExcelUtil.excelLockExport(WaterInExcelVO.class, WATER_NAME_EXCEL, waterInExcelVOS, "水费上传专用表");
    }

    /**
     * 获取房间数据 开启线程 判断是否存在该用户该月的用量单 插入数据库
     *
     * @return
     */
    @Transactional
    public List<CostVo> allHouseNameForCost() {
        List<CostVo> allHouseNameForCost = houseMapper.getAllHouseNameForCost();
        for (CostVo costVo : allHouseNameForCost) {
            costVo.setDate(DateUtil.getCurrentMonth());
        }
        TaskCenterUtil taskCenterUtil = TaskCenterUtil.getTaskCenterUtil();
        taskCenterUtil.submitTask(() -> {
            System.out.println("开启线程池");
            List<CostVo> costVos = costMapper.noCostMonth();
            for (CostVo costVo : costVos) {
                costMapper.insertNewCost(costVo.getRentId());
            }
            return null;
        });
        return allHouseNameForCost;
    }

    /**
     * 上传文件 用电表
     *
     * @param excel
     * @return
     */
    @Override
    @CacheEvict(cacheNames = "getUserChart", allEntries = true)
    public CommonResult getElectricExcel(MultipartFile excel) {
        CommonResult checkExcelParam = checkExcelParam(excel);
        if (checkExcelParam.getCode() != 200) return checkExcelParam;
        CommonResult commonResult = uploadFile(excel);
        if (commonResult.getCode() != 200) return commonResult;
        String filePath = (String) commonResult.getData();
        if (StringUtil.isBlank(filePath)) {
            return CommonResult.failed("【导入Excel文件】生成的Excel文件的路径为空");
        }
        boolean flag = excel.getOriginalFilename().substring(0, 15).equals(ELECTRIC_NAME_EXCEL);
        // 3.读取excel文件
        List<ElectricInExcelVO> excelVos = excelUtil.simpleExcelRead(filePath, ElectricInExcelVO.class);
        if (CollectionUtil.isEmpty(excelVos) || excelVos.size() < 2 || !flag) {
            System.out.println("【导入Excel文件】上传Excel文件" + excel.getOriginalFilename() + "为空");
            return CommonResult.failed("上传Excel文件为空");
        }
        /**
         * 把得到的数据分为 有 与 空
         */
        HashMap<String, List<ElectricInExcelVO>> map = new HashMap<>();
        List<ElectricInExcelVO> has = new ArrayList<>();
        List<ElectricInExcelVO> none = new ArrayList<>();
        for (ElectricInExcelVO electricInExcelVO : excelVos) {
            System.out.println(electricInExcelVO);
            if (StringUtils.isEmpty(electricInExcelVO.getNum())) {
                none.add(electricInExcelVO);
            } else {
                has.add(electricInExcelVO);
            }
        }
        map.put("has", has);
        map.put("none", none);
        return CommonResult.success(map);
    }


    /**
     * 上传水费
     *
     * @param excel
     * @return
     */
    @Override
    @CacheEvict(cacheNames = "getUserChart", allEntries = true)
    public CommonResult getWaterExcel(MultipartFile excel) {
        CommonResult checkExcelParam = checkExcelParam(excel);
        if (checkExcelParam.getCode() != 200) return checkExcelParam;
        CommonResult commonResult = uploadFile(excel);
        if (commonResult.getCode() != 200) return commonResult;
        String filePath = (String) commonResult.getData();
        if (StringUtil.isBlank(filePath)) {
            return CommonResult.failed("【导入Excel文件】生成的Excel文件的路径为空");
        }
        System.out.println(excel.getOriginalFilename().substring(0, 15));
        boolean flag = excel.getOriginalFilename().substring(0, 15).equals(WATER_NAME_EXCEL);
        // 3.读取excel文件
        List<WaterInExcelVO> excelVos = excelUtil.simpleExcelRead(filePath, WaterInExcelVO.class);
        if (CollectionUtil.isEmpty(excelVos) || excelVos.size() < 2 || !flag) {
            System.out.println("【导入Excel文件】上传Excel文件" + excel.getOriginalFilename() + "为空");
            return CommonResult.failed("上传Excel文件有误");
        }
        /**
         * 把得到的数据分为 有 与 空
         */
        HashMap<String, List<WaterInExcelVO>> map = new HashMap<>();
        List<WaterInExcelVO> has = new ArrayList<>();
        List<WaterInExcelVO> none = new ArrayList<>();
        for (WaterInExcelVO waterInExcelVO : excelVos) {
            System.out.println(waterInExcelVO);
            if (StringUtils.isEmpty(waterInExcelVO.getNum())) {
                none.add(waterInExcelVO);
            } else {
                has.add(waterInExcelVO);
            }
        }
        map.put("has", has);
        map.put("none", none);
        return CommonResult.success(map);
    }

    /**
     * 判断excel
     *
     * @param electricInExcelVOS
     * @return
     */
    @Override
    @Transactional
    public CommonResult judgeElectric(List<ElectricInExcelVO> electricInExcelVOS) {
        HashMap<String, List<ElectricInExcelVO>> map = new HashMap<>();
        List<ElectricInExcelVO> monthElectric = costMapper.getMonthElectric();
        List<ElectricInExcelVO> newList = new ArrayList<>();
        List<ElectricInExcelVO> oldList = new ArrayList<>();
        for (ElectricInExcelVO electricInExcelVO : electricInExcelVOS) {
            boolean flag = true;
            for (ElectricInExcelVO electricInExcelVO1 : monthElectric) {
                if (electricInExcelVO1.getRentId() == electricInExcelVO.getRentId()) {
                    flag = false;
                    if (electricInExcelVO1.getState() == 0) {
                        newList.add(electricInExcelVO);
                        oldList.add(electricInExcelVO1);
                    }
                }
            }
            electricInExcelVO.setNum(electricInExcelVO.getNum().replaceAll(",", ""));
            if (flag) costMapper.updateElectric(electricInExcelVO);
//            System.out.println(electricInExcelVO);
        }
        map.put("new", newList);
        map.put("old", oldList);
        return CommonResult.success(map);
    }

    /**
     * 存储水费
     *
     * @param waterInExcelVOS
     * @return
     */
    @Override
    @Transactional
    public CommonResult judgeWater(List<WaterInExcelVO> waterInExcelVOS) {
        HashMap<String, List<WaterInExcelVO>> map = new HashMap<>();
        List<WaterInExcelVO> monthWater = costMapper.getMonthWater();
        List<WaterInExcelVO> newList = new ArrayList<>();
        List<WaterInExcelVO> oldList = new ArrayList<>();
        for (WaterInExcelVO waterInExcelVO : waterInExcelVOS) {
            boolean flag = true;
            for (WaterInExcelVO waterInExcelVO1 : monthWater) {
                if (waterInExcelVO1.getRentId() == waterInExcelVO.getRentId()) {
                    flag = false;
                    if (waterInExcelVO1.getState() == 0) {
                        newList.add(waterInExcelVO);
                        oldList.add(waterInExcelVO1);
                    }
                }
            }
            waterInExcelVO.setNum(waterInExcelVO.getNum().replaceAll(",", ""));
            if (flag) costMapper.updateWater(waterInExcelVO);
//            System.out.println(electricInExcelVO);
        }
        map.put("new", newList);
        map.put("old", oldList);
        return CommonResult.success(map);
    }

    /**
     * 替换源电费数据
     *
     * @param electricInExcelVOS
     * @return
     */
    @Override
    @CacheEvict(cacheNames = "getUserChart", allEntries = true)
    public CommonResult toggleElectric(List<ElectricInExcelVO> electricInExcelVOS) {
        for (ElectricInExcelVO electricInExcelVO : electricInExcelVOS) {
            electricInExcelVO.setNum(electricInExcelVO.getNum().replaceAll(",", ""));
            costMapper.updateElectric(electricInExcelVO);
//            System.out.println(electricInExcelVO);
        }
        return CommonResult.success();
    }


    /**
     * 替换源水费数据
     *
     * @param waterInExcelVOS
     * @return
     */
    @Override
    @CacheEvict(cacheNames = "getUserChart", allEntries = true)
    public CommonResult toggleWater(List<WaterInExcelVO> waterInExcelVOS) {
        for (WaterInExcelVO waterInExcelVO : waterInExcelVOS) {
            waterInExcelVO.setNum(waterInExcelVO.getNum().replaceAll(",", ""));
            costMapper.updateWater(waterInExcelVO);
//            System.out.println(electricInExcelVO);
        }
        return CommonResult.success();
    }

    /**
     * 按需查询使用信息
     *
     * @param currentPage
     * @param pageSize
     * @param costVo
     * @return
     */
    @Override
    public IPage<CostVo> getAllCost(int currentPage, int pageSize, CostVo costVo) {
        Page<CostVo> page = new Page<>(currentPage, pageSize);
        QueryWrapper<CostVo> queryWrapper = new QueryWrapper<>();
        return costMapper.getAllCost(costVo.getHouseName(), costVo.getDate(), page, queryWrapper);
    }

    /**
     * 更新用电信息
     *
     * @param cost
     * @return
     */
    @Override
    public CommonResult updateElectricByCostId(Cost cost) {
        return costMapper.updateElectricByCostId(cost) > 0 ? CommonResult.success() : CommonResult.failed();
    }

    /**
     * 更新用水信息
     *
     * @param cost
     * @return
     */
    @Override
    public CommonResult updateWaterByCostId(Cost cost) {
        return costMapper.updateWaterByCostId(cost) > 0 ? CommonResult.success() : CommonResult.failed();
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @Override
    public CommonResult uploadFile(MultipartFile file) {
        System.out.println("【文件上传】进入到文件上传方法");
        // 1.参数校验
        if (null == file || file.isEmpty()) {
            log.error("【文件上传】文件为空!");
            throw new ParamErrorException();
        }
        // 2.上传文件
        CommonResult commonResult = FileUtil.uploadFile(file);
        return commonResult;
    }

    /**
     * 检验Excel文件
     *
     * @param file
     * @return
     */
    public CommonResult checkExcelParam(MultipartFile file) {
        System.out.println("【上传Excel文件】进入到上传Excel文件方法...");
        if (null == file || file.isEmpty()) {
            System.out.println("【上传Excel文件】上传的文件为空，" + file);
            throw new ParamErrorException();
        }
        boolean b = ExcelUtil.checkExcelExtension(file);
        if (!b) {
            return CommonResult.failed("上传的不是Excel文件，请上传正确格式的Excel文件");
        }
        return CommonResult.success();
    }

    /**
     * 删除帐单
     *
     * @param costId
     * @return
     */
    @Override
    public CommonResult deleteCost(Integer costId) {
        return costMapper.deleteById(costId) > 0 ? CommonResult.success() : CommonResult.failed();
    }


    /**
     * 计算生成新的CostVo
     *
     * @param costVo
     * @return
     * @throws ParseException
     */
    private CostVo performCostVo(CostVo costVo) throws ParseException {
        /**
         * 计算电费以及水费
         */
        List<Basic> basics = basicMapper.selectList(null);

        //计算电费
        String electric = basicService.getActuallyPrice(basics.get(0), costVo.getDate());
        if (StringUtils.isEmpty(electric)) electric = "0";
        float costElectric = Float.parseFloat(electric) * Float.parseFloat(costVo.getNumElectric());
//        System.out.println("【costElectric：】" + costElectric);

        //计算水费
        String water = basicService.getActuallyPrice(basics.get(1), costVo.getDate());
        if (StringUtils.isEmpty(water)) water = "0";
        float costWater = Float.parseFloat(water) * Float.parseFloat(costVo.getNumWater());
//        System.out.println("【costWater：】" + costWater);

        //计算杂费
        List<Basic> basicByRentId = basicMapper.getBasicByRentId(costVo.getRentId());
        Float mis = 0.0f;
        if (basicByRentId.size() > 0) {
            for (Basic basic : basicByRentId) {
                mis += Float.valueOf(basicService.getActuallyPrice(basic, costVo.getDate()));
            }
        }

        /**
         * 计算杂费信息
         */

        //计算房费
        String housePrice = houseMapper.getHousePriceByCostId(costVo.getCostId()).replaceAll(",", "");

        //计算除了水电以外的费用
        String deposit = String.valueOf(Float.valueOf(housePrice) + mis);

        //计算总收费
//        System.out.println("【housePrice：】" + housePrice);

        String total = String.valueOf(Float.valueOf(deposit) + costWater + costElectric);
//        System.out.println("【total：】" + String.valueOf(total));

        //完善cost
        costVo.setHousePrice(housePrice);
        costVo.setCostElectric(String.valueOf(costElectric));
        costVo.setCostWater(String.valueOf(costWater));
        costVo.setMis(String.valueOf(mis));
        costVo.setTotal(total);
        costVo.setDeposit(deposit);

//        System.out.println(costVo);

        return costVo;
    }

    /**
     * 生成账单
     *
     * @param costVo
     */
    public void generateCost(CostVo costVo) throws ParseException {
        costVo.setState(1);

        System.out.println("【getCostId】" + costVo.getCostId());

        //修改cost表
        costMapper.updateByCostVO(costVo);
        System.out.println("1");


        //修改miscellaneous表 把2置为0
        basicMapper.changeMiscellaneousStateByRentId(costVo.getRentId());
        System.out.println("2");

        //重新计算deposit 修改rent表
        rentMapper.updateDeposit(costVo.getRentId(), costVo.getDeposit());
        System.out.println("3");


        //插入order_pay表
        String misPrice = basicService.getBasicByRentId(costVo.getRentId(), costVo.getDate());

        Order order = new Order();
        order.setRentId(costVo.getRentId());
        order.setTotal(costVo.getTotal());
        order.setOutTradeNo(OrderUtil.getOrderNo());
        order.setType("租金");
        order.setMisPrice(misPrice);
        System.out.println("4");
        orderMapper.generateOrder(order);
    }

    /**
     * 生成单一用户报表
     *
     * @param costVo
     */
    @Override
    public void generatePersonCost(CostVo costVo) throws ParseException {
        System.out.println(costVo);
        List<CostVo> costVos = new ArrayList<>();
        if (costVo.getState() == 0) {

            CostVo costVo1 = performCostVo(costVo);

            costVos.add(costVo1);

            /**
             * 开启线程 插入数据库
             */

            TaskCenterUtil taskCenterUtil = TaskCenterUtil.getTaskCenterUtil();
            taskCenterUtil.submitTask(() -> {
                System.out.println("开启线程池");
                generateCost(costVo1);
//            System.out.println("4");
                return null;
            });
        } else {
            CostVo costVOByCostId = costMapper.getCostVOByCostId(costVo);
            costVOByCostId.setHousePrice(houseMapper.getHousePriceByCostId(costVo.getCostId()).replaceAll(",", ""));
            costVos.add(costVOByCostId);
        }

        /**
         * 导出excel
         */
        ExcelUtil.excelLockExport(CostVo.class, COST_EXCEL + "_" + costVo.getHouseName(), costVos, "收费表");
    }

    /**
     * 按需生成全部账单
     *
     * @param time
     */
    @Override
    public void generateALlCost(String time) throws ParseException {
        List<CostVo> costVOByTime = costMapper.getCostVOByTime(time);
        List<CostVo> costVos = new ArrayList<>();
        for (CostVo costVo : costVOByTime) {
            if (costVo.getState() == 0) {
                CostVo costVo1 = performCostVo(costVo);
                costVos.add(costVo1);
            } else {
                costVo.setHousePrice(houseMapper.getHousePriceByCostId(costVo.getCostId()).replaceAll(",", ""));
                costVos.add(costVo);
            }
        }
        TaskCenterUtil taskCenterUtil1 = TaskCenterUtil.getTaskCenterUtil();
        taskCenterUtil1.submitTask(() -> {
            System.out.println("开启1线程池");
            for (CostVo costVo : costVos) {
                if (costVo.getState() == 0) {
                    System.out.println(costVo);
                    generateCost(costVo);
                }

            }
            System.out.println("结束");
            return null;
        });
        /**
         * 计算总和
         */
        Float housePrice = 0.0f;
        Float costWater = 0.0f;
        Float numWater = 0.0f;
        Float costElectric = 0.0f;
        Float numElectric = 0.0f;
        Float costMis = 0.0f;
        Float total = 0.0f;
        for (CostVo costVo : costVos) {
//            System.out.println(costVo);

//            System.out.println("【housePrice】" + costVo.getHousePrice());
            housePrice += Float.valueOf(costVo.getHousePrice());

//            System.out.println("【getCostWater】" + costVo.getCostWater());
            if (!StringUtils.isEmpty(costVo.getCostWater())) costWater += Float.valueOf(costVo.getCostWater());

//            System.out.println("【getNumWater】" + costVo.getNumWater());
            if (!StringUtils.isEmpty(costVo.getNumWater())) numWater += Float.valueOf(costVo.getNumWater());

//            System.out.println("【getCostElectric】" + costVo.getCostElectric());
            if (!StringUtils.isEmpty(costVo.getCostElectric())) costElectric += Float.valueOf(costVo.getCostElectric());

//            System.out.println("【getNumElectric】" + costVo.getNumElectric());
            if (!StringUtils.isEmpty(costVo.getNumElectric())) numElectric += Float.valueOf(costVo.getNumElectric());

//            System.out.println("【getMis】" + costVo.getMis());
            if (!StringUtils.isEmpty(costVo.getMis())) costMis += Float.valueOf(costVo.getMis());

//            System.out.println("【getTotal】" + costVo.getTotal());
            total += Float.valueOf(costVo.getTotal());
        }

        CostVo last = new CostVo();
        last.setHouseName("总计");
        last.setDate(DateUtil.getCurrentMonth());
        last.setHousePrice(String.valueOf(housePrice));
        last.setCostWater(String.valueOf(costWater));
        last.setCostElectric(String.valueOf(costElectric));
        last.setMis(String.valueOf(costMis));
        last.setTotal(String.valueOf(total));
        last.setNumWater(String.valueOf(numWater));
        last.setNumElectric(String.valueOf(numElectric));

        costVos.add(last);
        TaskCenterUtil taskCenterUtil2 = TaskCenterUtil.getTaskCenterUtil();
        taskCenterUtil2.submitTask(() -> {
            System.out.println("开启2线程池");
            /**
             * 先判断是否可以插入 插入financial表
             */
            if (financialMapper.judgeFinancialByDate(last.getDate(), "0") == 0) {
                Financial financial = new Financial();
                financial.setDate(last.getDate());
                financial.setInWater(last.getCostWater());
                financial.setInElectric(last.getCostElectric());
                financial.setInMis(last.getMis());
                financial.setInRent(last.getHousePrice());
                financial.setAdvanceTotal(last.getTotal());
                System.out.println(financial);
                financialService.updateHouseFinancial(financial);
            }
            return null;
        });

        /**
         * 导出excel
         */
        ExcelUtil.excelLockExport(CostVo.class, COST_EXCEL, costVos, "租赁账单");
    }

    /**
     * 判断是否满足条件一键生成
     *
     * @param time
     * @return
     */
    @Override
    public CommonResult judgeCost(String time) {
        if (costMapper.judgeHasCost(time) > 0) {
            if (costMapper.judgeHasNum(time) > 0) {
                return CommonResult.failed("该月信息尚未完善，请完善后再生成账单...");
            }
            return CommonResult.success("正在生成中，请稍等...");
        }
        return CommonResult.failed("不存在该月信息，请重新选择...");
    }

    /**
     * 获得水电信息
     *
     * @param rentId
     * @param time
     * @return
     */
    @Override
    public CommonResult getCostByRentId(Integer rentId, String time) {
        Cost cost = costMapper.getCostByRentId(rentId, time);
        if (StringUtils.isEmpty(cost.getNumWater()) || StringUtils.isEmpty(cost.getNumElectric()) || StringUtils.isEmpty(cost.getCostWater()) || StringUtils.isEmpty(cost.getCostElectric())) {
            return CommonResult.success();
        } else {
            Float basicWater = Float.valueOf(cost.getCostWater()) / Float.valueOf(cost.getNumWater());
            Float basicElectric = Float.valueOf(cost.getCostElectric()) / Float.valueOf(cost.getNumElectric());
            OrderVO orderVO = new OrderVO();
            orderVO.setCostElectric(cost.getCostElectric());
            orderVO.setCostWater(cost.getCostWater());
            orderVO.setBasicElectric(String.valueOf(basicElectric));
            orderVO.setBasicWater(String.valueOf(basicWater));
            orderVO.setNumElectric(cost.getNumElectric());
            orderVO.setNumWater(cost.getNumWater());
            orderVO.setMis(cost.getMis());
            return CommonResult.success(orderVO);
        }
    }

    /**
     * 获取用户图表
     *
     * @param userId
     * @return
     */
    @Override
//    @Cacheable(cacheNames = "getUserChart", key = "#userId")
    public CommonResult getUserChart(Integer userId) {
        List<Cost> costList = costMapper.getUserChart(userId);
        List<ChartVO> list = new ArrayList<>();
        ArrayList eleList = new ArrayList<>();
        ArrayList waterList = new ArrayList<>();
        if (costList.size() > 0) {
            for (int i = 0; i < 12; i++) {
                String ele = "";
                String water = "";
                for (Cost cost : costList) {
                    if (Integer.valueOf(cost.getDate()) - 1 == i) {
                        ele = cost.getNumElectric();
                        water = cost.getNumWater();
                    }
                }
                eleList.add(ele);
                waterList.add(water);
            }
        }
//        System.out.println(waterList);
//        System.out.println(eleList);
        list.add(new ChartVO("用水量(方)", "line", "", true, waterList));
        list.add(new ChartVO("用电量(度)", "line", "", true, eleList));
        return CommonResult.success(list);
    }

    /**
     * 导出选择的住房账单
     *
     * @param list
     */
    @Override
    public void getAllCostExcel(String list) {
        List<CostVo> costVos = JSON.parseArray(list, CostVo.class);
        List<CostVo> costVoList = new ArrayList<>();
//        System.out.println(costVos.size());
        for (CostVo costVo : costVos) {
            CostVo costVOByCostId = costMapper.getCostVOByCostId(costVo);
            costVOByCostId.setHousePrice(houseMapper.getHousePriceByCostId(costVo.getCostId()).replaceAll(",", ""));
            costVoList.add(costVOByCostId);
        }
        ExcelUtil.excelLockExport(CostVo.class, "租赁账单", costVoList, "租赁账单");
    }
}

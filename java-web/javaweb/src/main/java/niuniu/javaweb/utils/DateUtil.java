package niuniu.javaweb.utils;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.utils
 * @date 2023/2/10 17:42
 */
@Component
public class DateUtil {

    /**
     * 默认日期转换格式
     */
    static String DEFAULT_FORMAT_PATTERN = "yyyy-MM-dd-HH-mm-ss";

    static String DEFAULT_FORMAT_MONTH = "yyyy-MM";

    static String DEFAULT_FORMAT_YEAR = "yyyy";

    static String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";


    static String MONTH[] = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

    /**
     * 返回月
     *
     * @param i
     * @return
     */
    public static String getMonth(int i) {
        return MONTH[i];
    }

    /**
     * 转换当前日期 转换为 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getNowTime() {
        DateTime now = new DateTime();
        return now.toString(DEFAULT_FORMAT);
    }

    /**
     * 获取当前年
     *
     * @return
     */
    public static String getYear() {
        DateTime now = new DateTime();
        return now.toString(DEFAULT_FORMAT_YEAR);
    }

    /**
     * 转换当前日期 转换为 yyyy-MM-dd-HH-mm-ss
     *
     * @return
     */
    public static String getCurrentTime() {
        DateTime now = new DateTime();
        return now.toString(DEFAULT_FORMAT_PATTERN);
    }

    /**
     * 获取当前日期 转换为 YYYY-MM格式
     *
     * @return
     */
    public static String getCurrentMonth() {
        DateTime now = new DateTime();
        return now.toString(DEFAULT_FORMAT_MONTH);
    }

    /**
     * 转换日期为相应格式
     *
     * @param time
     * @return
     */
    public static String formatCurrentMonth(String time) {
        DateTime dateTime = new DateTime(time);
        return dateTime.toString(DEFAULT_FORMAT_MONTH);
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getCurrentDate() {
        LocalDate localDate = new LocalDate();
        return localDate.toString();
    }

    /**
     * 传入日期转换为int型
     *
     * @param date
     * @return
     */
    public static int getYearMonth(Date date) {
        //传入日期
        Calendar calder = Calendar.getInstance();
        calder.setTime(date);//设置时间
        int year = calder.get(Calendar.YEAR);//获取年份
        int month = calder.get(Calendar.MONTH);//获取月份
        //返回年份乘以100加上月份的值，因为月份最多2位数，
        // 所以年份乘以100可以获取一个唯一的年月数值
        return year * 100 + month;
    }

    /**
     * 比较是否是相同年月
     *
     * @param baseMonth
     * @param nowMonth
     * @return
     * @throws ParseException
     */
    public static boolean compareMonth(String baseMonth, String nowMonth) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        return getYearMonth(df.parse(baseMonth)) >= getYearMonth(df.parse(nowMonth)) ? true : false;
    }

    /**
     * 比较是否相同
     *
     * @param baseMonth
     * @param nowMonth
     * @return
     * @throws ParseException
     */
    public static boolean compareMonth1(String baseMonth, String nowMonth) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        return getYearMonth(df.parse(baseMonth)) == getYearMonth(df.parse(nowMonth)) ? true : false;
    }

    /**
     * 时间字符串转时间戳
     *
     * @param time
     * @return
     */
    public static long dateToStamp(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_FORMAT_MONTH);
        Date date = simpleDateFormat.parse(time);
        return date.getTime();

    }

    /**
     * 校验是否是前三天
     *
     * @param time
     * @return
     * @throws ParseException
     */
    public static boolean checkOrderTime(String time) {
        Integer start = Integer.valueOf(time.substring(8, 10));
        System.out.println(time.substring(8, 10));
        System.out.println("start:" + start);
        System.out.println(start <= 3);
        return start <= 3 ? true : false;
    }

    /**
     * 获取指定月
     *
     * @param time
     * @param mon
     * @return
     * @throws ParseException
     */
    public static String[] getMon(String time, int mon) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(time));
        calendar.add(Calendar.MONTH, mon);
        String date = sdf.format(calendar.getTime());
        return date.split("-");
    }

    /**
     * 求两个日期相差天数
     *
     * @param time
     * @return
     * @throws ParseException
     */
    public static Long differDay(String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowTime = getNowTime();
        Date star = sdf.parse(time);//开始时间
        Date endDay = sdf.parse(nowTime);//结束时间
        Long starTime = star.getTime();
        Long endTime = endDay.getTime();
        Long num = endTime - starTime;//时间戳相差的毫秒数
        return num / 24 / 60 / 60 / 1000;
    }
}

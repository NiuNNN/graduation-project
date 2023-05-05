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

    static String DEFAULT_FORMAT_DAY = "yyyy-MM-dd";

    static String MONTH[] = new String[]{"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};

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
    public static boolean checkOrderTime(String time) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_FORMAT_DAY);
        // 获取当月第三天
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 3);
        String day = format.format(calendar.getTime());
        System.out.println(day);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(format.parse(day));
        c2.setTime(format.parse(time));
        int result = c1.compareTo(c2);
        if (result >= 0) {
            return true;
        }
        return false;
    }
    
}

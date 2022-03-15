package com.lianekai.util.date;

import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.util.ObjectUtils;

import java.security.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 时间日期工具类
 *
 * @author lianekai
 * @version: 1.0
 * @date 2022/03/15 23:58
 */
@Slf4j
public class DateUtils {
    private DateUtils(){}

    // 年-月-日 00:00:00 某天的开始时间
    public static final String DATE_FORMAT_YYYY_MM_DD_BEGIN = "yyyy-MM-dd 00:00:00";

    // 年-月-日 23:59:59 某天的结束时间
    public static final String DATE_FORMAT_YYYY_MM_DD_END = "yyyy-MM-dd 23:59:59";

    // 年
    public static final String DATE_FORMAT_YYYY = "yyyy";

    // 年月
    public static final String DATE_FORMAT_YYYYMM = "yyyyMM";

    // 年-月
    public static final String DATE_FORMAT_YYYY_MM = "yyyy-MM";

    // 年月日
    public static final String DATE_FORMAT_YYMMDD = "yyMMdd";

    // 年-月-日
    public static final String DATE_FORMAT_YY_MM_DD = "yy-MM-dd";

    // 年月日
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";

    // 年-月-日
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    // 月-年
    public static final String DATE_FORMAT_MM_YYYY = "MM-yyyy";

    public static final String UTC = "UTC";

    // 日-月-年
    public static final String DATE_FORMAT_DD_MM_YYYY = "dd-MM-yyyy";

    // 月-日-年
    public static final String DATE_FORMAT_MM_DD_YYYY = "MM-dd-yyyy";

    // 日-月-年 时:分:秒
    public static final String DATE_TIME_FORMAT_DD_MM_YYYY_HH_MI_SS = "dd-MM-yyyy HH:mm:ss";

    // 年.月.日
    public static final String DATE_FORMAT_POINTYYYYMMDD = "yyyy.MM.dd";

    // 某年某月某日
    public static final String DATE_TIME_FORMAT_YYYY年MM月DD日 = "yyyy年MM月dd日";

    // 某月某日 HH:mm
    public static final String DATE_TIME_FORMAT_MM月DD日TIME = "MM月dd日 HH:mm";

    // 年月日时分
    public static final String DATE_FORMAT_YYYYMMDDHHmm = "yyyyMMddHHmm";

    // 年月日 时:分
    public static final String DATE_TIME_FORMAT_YYYYMMDD_HH_MI = "yyyyMMdd HH:mm";

    // 年-月-日 时:分
    public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI = "yyyy-MM-dd HH:mm";

    // 年月日时分秒
    public static final String DATE_TIME_FORMAT_YYYYMMDDHHMISS = "yyyyMMddHHmmss";

    // 年-月-日 时:分:秒
    public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS = "yyyy-MM-dd HH:mm:ss";

    // 年月日时分秒毫秒
    public static final String DATE_TIME_FORMAT_YYYYMMDDHHMISSSSS = "yyyyMMddHHmmssSSS";

    // 年/月/日 时:分:秒
    public static final String DATE_TIME_FORMAT_YYYYMMDDHHMMSS = "yyyy/MM/dd HH:mm:ss";

    // 月-日 时:分
    public static final String DATE_FORMAT_MMDDHHMI = "MM-dd HH:mm";

    // 月-日
    public static final String DATE_FORMAT_MMDD = "MM-dd";

    // 时:分:秒
    public static final String DATE_FORMAT_HHMISS = "HH:mm:ss";

    /**
     * 获取某日期的年份
     *
     * @param date
     * @return
     */
    public static Integer getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取某日期的月份
     *
     * @param date
     * @return
     */
    public static Integer getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取某日期的日数
     *
     * @param date
     * @return
     */
    public static Integer getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DATE);
    }

    /**
     * 获取某日期的当前时间12h制
     *
     * @param date
     * @return
     */
    public static Integer getHour(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR);
    }

    /**
     * 获取某日期的当前时间24h制
     *
     * @param date
     * @return
     */
    public static Integer getHourOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 格式化Date时间
     *
     * @param time       Date类型时间
     * @param timeFormat String类型格式
     * @return 格式化后的字符串
     */
    public static String getDateString(Date time, String timeFormat) {
        if(ObjectUtils.isEmpty(time)){
            return StringUtils.EMPTY;
        }
        timeFormat = StringUtils.isNotBlank(timeFormat) ? timeFormat : DATE_FORMAT_YYYY_MM_DD;
        DateFormat dateFormat = getSimpleDateFormat(timeFormat);
        return dateFormat.format(time);
    }

    /**
     * 格式化Timestamp时间
     *
     * @param timestamp  Timestamp类型时间
     * @param timeFormat String类型格式
     * @return 格式化后的字符串
     */
    public static String parseTimestampToStr(Timestamp timestamp, String timeFormat) {
        timeFormat = StringUtils.isNotBlank(timeFormat) ? timeFormat : DATE_FORMAT_YYYY_MM_DD;
        SimpleDateFormat sdf = getSimpleDateFormat(timeFormat);
        return sdf.format(timestamp);
    }

    /**
     * 格式化Date时间
     *
     * @param time         Date类型时间
     * @param timeFormat   String类型格式
     * @param defaultValue 默认值为当前时间Date
     * @return 格式化后的字符串
     */
    public static String parseDateToStr(Date time, String timeFormat, final Date defaultValue) {
        try {
            timeFormat = StringUtils.isNotBlank(timeFormat) ? timeFormat : DATE_FORMAT_YYYY_MM_DD;
            DateFormat dateFormat = getSimpleDateFormat(timeFormat);
            return dateFormat.format(time);
        } catch (Exception e) {
            if (!ObjectUtils.isEmpty(defaultValue)) {
                return getDateString(defaultValue, timeFormat);
            } else {
                return getDateString(new Date(), timeFormat);
            }
        }
    }

    /**
     * 格式化Date时间
     *
     * @param time         Date类型时间
     * @param timeFormat   String类型格式
     * @param defaultValue 默认时间值String类型
     * @return 格式化后的字符串
     */
    public static String parseDateToStr(Date time, String timeFormat, final String defaultValue) {
        try {
            timeFormat = StringUtils.isNotBlank(timeFormat) ? timeFormat : DATE_FORMAT_YYYY_MM_DD;
            DateFormat dateFormat = getSimpleDateFormat(timeFormat);
            return dateFormat.format(time);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 格式化String时间
     *
     * @param time       String类型时间
     * @param timeFormat String类型格式
     * @return 格式化后的Date日期
     */
    public static Date parseStrToDate(String time, String timeFormat) {
        if (ObjectUtils.isEmpty(time) || time.equals("")) {
            return null;
        }
        timeFormat = StringUtils.isNotBlank(timeFormat) ? timeFormat : DATE_FORMAT_YYYY_MM_DD;
        Date date = null;
        try {
            DateFormat dateFormat = getSimpleDateFormat(timeFormat);
            date = dateFormat.parse(time);
        } catch (Exception e) {
            return null;
        }
        return date;
    }

    /**
     * 格式化String时间
     *
     * @param strTime      String类型时间
     * @param timeFormat   String类型格式
     * @param defaultValue 异常时返回的默认值
     * @return
     */
    public static Date parseStrToDate(String strTime, String timeFormat, Date defaultValue) {
        timeFormat = StringUtils.isNotBlank(timeFormat) ? timeFormat : DATE_FORMAT_YYYY_MM_DD;
        try {
            DateFormat dateFormat = getSimpleDateFormat(timeFormat);
            return dateFormat.parse(strTime);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 当strTime为2008-9时返回为2008-9-1 00:00格式日期时间，无法转换返回null.
     *
     * @param strTime
     * @return
     */
    public static Date strToDate(String strTime) {
        if (ObjectUtils.isEmpty(strTime) || strTime.trim().length() <= 0) {
            return null;
        }

        Date date = null;
        List<String> list = new ArrayList<>(0);

        list.add(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
        list.add(DATE_TIME_FORMAT_YYYYMMDDHHMISSSSS);
        list.add(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI);
        list.add(DATE_TIME_FORMAT_YYYYMMDD_HH_MI);
        list.add(DATE_TIME_FORMAT_YYYYMMDDHHMISS);
        list.add(DATE_FORMAT_YYYY_MM_DD);
        list.add(DATE_FORMAT_YYYYMMDD);
        list.add(DATE_FORMAT_YYYY_MM);
        list.add(DATE_FORMAT_YYYYMM);
        list.add(DATE_FORMAT_YYYY);

        for (String format : list) {
            if (strTime.indexOf("-") > 0 && !format.contains("-")) {
                continue;
            }
            if (!strTime.contains("-") && format.indexOf("-") > 0) {
                continue;
            }
            if (strTime.length() > format.length()) {
                continue;
            }
            date = parseStrToDate(strTime, format);
            if (!ObjectUtils.isEmpty(date)) {
                break;
            }
        }

        return date;
    }

    /**
     * 解析两个日期之间的所有月份
     *
     * @param beginDateStr 开始日期，至少精确到yyyy-MM
     * @param endDateStr   结束日期，至少精确到yyyy-MM
     * @return yyyy-MM日期集合
     */
    public static List<String> getMonthListOfDate(String beginDateStr, String endDateStr) {
        // 指定要解析的时间格式
        SimpleDateFormat f = getSimpleDateFormat(DATE_FORMAT_YYYY_MM);
        // 返回的月份列表
        String sRet = "";

        // 定义一些变量
        Date beginDate = null;
        Date endDate = null;

        GregorianCalendar beginGC = null;
        GregorianCalendar endGC = null;
        List<String> list = new ArrayList<String>();

        try {
            // 将字符串parse成日期
            beginDate = f.parse(beginDateStr);
            endDate = f.parse(endDateStr);

            // 设置日历
            beginGC = new GregorianCalendar();
            beginGC.setTime(beginDate);

            endGC = new GregorianCalendar();
            endGC.setTime(endDate);

            // 直到两个时间相同
            while (beginGC.getTime().compareTo(endGC.getTime()) <= 0) {
                sRet = beginGC.get(Calendar.YEAR) + "-"
                        + (beginGC.get(Calendar.MONTH) + 1);
                list.add(sRet);
                // 以月为单位，增加时间
                beginGC.add(Calendar.MONTH, 1);
            }
        } catch (Exception e) {
            log.error("DateFormatUtil.getMonthListOfDate  error = {}", e.getMessage());
        }
        return list;
    }

    /**
     * 解析两个日期段之间的所有日期
     *
     * @param beginDateStr 开始日期  ，至少精确到yyyy-MM-dd
     * @param endDateStr   结束日期  ，至少精确到yyyy-MM-dd
     * @return yyyy-MM-dd日期集合
     */
    public static List<String> getDayListOfDate(String beginDateStr, String endDateStr) {
        // 指定要解析的时间格式
        SimpleDateFormat f = getSimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);

        // 定义一些变量
        Date beginDate = null;
        Date endDate = null;

        Calendar beginGC = null;
        Calendar endGC = null;
        List<String> list = new ArrayList<String>();

        try {
            // 将字符串parse成日期
            beginDate = f.parse(beginDateStr);
            endDate = f.parse(endDateStr);

            // 设置日历
            beginGC = Calendar.getInstance();
            beginGC.setTime(beginDate);

            endGC = Calendar.getInstance();
            endGC.setTime(endDate);
            SimpleDateFormat sdf = getSimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);

            // 直到两个时间相同
            while (beginGC.getTime().compareTo(endGC.getTime()) <= 0) {

                list.add(sdf.format(beginGC.getTime()));
                // 以日为单位，增加时间
                beginGC.add(Calendar.DAY_OF_MONTH, 1);
            }

        } catch (Exception e) {
            log.error("DateFormatUtil.getDayListOfDate  error = {}", e.getMessage());
        }
        return list;
    }

    /**
     * 获取当下年份指定前后数量的年份集合
     *
     * @param before 当下年份前年数
     * @param behind 当下年份后年数
     * @return 集合
     */
    public static List<Integer> getYearListOfYears(int before, int behind) {
        if (before < 0 || behind < 0) {
            return null;
        }
        List<Integer> list = new ArrayList<Integer>();
        Calendar c = null;
        c = Calendar.getInstance();
        c.setTime(new Date());
        int currYear = Calendar.getInstance().get(Calendar.YEAR);

        int startYear = currYear - before;
        int endYear = currYear + behind;
        for (int i = startYear; i < endYear; i++) {
            list.add(i);
        }
        return list;
    }

    /**
     * 获取当前日期是一年中第几周
     *
     * @param date
     * @return
     */
    public static Integer getWeekthOfYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取某一年各星期的始终时间
     * 实例：getWeekList(2016)，第52周(从2016-12-26至2017-01-01)
     *
     * @param year
     * @return
     */
    public static HashMap<Integer, String> getWeekTimeOfYear(int year) {
        HashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
        Calendar c = new GregorianCalendar();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        int count = getWeekthOfYear(c.getTime());

        SimpleDateFormat sdf = getSimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
        String dayOfWeekStart = "";
        String dayOfWeekEnd = "";
        for (int i = 1; i <= count; i++) {
            dayOfWeekStart = sdf.format(getFirstDayOfWeek(year, i));
            dayOfWeekEnd = sdf.format(getLastDayOfWeek(year, i));
            map.put(i, "第" + i + "周(从" + dayOfWeekStart + "至" + dayOfWeekEnd + ")");
        }
        return map;

    }

    /**
     * 获取某一年的总周数
     *
     * @param year
     * @return
     */
    public static Integer getWeekCountOfYear(int year) {
        Calendar c = new GregorianCalendar();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        return getWeekthOfYear(c.getTime());
    }

    /**
     * 获取指定日期所在周的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }

    /**
     * 获取指定日期所在周的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }

    /**
     * 获取某年某周的第一天
     *
     * @param year 目标年份
     * @param week 目标周数
     * @return
     */
    public static Date getFirstDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);

        return getFirstDayOfWeek(cal.getTime());
    }

    /**
     * 获取某年某周的最后一天
     *
     * @param year 目标年份
     * @param week 目标周数
     * @return
     */
    public static Date getLastDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);

        return getLastDayOfWeek(cal.getTime());
    }

    /**
     * 获取某年某月的第一天
     *
     * @param year  目标年份
     * @param month 目标月份
     * @return
     */
    public static Date getFirstDayOfMonth(int year, int month) {
        month = month - 1;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);

        int day = c.getActualMinimum(Calendar.DAY_OF_MONTH);

        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取某年某月的最后一天
     *
     * @param year  目标年份
     * @param month 目标月份
     * @return
     */
    public static Date getLastDayOfMonth(int year, int month) {
        month = month - 1;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        int day = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /**
     * 获取某个日期为星期几
     *
     * @param date
     * @return String "星期*"
     */
    public static String getDayWeekOfDate1(Date date) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }

        return weekDays[w];
    }

    /**
     * 获得指定日期的星期几数
     *
     * @param date
     * @return int
     */
    public static Integer getDayWeekOfDate2(Date date) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(date);
        return aCalendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 验证字符串是否为日期
     * 验证格式:YYYYMMDD、YYYY_MM_DD、YYYYMMDDHHMISS、YYYYMMDD_HH_MI、YYYY_MM_DD_HH_MI、YYYYMMDDHHMISSSSS、YYYY_MM_DD_HH_MI_SS
     *
     * @param strTime
     * @return null时返回false;true为日期，false不为日期
     */
    public static boolean validateIsDate(String strTime) {
        if (ObjectUtils.isEmpty(strTime) || strTime.trim().length() <= 0) {
            return false;
        }

        Date date = null;
        List<String> list = new ArrayList<String>(0);

        list.add(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
        list.add(DATE_TIME_FORMAT_YYYYMMDDHHMISSSSS);
        list.add(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI);
        list.add(DATE_TIME_FORMAT_YYYYMMDD_HH_MI);
        list.add(DATE_TIME_FORMAT_YYYYMMDDHHMISS);
        list.add(DATE_FORMAT_YYYY_MM_DD);
        list.add(DATE_FORMAT_YYYYMMDD);

        for (String format : list) {
            if (strTime.indexOf("-") > 0 && !format.contains("-")) {
                continue;
            }
            if (!strTime.contains("-") && format.indexOf("-") > 0) {
                continue;
            }
            if (strTime.length() > format.length()) {
                continue;
            }
            date = parseStrToDate(strTime.trim(), format);
            if (!ObjectUtils.isEmpty(date)) {
                break;
            }
        }

        if (!ObjectUtils.isEmpty(date)) {
            log.info("生成的日期:{}" ,parseDateToStr(date, DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS, "--null--"));
            return true;
        }
        return false;
    }

    /**
     * 将指定日期的时分秒格式为零
     *
     * @param date
     * @return
     */
    public static Date formatHhMmSsOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 将指定日期减去一天
     *
     * @param date
     * @return
     */
    public static Date formatSubtractDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, -24);
        return cal.getTime();
    }

    /**
     * 获得指定时间加减参数后的日期(不计算则输入0)
     *
     * @param date        指定日期
     * @param year        年数，可正可负
     * @param month       月数，可正可负
     * @param day         天数，可正可负
     * @param hour        小时数，可正可负
     * @param minute      分钟数，可正可负
     * @param second      秒数，可正可负
     * @param millisecond 毫秒数，可正可负
     * @return 计算后的日期
     */
    public static Date addDate(Date date, int year, int month, int day, int hour, int minute, int second, int millisecond) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, year);//加减年数
        c.add(Calendar.MONTH, month);//加减月数
        c.add(Calendar.DATE, day);//加减天数
        c.add(Calendar.HOUR, hour);//加减小时数
        c.add(Calendar.MINUTE, minute);//加减分钟数
        c.add(Calendar.SECOND, second);//加减秒
        c.add(Calendar.MILLISECOND, millisecond);//加减毫秒数

        return c.getTime();
    }

    /**
     * 获得两个日期的时间戳之差
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Long getDistanceTimestamp(Date startDate, Date endDate) {
        return (endDate.getTime() - startDate.getTime() + 1000000) / (3600 * 24 * 1000);
    }

    /**
     * 判断二个时间是否为同年同月
     *
     * @param date1
     * @param date2
     * @return
     */
    public static Boolean compareIsSameMonth(Date date1, Date date2) {
        boolean flag = false;
        int year1 = getYear(date1);
        int year2 = getYear(date2);
        if (year1 == year2) {
            int month1 = getMonth(date1);
            int month2 = getMonth(date2);

            if (month1 == month2) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 获得两个日期相差多少小时
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 相差多少小时
     */
    public static long getDistanceHourTime(Date startDate, Date endDate) {
        long time1 = startDate.getTime();
        long time2 = endDate.getTime();
        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        return diff / 1000 / 60 / 60;
    }

    /**
     * 获得两个时间相差距离多少天多少小时多少分多少秒
     *
     * @param one 时间参数 1 格式：1990-01-01 12:00:00
     * @param two 时间参数 2 格式：2009-01-01 12:00:00
     * @return long[] 返回值为：{天, 时, 分, 秒}
     */
    public static long[] getDistanceTime(Date one, Date two) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {

            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (Exception e) {
            log.error("DateFormatUtil.getDistanceTime  error = {}", e.getMessage());
        }
        return new long[]{day, hour, min, sec};
    }

    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     *
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：{天, 时, 分, 秒}
     */
    public static long[] getDistanceTime(String str1, String str2) {
        DateFormat df = getSimpleDateFormat(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (ParseException e) {
            log.error("DateFormatUtil.getDistanceTime  error = {}", e.getMessage());
        }
        return new long[]{day, hour, min, sec};
    }

    /**
     * 两个时间之间相差距离多少天
     *
     * @param str1 时间参数 1：
     * @param str2 时间参数 2：
     * @return 相差天数
     */
    public static Long getDistanceDays(String str1, String str2) {
        DateFormat df = getSimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
        Date one;
        Date two;
        long days = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            log.error("DateFormatUtil.getDistanceDays  error = {}", e.getMessage());
        }
        return days;
    }

    /**
     * 两个时间之间相差距离多少小时
     *
     * @param str1 时间参数 1：
     * @param str2 时间参数 2：
     * @return 相差天数
     */
    public static Long getDistanceHours(String str1, String str2) throws Exception {
        DateFormat df = getSimpleDateFormat(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
        Date one;
        Date two;
        long hours = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            hours = diff / (1000 * 60 * 60);
        } catch (ParseException e) {
            log.error("DateFormatUtil.getDistanceHours  error = {}", e.getMessage());
        }
        return hours;
    }

    /**
     * 两个时间之间相差距离多少分钟
     *
     * @param str1 时间参数 1：
     * @param str2 时间参数 2：
     * @return 相差天数
     */
    public static Long getDistanceMinutes(String str1, String str2) {
        DateFormat df = getSimpleDateFormat(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
        Date one;
        Date two;
        long mins = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            mins = diff / (1000 * 60);
        } catch (ParseException e) {
            log.error("DateFormatUtil.getDistanceMinutes  error = {}", e.getMessage());
        }
        return mins;
    }

    /**
     * 获取指定时间的那天 00:00:00.000 的时间
     *
     * @param date
     * @return
     */
    public static Date getDayBeginTime(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取指定时间的那天 23:59:59.999 的时间
     *
     * @param date
     * @return
     */
    public static Date getDayEndTime(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }


    /**
     * 从Date转化为LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }


    /**
     * 从Date转化为LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate dateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalDate();
    }


    /**
     * 从Date转化为LocalTime
     *
     * @param date 日期
     * @return LocalTime
     */
    public static LocalTime dateToLocalTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalTime();
    }


    /**
     * 从LocalDateTime转化为Date
     *
     * @param localDateTime 本地日期时间
     * @return Date
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
//        localDateTime = LocalDateTime.now();
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }


    /**
     * 从LocalDate转化为Date
     *
     * @param localDate 本地日期
     * @return Date
     */
    public static Date localDateToDate(LocalDate localDate) {
//        localDate = LocalDate.now();
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }


    /**
     * 从LocalTime转化为Date
     *
     * @param localDate 本地日期
     * @param localTime 本地时间
     * @return Date
     */
    public static Date localTimeToDate(LocalDate localDate, LocalTime localTime) {
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * 格式化本地时间
     *
     * @param time       时间
     * @param timeFormat 时间格式
     * @return 本地时间
     */
    public static String getLocalDateString(LocalDateTime time, String timeFormat) {
        timeFormat = StringUtils.isNotBlank(timeFormat) ? timeFormat : DATE_FORMAT_YYYY_MM_DD;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(timeFormat);
        return dtf.format(time);
    }

    /**
     * 格式化本地日期
     *
     * @param date       日期
     * @param timeFormat 时间格式
     * @return 本地时间
     */
    public static String getLocalDateString(LocalDate date, String timeFormat) {
        timeFormat = StringUtils.isNotBlank(timeFormat) ? timeFormat : DATE_FORMAT_YYYY_MM_DD;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(timeFormat);
        return dtf.format(date);
    }

    /**
     * 获取date2比date1多的天数
     *
     * @param date1 第一个日期
     * @param date2 第二个日期
     * @return 第二个日期比第一个日期多的天数
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) {  //不同一年
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {   //闰年
                    timeDistance += 366;
                } else {    //不是闰年
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1);
        } else {   //同一年
            log.info("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
    }


    /**
     * 获取两个日期相差的月数
     *
     * @param d1 第一个日期
     * @param d2 第二个日期
     * @return 两个日期相差的月数
     */
    public static int getMonthDiff(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        // 获取年的差值 
        int yearInterval = year1 - year2;
        // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
        if (month1 < month2 || month1 == month2 && day1 < day2) {
            yearInterval--;
        }
        // 获取月数差值
        int monthInterval = (month1 + 12) - month2;
        if (day1 < day2) {
            monthInterval--;
        }
        monthInterval %= 12;
        return Math.abs(yearInterval * 12 + monthInterval);
    }


    /**
     * 校验时间格式的合法性 正确的日期格式为yyyy-MM-dd HH:mm:ss
     *
     * @param timeStr 时间字符串
     * @return boolean
     */
    public static boolean isValidDate(String timeStr) {
        boolean convertSuccess = true;
        SimpleDateFormat format = getSimpleDateFormat(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
        try {
            // 设置lenient为false.
            // 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(timeStr);
        } catch (Exception e) {
            log.error("DateFormatUtil.isValidDate  error = {}", e.getMessage());
            convertSuccess = isValidDateRegex(timeStr);

        }
        return convertSuccess;
    }


    /**
     * 验证日期格式的合法性
     *
     * @param dateStr    日期字符串
     * @param dateFormat 日期格式
     * @return boolean
     */
    public static boolean isValidDate(String dateStr, String dateFormat) {
        dateFormat = StringUtils.isNotBlank(dateFormat) ? dateFormat : DATE_FORMAT_YYYY_MM_DD;
        SimpleDateFormat format = getSimpleDateFormat(dateFormat);
        try {
            // 设置lenient为false.
            // 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(dateStr);
        } catch (Exception e) {
            log.error("DateFormatUtil.isValidDate  error = {}", e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 校验日期是否合法
     * 2021-01-030，也超过日期格式yyyy-MM-dd的长度，会校验失败
     * @param dateStr
     * @param format
     * @return
     */
    public static boolean isValidDate2(String dateStr, String format) {
        format = StringUtils.isNotBlank(format) ? format : DATE_FORMAT_YYYY_MM_DD;
        DateTimeFormatter ldt = DateTimeFormatter.ofPattern(format.replace("y", "u")).withResolverStyle(ResolverStyle.STRICT);
        try {
            return LocalDate.parse(dateStr, ldt)==null?false:true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 校验日期格式的合法性 正确的日期格式为dd-MM-yyyy
     *
     * @param dateStr 日期字符串
     * @return boolean
     */
    public static boolean isValidDateRegex(String dateStr) {
        String timeRegex = "(((0[1-9]|[12][0-9]|3[01])-((0[13578]|1[02]))|((0[1-9]|[12][0-9]|30)-(0[469]|11))|(0[1-9]|[1][0-9]|2[0-8])-(02))" +
                "-([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3}))|" +
                "(29-02-(([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00)))";
        return Pattern.matches(timeRegex, dateStr);
    }

    /**
     * 校验时间格式
     *
     * @param timeStr 时间字符串 00:00:00
     * @return boolean
     */
    public static boolean isValidTime(String timeStr) {
        String timeRegex = "([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
        return Pattern.matches(timeRegex, timeStr);
    }


    /**
     * 某时间加上或减去若干分钟得到新的时间
     *
     * @param dateTime 日期时间
     * @param minutes  分
     * @return newTime
     * @throws Exception
     */
    public static String getNewTime(String dateTime, int minutes) {

        SimpleDateFormat formatter = getSimpleDateFormat(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
        Date newDate;
        try {
            Date originalDate = formatter.parse(dateTime);

            Calendar newTime = Calendar.getInstance();
            newTime.setTime(originalDate);
            newTime.add(Calendar.MINUTE, minutes);//日期加n分,负数则减n分

            newDate = newTime.getTime();
        } catch (ParseException e) {
            log.error("DateFormatUtil.getNewTime error = {}", e.getMessage());
            return null;
        }

        return formatter.format(newDate);
    }


    /**
     * 获取增加天数的日期
     *
     * @param date 日期
     * @param day  天数
     * @return Date
     */
    public static Date addDayDate(Date date, int day) {
        if(ObjectUtils.isEmpty(date)){
            return null;
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day); // 把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime();
        return date;
    }


    /**
     * 获取增加月份的日期
     *
     * @param date  日期
     * @param month 月
     * @return Date
     */
    public static Date addMonthDate(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month); //增加一个月,负数则为减去月数
        return calendar.getTime();
    }

    /**
     * 判断某个日期是否在两个日期范围之内
     *
     * @param startDate   开始日期
     * @param endDate     结束日期
     * @param currentDate 当前日期
     * @return boolean
     */
    public static boolean isInsideDate(String startDate, String endDate, String currentDate) {
        try {
            SimpleDateFormat simpleDateFormat = getSimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
            Date date1 = simpleDateFormat.parse(startDate);
            Date date2 = simpleDateFormat.parse(endDate);
            Date date3 = simpleDateFormat.parse(currentDate);
            return date1.getTime() <= date3.getTime() && date2.getTime() >= date3.getTime();
        } catch (ParseException e) {
            log.error("DateFormatUtil.isInsideDate  error = {}", e.getMessage());
            return false;
        }
    }

//    public static String numberToStr(int i) {
//        return ("" + i).length() == 1 ? "0" + i : String.valueOf(i);
//    }

    /**
     * 当前日期字符串
     *
     * @param format 日期格式
     * @return 当前日期字符串
     */
    public static String getNowStr(String format) {
        format = StringUtils.isNotBlank(format) ? format : DATE_FORMAT_YYYY_MM_DD;
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat(format);
        return simpleDateFormat.format(now);
    }


    /**
     * 格式化当前日期
     *
     * @param format 日期格式
     * @return nowDate
     */
    public static Date getNowDate(String format) {
        format = StringUtils.isNotBlank(format) ? format : DATE_FORMAT_YYYY_MM_DD;
        SimpleDateFormat sdf = getSimpleDateFormat(format);
        Date nowDate;
        try {
            nowDate = sdf.parse(getNowStr(format));
        } catch (ParseException e) {
            log.error("DateFormatUtil.getNowDate  error = {}", e.getMessage());
            nowDate = new Date();
        }
        return nowDate;
    }


    /**
     * 校验多个时间段是否交叉--有开始和结束时间段
     * todo ！！！
     * @param dateArray 日期时间数组
     * @return boolean
     */
    @Deprecated
    public static boolean hasMultipleOverlap(String[][] dateArray) {
        for (int i = 0; i < dateArray.length - 1; i++) {
            String startDateBegin = dateArray[i][0];
            String endDateEnd = dateArray[i][1];
            for (int j = i + 1; j < dateArray.length; j++) {
                String startDate = dateArray[j][0];
                String endDate = dateArray[j][1];
                if (startDateBegin.compareTo(endDate) >= 0 || endDateEnd.compareTo(startDate) <= 0) {
                    continue;
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取日期格式对象
     *
     * @param timeFormat 时间格式
     * @return 日期格式对象
     */
    private static SimpleDateFormat getSimpleDateFormat(String timeFormat){
        timeFormat = StringUtils.isNotBlank(timeFormat) ? timeFormat : DATE_FORMAT_YYYY_MM_DD;
        return new SimpleDateFormat(timeFormat);
    }

    /**
     * 获取当前年份 yyyy
     *
     * @return currentYear
     */
    public static String getCurrentYear() {
        SimpleDateFormat sdf = getSimpleDateFormat(DATE_FORMAT_YYYY);
        return sdf.format(new Date());
    }

    /**
     * 获取当前月份 yyyy-MM
     *
     * @return currentMonth
     */
    public static String getCurrentMonth() {
        SimpleDateFormat sdf = getSimpleDateFormat(DATE_FORMAT_YYYY_MM);
        return sdf.format(new Date());
    }

    /**
     * 获取当前日期 yyyy-MM-dd
     *
     * @return 当前日期 dateStr yyyy-MM-dd
     */
    public static String getCurrentDay() {
        SimpleDateFormat sdf = getSimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
        return sdf.format(new Date());
    }

    /**
     * 获取当前日期 yyyyMMdd
     *
     * @return 当前日期 dateStr yyyyMMdd
     */
    public static String getCurrentDay2() {
        SimpleDateFormat sdf = getSimpleDateFormat(DATE_FORMAT_YYYYMMDD);
        return sdf.format(new Date());
    }

    /**
     * 获取当前日期 yyyy-MM-dd
     *
     * @return 当前日期 date
     */
    public static Date getCurrentDate() {
        SimpleDateFormat sdf = getSimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
        Date formatDate;
        try {
            String dateStr = sdf.format(new Date());
            formatDate = sdf.parse(dateStr);
        } catch (ParseException e) {
            log.error("DateFormatUtil.getCurrentDate  error = {}", e.getMessage());
            return new Date();
        }
        return formatDate;
    }

    /**
     * 获取当前时间 yyyy-MM-dd HH:mm:ss
     *
     * @return 当前时间 dateStr yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentTime() {
        SimpleDateFormat sdf = getSimpleDateFormat(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
        return sdf.format(new Date());
    }

    /**
     * 获取当前时间 yyyyMMddHHmmss
     *
     * @return 当前时间 dateStr yyyyMMddHHmmss
     */
    public static String getCurrentTime2() {
        SimpleDateFormat sdf = getSimpleDateFormat(DATE_TIME_FORMAT_YYYYMMDDHHMISS);
        return sdf.format(new Date());
    }

    /**
     * 格式化指定日期 str->date
     *
     * @param dateStr yyyy-MM-dd
     * @return date
     */
    public static Date formatSpecifyDate(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        return formatStrToDate(dateStr, DATE_FORMAT_YYYY_MM_DD);
    }

    /**
     * 格式化指定日期 str->date
     *
     * @param dateStr dd-MM-yyyy
     * @return date
     */
    public static Date formatSpecifyDate2(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        return formatStrToDate(dateStr, DATE_FORMAT_DD_MM_YYYY);
    }

    /**
     * 格式化指定日期 str->date
     *
     * @param dateStr yyyy-MM-dd HH:mm:ss
     * @return date
     */
    public static Date formatSpecifyTime(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        return formatStrToDate(dateStr, DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
    }

    /**
     * 格式化指定日期 str->date
     *
     * @param dateStr dd-MM-yyyy HH:mm:ss
     * @return date
     */
    public static Date formatSpecifyTime2(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        return formatStrToDate(dateStr, DATE_TIME_FORMAT_DD_MM_YYYY_HH_MI_SS);
    }

    /**
     * 日期转换
     *
     * @param dateStr   日期
     * @param formatStr 日期格式
     * @return date
     */
    public static Date formatStrToDate(String dateStr, String formatStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        formatStr = StringUtils.isNotBlank(formatStr) ? formatStr : DATE_FORMAT_YYYY_MM_DD;
        SimpleDateFormat sdf = getSimpleDateFormat(formatStr);
        Date formatDate;
        try {
            formatDate = sdf.parse(dateStr);
        } catch (ParseException e) {
            log.error("DateFormatUtil.formatStrToDate  error = {}", e.getMessage());
            return getCurrentDate();
        }
        return formatDate;
    }

    /**
     * 格式化指定日期字符串
     *
     * @param date 日期 date->str
     * @return dateStr yyyy-MM-dd
     */
    public static String formatSpecifyDate(Date date) {
        if (ObjectUtils.isEmpty(date)) {
            return StringUtils.EMPTY;
        }
        return getDateString(date, DATE_FORMAT_YYYY_MM_DD);
    }

    /**
     * 格式化指定日期字符串
     *
     * @param date 日期 date->str
     * @return dateStr dd-MM-yyyy
     */
    public static String formatSpecifyDate2(Date date) {
        if (ObjectUtils.isEmpty(date)) {
            return StringUtils.EMPTY;
        }
        return getDateString(date, DATE_FORMAT_DD_MM_YYYY);
    }

    /**
     * 格式化指定日期字符串
     *
     * @param date 日期 date->str
     * @return dateStr yyyy.MM.dd
     */
    public static String formatSpecifyDate3(Date date) {
        if (ObjectUtils.isEmpty(date)) {
            return StringUtils.EMPTY;
        }
        return getDateString(date, DATE_FORMAT_POINTYYYYMMDD);
    }

    /**
     * 格式化指定日期字符串
     *
     * @param date 日期 date->str
     * @return dateStr yyyyMMdd
     */
    public static String formatSpecifyDate4(Date date) {
        if (ObjectUtils.isEmpty(date)) {
            return StringUtils.EMPTY;
        }
        return getDateString(date, DATE_FORMAT_YYYYMMDD);
    }

    /**
     * 格式化指定时间字符串
     *
     * @param date 日期
     * @return dateStr yyyy-MM-dd HH:mm:ss
     */
    public static String formatSpecifyTime(Date date) {
        if (ObjectUtils.isEmpty(date)) {
            return StringUtils.EMPTY;
        }
        return getDateString(date, DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
    }

    /**
     * 格式化指定时间字符串
     *
     * @param date 日期
     * @return dateStr dd-MM-yyyy HH:mm:ss
     */
    public static String formatSpecifyTime2(Date date) {
        if (ObjectUtils.isEmpty(date)) {
            return StringUtils.EMPTY;
        }
        return getDateString(date, DATE_TIME_FORMAT_DD_MM_YYYY_HH_MI_SS);
    }

    /**
     * 日期格式转换 dd-MM-yyyy转为yyyy-MM-dd
     *
     * @param dateStr dd-MM-yyyy日期格式字符串
     * @return yyyy-MM-dd日期格式字符串
     */
    public static String convertDate(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return StringUtils.EMPTY;
        }
        return formatSpecifyDate(formatSpecifyDate2(dateStr));
    }

    /**
     * 日期格式转换 yyyy-MM-dd转为dd-MM-yyyy
     *
     * @param dateStr yyyy-MM-dd日期格式字符串
     * @return dd-MM-yyyy日期格式字符串
     */
    public static String convertDate2(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return StringUtils.EMPTY;
        }
        return formatSpecifyDate2(formatSpecifyDate(dateStr));
    }

    /**
     * 日期格式转换 yyyy-MM-dd转为yyyyMMdd
     *
     * @param dateStr yyyy-MM-dd日期格式字符串
     * @return yyyyMMdd日期格式字符串
     */
    public static String convertDate3(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return StringUtils.EMPTY;
        }
        return formatSpecifyDate4(formatSpecifyDate(dateStr));
    }

    /**
     * 时间格式转换 yyyy-MM-dd HH:mm:ss转为dd-MM-yyyy HH:mm:ss
     *
     * @param timeStr yyyy-MM-dd HH:mm:ss日期格式字符串
     * @return dd-MM-yyyy HH:mm:ss日期格式字符串
     */
    public static String convertTime(String timeStr) {
        if (StringUtils.isBlank(timeStr)) {
            return StringUtils.EMPTY;
        }
        Date date = formatStrToDate(timeStr, DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
        return formatSpecifyTime2(date);
    }

    /**
     * 时间格式转换 dd-MM-yyyy HH:mm:ss转为yyyy-MM-dd HH:mm:ss
     *
     * @param timeStr dd-MM-yyyy HH:mm:ss日期格式字符串
     * @return yyyy-MM-dd HH:mm:ss日期格式字符串
     */
    public static String convertTime2(String timeStr) {
        if (StringUtils.isBlank(timeStr)) {
            return StringUtils.EMPTY;
        }
        Date date = formatStrToDate(timeStr, DATE_TIME_FORMAT_DD_MM_YYYY_HH_MI_SS);
        return formatSpecifyTime(date);
    }

    /**
     * 获取不为空日期
     *
     * @param dateStr
     * @return
     */
    @Deprecated
    public static String getNotEmptyDate(String dateStr){
        if (StringUtils.isEmpty(dateStr)) {
            dateStr = getCurrentDay();
        }
        return dateStr;
    }

    /**
     * 获取第二个日期，如果为空，第二个日期为第一个日期
     *
     * @param firstDateStr
     * @param secondDateStr
     * @return
     */
    @Deprecated
    public static String getSecondDate(String firstDateStr, String secondDateStr){
        if (StringUtils.isEmpty(secondDateStr)) {
            secondDateStr = firstDateStr;
        }
        return secondDateStr;
    }

    /**
     * 获取年龄
     *
     * @param birthDate 生日
     * @return 年龄
     * @throws Exception
     */
    public static String getAge(Date birthDate) {
        if (ObjectUtils.isEmpty(birthDate)) {
            return StringUtils.EMPTY;
        }
        Calendar cal = Calendar.getInstance();
        // 出生日期晚于当前时间，无法计算，抛出异常
        if (cal.before(birthDate)) {
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        }
        int nowYear = cal.get(Calendar.YEAR);
        int nowMonth = cal.get(Calendar.MONTH);
        int nowDay = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDate);
        int birthYear = cal.get(Calendar.YEAR);
        int birthMonth = cal.get(Calendar.MONTH);
        int birthDay = cal.get(Calendar.DAY_OF_MONTH);
        int age = nowYear - birthYear;
        if (nowMonth <= birthMonth) {
            if (nowMonth == birthMonth) {
                // 当前日期在生日之前，年龄减一
                if (nowDay < birthDay) {
                    age--;
                }
            } else {
                // 当前月份在生日之前，年龄减一
                age--;
            }
        }
        return String.valueOf(age);
    }

    /*--------------------------------------------------------分割线-----------------------------------------------------*/


    public static boolean isSameDate(Date date1, Date date2)
    {
        SimpleDateFormat format=new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
        format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String dateStr1=format.format(date1);
        String dateStr2=format.format(date2);
        return dateStr1.equalsIgnoreCase(dateStr2);
    }

    public static boolean isSameDate(Long dateLong1,Long dateLong2,Boolean defaultValue)
    {
        if(dateLong1==null || dateLong2==null)
        {
            return defaultValue==null?false:defaultValue;
        }
        Date date1=new Date(dateLong1);
        Date date2=new Date(dateLong2);
        return isSameDate(date1,date2);
    }


    /**
     * 时间去掉时分秒
     *
     * @param date date
     * @return
     * @version: 1.0
     * @date: 2021-05-12 15:15
     * @author: zsz
     */
    public static Date discardHourMinSecond(Date date)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        // 将时分秒,毫秒域清零
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        return cal1.getTime();
    }


    /**
     * 时间累加器<br>
     *
     * @ClassName DateAccumulator
     * @author leigang.yang <br>
     * @version V1.0.0<br>
     * @Date 2021/12/6 16:48<br>
     */
    @Getter
    public static class DateAccumulator{

        private Date date;

        public DateAccumulator(Date date){
            this.date = date;
        }

        /**
         * 时间追加年
         * @param amount
         * @return
         * @version: 1.0
         * @date: 2021/12/6 16:48
         * @author: leigang.yang
         */
        public DateAccumulator addYears(int amount){
            if(date != null){
                date = org.apache.commons.lang3.time.DateUtils.addYears(date, amount);
            }
            return this;
        }

        /**
         * 时间追加月
         * @param amount
         * @return
         * @version: 1.0
         * @date: 2021/12/6 16:48
         * @author: leigang.yang
         */
        public DateAccumulator addMonths(int amount){
            if(date != null){
                date = org.apache.commons.lang3.time.DateUtils.addMonths(date, amount);
            }
            return this;
        }

        /**
         * 时间追加周
         * @param amount
         * @return
         * @version: 1.0
         * @date: 2021/12/6 16:48
         * @author: leigang.yang
         */
        public DateAccumulator addWeeks(int amount){
            if(date != null){
                date = org.apache.commons.lang3.time.DateUtils.addWeeks(date, amount);
            }
            return this;
        }

        /**
         * 时间追加日
         * @param amount
         * @return
         * @version: 1.0
         * @date: 2021/12/6 16:48
         * @author: leigang.yang
         */
        public DateAccumulator addDays(int amount){
            if(date != null){
                date = org.apache.commons.lang3.time.DateUtils.addDays(date, amount);
            }
            return this;
        }

        /**
         * 时间追加小时
         * @param amount
         * @return
         * @version: 1.0
         * @date: 2021/12/6 16:48
         * @author: leigang.yang
         */
        public DateAccumulator addHours(int amount){
            if(date != null){
                date = org.apache.commons.lang3.time.DateUtils.addHours(date, amount);
            }
            return this;
        }

        /**
         * 时间追加分钟
         * @param amount
         * @return
         * @version: 1.0
         * @date: 2021/12/6 16:48
         * @author: leigang.yang
         */
        public DateAccumulator addMinutes(int amount){
            if(date != null){
                date = org.apache.commons.lang3.time.DateUtils.addMinutes(date, amount);
            }
            return this;
        }

        /**
         * 时间追加秒
         * @param amount
         * @return
         * @version: 1.0
         * @date: 2021/12/6 16:48
         * @author: leigang.yang
         */
        public DateAccumulator addSeconds(int amount){
            if(date != null){
                date = org.apache.commons.lang3.time.DateUtils.addSeconds(date, amount);
            }
            return this;
        }
    }

    /**
     * 获取当前时间的时间累加器
     * @return
     * @version: 1.0
     * @date: 2021/12/6 16:48
     * @author: leigang.yang
     */
    public static DateAccumulator getDateAccumulator(){
        return getDateAccumulator(new Date());
    }

    /**
     * 获取指定时间的时间累加器
     * @param date
     * @return
     * @version: 1.0
     * @date: 2021/12/6 16:48
     * @author: leigang.yang
     */
    public static DateAccumulator getDateAccumulator(Date date){
        return new DateAccumulator(date);
    }

    /**
     * 获取两个日期之间的所有日期
     *
     * @param start start
     * @param end end
     * @return
     * @version: 1.0
     * @date: 2021-09-01 19:55
     * @author: zsz
     */
//    public static List<String> getListBetwentStart2End(Date start, Date end) {
//        // 返回的日期集合
//        List<String> days = new ArrayList<>();
//        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
//
//        Calendar tempStart = Calendar.getInstance();
//        tempStart.setTime(start);
//
//        Calendar tempEnd = Calendar.getInstance();
//        tempEnd.setTime(end);
//        tempEnd.add(Calendar.DATE, +1);
//        while (tempStart.before(tempEnd)) {
//            days.add(dateFormat.format(tempStart.getTime()));
//            tempStart.add(Calendar.DAY_OF_YEAR, 1);
//        }
//        return days;
//    }

    /**
     * 获取指定时区格式化时间（yyyy/MM/dd HH:mm:ss）<br>
     *
     * @author huiping.peng
     * @Date 2020/09/22
     * @param date
     * @param zoneId
     * @return
     */
    public static String formatOfZoneId(Date date, String zoneId) {
        return formatOfZoneId(date, DATE_TIME_FORMAT_YYYYMMDDHHMMSS, zoneId);
    }

    /**
     * 获取指定格式和指定时区格式化时间<br>
     *
     * @author huiping.peng
     * @Date 2020/09/22
     * @param date
     * @param pattern
     * @param zoneId
     * @return
     */
    public static String formatOfZoneId(Date date, String pattern, String zoneId) {
        return FastDateFormat.getInstance(pattern, TimeZone.getTimeZone(zoneId)).format(date);
    }

    /**
     * LocalDate转换为UTC时间<br>
     *
     * @author huiping.peng
     * @Date 2020/09/22
     * @param localDate
     * @return
     */
    public static Date asUtcDate(LocalDate localDate) {
        return asDateByZoneId(localDate, UTC);
    }

    /**
     * LocalDate转换为指定时区Date<br>
     *
     * @author huiping.peng
     * @Date 2020/09/22
     * @param localDate
     * @param zoneId
     * @return
     */
    public static Date asDateByZoneId(LocalDate localDate, String zoneId) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.of(zoneId)).toInstant());
    }

    /**
     * LocalDateTime转换为UTC时间<br>
     *
     * @author huiping.peng
     * @Date 2020/09/22
     * @param localDateTime
     * @return
     */
    public static Date asUtcDate(LocalDateTime localDateTime) {
        return asDateByZoneId(localDateTime, UTC);
    }

    /**
     * LocalDateTime转换为指定时区Date<br>
     *
     * @author huiping.peng
     * @Date 2020/09/22
     * @param localDateTime
     * @param zoneId
     * @return
     */
    public static Date asDateByZoneId(LocalDateTime localDateTime, String zoneId) {
        return Date.from(localDateTime.atZone(ZoneId.of(zoneId)).toInstant());
    }

    /**
     * 转换为指定时区LocalDate<br>
     *
     * @author huiping.peng
     * @Date 2020/09/22
     * @param date
     * @param zoneId
     * @return
     */
    public static LocalDate asLocalDate(Date date, String zoneId) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.of(zoneId)).toLocalDate();
    }

    /**
     * 转换为指定时区LocalDateTime<br>
     *
     * @author huiping.peng
     * @Date 2020/09/22
     * @param date
     * @param zoneId
     * @return
     */
    public static LocalDateTime asLocalDateTime(Date date, String zoneId) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.of(zoneId)).toLocalDateTime();
    }

    /**
     * 判断date1是否早于date2<br>
     *
     * @author huiping.peng
     * @Date 2020/12/24
     * @param date1
     * @param date2
     * @return
     */
    public static boolean before(Date date1, Date date2) {
        if (ObjectUtil.isNull(date1)) {
            return false;
        }

        if (ObjectUtil.isNull(date2)) {
            return true;
        }
        return date1.before(date2);
    }

    /**
     * Unix时间戳转换为日期类型<br>
     *
     * @author huiping.peng
     * @Date 2021/03/05
     * @param timestamp
     */
    public static Date unixTimeStamp2Date(long timestamp) {
        return new Date(timestamp * 1000);
    }

    /**
     * 日期类型格式字符串转换为Unix时间戳<br>
     *
     * @author huiping.peng
     * @Date 2021/03/05
     * @param dateStr
     * @param format
     * @return
     */
    public static long date2UnixTimeStamp(CharSequence dateStr, String format) {
        return date2UnixTimeStamp(DateUtil.parse(dateStr, format));
    }

    /**
     * 日期类型格式字符串转换为Unix时间戳<br>
     *
     * @author huiping.peng
     * @Date 2021/03/05
     * @param dateStr
     * @return
     */
    public static long date2UnixTimeStamp(CharSequence dateStr, DateFormat dateFormat) {
        return date2UnixTimeStamp(DateUtil.parse(dateStr, dateFormat));
    }

    /**
     * 日期类型转换为Unix时间戳<br>
     *
     * @author huiping.peng
     * @Date 2021/03/05
     * @param date
     * @return
     */
    public static long date2UnixTimeStamp(Date date) {
        if (ObjectUtil.isNull(date)) {
            return NumberUtils.LONG_ZERO.longValue();
        }
        return date.getTime() / 1000;
    }

    /**
     * 获取当前Unix时间戳<br>
     *
     * @author huiping.peng
     * @Date 2021/03/05
     * @return
     */
    public static long nowUnixTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 获取当前本地时间<br>
     *
     * @author huiping.peng
     * @Date 2021/04/22
     * @param zoneId
     * @return
     */
    public static long getCurrentLocalTime(String zoneId) {
        return getCurrentLocalTime(new Date(), zoneId);
    }

    /**
     * 获取当前本地时间<br>
     *
     * @author huiping.peng
     * @Date 2021/04/22
     * @param zoneId
     * @return
     */
    public static long getCurrentLocalTime(Date date, String zoneId) {
        if (StringUtils.isBlank(zoneId)) {
            return Convert.toLong(DatePattern.PURE_DATETIME_FORMAT.format(date), NumberUtils.LONG_ZERO);
        }

        return Convert.toLong(formatOfZoneId(date, DatePattern.PURE_DATETIME_PATTERN, zoneId), NumberUtils.LONG_ZERO);
    }

    /**
     * @Description: 计算两个日期相隔多少天
     * @Param: [start, end]
     * @return: int
     * @Author: xiaotao.liang
     * @Date: 2021/01/22
     */
    public static int daysApart(Date start, Date end) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date StartDate = df.parse(df.format(start));
            Date endDate = df.parse(df.format(end));
            return (int)((endDate.getTime() - StartDate.getTime()) / (24 * 60 * 60 * 1000L));
        } catch (Exception e) {
            log.error("计算两个日期相隔天数错误信息为", e);
        }
        return 0;
    }

    /**
     * 获取x个季度之前的季度开始日期
     *
     * @author xiaohui.cai
     * @param x
     * @return
     */
    public static Date getStartDateOfBeforXXQuarter(Date nowDate, int x) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        int tempYear = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH) + 1;
        int currentQuarter = getCurrentQuarter(m);
        int tempQuarter = currentQuarter;

        for (int i = 0; i < x; i++) {
            if (i == 0) {
                tempQuarter = currentQuarter - 1;
            } else {
                tempQuarter = tempQuarter - 1;
            }
            // 年度减一
            if (tempQuarter <= 0) {
                tempYear -= 1;
                tempQuarter = 4;
            }
        }
        String dateTime = tempYear + "" + getQuarterStartDate(tempQuarter);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            return sdf.parse(dateTime);
        } catch (Exception e) {
            log.error("faild get the quarter start date before x quarters!",e);
        }

        return null;
    }

    /**
     * 获得当前季度
     *
     * @author xiaohui.cai
     * @param m
     * @return
     */
    public static int getCurrentQuarter(int m) {
        if (m >= 1 && m <= 3) {
            return 1;
        } else if (m >= 4 && m <= 6) {
            return 2;
        } else if (m >= 7 && m <= 9) {
            return 3;
        } else if (m >= 10 && m <= 12) {
            return 4;
        }
        return -1;
    }

    /**
     * 获得季度的开始日期
     *
     * @param q
     * @return
     */
    public static String getQuarterStartDate(int q) {
        if (q == 1) {
            return "0101";
        } else if (q == 2) {
            return "0401";
        } else if (q == 3) {
            return "0701";
        } else if (q == 4) {
            return "1001";
        }
        return "";
    }

}

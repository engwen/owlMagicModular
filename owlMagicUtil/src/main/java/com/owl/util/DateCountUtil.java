package com.owl.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具類
 * @author engwen
 * email xiachanzou@outlook.com
 * 2017/5/10.
 */
public abstract class DateCountUtil {
    public static final SimpleDateFormat YMDHMS = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public static final SimpleDateFormat YMDHM = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    public static final SimpleDateFormat YMDH = new SimpleDateFormat("yyyy/MM/dd HH");
    public static final SimpleDateFormat YMD = new SimpleDateFormat("yyyy/MM/dd");
    public static final SimpleDateFormat YMDHMS4BAR = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat YMDHM4BAR = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat YMDH4BAR = new SimpleDateFormat("yyyy-MM-dd HH");
    public static final SimpleDateFormat YMD4BAR = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat YMD4EN = new SimpleDateFormat("MMM.dd, yyyy", Locale.UK);
    public static final SimpleDateFormat YM4BAR = new SimpleDateFormat("yyyy-MM");
    public static final SimpleDateFormat YMD4INT = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat YM4INT = new SimpleDateFormat("yyyyMM");

    public static final SimpleDateFormat YYYY = new SimpleDateFormat("yyyy");
    public static final SimpleDateFormat MM = new SimpleDateFormat("MM");

    public static final SimpleDateFormat HHMMSS = new SimpleDateFormat("HH:mm:ss");
    public static final SimpleDateFormat HHMM = new SimpleDateFormat("HH:mm");

    public static final Long Minute = 60 * 1000L;


    /**
     * 获取指定的日期格式
     * @param date 日期
     * @param sdf  格式
     * @return 日期字符串
     */
    public static String getDateFormSdf(Date date, SimpleDateFormat sdf) {
        return sdf.format(date);
    }

    /**
     * 返回指定格式的日期
     * @param date   日期
     * @param sdfStr 格式
     * @return 日期
     */
    public static String getDateFormSdfStr(Date date, String sdfStr) {
        return getDateFormSdf(date, new SimpleDateFormat(sdfStr));
    }

    /**
     * 根据指定格式的字符串，返回指定格式的日期
     * @param dateString dates
     * @param sdf        格式
     * @return 日期字符串
     */
    public static Date getDateFormSdf(String dateString, SimpleDateFormat sdf) {
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 根据指定格式的字符串，返回指定格式的日期
     * @param dateString dates
     * @param sdfStr     格式
     * @return 日期
     */
    public static Date getDateFormSdfStr(String dateString, String sdfStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(sdfStr);
        return getDateFormSdf(dateString, sdf);
    }

    /**
     * 获取指定的日期格式
     * @param date 日期
     * @param sdf  格式
     * @return 日期字符串
     */
    public static String getDateFormSdfWithStr(String date, SimpleDateFormat sdf) {
        Date temp = DateCountUtil.getDateFormSdf(date, sdf);
        return DateCountUtil.getDateFormSdf(temp, sdf);
    }

    /**
     * 根据指定日期，返回英文格式的日期
     * @param dateYMD date
     * @return 日期
     */
    public static String changeYMDtoEn(Date dateYMD) {
        return YMD4EN.format(dateYMD);
    }

    /**
     * 获取距离现在多少年
     * @param date 日期
     * @return int
     */
    public static int getDateToNowHowYears(Date date) {
        int backYears = getDateToDateHowYears(date, new Date());
        return Math.max(backYears, 0);
    }

    /**
     * 计算两个日期之间的年份
     * @param oldDate 旧的日期
     * @param newDate 新的日期
     * @return int
     */
    public static int getDateToDateHowYears(Date oldDate, Date newDate) {
        Calendar calendarOld = Calendar.getInstance();
        calendarOld.clear();
        calendarOld.setTime(oldDate);
        Calendar calendarNew = Calendar.getInstance();
        calendarNew.clear();
        calendarNew.setTime(newDate);
        int backYears = calendarNew.get(Calendar.YEAR) - calendarOld.get(Calendar.YEAR);
        return Math.max(backYears, 0);
    }

    /**
     * 计算两个日期之间的月份，日期不足一月按一月算
     * @param oldDate 旧的日期
     * @param newDate 新的日期
     * @return int
     */
    public static int getDateToDateHowMonth(Date oldDate, Date newDate) {
        Calendar calendarOld = Calendar.getInstance();
        calendarOld.clear();
        calendarOld.setTime(oldDate);
        Calendar calendarNew = Calendar.getInstance();
        calendarNew.clear();
        calendarNew.setTime(newDate);
        int backYears = calendarNew.get(Calendar.YEAR) - calendarOld.get(Calendar.YEAR);
        int backMonth = calendarNew.get(Calendar.MONTH) - calendarOld.get(Calendar.MONTH);
        return backYears >= 0 ? ((backYears * 12) + (backMonth + 1)) : 0;
    }

    private static int getDateToDateByCount(Date oldDate, Date newDate, Float count) {
        Calendar calendarOld = Calendar.getInstance();
        calendarOld.clear();
        calendarOld.setTime(oldDate);
        Calendar calendarNew = Calendar.getInstance();
        calendarNew.clear();
        calendarNew.setTime(newDate);
        return (int) Math.ceil((calendarNew.getTime().getTime() - calendarOld.getTime().getTime()) / count);
    }


    /**
     * 计算两个日期之间的天数
     * @param oldDate 旧的日期
     * @param newDate 新的日期
     * @return int
     */
    public static int getDateToDateHowDate(Date oldDate, Date newDate) {
        return getDateToDateByCount(oldDate, newDate, 24 * 3600 * 1000F);
    }

    /**
     * 计算两个日期之间的小时数
     * @param oldDate 旧的日期
     * @param newDate 新的日期
     * @return int
     */
    public static int getDateToDateHowHour(Date oldDate, Date newDate) {
        return getDateToDateByCount(oldDate, newDate, 3600 * 1000F);
    }

    /**
     * 计算两个日期之间的分钟数
     * @param oldDate 旧的日期
     * @param newDate 新的日期
     * @return int
     */
    public static int getDateToDateHowMinute(Date oldDate, Date newDate) {
        return getDateToDateByCount(oldDate, newDate, 60 * 1000F);
    }


    /**
     * 獲取指定日期指定天數後的00:00开始日期
     * @param date 旧的日期
     * @param day  指定天數後
     * @return Date
     */
    public static Date addDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 獲取指定日期指定天數後的yyyy-MM-dd 23:59:59日期
     * @param date 旧的日期
     * @param day  指定天數後
     * @return Date
     */
    public static Date addDayWithLastTime(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 獲取指定日期指定天數後的日期
     * @param date 旧的日期
     * @param day  指定天數後
     * @return Date
     */
    public static Date addFullDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    /**
     * 在指定的时间上加或减去几小时-支持浮点数
     * @param date 旧的日期
     * @param hour 小時
     * @return Date
     */
    public static Date addHour(Date date, float hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, (int) (hour * 60));
        return calendar.getTime();
    }

    /**
     * 獲取指定日期指定天數後的日期
     * @param date  旧的日期
     * @param month 月
     * @return Date
     */
    public static Date add30DayByOneMonth(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 30 * month);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 獲取指定日期指定月后的日期
     * @param date  旧的日期
     * @param month 月
     * @return Date
     */
    public static Date addMoth(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }


    /**
     * 计算距离当前日期指定天数的日期，并取该日期的最小值(0时0分0秒)
     * @param day 距离当前日期的天数，正数表示当前日期之后几天的日期，负数表示当前日期之前几天的日期
     * @return date
     */
    public static Date addDay(int day) {
        return addDay(new Date(), day);
    }

    /**
     * 将yyyy-MM-dd HH:mm格式的字符串转化为日期
     * @param strdate 開始日期
     * @return date
     */
    public static Date getHHmmDate(String strdate) {
        Date date = null;
        try {
            date = YMDHM4BAR.parse(strdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将传入日期的小时和分钟和秒提取出来
     * @param date 日期
     * @return str
     */
    public static String getHHmmss(Date date) {
        return HHMMSS.format(date);
    }

    /**
     * 将传入日期的小时和分钟提取出来
     * @param date 日期
     * @return str
     */
    public static String getHHmm(Date date) {
        return HHMM.format(date);
    }

    /**
     * 获取两个日期之间的月份，然后用list返回
     * @param startDateStr 开始时间
     * @param endDateStr   结束时间
     * @return 年月集合
     */
    public static List<String> getMothForTwoDate(String startDateStr, String endDateStr) {
        List<String> list = new ArrayList<>();
        SimpleDateFormat sdf = null;
        if (startDateStr.length() == 6) {
            startDateStr = startDateStr + "01";
            endDateStr = endDateStr + "01";
            sdf = DateCountUtil.YMD4INT;
        } else {
            startDateStr = startDateStr + "-01";
            endDateStr = endDateStr + "-01";
            sdf = DateCountUtil.YMD4BAR;
        }
        Date startDate = DateCountUtil.getDateFormSdf(startDateStr, sdf);
        Date endDate = DateCountUtil.getDateFormSdf(endDateStr, sdf);
        while (startDate.getTime() <= endDate.getTime()) {
            String monthLastDate = DateCountUtil.getDateFormSdf(DateCountUtil.getMonthEndDate(startDate), DateCountUtil.YM4BAR);
            list.add(monthLastDate);
            startDate = DateCountUtil.addMoth(startDate, 1);
        }
        return list;
    }


    /**
     * 获取传入日期的月份
     * @param date 日期
     * @return str
     */
    public static String getMM(Date date) {
        return MM.format(date);
    }

    /**
     * 获取传入日期的年
     * @param date 日期
     * @return str
     */
    public static String getYYYY(Date date) {
        return YYYY.format(date);
    }


    /**
     * 获取 yyyy-mm-dd
     * @return str
     */
    public static String getYYYYMMDD() {
        return YMD.format(new Date());
    }

    /**
     * @param date 日期
     *             获取 yyyy-mm-dd
     * @return str
     */
    public static String getYMD(Date date) {
        return YMD4BAR.format(date);
    }

    /**
     * 获取 yyyy-mm-dd
     * @return str
     */
    public static String getYMD() {
        return YMD4BAR.format(new Date());
    }

    /**
     * 获取 yyyy-mm-dd
     * @return str
     */
    public static String getYMDHMS() {
        return YMDHMS4BAR.format(new Date());
    }

    /**
     * 获取 yyyy-mm-dd
     * @param date 日期
     * @return str
     */
    public static String getYMDHMS(Date date) {
        return YMDHMS4BAR.format(date);
    }

    /**
     * 获取日期所在周，礼拜一的日期
     * @param date 日期
     * @return 日期
     */
    public static String getWeekStartDay(Date date) {
        return DateCountUtil.getWeekStartDay(date, YMD4BAR);
    }

    /**
     * 获取日期所在周，礼拜一的日期
     * @param date 日期
     * @param sdf  日期格式
     * @return 日期
     */
    public static String getWeekStartDay(Date date, SimpleDateFormat sdf) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 获取本周一的日期
        return sdf.format(cal.getTime());
    }

    /**
     * 获取日期所在周，礼拜天的日期
     * @param date 日期
     * @return 日期
     */
    public static String getWeekEndDay(Date date) {
        return DateCountUtil.getWeekEndDay(date, YMD4BAR);
    }

    /**
     * 获取日期所在周，礼拜天的日期
     * @param date 日期
     * @param sdf  日期格式
     * @return 日期
     */
    public static String getWeekEndDay(Date date, SimpleDateFormat sdf) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); // 获取本周一的日期
        // 增加一个星期，才是我们中国人理解的本周日的日期
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        return sdf.format(cal.getTime());
    }


    /**
     * 指定周的第一天和最後一天
     * @param date 日期
     * @return map
     */
    public static Map<String, String> getWeekDay(Date date) {
        return DateCountUtil.getWeekDay(date, YMD4BAR);
    }

    /**
     * 指定周的第一天和最後一天
     * @param oneDate 日期
     * @param sdf     格式
     * @return map
     */
    public static Map<String, String> getWeekDay(Date oneDate, SimpleDateFormat sdf) {
        Map<String, String> map = new HashMap<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(oneDate);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 获取本周一的日期
        map.put("start", sdf.format(cal.getTime()));
        // 这种输出的是上个星期周日的日期，因为国外把周日当成第一天
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        // 增加一个星期，才是我们中国人理解的本周日的日期
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        map.put("end", sdf.format(cal.getTime()));
        return map;
    }

    /**
     * 获取日期所在月第一天日期
     * @param date 日期
     * @return 日期
     */
    public static String getMonthStartDateStr(Date date) {
        return DateCountUtil.getMonthStartDateStr(date, YMD4BAR);
    }

    /**
     * 获取日期所在月第一天日期
     * @param date 日期
     * @param sdf  格式
     * @return 日期
     */
    public static String getMonthStartDateStr(Date date, SimpleDateFormat sdf) {
        return sdf.format(DateCountUtil.getMonthStartDate(date));
    }


    /**
     * 获取日期所在月第一天日期
     * @param date 日期
     * @return 日期
     */
    public static Date getMonthStartDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
        return cal.getTime();
    }


    /**
     * 获取日期所在月最后一天的日期
     * @param date 日期
     * @return 日期
     */
    public static String getMonthEndDateStr(Date date) {
        return DateCountUtil.getMonthEndDateStr(date, YMD4BAR);
    }

    /**
     * 获取日期所在月最后一天的日期
     * @param date 日期
     * @param sdf  格式
     * @return 日期
     */
    public static String getMonthEndDateStr(Date date, SimpleDateFormat sdf) {
        return sdf.format(DateCountUtil.getMonthEndDate(date));
    }

    /**
     * 获取当前月份的最后一天
     * @param oneDate 指定日期
     * @return 当月最后一天
     */
    public static Date getMonthEndDate(Date oneDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(oneDate);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        return calendar.getTime();
    }

    /**
     * 指定月的第一天和最後一天
     * @param date 日期
     * @return map
     */
    public static Map<String, String> getMonthDate(Date date) {
        return DateCountUtil.getMonthDate(date, YMD4BAR);
    }

    /**
     * 指定月的第一天和最後一天
     * @param oneDate 日期
     * @param sdf     格式
     * @return map
     */
    public static Map<String, String> getMonthDate(Date oneDate, SimpleDateFormat sdf) {
        Map<String, String> map = new HashMap<>();
        // 获取Calendar
        Calendar calendar = Calendar.getInstance();
        // 设置时间,当前时间不用设置
        calendar.setTime(oneDate);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
        map.put("start", sdf.format(calendar.getTime()));
        // 设置日期为本月最大日期
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        map.put("end", sdf.format(calendar.getTime()));
        return map;
    }


    /**
     * 判断时间點是否包含在时间段1中
     * @param start1      开始时间
     * @param end1        结束时间
     * @param containTime 开始时间
     * @return 是否包含
     */
    public static boolean isContain(Date start1, Date end1, Date containTime) {
        if (RegexUtil.isParamsHaveEmpty(start1, end1, containTime) || start1.getTime() > end1.getTime()) {
            System.out.println("输入的时间错误");
            return false;
        }
        return start1.getTime() < containTime.getTime() && containTime.getTime() < end1.getTime();
    }

    /**
     * 判断时间段2是否包含在时间段1中
     * @param start1 开始时间
     * @param end1   结束时间
     * @param start2 开始时间
     * @param end2   结束时间
     * @return 是否包含
     */
    public static boolean isContain(Date start1, Date end1, Date start2, Date end2) {
        if (RegexUtil.isParamsHaveEmpty(start1, end1, start2, end2) || start1.getTime() > end1.getTime() || start2.getTime() > end2.getTime()) {
            System.out.println("输入的时间错误");
            return false;
        }
        //如果第二段時間的開始時間比第一段開始時間更早
        if (start2.getTime() <= start1.getTime()) {
            //第二段的結束時間處於開始時間之後則為包含
            return end2.getTime() > start1.getTime();
        } else {
            //第二段開始時間在第一段的開始時間之後
            //儅第二段的結束時間在第一段的結束時間之前 則爲包含
            return start2.getTime() < end1.getTime();
        }
    }

}

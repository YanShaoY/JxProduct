package com.vdin.JxProduct.OSSService;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by admin on 2016/8/5.
 */
public class DateUtils {

    public static long CLICK_TIME = 1000;
    public static String[] WEEK_STRS = {"一", "二", "三", "四", "五", "六", "日"};

    /**
     * 得到SimpleDateFormat
     *
     * @param pattern
     * @return
     */
    public static SimpleDateFormat getSimpleDateFormat(String pattern) {
        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        sf.setTimeZone(TimeZone.getTimeZone("GMT+08"));//**TimeZone时区，加上这句话就解决啦**
        return sf;
    }
    /**
     * 比较两个时间是否相等
     *
     * @param time
     * @param lastTime
     * @return
     */
    public static boolean timeIsEqual(String time, String lastTime) {
        try {
            SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(time);
            Date lastDate = sdf.parse(lastTime);
            if (date.equals(lastDate)) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 通过年月日得到时间字符串
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String getDateStrByYMD(int year, int month, int day) {
        String m = month < 10 ? ("0" + month) : "" + month;
        String d = day < 10 ? ("0" + day) : "" + day;
        return year + "-" + m + "-" + d;
    }

    /**
     * 通过年月日得到时间字符串  YY月dd日
     *
     * @param month
     * @param day
     * @return
     */
    public static String getDateStrByMD(int month, int day) {
        String m = month < 10 ? ("0" + month) : "" + month;
        String d = day < 10 ? ("0" + day) : "" + day;
        return m + "月" + d + "日";
    }

    public static Date formationDate(String date) {
        try {
            SimpleDateFormat sf = getSimpleDateFormat("yyyy-MM-dd");
            return sf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到日期的年月日
     *
     * @param date
     * @return
     */
    public static int[] getDateYMD(String date) {
        Calendar cal = Calendar.getInstance();
        if (!TextUtils.isEmpty(date)) {
            cal.setTime(formationDate(date));
        }
        return new int[]{cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)};
    }

    /**
     * 得到时间的时分
     *
     * @param date
     * @return
     */
    public static int[] getTimeHm(String date) {
        Calendar cal = Calendar.getInstance();
        if (!TextUtils.isEmpty(date)) {
            cal.setTime(formationDate(date));
        }
        return new int[]{cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE)};
    }

    /**
     * 得到日期的月日
     *
     * @param date
     * @return
     */
    public static int[] getDateMD(String date) {
        Calendar cal = Calendar.getInstance();
        if (!TextUtils.isEmpty(date)) {
            cal.setTime(formationDate(date));
        }
        return new int[]{cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)};
    }

    /**
     * 比较两个时间前后
     * 开始时间在结束时间之前或相等返回 true 否则false
     * @param dateFlag
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean choiceTimeIsRight(int dateFlag, String startTime, String endTime) {
        if (dateFlag == 0 && "".equals(endTime)) {
            return true;
        }
        if (dateFlag != 0 && "".equals(startTime)) {
            return true;
        }
        SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            if (sdf.parse(startTime).before(sdf.parse(endTime)) || startTime.equals(endTime)) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
    /**
     * 比较两个时间前后
     *  startTime早于endTime返回1   startTime=endTime返回0   startTime晚于endTime 返回-1
     * @param startTime
     * @param endTime
     * @return
     */
    public static int choiceTime(String startTime, String endTime) {
        if (startTime.equals(endTime)){
            return 0;
        }
        SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd");
        try {

            if (sdf.parse(startTime).before(sdf.parse(endTime))) {
                return 1;
            }else {
                return -1;
            }
        } catch (Exception e) {
            return -1;
        }
    }
    /**
     * 比较两个时间前后
     *  startTime早于endTime返回1   startTime=endTime返回0   startTime晚于endTime 返回-1
     * @return
     */
    public static boolean isBetween(String startDate, String endDate, String date) {
        if (date.equals(startDate)||date.equals(endDate)){
            return true;
        }
        SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd");
        try {

            if (sdf.parse(startDate).before(sdf.parse(date))&&sdf.parse(date).before(sdf.parse(endDate))) {
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * @param addtime
     * @return
     */
    public static String getNewNowtime(Long addtime) {
        try {
            SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd");
            return sdf.format(new Date(addtime));
        } catch (Exception e) {

        }
        return "";
    }


    public static long getTimeLongforStr(String timestr) {
        try {
            SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(timestr).getTime();
        } catch (Exception e) {

        }
        return 0;
    }


    public static long getTimeLongforStrCHN(String timestr) {
        try {
            SimpleDateFormat sdf = getSimpleDateFormat("yyyy年MM月dd日 HH时mm分");
            return sdf.parse(timestr).getTime();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 判断时间是不是在当前时间之后
     *
     * @param time
     * @return
     */
    public static boolean timeBeforeNotime(String time) {
        try {
            SimpleDateFormat sdf = getSimpleDateFormat("yyyy年MM月dd日 HH时mm分");
            Date date = sdf.parse(time);
            if (date.after(new Date(System.currentTimeMillis()))) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    /**
     * 得到当前时间的前一天时间
     *
     * @param nowtime
     * @return
     */
    public static String getLastTime(String nowtime) {
        if ("".equals(nowtime)) {
            return "";
        }
        SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.format(new Date(sdf.parse(nowtime).getTime() - 24 * 60 * 60 * 1000));
        } catch (Exception e) {
        }
        return "";
    }


    public static String getTimeStanderd(Long time) {
        try {
            SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(new Date(time));
        } catch (Exception e) {

        }
        return "";
    }


    /**
     * 得到标准时间字符串
     *
     * @param time
     * @return
     */
    public static String getTimeforLong(long time) {
        try {
            SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(new Date(time));
        } catch (Exception e) {

        }
        return "";
    }

    /**
     * 得到当前时间的标准时间字符串
     *
     * @return
     */
    public static String getNowTime() {
        try {
            SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(new Date());
        } catch (Exception e) {

        }
        return "";
    }

    /**
     * 得到当前时间的标准时间字符串
     *
     * @return
     */
    public static String getNowTimeYMDhm() {
        try {
            SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd HH:mm");
            return sdf.format(new Date());
        } catch (Exception e) {

        }
        return "";
    }

    public static String getTimeforPicName(long time) {
        try {
            SimpleDateFormat sdf = getSimpleDateFormat("yyyyMMddHHmmss");
            return sdf.format(new Date(time));
        } catch (Exception e) {

        }
        return "";
    }

    /**
     * 得到当前日期
     *
     * @return
     */
    public static String getNowDateStr() {
        try {
            SimpleDateFormat sdf = getSimpleDateFormat("MM月dd日");
            return sdf.format(new Date(System.currentTimeMillis()));
        } catch (Exception e) {

        }
        return "";
    }

    /**
     * @return
     */
    public static String getNowDateYMD() {
        try {
            SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd");
            return sdf.format(new Date(System.currentTimeMillis()));
        } catch (Exception e) {

        }
        return "";

    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 获取某月有多少天
     *
     * @param year
     * @param month
     * @return
     */
    public static int getDayNumFromMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year); // 年
        c.set(Calendar.MONTH, month); // 月
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static int getMonthFristDayWeek(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year); // 年
        c.set(Calendar.MONTH, month); // 月
        c.set(Calendar.DAY_OF_MONTH, day); // 月
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String getStanderedDate(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year); // 年
        c.set(Calendar.MONTH, month); // 月
        c.set(Calendar.DAY_OF_MONTH, day); // 月
        SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(c.getTimeInMillis()));
    }

    /**
     * 得到上一个月有多少天
     *
     * @param year
     * @param month
     * @return
     */
    public static int getLastYearDayNumFromMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        if (month == 0) {
            c.set(Calendar.YEAR, year - 1); // 年
            c.set(Calendar.MONTH, 11); // 月
        } else {
            c.set(Calendar.YEAR, year); // 年
            c.set(Calendar.MONTH, month - 1); // 月
        }
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 得到当前时间的年份
     *
     * @return
     */
    public static int getNowYear() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        return c.get(Calendar.YEAR);
    }

    /**
     * 得到当前时间月份
     *
     * @return
     */
    public static int getNowMonth() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        return c.get(Calendar.MONTH);
    }

    /**
     * @param year
     * @param month
     * @return
     */
    public static String getStanderedDateYearMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year); // 年
        c.set(Calendar.MONTH, month); // 月
        SimpleDateFormat sdf = getSimpleDateFormat("yyyy年MM月");
        return sdf.format(new Date(c.getTimeInMillis()));
    }

    /**
     * 传给服务器时间
     *
     * @param year
     * @param month
     * @return
     */
    public static String getStanderedDateYearMonthForSer(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year); // 年
        c.set(Calendar.MONTH, month); // 月
        SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM");
        return sdf.format(new Date(c.getTimeInMillis()));
    }

    /**
     * @return
     */
    public static String getStanderedDateNow() {
        SimpleDateFormat sdf = getSimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(new Date(System.currentTimeMillis()));
    }


    /**
     * 获取当前日期是星期几<br>
     *
     * @return 当前日期是星期几
     */
    public static String getStanderedWeekNow() {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    /**
     * 当前时间
     *
     * @return
     */
    public static String getStanderedTimeNow() {
        SimpleDateFormat sdf = getSimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    /**
     * 获取月日
     *
     * @param time
     * @return
     */
    public static String getMMddTimeStr(String time) {
        try {
            SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf1 = getSimpleDateFormat("MM月dd");
            Date date = sdf.parse(time);
            return sdf1.format(date);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 获取月日
     *
     * @param time
     * @return
     */
    public static String getMM_ddTimeStr(String time) {
        try {
            SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf1 = getSimpleDateFormat("MM-dd");
            Date date = sdf.parse(time);
            return sdf1.format(date);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 获取月日 时分
     *
     * @param time
     * @return
     */
    public static String getMdhmTimeStr(String time) {
        try {
            SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf1 = getSimpleDateFormat("MM-dd HH:mm");
            Date date = sdf.parse(time);
            return sdf1.format(date);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * @param time
     * @return
     */
    public static String getTimeStrByDateFormat(String time, String dateFormat) {
        try {
            SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf1 = getSimpleDateFormat(dateFormat);
            Date date = sdf.parse(time);
            return sdf1.format(date);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 获取年-月-日 by 年月日
     *
     * @param time
     * @return
     */
    public static String getYMDDateStrByYmd(String time) {
        try {
            SimpleDateFormat sdf = getSimpleDateFormat("yyyyMMdd");
            SimpleDateFormat sdf1 = getSimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(time);
            return sdf1.format(date);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 获取月日 时分
     *
     * @param time
     * @return
     */
    public static String getYMdhmTimeStr(String time) {
        try {
            SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf1 = getSimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = sdf.parse(time);
            return sdf1.format(date);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 获取ymd
     *
     * @param time
     * @return
     */
    public static String getYMDTimeStr(String time) {
        try {
            SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf1 = getSimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(time);
            return sdf1.format(date);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 获取 时分秒
     *
     * @param time
     * @return
     */
    public static String getHMSTimeStr(String time) {
        try {
            SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf1 = getSimpleDateFormat("HH:mm:ss");
            Date date = sdf.parse(time);
            return sdf1.format(date);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 获取月日 时分
     *
     * @param time
     * @return
     */
    public static String getYMDHMTimeStr(String time) {
        try {
            SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf1 = getSimpleDateFormat("yyMMddHHmm");
            Date date = sdf.parse(time);
            return sdf1.format(date);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 通过时间获取星期几
     *
     * @param time
     * @return
     */
    public static String getWeekStr(String time) {
        try {
            SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(time);
            return getWeekOfDate(date);
        } catch (Exception e) {
        }
        return "";
    }

    public static String getNowDateAndWeek() {
        try {
            SimpleDateFormat sdf = getSimpleDateFormat("yyyy年MM月dd");
            Date curDate = new Date(System.currentTimeMillis());
            String ymd = sdf.format(curDate);
            String week = getWeekOfDate(curDate);
            return ymd + "  " + week;
        } catch (Exception e) {

        }
        return "";
    }

    /**
     * 得到当前时间的标准时间字符串
     * yyyyMMddHHmmss
     *
     * @return
     */
    public static String getNowTimeStr() {
        try {
            SimpleDateFormat sdf = getSimpleDateFormat("yyyyMMddHHmmss");
            return sdf.format(new Date());
        } catch (Exception e) {

        }
        return "";
    }

    /**
     * 判断time 是否是1天前
     *
     * @return
     */
    public static boolean isOneDayBefore(String time) {
        try {
            SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(time);
            Date nowDate = new Date();
            long dayMin = 24 * 60 * 60 * 1000;
            if (nowDate.getTime() - date.getTime() >= dayMin) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
        }
        return true;
    }

    /**
     * 获取hour小时前的时间
     *
     * @return
     */
    public static String getHourBeforTime(int hour) {
        try {
            Date nowDate = new Date();
            long sexHour = hour * 60 * 60 * 1000;
            long sexHourBefor = nowDate.getTime() - sexHour;
            SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(new Date(sexHourBefor));
        } catch (Exception e) {
        }
        return "";
    }
    /**
     * 获取minute分钟前的时间
     *
     * @return
     */
    public static String getMinuteBeforTime(int minute) {
        try {
            Date nowDate = new Date();
            long sexHour =minute * 60 * 1000;
            long sexHourBefor = nowDate.getTime() - sexHour;
            SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(new Date(sexHourBefor));
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 判断time 是否是1小时内
     *
     * @return
     */
    public static boolean isInOneHour(String time, int hour) {
        try {
            SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(time);
            Date nowDate = new Date();
            long hourMin = hour *60 * 60 * 1000;
            if (nowDate.getTime() - date.getTime() < hourMin) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
        }
        return true;
    }

    /**
     * 判断time是否是一小时以后
     *
     * @return int 0预到时间在一小时之前；1预到时间在一小时之间； 2 预到时间小于当前时间
     */
    public static int isInHour(int hour, String arriveTime) {
        try {
            SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(arriveTime);
            Date nowDate = new Date();
            long hourMin = hour * 60 * 60 * 1000;
            //预到时间减-当前时间
            long cha = date.getTime() - nowDate.getTime();
            if (cha >= 0 && cha < hourMin) {
                //相差时间大于0 小于hour
                return 1;
            } else if (cha < 0) {
                //相差时间小于0
                return 2;
            } else if (cha > hour) {
                return 0;
            }

        } catch (Exception e) {
        }
        return 0;
    }

    public static String getMonthFirstDay(String time){
        SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd");
        //获取当前月第一天：
        Calendar calstr = Calendar.getInstance();
        calstr.setTime(formationDate(time));
        //本月
        calstr.add(Calendar.MONTH, 0);
        //设置为1号为本月第一天
        calstr.set(Calendar.DAY_OF_MONTH,1);
        String first = sdf.format(calstr.getTime());
        return first;
    }
    public static String getMonthLastDay(String time){
        SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd");
        //获取当前月第一天：
        Calendar calstr = Calendar.getInstance();
        calstr.setTime(formationDate(time));
        //本月
        calstr.add(Calendar.MONTH, 0);
        //设置当月为最后一天
        calstr.set(Calendar.DAY_OF_MONTH, calstr.getActualMaximum(Calendar.DAY_OF_MONTH));
        String first = sdf.format(calstr.getTime());
        return first;
    }

    /**
     * 获取离店时间
     *
     * @param checkInDate  入住日期
     * @param checkOutTime 最晚离开时间
     * @param checkInDay   入住天数
     * @return
     */
    public static String getLeaveTime(String checkInDate, String checkOutTime, int checkInDay) {

        try {
            SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(checkInDate);
            long leaveTime = date.getTime() + checkInDay * 24 * 60 * 60 * 1000;
            String leaveTimeStr = sdf.format(new Date(leaveTime)) + " " + checkOutTime;
            return leaveTimeStr;
        } catch (Exception e) {
        }
        return "";
    }
    /**
     * 获取离店时间
     *
     * @param checkInDate  入住日期
     * @param checkInDay   入住天数
     * @return
     */
    public static String getLeaveDate(String checkInDate, int checkInDay) {
        try {
            return getNextDayTime(checkInDate,checkInDay);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 得到当前时间的后x天时间
     *
     * @param nowtime
     * @return
     */
    public static String getNextDayTime(String nowtime, int day) {
        if ("".equals(nowtime)) {
            return "";
        }
        if (day == 0){
            return nowtime;
        }
        SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd");
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(formationDate(nowtime));
            c.set(Calendar.DATE,c.get(Calendar.DATE)+day);
            return sdf.format(c.getTime());
        } catch (Exception e) {
        }
        return "";
    }

    /**
     *  获取两个时间间隔
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getTwoDateInterval(String startDate, String endDate) {
            if (startDate.equals(endDate)){
                return 1;
            }
        try {
            Date a1 = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            Date b1 = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
            //获取相减后天数
            long day = (b1.getTime() - a1.getTime()) / (24 * 60 * 60 * 1000);
            return (int) day;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

package com.phycholee.blog.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by PhychoLee on 2016/11/14 16:14.
 * Description: 时间工具类
 */
public class TimeUtil {
    private static final Calendar CALENDAR =  Calendar.getInstance();

    /**
     * 获取当前时间的年月日，如：20161114
     * @return
     */
    public static String getYearMonthDay(){
        CALENDAR.setTime(new Date());
        int year = CALENDAR.get(Calendar.YEAR);
        int month = CALENDAR.get(Calendar.MONTH)+1;
        int day = CALENDAR.get(Calendar.DAY_OF_MONTH);
        return year+""+month+""+day;
    }

}

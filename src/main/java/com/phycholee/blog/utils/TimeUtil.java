package com.phycholee.blog.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by PhychoLee on 2016/11/14 16:14.
 * Description: 时间工具类
 */
public class TimeUtil {
    private static final Calendar CALENDAR =  Calendar.getInstance();

    /**
     * 获取当前时间的年月，如：201611
     * @return
     */
    public static String getYearMonthDay(){
        CALENDAR.setTime(new Date());
        int year = CALENDAR.get(Calendar.YEAR);
        int month = CALENDAR.get(Calendar.MONTH)+1;
        return year+""+month;
    }

    public static String getDateTime(){
        CALENDAR.setTime(new Date());
        return formatDate(CALENDAR.getTime());
    }


    private static String formatDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return simpleDateFormat.format(date);
    }

}

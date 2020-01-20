package com.eex.common.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ADate {

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    public static Date getNowDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return date;
    }

    public static String getDateToString(String pattern) {
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }


}

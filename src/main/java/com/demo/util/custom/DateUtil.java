package com.demo.util.custom;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by shi.lingfeng on 2018/3/30.
 */
public class DateUtil {
    /*
	 * 获取当前日期
	 * pattern: yyyy-MM-dd HH:mm:ss
	 * pattern模式，dayInterval 日期间隔
	 */
    public String getDay(String pattern,String dayInterval) throws IOException, Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, Integer.parseInt(dayInterval));
        String str = new SimpleDateFormat(pattern).format(calendar.getTime());
//        System.out.println(str);
        return str;
    }

    public String getHour(String pattern,String hourInterval) throws IOException, Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, Integer.parseInt(hourInterval));
        String str = new SimpleDateFormat(pattern).format(calendar.getTime());
//        System.out.println(str);
        return str;
    }

    public static void main(String[] args) throws  Exception {
//        System.out.println(new DateUtil().getDay("yyyy-MM-dd HH:mm:ss","1"));
        System.out.println(new DateUtil().getHour("HH","8"));
    }

}

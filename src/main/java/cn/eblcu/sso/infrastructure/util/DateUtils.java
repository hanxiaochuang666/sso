package cn.eblcu.sso.infrastructure.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @ClassName DateUtils
 * @Author 焦冬冬
 * @Date 2019/3/25 16:58
 **/
public class DateUtils {
    private DateUtils() {}

    public static Calendar calendar = new GregorianCalendar();
    /**
     * @Author 焦冬冬
     * @Description 时间计算器
     * @Date 17:02 2019/3/25
     * @Param 
     * @return 
     **/
    public static Date compute(Date date,int field, int amount){
        calendar.setTime(date);
        calendar.add(field,amount);
        return calendar.getTime();
    }
    
    /**
     * @Author 焦冬冬
     * @Description 字符串转日期
     * @Date 10:20 2019/3/26
     * @Param 
     * @return 
     **/
    public static Date string2Date(String dateStr,String format)throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(dateStr);
    }
    
    /**
     * @Author 焦冬冬
     * @Description 日期转字符串
     * @Date 9:32 2019/3/27
     * @Param 
     * @return 
     **/
    public static String date2String(Date date,String format)throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
}

package cn.eblcu.sso.ui.config;

import cn.eblcu.sso.infrastructure.util.DateUtils;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * @ClassName MyLoggerFilter
 * @Author 焦冬冬
 * @Date 2019/5/10 16:48
 * 过滤日志，实现不同的类按日期生成不同的日志
 **/
@Component
public class MyLoggerFilter extends Filter {


    /**
     * 过滤包路径
     */
    private String packageLogerPath;
    /**
     * 日志基础路径
     */
    private String filterPackagePath;
    @Override
    public int decide(LoggingEvent event) {
        //1.获取包路径,判断是否属于
        //在过滤其中实现日志文件分类打包
        //1.获取包路径名:
        String loggerPath=event.getLogger().getName();
        if(loggerPath.contains(filterPackagePath)){
            //拆解日志并输出
            String levelStr=event.getLevel().toString();
            long timeStamp = event.getTimeStamp();
            Date date = new Date(timeStamp);
            String message = event.getMessage().toString();
            int lastIndexOf = loggerPath.lastIndexOf(".");
            String dirBefore = loggerPath.substring(lastIndexOf+1);
            File dir = new File(packageLogerPath+"/"+dirBefore);
            //如果对应的类路径不存在，则创建路径
            if(!dir.exists()){
                dir.mkdir();
            }
            String dirEnd=null;
            try {
                dirEnd=DateUtils.date2String(new Date(),"yyy-MM-dd");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dir=new File(packageLogerPath+"/"+dirBefore+"/"+dirEnd+".txt");
            BufferedWriter output=null;
            if(!dir.exists()){
                try {
                    dir.createNewFile();
                    output = new BufferedWriter(new FileWriter(dir,false));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else{
                try {
                    output = new BufferedWriter(new FileWriter(dir,true));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String date2String="";
            try {
                date2String = DateUtils.date2String(date, "yyyy-MM-dd HH:mm:ss");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String res=date2String+"["+dirBefore+"]"+" "+levelStr+" : "+message+"\r\n";
            try {
                output.write(res);
                output.flush();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public String getPackageLogerPath() {
        return packageLogerPath;
    }

    public void setPackageLogerPath(String packageLogerPath) {
        this.packageLogerPath = packageLogerPath;
    }

    public String getFilterPackagePath() {
        return filterPackagePath;
    }

    public void setFilterPackagePath(String filterPackagePath) {
        this.filterPackagePath = filterPackagePath;
    }

}

package cn.eblcu.sso.ui.model;

import java.io.Serializable;

public class BaseModle<T> implements Serializable  {

    private boolean success;
    private String code;
    private String describe;
    private long  total;
    private T data;

    private BaseModle(T t){
        this.data=t;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    /**
     * 封装成功处理函数
     * @param t
     * @return
     */

    public  static BaseModle getSuccessData(Object t){
        BaseModle objectBaseModle = new BaseModle(t);
        objectBaseModle.setCode("001");
        objectBaseModle.setSuccess(true);
        objectBaseModle.setDescribe("操作成功");
        return objectBaseModle;
    }
    /**
     * 封装成功处理函数
     * @param t
     * @return
     */

    public  static BaseModle getSuccessData(Object t,long total){
        BaseModle objectBaseModle = new BaseModle(t);
        objectBaseModle.setTotal(total);
        objectBaseModle.setCode("001");
        objectBaseModle.setSuccess(true);
        objectBaseModle.setDescribe("操作成功");
        return objectBaseModle;
    }
    
    /**
     * @Author 焦冬冬
     * @Description  封装成功处理函数
     * @Date 16:30 2019/3/22
     * @Param 
     * @return 
     **/
    public  static BaseModle getSuccessData(){
        BaseModle objectBaseModle = new BaseModle(null);
        objectBaseModle.setCode("001");
        objectBaseModle.setSuccess(true);
        objectBaseModle.setDescribe("操作成功");
        return objectBaseModle;
    }
    
    /**
     * @Author 焦冬冬
     * @Description 失败处理
     * @Date 16:30 2019/3/22
     * @Param 
     * @return 
     **/
    public static BaseModle getFailData(String code,String describe){
        BaseModle objectBaseModle = new BaseModle(null);
        objectBaseModle.setCode(code);
        objectBaseModle.setSuccess(false);
        objectBaseModle.setDescribe(describe);
        return  objectBaseModle;
    }
}

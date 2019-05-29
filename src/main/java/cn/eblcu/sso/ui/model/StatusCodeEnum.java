package cn.eblcu.sso.ui.model;

public enum StatusCodeEnum {

    SUCCESS("000","成功"),
    PARAM_ERROR("001","参数错误"),
    NO_AUTHORITY("002","无访问权限"),
    INVALID_DATA("003","非法数据"),
    BUSINESS_ERROR("004","业务逻辑错误"),
    NO_VALID_NEWS("005","暂无新闻"),
    ;

    private StatusCodeEnum(String code, String describe){
        this.code=code;
        this.describe=describe;
    }
    private String code;
    private String describe;

    public String getCode() {
        return code;
    }

    public String getDescribe(){
        return describe;
    }

}

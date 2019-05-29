package cn.eblcu.sso.client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @ClassName BlackListPropConfig
 * @Author 焦冬冬
 * @Date 2019/5/28 9:35
 **/
@Component
@ConfigurationProperties(prefix = "com")
@PropertySource(value = "classpath:black-list.properties")
public class BlackListPropConfig {
    /**
     * html 黑名单str
     */
    private String htmlList;
    /**
     * aoi  黑名单str
     */
    private String apiList;
    /**
     * 单点登录检验token的URL
     */
    private String ssoUrl;

    public String getSsoUrl() {
        return ssoUrl;
    }

    public void setSsoUrl(String ssoUrl) {
        this.ssoUrl = ssoUrl;
    }

    public String getHtmlList() {
        return htmlList;
    }

    public void setHtmlList(String htmlList) {
        this.htmlList = htmlList;
    }

    public String getApiList() {
        return apiList;
    }

    public void setApiList(String apiList) {
        this.apiList = apiList;
    }
}

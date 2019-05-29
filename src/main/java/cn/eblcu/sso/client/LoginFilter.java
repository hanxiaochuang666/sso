package cn.eblcu.sso.client;

import cn.eblcu.sso.infrastructure.util.HttpReqUtil;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName LoginFilter
 * @Author 焦冬冬
 * @Date 2019/5/27 17:53
 **/
@WebFilter(filterName = "loginFilter",urlPatterns="/*")
public class LoginFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(LoginFilter.class);
    //htmlList
    private static List<String> HTML_LIST=new ArrayList<>();
    //apiList
    private static List<String> API_LIST=new ArrayList<>();
    @Autowired
    private BlackListPropConfig blackListPropConfig;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //容器启动时，解析黑名单数据
        String htmlList = blackListPropConfig.getHtmlList();
        String[] split = htmlList.split(";");
        for (String s : split) {
            HTML_LIST.add(s);
        }
        String apiList = blackListPropConfig.getApiList();
        split=apiList.split(";");
        for (String s : split) {
            API_LIST.add(s);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest HttpServletRequest=(HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse=(HttpServletResponse)servletResponse;
        boolean isDoFilter=true;
        boolean isValid=false;
        String requestURI = HttpServletRequest.getRequestURI();
        for (String s : HTML_LIST) {
            if(requestURI.contains(s)) {
                isValid=true;
                break;
            }
        }
        for (String s : API_LIST) {
            if(requestURI.contains(s)) {
                isValid=true;
                break;
            }
        }
        if(isValid){
            String token = HttpServletRequest.getParameter("token");
            if(token==null) {
                //2表示没有传token
                validFail(httpServletResponse,2);
                return ;
            }
            String format = String.format(blackListPropConfig.getSsoUrl(), token);
            try {
                HttpResponse httpResponse = HttpReqUtil.getObjectReq( format , null ) ;
                Map<String,Object> responsemap = HttpReqUtil.parseHttpResponse( httpResponse ) ;
                String isPassedStr = responsemap.get("isPassed").toString();
                int i = Integer.valueOf(isPassedStr).intValue();
                /**
                 * 返回0表示验证通过,1表示不通过
                 */
                if(i==1)
                    isDoFilter=false;
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("请求验证token失败");
                //3表示sso平台故障
                validFail(httpServletResponse,3);
            }
        }
        if(!isDoFilter){
            validFail(httpServletResponse,1);
        }else{
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    /**
     * 登陆验证失败
     * @param httpServletResponse
     */
    private void validFail(HttpServletResponse httpServletResponse,int falg){
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        PrintWriter writer=null;
        try {
            writer = httpServletResponse.getWriter();
            //防止与一些业务数据冲突
            writer.append("{'ssoValid':11111}");
        }catch (Exception e){
            logger.info("回写数据失败");
        }finally {
            writer.close();
        }
    }
    @Override
    public void destroy() {

    }
}

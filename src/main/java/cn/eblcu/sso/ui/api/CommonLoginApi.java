package cn.eblcu.sso.ui.api;

import cn.eblcu.sso.domain.service.IQqService;
import cn.eblcu.sso.domain.service.IWeChatService;
import cn.eblcu.sso.domain.service.IWeiboService;
import cn.eblcu.sso.domain.service.UserService;
import cn.eblcu.sso.ui.model.BaseModle;
import cn.eblcu.sso.ui.model.UserInfoApiModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/login")
@Api(tags = "登录验证接口Api", description = "包含微信,QQ,手机号,邮箱,用户名密码登录")
@Slf4j
public class CommonLoginApi {

    @Autowired
    private IWeiboService weiboService;

    @Autowired
    private IQqService qqService;

    @Autowired
    private IWeChatService weChatService;
    @Autowired
    private UserService userService;

    @GetMapping(value = "/getWeChatCheckUrl", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "获取微信的登录跳转地址")
    public String getWeChatCheckUrl(){
        return weChatService.getUrl();
    }


    @GetMapping(value = "/getWeiboCheckUrl", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "获取微博的登录跳转地址")
    public String getWeiboCheckUrl() throws Exception{
        return weiboService.getUrl();
    }

    @GetMapping(value = "/getQqCheckUrl", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "获取QQ的登录跳转地址")
    public String getQqCheckUrl() {
        return qqService.getUrl();
    }

    @GetMapping(value = "/getValidState", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "获取第三方state校验码")
    public Map<String, String> getValidState() {
        return qqService.getValidState();
    }

    @GetMapping(value = "/callBack", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "第三方登录")
    public BaseModle callBack(@RequestParam("state") String state,
                              @RequestParam("code") String code,
                              @ApiParam(name = "callType", value = "登录方式 1：微博 2：QQ 3：微信", required = true) @RequestParam("callType") String callType) throws Exception {

        if ("1".equals(callType)) {// 微博
            return BaseModle.getSuccessData(weiboService.weiboCallBack(code, state));
        }

        if ("2".equals(callType)) {// QQ
            return BaseModle.getSuccessData(qqService.qqCallBack(code, state));
        }

        if ("3".equals(callType)) {// 微信
            return BaseModle.getSuccessData(weChatService.landCallBack(code,state));
        }
        return BaseModle.getSuccessData();
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "账号登录")
    public BaseModle login(@RequestParam(value = "userInfoApiModel") UserInfoApiModel userInfoApiModel) throws Exception {

        return BaseModle.getSuccessData(userService.login(userInfoApiModel));
    }

}

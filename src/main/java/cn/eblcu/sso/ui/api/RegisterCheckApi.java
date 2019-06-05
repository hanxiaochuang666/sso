package cn.eblcu.sso.ui.api;

import cn.eblcu.sso.domain.service.IRegisterCheckService;
import cn.eblcu.sso.infrastructure.util.StringUtils;
import cn.eblcu.sso.persistence.entity.dto.User;
import cn.eblcu.sso.ui.model.BaseModle;
import cn.eblcu.sso.ui.model.StatusCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/register")
@Api(tags = "验证码API" , value = "验证码发送接口",description = "验证码发送接口")
public class RegisterCheckApi{
    private Logger logger = LoggerFactory.getLogger(RegisterCheckApi.class);

    @Resource(name="registerCheckService")
    private IRegisterCheckService registerCheckService;


    @ApiOperation(value = "发送验证请求", notes = "发送验证请求",httpMethod = "GET")
    @RequestMapping(value = "/sendCheckInfo", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    @ResponseBody
    public BaseModle sendCheckInfo(@RequestParam Map<String,Object> param, User user, HttpServletRequest request){
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");

        try
        {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null)
            {
                sb.append(str);
            }
            br.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != br)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        logger.info(sb.toString());
        //1.检验参数
        if(StringUtils.isEmpty(param.get("type"))) {
            logger.info("请求类型不能为空");
            return BaseModle.getFailData(StatusCodeEnum.PARAM_ERROR.getCode(),"请求类型不能为空");
        }
        if(StringUtils.isEmpty(param.get("account"))){
            logger.info("请求账号不能为空");
            return BaseModle.getFailData(StatusCodeEnum.PARAM_ERROR.getCode(),"请求账号不能为空");
        }
        //2.发送验证码
        int type = Integer.valueOf(param.get("type").toString()).intValue();
        if(1==type){
            //手机注册，暂未实现
        }else if(2==type){
            //邮箱注册
            return registerCheckService.sendEmailInfo(param.get("account").toString());
        }
        return BaseModle.getSuccessData();
    }

}

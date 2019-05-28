package cn.eblcu.sso.ui.api;

import cn.eblcu.sso.domain.service.RedisUtils;
import cn.eblcu.sso.persistence.entity.dto.User;
import cn.eblcu.sso.domain.service.UserService;
import cn.eblcu.sso.ui.model.UserApiModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Api(tags = "用户信息维护相关接口" , value = "用户接口",description = "用户信息维护相关接口")
@RequestMapping("/user")
@Slf4j
public class UserApi {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation(value = "获取用户列表",notes="获取用户列表")
    @RequestMapping(value="/list",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<User> index(){
        return userService.getUserList();
    }

    @ApiOperation(value = "根据条件查询用户",notes="根据条件查询用户")
    @RequestMapping(value="/queryUserInfo",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User queryUserInfo(@RequestBody User user){

        return userService.getUser(user);
    }

    @ApiOperation(value = "删除", notes = "删除")
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String delete(@PathVariable("id") Integer id){
        userService.deleteUser(id);
        return "你已经删掉id为"+id+"的用户";
    }

    @ApiOperation(value = "用户注册", notes = "新增用户")
    @RequestMapping(value = "/registerUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map registerUser(@RequestBody UserApiModel userApiModel){

        log.info("用户注册信息：~~~~~~~~~~~~~~" + userApiModel.toString());
        Map<String,Object> retMap = new HashMap<>();
        retMap.put("successCode","0");
        retMap.put("msg","注册成功");
        retMap.put("errMsg","");
        retMap.put("data","");
        try {
            retMap.put("data",userService.addUser(userApiModel));
        } catch (Exception e) {
            retMap.put("successCode","1");
            retMap.put("msg","注册失败");
            retMap.put("errMsg",e.getMessage());
        }
        return retMap;
    }

    @ApiOperation(value = "验证用户token信息", notes = "验证用户token信息")
    @RequestMapping(value = "/checkToken/{token}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map checkToken(@ApiParam(value = "token",required = true)  @PathVariable("token") String token ){

        log.info("传入的token==================="+token);
        Map<String,Object> retMap = new HashMap<>();
        if(redisUtils.isexists(token)){
            retMap.put("isPassed","0");
        }else {
            retMap.put("isPassed","1");
        }
        return retMap;
    }
}

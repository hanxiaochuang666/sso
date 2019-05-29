package cn.eblcu.sso.ui.api;

import cn.eblcu.sso.domain.service.impl.RedisUtils;
import cn.eblcu.sso.infrastructure.util.FileUtils;
import cn.eblcu.sso.persistence.entity.dto.User;
import cn.eblcu.sso.domain.service.UserService;
import cn.eblcu.sso.persistence.entity.dto.UserInfo;
import cn.eblcu.sso.ui.model.UserInfoApiModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Api(tags = "用户信息维护API" , value = "用户接口",description = "用户信息维护相关接口")
@RequestMapping("/user")
@Slf4j
public class UserApi {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation(value = "获取用户列表",notes="获取用户列表")
    @RequestMapping(value="/getUserList",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<User> getUserList(){
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
    public Map registerUser(@RequestBody UserInfoApiModel userApiModel){

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
            retMap.put("isPassed","-1");
        }
        return retMap;
    }

    @ApiOperation(value = "头像上传接口", notes = "头像上传接口")
    @RequestMapping(value="/uploadFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String uploadFile(@RequestParam("fileName") MultipartFile file,@RequestParam("userId") int userId) {

        // 获取文件名
        String fileName = file.getOriginalFilename();
        fileName = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()) + "_" + fileName;
        log.info("文件名称=========："+fileName);
        String url = "";
        try {
            url = FileUtils.upLoadFile(file,fileName);
            // 将头像链接放到数据库中
            UserInfo userInfo = new UserInfo();
            userInfo.setUserid(userId);
            userInfo.setHeadphoto(url);
            userService.editUserInfo(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    @ApiOperation(value = "用户信息完善接口", notes = "用户信息完善接口，包括第三方调用回来的用户信息")
    @RequestMapping(value = "/editUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map editUser(@RequestBody @Validated User user){

        log.info("用户信息完善接口入参：~~~~~~~~~~~~~~" + user.toString());
        Map<String,Object> retMap = new HashMap<>();
        retMap.put("successCode","0");
        retMap.put("msg","成功！");
        retMap.put("errMsg","");
        retMap.put("data","");
        if(StringUtils.isEmpty(user.getId())){
            retMap.put("successCode","-1");
            retMap.put("msg","失败！");
            retMap.put("errMsg","用户id不能为空！");
        }
        try {
            userService.editUser(user);
        } catch (Exception e) {
            retMap.put("successCode","-1");
            retMap.put("msg","失败！");
            retMap.put("errMsg",e.getMessage());
        }
        return retMap;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String argumentNotValidException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        return bindingResult.getFieldError().getDefaultMessage();
    }

    @ApiOperation(value = "绑定接口", notes = "用来绑定手机号或者邮箱")
    @RequestMapping(value = "/bindUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map bindUser(@RequestBody @Validated UserInfoApiModel user){

        log.info("用户信息绑定接口入参：~~~~~~~~~~~~~~" + user.toString());
        Map<String,Object> retMap = new HashMap<>();
        retMap.put("successCode","0");
        retMap.put("msg","成功！");
        retMap.put("errMsg","");
        retMap.put("data","");
        if(StringUtils.isEmpty(user.getUserId())){
            retMap.put("successCode","-1");
            retMap.put("msg","失败！");
            retMap.put("errMsg","用户id不能为空！");
        }
        try {
            userService.bindUser(user);
        } catch (Exception e) {
            retMap.put("successCode","-1");
            retMap.put("msg","失败！");
            retMap.put("errMsg",e.getMessage());
        }
        return retMap;
    }
}

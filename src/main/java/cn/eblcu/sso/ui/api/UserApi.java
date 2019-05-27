package cn.eblcu.sso.ui.api;

import cn.eblcu.sso.persistence.entity.dto.User;
import cn.eblcu.sso.domain.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@Api(value = "用户接口")
@RequestMapping("/user")
public class UserApi {
    @Autowired
    private UserService userService;
    @Autowired
    private User user;

    @ApiOperation(value = "获取用户列表",notes="获取用户列表")
    @RequestMapping(value="/list",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<User> index(){
        return userService.getUser();
    }

    @ApiOperation(value = "删除", notes = "删除")
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String delete(@PathVariable("id") Integer id){
        userService.deleteUser(id);
        return "你已经删掉id为"+id+"的用户";
    }

    @ApiOperation(value = "新增", notes = "新增用户")
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String addUser(){
//        user.setAge(33);
//        user.setUsername("阿成");
        userService.addUser(user);
        return "增加用户成功";
    }
}

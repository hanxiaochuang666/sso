package cn.eblcu.sso.ui.api;


import cn.eblcu.sso.domain.service.impl.RedisUtils;
import cn.eblcu.sso.infrastructure.util.RsaUtils;
import cn.eblcu.sso.infrastructure.util.SupperTokenUtils;
import cn.eblcu.sso.infrastructure.util.TokenUtils;
import cn.eblcu.sso.persistence.entity.dto.User;
import cn.eblcu.sso.persistence.entity.dto.UserInfo;
import cn.eblcu.sso.ui.model.UserInfoApiModel;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class UserApiTest {


    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @Autowired
    private RedisUtils redisUtils;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void delete() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void registerUser() throws Exception {

        // 2、然后输入验证码进行注册
        UserInfoApiModel userApiModel = new UserInfoApiModel();
        userApiModel.setEmail("623233529@qq.com");
        userApiModel.setPassword("123123");
        userApiModel.setRegisterType(2);// 邮箱注册
        userApiModel.setCode("E3r4t5");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/registerUser")
                .content(JSONObject.toJSONString(userApiModel))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void checkToken() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/checkToken/redisKey")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void uploadFile() throws Exception {

        File file = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\baxian.jpg");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "baxian.jpg", MediaType.TEXT_PLAIN_VALUE,
                new BufferedInputStream(new FileInputStream(file)));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/user/uploadFile")
                .file(mockMultipartFile)
                .param("userId","1")
//                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void editUser() throws Exception {
        User user = new User();
        user.setId(1);
        user.setLoginname("hhh");
        UserInfo userInfo = new UserInfo();
        userInfo.setUserid(1);
        userInfo.setCityid("123");
        userInfo.setNickname("acl");
        user.setUserinfo(userInfo);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/editUser")
                .content(JSONObject.toJSONString(user))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void bindUser() throws Exception {

        redisUtils.setStr("623233529@qq.com","CUIRLd");

        UserInfoApiModel user = new UserInfoApiModel();
        user.setEmail("623233529@qq.com");
        user.setLoginname("hanchuang");
        user.setCode("CUIRLd");// 绑定邮箱需要输入邮箱的验证码
        user.setBindType(2);// 绑定类型为邮箱
        user.setUserId(1);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/bindUser")
                .content(JSONObject.toJSONString(user))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void login() throws Exception{

        JSONObject JObject=new JSONObject();
        JObject.put("loginName","hanchuang");
        JObject.put("password","123456");
        JObject.put("loginType","2");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/login/login")
                .content(JSONObject.toJSONString(JObject))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }
}
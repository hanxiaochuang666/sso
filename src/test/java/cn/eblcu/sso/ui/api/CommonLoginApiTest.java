package cn.eblcu.sso.ui.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class CommonLoginApiTest {


    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();//建议使用这种
    }

    @Test
    public void getWeChatCheckUrl() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/login/getWeChatCheckUrl")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(200, status);
        System.out.println("返回参数:" + content);
    }

    @Test
    public void getWeiboCheckUrl() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/login/getWeiboCheckUrl")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(200, status);
        System.out.println("返回参数:" + content);
    }

    @Test
    public void getQqCheckUrl() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login/getQqCheckUrl")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    public void getValidState() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/login/getValidState")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(200, status);
        System.out.println("返回参数:" + content);
    }

    @Test
    public void callBack() throws Exception {

//        MultiValueMap<String,String> valueMap = new LinkedMultiValueMap<>();
//        valueMap.add("state", "123");
//        valueMap.add("code", "456");
//        valueMap.add("callType", "1");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/login/callBack")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
//                .params(valueMap)
                .param("state", "123")
                .param("code", "456")
                .param("callType", "1")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(200, status);
        System.out.println("返回参数:" + content);
    }


}
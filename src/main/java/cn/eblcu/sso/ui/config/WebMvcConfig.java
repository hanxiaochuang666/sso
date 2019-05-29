package cn.eblcu.sso.ui.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


/**
  * @Author hanchuang
  * @Version 1.0
  * @Date add on 2019/5/29
  * @Description   文件上传配置类
  */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /**
     * 在配置文件中配置的文件保存路径
     */
    @Value("${file.imagePath}")
    private String mImagesPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (mImagesPath.equals("") || mImagesPath.equals("${file.imagePath}")) {
            String imagesPath = WebMvcConfig.class.getClassLoader().getResource("").getPath();
            System.out.print("1.上传配置类imagesPath==" + imagesPath + "\n");
            if (imagesPath.indexOf(".jar") > 0) {
                imagesPath = imagesPath.substring(0, imagesPath.indexOf(".jar"));
            } else if (imagesPath.indexOf("classes") > 0) {
                imagesPath = "file:" + imagesPath.substring(0, imagesPath.indexOf("classes"));
            }
            imagesPath = imagesPath.substring(0, imagesPath.lastIndexOf("/")) + "/images/";
            mImagesPath = imagesPath;
        }
        // 注册头像
        registry.addResourceHandler("/images/**").addResourceLocations(mImagesPath);
        System.out.print("上传配置类mImagesPath==" + mImagesPath + "\n");
        // 注册swagger
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

}

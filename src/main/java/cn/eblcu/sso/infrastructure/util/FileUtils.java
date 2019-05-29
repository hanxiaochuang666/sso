package cn.eblcu.sso.infrastructure.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @desc
 * @Author：hanchuang
 * @Version 1.0
 * @Date：add on 9:44 2019/5/29
 */
@Slf4j
public class FileUtils {

    @Value("${file.imagePath}")
    private static String fileUrl;

    @Value("${localUrl}")
    private static String localUrl;

    /**
     * @Author hanchuang
     * @Version 1.0
     * @Date add on 2019/5/29
     * @Description 文件上传
     */
    public static String upLoadFile(MultipartFile file, String fileName) throws Exception {

        String filePath = fileUrl + fileName;
        File targetFile = new File(filePath);
        if (targetFile.exists()) {
            throw new Exception("文件已存在！");
        }
        //判断文件父目录是否存在
        if (null != targetFile.getParentFile() && !targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        String url = "";
        try {
            //上传文件
            file.transferTo(targetFile);
            System.out.print("保存文件路径==============" + filePath + "\n");
            //url="http://域名/项目名/images/"+fileName;//正式项目
//            url = "http://localhost:8080/images/"+fileName;//本地运行项目
            url = localUrl + "/images/" + fileName;//本地运行项目
            log.info("文件地址url====" + url + "\n");
        } catch (IOException e) {
            throw new Exception(e);
        }
        return url;
    }
}

package cn.eblcu.sso.infrastructure.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Slf4j
public class FileUtils {

    @Value("${file.imagePath}")
    private static String fileUrl;

    @Value("${localUrl}")
    private static String localUrl;

    public static String upLoadFile(MultipartFile file, String fileName) throws Exception {

        String filePath = fileUrl + fileName;
        File targetFile = new File(filePath);
        //判断文件父目录是否存在
        if (null != targetFile.getParentFile() && !targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        String url = "";
        try {
            //上传文件
            file.transferTo(targetFile);
            log.info("文件绝对路径==============" + filePath + "\n");
//            url="http://域名/项目名/images/"+fileName;//正式项目
//            url = "http://localhost:8080/images/"+fileName;//本地运行项目
            url = localUrl + "/images/" + fileName;//本地运行项目
            log.info("文件对外暴露的虚拟路径=============" + url + "\n");
        } catch (IOException e) {
            throw new Exception(e);
        }
        return url;
    }

    /* TXT文件读取*/
    public static String readTxt(String filePath)throws Exception{
        File filename = new File(filePath);
        InputStreamReader reader = new InputStreamReader(
                new FileInputStream(filename)); // 建立一个输入流对象reader
        BufferedReader br = new BufferedReader(reader);
        StringBuilder res=new StringBuilder();
        String line=br.readLine();
        while(null!=line){
            res.append(line);
            line = br.readLine();
        }
        return res.toString();
    }
}

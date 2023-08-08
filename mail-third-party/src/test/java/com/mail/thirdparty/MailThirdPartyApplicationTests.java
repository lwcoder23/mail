package com.mail.thirdparty;

import com.aliyun.oss.OSSClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SpringBootTest
class MailThirdPartyApplicationTests {

    @Resource
    OSSClient ossClient;

    @Test
    void contextLoads() throws FileNotFoundException {
        InputStream is = new FileInputStream("C:\\Users\\lanli\\Pictures\\Screenshots\\mysql-log.png");
        ossClient.putObject("mail-boot", "test.png", is);
        ossClient.shutdown();
        System.out.println("oss success");
    }

}

package com.atguigu.srb.sms;
import com.atguigu.srb.sms.util.SmsProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: baifengxiao
 * @date: 2022/4/7 11:35
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UtilsTests {

    @Test
    public void testProperties(){

        System.out.println(SmsProperties.ACCOUNTS_ID);
        System.out.println(SmsProperties.ACCOUNTTOKEN);
        System.out.println(SmsProperties.APPID);
        System.out.println(SmsProperties.SERVERIP);
        System.out.println(SmsProperties.SERVERPort);

    }
}


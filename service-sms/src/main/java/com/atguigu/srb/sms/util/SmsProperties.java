package com.atguigu.srb.sms.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: baifengxiao
 * @date: 2022/4/7 13:02
 */
@Setter
@Getter //idea2020.2.3版配置文件自动提示需要这个
@Component
//注意prefix要写到最后一个 "." 符号之前
//调用setter为成员赋值
@ConfigurationProperties(prefix = "rly.sms")
public class SmsProperties implements InitializingBean {

    @Value(value = "${rly.sms.accountSId}")
    private String accountSId;
    @Value(value = "${rly.sms.accountToken}")
    private String accountToken;
    @Value(value = "${rly.sms.appId}")
    private String appId;
    @Value(value = "${rly.sms.serverIp}")
    private String serverIp;
    @Value(value = "${rly.sms.serverPort}")
    private String serverPort;

    public static String ACCOUNTS_ID;
    public static String ACCOUNTTOKEN;
    public static String APPID;
    public static String SERVERIP;
    public static String SERVERPort;

        //当私有成员被赋值后，此方法自动被调用，从而初始化常量
    @Override
    public void afterPropertiesSet() throws Exception {
        ACCOUNTS_ID = accountSId;
        ACCOUNTTOKEN = accountToken;
        APPID = appId;
        SERVERIP = serverIp;
        SERVERPort = serverPort;
    }
}

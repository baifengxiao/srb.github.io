package com.atguigu.srb.sms.service.imp;

import com.atguigu.srb.sms.service.SmsService;
import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.atguigu.srb.sms.util.SmsProperties.*;

/**
 * @author: baifengxiao
 * @date: 2022/4/7 14:45
 */
@Service
public class SmsServiceImpl implements SmsService {
    @Override
    public void send(String mobile, Map<String, Object> param) {
        //创建客户端及信息
        CCPRestSmsSDK ccpRestSmsSDK = new CCPRestSmsSDK();
        ccpRestSmsSDK.setAccount(ACCOUNTS_ID, ACCOUNTTOKEN);
        ccpRestSmsSDK.setAppId(APPID);
        ccpRestSmsSDK.setBodyType(BodyType.Type_JSON);
        ccpRestSmsSDK.init(SERVERIP, SERVERPort);


        String[] datas = {(String) param.get("code"), "2"};
        //容联云发送配置
        HashMap<String, Object> result = ccpRestSmsSDK.sendTemplateSMS(mobile, "1", datas);
        System.out.println("SDKTestGetSubAccounts result=" + result);

        if ("000000".equals(result.get("statusCode"))) {
            //正常返回输出data包体信息（map）
            HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for (String key : keySet) {
                Object object = data.get(key);
                System.out.println(key + " = " + object);
            }
        } else {
            //异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
        }
    }
}

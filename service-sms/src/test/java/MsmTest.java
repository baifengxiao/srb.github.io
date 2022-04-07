import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;

import java.util.HashMap;
import java.util.Set;

/**
 * @author: baifengxiao
 * @date: 2022/4/7 11:35
 */
    public class MsmTest {
        public static void main(String[] args) {
            //生产环境请求地址：app.cloopen.com
            String serverIp = "app.cloopen.com";
            //请求端口
            String serverPort = "8883";
            // TODO 主账号,登陆云通讯网站后,可在控制台首页看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN
            String accountSId = "8aaf07087fe90a32017fffc1f1080590";

            String accountToken = "0b5f00537eb5461692d664446fb4b21f";
            // TODO 请使用管理控制台中已创建应用的APPID
            String appId = "8aaf07087fe90a32017fffc1f1eb0597";

            CCPRestSmsSDK sdk = new CCPRestSmsSDK();
            sdk.init(serverIp, serverPort);
            sdk.setAccount(accountSId, accountToken);
            sdk.setAppId(appId);
            sdk.setBodyType(BodyType.Type_JSON);

            // 模拟验证码
            String code = "884812";
            // TODO 手机号
            String to = "166***";
            // 短信模板 测试接口默认为 1
            String templateId= "1";
            String[] datas = {code, "5"}; // 验证码， 过期时间五分钟

            // String subAppend="1234";  //可选 扩展码，四位数字 0~9999
            // String reqId="fadfafas";  //可选 第三方自定义消息id，最大支持32位英文数字，同账号下同一自然天内不允许重复
            HashMap<String, Object> result = sdk.sendTemplateSMS(to,templateId,datas);
            // HashMap<String, Object> result = sdk.sendTemplateSMS(to,templateId,datas,subAppend,reqId);

            if("000000".equals(result.get("statusCode"))){
                //正常返回输出data包体信息（map）
                HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
                Set<String> keySet = data.keySet();
                for(String key:keySet){
                    Object object = data.get(key);
                    System.out.println(key +" = "+object);
                }
            }else{
                //异常返回输出错误码和错误信息
                System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
            }
        }
    }


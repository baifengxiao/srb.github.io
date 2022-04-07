package com.atguigu.srb.sms.service;

import java.util.Map;

/**
 * @author: baifengxiao
 * @date: 2022/4/7 14:42
 */
public interface SmsService {
    void send(String mobile , Map<String,Object> param);
}

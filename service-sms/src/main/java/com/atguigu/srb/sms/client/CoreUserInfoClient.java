package com.atguigu.srb.sms.client;

import com.atguigu.common.result.R;
import com.atguigu.srb.sms.client.fallback.CoreUserInfoClientFallback;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: baifengxiao
 * @date: 2022/4/14 14:52
 */
@FeignClient(value = "service-core",fallback = CoreUserInfoClientFallback.class)
public interface CoreUserInfoClient {

    @GetMapping("/api/core/userInfo/checkMobile/{mobile}")
    boolean checkMobile(@PathVariable String mobile);
}

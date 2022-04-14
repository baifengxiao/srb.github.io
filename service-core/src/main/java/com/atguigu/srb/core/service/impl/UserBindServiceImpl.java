package com.atguigu.srb.core.service.impl;

import com.atguigu.srb.core.hfb.FormHelper;
import com.atguigu.srb.core.hfb.HfbConst;
import com.atguigu.srb.core.hfb.RequestHelper;
import com.atguigu.srb.core.pojo.entity.UserBind;
import com.atguigu.srb.core.mapper.UserBindMapper;
import com.atguigu.srb.core.pojo.vo.UserBindVO;
import com.atguigu.srb.core.service.UserBindService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * <p>
 * 用户绑定表 服务实现类
 * </p>
 *
 * @author yupengtao
 * @since 2022-03-30
 */
@Service
public class UserBindServiceImpl extends ServiceImpl<UserBindMapper, UserBind> implements UserBindService {

    @Override
    public String commitBindUser(UserBindVO userBindVO, Long userId) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("agentId",HfbConst.AGENT_ID);
        paramMap.put("agentUserId", userId);
        paramMap.put("idCard",userBindVO.getIdCard());
        paramMap.put("personalName", userBindVO.getName());
        paramMap.put("bankType", userBindVO.getBankType());
        paramMap.put("bankNo", userBindVO.getBankNo());
        paramMap.put("mobile", userBindVO.getMobile());
        paramMap.put("returnUrl", HfbConst.USERBIND_RETURN_URL);
        paramMap.put("notifyUrl", HfbConst.USERBIND_NOTIFY_URL);
        paramMap.put("timestamp", RequestHelper.getTimestamp());
        paramMap.put("sign", RequestHelper.getSign(paramMap));

        //生成动态表单字符串
        String formStr= FormHelper.buildForm(HfbConst.USERBIND_URL,paramMap);
        return formStr;
    }


}

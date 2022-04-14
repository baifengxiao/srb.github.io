package com.atguigu.srb.core.service;

import com.atguigu.srb.core.pojo.entity.UserBind;
import com.atguigu.srb.core.pojo.vo.UserBindVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户绑定表 服务类
 * </p>
 *
 * @author yupengtao
 * @since 2022-03-30
 */
public interface UserBindService extends IService<UserBind> {

    String commitBindUser(UserBindVO userBindVO, Long userId);


}

package com.atguigu.srb.core.service.impl;

import com.atguigu.srb.core.pojo.entity.UserLoginRecord;
import com.atguigu.srb.core.mapper.UserLoginRecordMapper;
import com.atguigu.srb.core.service.UserLoginRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户登录记录表 服务实现类
 * </p>
 *
 * @author yupengtao
 * @since 2022-03-30
 */
@Service
public class UserLoginRecordServiceImpl extends ServiceImpl<UserLoginRecordMapper, UserLoginRecord> implements UserLoginRecordService {

}

package com.atguigu.srb.core.service;

import com.atguigu.srb.core.pojo.entity.TransFlow;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 交易流水表 服务类
 * </p>
 *
 * @author yupengtao
 * @since 2022-03-30
 */
public interface TransFlowService extends IService<TransFlow> {

    List<TransFlow> selectByUserId(Long userId);
}

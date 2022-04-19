package com.atguigu.srb.core.service.impl;

import com.atguigu.srb.core.pojo.entity.TransFlow;
import com.atguigu.srb.core.mapper.TransFlowMapper;
import com.atguigu.srb.core.service.TransFlowService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 交易流水表 服务实现类
 * </p>
 *
 * @author yupengtao
 * @since 2022-03-30
 */
@Service
public class TransFlowServiceImpl extends ServiceImpl<TransFlowMapper, TransFlow> implements TransFlowService {

    @Override
    public List<TransFlow> selectByUserId(Long userId) {

        QueryWrapper<TransFlow> transFlowQueryWrapper = new QueryWrapper<>();
        transFlowQueryWrapper.eq("user_id",userId).orderByDesc("id");
        return baseMapper.selectList(transFlowQueryWrapper);
    }
}

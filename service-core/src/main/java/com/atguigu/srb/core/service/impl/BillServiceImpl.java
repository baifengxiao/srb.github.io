package com.atguigu.srb.core.service.impl;

import com.atguigu.srb.base.util.JwtUtils;
import com.atguigu.srb.core.mapper.BillMapper;
import com.atguigu.srb.core.mapper.IntegralGradeMapper;
import com.atguigu.srb.core.pojo.entity.Bill;
import com.atguigu.srb.core.pojo.entity.IntegralGrade;
import com.atguigu.srb.core.pojo.vo.BillVO;
import com.atguigu.srb.core.service.BillService;
import com.atguigu.srb.core.service.UserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: baifengxiao
 * @date: 2022/4/20 3:30
 */
@Service
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill> implements BillService {

    @Resource
    private BillMapper billMapper;
    @Resource
    private UserInfoService userInfoService;

    @Override
    public BillVO save(@RequestBody BillVO billVO, HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);

        Bill bill = new Bill();
        bill.setUser_id(userId);

        bill.setTitle(billVO.getTitle());
        bill.setTypeId(billVO.getType());
        bill.setPrice(billVO.getPrice());
        bill.setExplain(billVO.getExplain());

        Integer homeid = userInfoService.getHomeIdById(userId);
        bill.setHome_id(homeid);

        billMapper.insert(bill);
        return billVO;
    }

    @Override
    public List<Bill> listAll(HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        Integer homeId = userInfoService.getHomeIdById(userId);
        QueryWrapper<Bill> billQueryWrapper = new QueryWrapper<>();
        billQueryWrapper.eq("user_id", userId)
                .eq("home_id", homeId);
        List<Bill> billList = baseMapper.selectList(billQueryWrapper);
        System.out.println("数据:"+billList);
        return billList;

    }


}

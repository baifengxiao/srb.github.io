package com.atguigu.srb.core.service.impl;

import com.atguigu.srb.base.util.JwtUtils;
import com.atguigu.srb.core.mapper.BillMapper;
import com.atguigu.srb.core.mapper.IntegralGradeMapper;
import com.atguigu.srb.core.pojo.entity.Bill;
import com.atguigu.srb.core.pojo.entity.Borrower;
import com.atguigu.srb.core.pojo.entity.IntegralGrade;
import com.atguigu.srb.core.pojo.entity.UserInfo;
import com.atguigu.srb.core.pojo.vo.BillVO;
import com.atguigu.srb.core.service.BillService;
import com.atguigu.srb.core.service.UserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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
    @Resource
    private BillService billService;

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

        return billList;

    }

    @Override
    public IPage<Bill> listPage(Page<Bill> pageParam) {

        QueryWrapper<Bill> billQueryWrapper = new QueryWrapper<>();
        billQueryWrapper.orderByDesc("id_");
        return baseMapper.selectPage(pageParam, billQueryWrapper);
    }

    @Override
    public Bill listMyBill(HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        Integer homeId = userInfoService.getHomeIdById(userId);
        QueryWrapper<Bill> billQueryWrapper = new QueryWrapper<>();
        billQueryWrapper.eq("user_id", userId)
                .eq("home_id", homeId)
                .ne("type_id_", 1);


        Bill billList = baseMapper.selectOne(billQueryWrapper.orderByDesc("price_").last("limit 1"));

        return billList;

    }

    @Override
    public List<Bill> listOurBill(HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        Integer homeId = userInfoService.getHomeIdById(userId);
        QueryWrapper<Bill> billQueryWrapper = new QueryWrapper<>();
        billQueryWrapper.eq("user_id", userId)
                .eq("home_id", homeId)
                .eq("type_id_", 1);
        List<Bill> listMyBill = baseMapper.selectList(billQueryWrapper);


        return listMyBill;

    }

    @Override
    public Long listMyBillAccount(HttpServletRequest request) {
        //总收益
//        Bill bill =new Bill();
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        Integer homeId = userInfoService.getHomeIdById(userId);
        QueryWrapper<Bill> queryWrapper = new QueryWrapper<Bill>();
        queryWrapper.eq("user_id", userId)
                .eq("home_id", homeId)
                .select("ifnull(sum(price_),0) as sumAll");
        Bill bill = billService.getOne(queryWrapper);
        //取值
        Long sumAll = bill.getSumAll();

        return sumAll;
    }

    @Override
    public Long listOurBillAccount(HttpServletRequest request) {
        //总收益
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        Integer homeId = userInfoService.getHomeIdById(userId);
        QueryWrapper<Bill> queryWrapper = new QueryWrapper<Bill>();
        queryWrapper.eq("user_id", userId)
                .eq("home_id", homeId)
                .eq("type_id_", 1).select("ifnull(sum(price_),0) as sumAll");
        Bill bill = billService.getOne(queryWrapper);
        //取值
        Long sumAll = bill.getSumAll();

        return sumAll;
    }

    @Override
    public void lock(Long id, Integer status) {
        Bill bill = new Bill();

        bill.setId(id);
        bill.setStatus(status);
        baseMapper.updateById(bill);
    }

    @Override
    public Long listUnSettled(HttpServletRequest request) {
        //总收益
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        Integer homeId = userInfoService.getHomeIdById(userId);
        QueryWrapper<Bill> queryWrapper = new QueryWrapper<Bill>();
        queryWrapper.eq("user_id", userId)
                .eq("home_id", homeId)
                .eq("type_id_", 1)
                .eq("status", 1)
                .select("ifnull(sum(price_),0) as sumAll");
        Bill bill = billService.getOne(queryWrapper);
        //取值
        Long sumAll = bill.getSumAll();

        return sumAll;
    }

    @Override
    public Integer listMyBillDetail(HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        QueryWrapper<Bill> queryWrapper = new QueryWrapper<Bill>();
        // QueryWrapper<Employee> queryWrapper2=Wrappers.<Employee>query();
        Integer count = baseMapper.selectCount(queryWrapper);
        return count;
    }




}

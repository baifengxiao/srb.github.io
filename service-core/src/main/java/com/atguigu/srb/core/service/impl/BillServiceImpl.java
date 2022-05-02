package com.atguigu.srb.core.service.impl;

import com.atguigu.srb.base.util.JwtUtils;
import com.atguigu.srb.core.mapper.BillMapper;
import com.atguigu.srb.core.pojo.entity.*;
import com.atguigu.srb.core.pojo.vo.BillPriceVO;
import com.atguigu.srb.core.pojo.vo.BillVO;
import com.atguigu.srb.core.service.BillService;
import com.atguigu.srb.core.service.UserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
                .eq("home_id", homeId)
        ;
        List<Bill> billList = baseMapper.selectList(billQueryWrapper);

        return billList;

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
    public Integer listMyBillAccount(HttpServletRequest request) {
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
        Integer sumAll = bill.getSumAll();

        return sumAll;
    }

    @Override
    public Integer listOurBillAccount(HttpServletRequest request) {
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
        Integer sumAll = bill.getSumAll();

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
    public Integer listUnSettled(HttpServletRequest request) {
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
        Integer sumAll = bill.getSumAll();
        return sumAll;
    }

    @Override
    public Integer listMyBillDetail(HttpServletRequest request) {

        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        Integer homeId = userInfoService.getHomeIdById(userId);
        QueryWrapper<Bill> billQueryWrapper = new QueryWrapper<>();
        billQueryWrapper.eq("user_id", userId)
                .eq("home_id", homeId)
                ;
        // QueryWrapper<Employee> queryWrapper2=Wrappers.<Employee>query();
        Integer count = baseMapper.selectCount(billQueryWrapper);
        return count;
    }

    @Override
    public IPage<Bill> listPage(Page<Bill> pageParam, HttpServletRequest request) {

        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        Integer homeId = userInfoService.getHomeIdById(userId);
        QueryWrapper<Bill> billQueryWrapper = new QueryWrapper<>();
        billQueryWrapper.eq("user_id", userId)
                .eq("home_id", homeId)
                .orderByDesc("id_");
        return baseMapper.selectPage(pageParam, billQueryWrapper);
    }

    @Override
    public BillPriceVO getUserPrice(Long userId) {

        Integer homeId = userInfoService.getHomeIdById(userId);
        QueryWrapper<Bill> queryWrapper1 = new QueryWrapper<Bill>();
        queryWrapper1.eq("user_id", userId)
                .eq("home_id", homeId)
                .eq("type_id_", 1)
//                .eq("status", 1)
                .select("ifnull(sum(price_),0) as sumAll");
        Bill bill1 = billService.getOne(queryWrapper1);
        //取值
        Integer type1 = bill1.getSumAll();


        QueryWrapper<Bill> queryWrapper2 = new QueryWrapper<Bill>();
        queryWrapper2.eq("user_id", userId)
                .eq("home_id", homeId)
                .eq("type_id_", 2)
//                .eq("status", 1)
                .select("ifnull(sum(price_),0) as sumAll");
        Bill bill2 = billService.getOne(queryWrapper2);
        //取值
        Integer type2 = bill2.getSumAll();


        QueryWrapper<Bill> queryWrapper3 = new QueryWrapper<Bill>();
        queryWrapper3.eq("user_id", userId)
                .eq("home_id", homeId)
                .eq("type_id_", 3)
//                .eq("status", 1)
                .select("ifnull(sum(price_),0) as sumAll");
        Bill bill3 = billService.getOne(queryWrapper3);
        //取值
        Integer type3 = bill3.getSumAll();


        QueryWrapper<Bill> queryWrapper4 = new QueryWrapper<Bill>();
        queryWrapper4.eq("user_id", userId)
                .eq("home_id", homeId)
                .eq("type_id_", 4)
//                .eq("status", 1)
                .select("ifnull(sum(price_),0) as sumAll");
        Bill bill4 = billService.getOne(queryWrapper4);
        //取值
        Integer type4 = bill4.getSumAll();


        QueryWrapper<Bill> queryWrapper5 = new QueryWrapper<Bill>();
        queryWrapper5.eq("user_id", userId)
                .eq("home_id", homeId)
                .eq("type_id_", 5)
//                .eq("status", 1)
                .select("ifnull(sum(price_),0) as sumAll");
        Bill bill5 = billService.getOne(queryWrapper5);
        //取值
        Integer type5 = bill5.getSumAll();



        QueryWrapper<Bill> queryWrapper6 = new QueryWrapper<Bill>();
        queryWrapper6.eq("user_id", userId)
                .eq("home_id", homeId)
                .eq("type_id_", 6)
//                .eq("status", 1)
                .select("ifnull(sum(price_),0) as sumAll");
        Bill bill6 = billService.getOne(queryWrapper6);
        //取值
        Integer type6 = bill6.getSumAll();


        QueryWrapper<Bill> queryWrapper7 = new QueryWrapper<Bill>();
        queryWrapper7.eq("user_id", userId)
                .eq("home_id", homeId)
                .eq("type_id_", 7)
//                .eq("status", 1)
                .select("ifnull(sum(price_),0) as sumAll");
        Bill bill7 = billService.getOne(queryWrapper7);
        //取值
        Integer type7 = bill7.getSumAll();


        QueryWrapper<Bill> queryWrapper8 = new QueryWrapper<Bill>();
        queryWrapper8.eq("user_id", userId)
                .eq("home_id", homeId)
                .eq("type_id_", 8)
//                .eq("status", 1)
                .select("ifnull(sum(price_),0) as sumAll");
        Bill bill8 = billService.getOne(queryWrapper8);
        //取值
        Integer type8 = bill8.getSumAll();



        QueryWrapper<Bill> queryWrapper9 = new QueryWrapper<Bill>();
        queryWrapper9.eq("user_id", userId)
                .eq("home_id", homeId)
                .eq("type_id_", 9)
//                .eq("status", 1)
                .select("ifnull(sum(price_),0) as sumAll");
        Bill bill9 = billService.getOne(queryWrapper9);
        //取值
        Integer type9 = bill9.getSumAll();


        QueryWrapper<Bill> queryWrapper10 = new QueryWrapper<Bill>();
        queryWrapper10.eq("user_id", userId)
                .eq("home_id", homeId)
                .eq("type_id_", 10)
//                .eq("status", 1)
                .select("ifnull(sum(price_),0) as sumAll");
        Bill bill10 = billService.getOne(queryWrapper10);
        //取值
        Integer type10 = bill10.getSumAll();

        //组装结果数据
        BillPriceVO billPriceVO = new BillPriceVO();


        billPriceVO.setCommon(type1);
        billPriceVO.setTransport(type2);
        billPriceVO.setCharge(type3);
        billPriceVO.setOutdoor(type4);
        billPriceVO.setWenhua(type5);
        billPriceVO.setRiyong(type6);
        billPriceVO.setDinner(type7);
        billPriceVO.setQinyou(type8);
        billPriceVO.setMeirong(type9);
        billPriceVO.setOther(type10);

        return billPriceVO;
    }

    @Override
    public BillVO updata(BillVO billVO, HttpServletRequest request, Long id) {
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);

        Bill bill = new Bill();
        bill.setId(id);
        bill.setUser_id(userId);
        bill.setTitle(billVO.getTitle());
        bill.setTypeId(billVO.getType());
        bill.setPrice(billVO.getPrice());
        bill.setExplain(billVO.getExplain());

        Integer homeid = userInfoService.getHomeIdById(userId);
        bill.setHome_id(homeid);

        billMapper.updateById(bill);
        return billVO;
    }


}

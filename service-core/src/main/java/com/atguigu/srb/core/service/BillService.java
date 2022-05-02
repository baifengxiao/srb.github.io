package com.atguigu.srb.core.service;

import com.atguigu.srb.core.pojo.entity.Bill;
import com.atguigu.srb.core.pojo.vo.BillPriceVO;
import com.atguigu.srb.core.pojo.vo.BillVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: baifengxiao
 * @date: 2022/4/20 3:22
 */
public interface BillService extends IService<Bill> {


    BillVO save(BillVO billVO, HttpServletRequest request);


    List<Bill> listAll(HttpServletRequest request);



    Bill listMyBill(HttpServletRequest request);

    List<Bill> listOurBill(HttpServletRequest request);

    Integer listMyBillAccount(HttpServletRequest request);

    Integer listOurBillAccount(HttpServletRequest request);

    void lock(Long id, Integer status);

    Integer listUnSettled(HttpServletRequest request);

    Integer listMyBillDetail(HttpServletRequest request);


    IPage<Bill> listPage(Page<Bill> pageParam, HttpServletRequest request);

    BillPriceVO getUserPrice(Long userId);

    BillVO updata(BillVO billVO, HttpServletRequest request, Long id);
}

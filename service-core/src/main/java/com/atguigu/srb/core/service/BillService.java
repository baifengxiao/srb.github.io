package com.atguigu.srb.core.service;

import com.atguigu.srb.core.pojo.entity.Bill;
import com.atguigu.srb.core.pojo.vo.BillVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: baifengxiao
 * @date: 2022/4/20 3:22
 */
public interface BillService extends IService<Bill> {


    BillVO save(BillVO billVO, HttpServletRequest request);
}

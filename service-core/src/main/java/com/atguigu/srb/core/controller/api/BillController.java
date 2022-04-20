package com.atguigu.srb.core.controller.api;

import com.atguigu.common.exception.Assert;
import com.atguigu.common.result.R;
import com.atguigu.common.result.ResponseEnum;
import com.atguigu.srb.base.util.JwtUtils;
import com.atguigu.srb.core.mapper.BillMapper;
import com.atguigu.srb.core.pojo.entity.Bill;
import com.atguigu.srb.core.pojo.entity.IntegralGrade;
import com.atguigu.srb.core.pojo.vo.BillVO;
import com.atguigu.srb.core.pojo.vo.RegisterVO;
import com.atguigu.srb.core.service.BillService;
import com.atguigu.srb.core.service.IntegralGradeService;
import com.atguigu.srb.core.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: baifengxiao
 * @date: 2022/4/20 3:17
 */

@Api(tags = "账单管理")
@RestController
@RequestMapping("/api/core/bill")
@Slf4j
public class BillController {

    @Resource
    private BillService billService;



    @ApiOperation("添加账单")
    @PostMapping("/save")
    public R list(@RequestBody BillVO billVO, HttpServletRequest request) {

        billService.save(billVO,request);

        return R.ok().data("billVO",billVO);
    }
}

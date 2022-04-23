package com.atguigu.srb.core.controller.api;

import com.atguigu.common.exception.Assert;
import com.atguigu.common.result.R;
import com.atguigu.common.result.ResponseEnum;
import com.atguigu.srb.base.util.JwtUtils;
import com.atguigu.srb.core.mapper.BillMapper;
import com.atguigu.srb.core.pojo.entity.Bill;
import com.atguigu.srb.core.pojo.entity.Borrower;
import com.atguigu.srb.core.pojo.entity.IntegralGrade;
import com.atguigu.srb.core.pojo.entity.UserInfo;
import com.atguigu.srb.core.pojo.query.UserInfoQuery;
import com.atguigu.srb.core.pojo.vo.BillVO;
import com.atguigu.srb.core.pojo.vo.RegisterVO;
import com.atguigu.srb.core.service.BillService;
import com.atguigu.srb.core.service.IntegralGradeService;
import com.atguigu.srb.core.service.UserInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

        billService.save(billVO, request);

        return R.ok().data("billVO", billVO);
    }


    @ApiOperation("账单列表")
    @GetMapping("/list")
    public R listAll(HttpServletRequest request) {

        List<Bill> listAll = billService.listAll(request);

        return R.ok().data("listAll", listAll).message("数据列表成功");
    }

    @ApiOperation("获取个人消费金额")
    @GetMapping("/listMyBill")
    public R listMyBill(HttpServletRequest request) {

        Bill listMyBill = billService.listMyBill(request);

        return R.ok().data("listMyBill", listMyBill).message("数据列表成功");
    }

    @ApiOperation("获取个人消费总额")
    @GetMapping("/listMyBillAccount")
    public R listMyBillAccount(HttpServletRequest request) {

        Long listMyBillAccount = billService.listMyBillAccount(request);

        return R.ok().data("listMyBillAccount", listMyBillAccount).message("数据列表成功");
    }


    @ApiOperation("获取个人消费笔数")
    @GetMapping("/listMyBillDetail")
    public R listMyBillDetail(HttpServletRequest request) {

        Integer listMyBillDetail = billService.listMyBillDetail(request);

        return R.ok().data("listMyBillDetail", listMyBillDetail).message("数据列表成功");
    }

    @ApiOperation("获取未结算总额")
    @GetMapping("/listUnSettled")
    public R listUnSettled(HttpServletRequest request) {

        Long listUnSettled = billService.listUnSettled(request);

        return R.ok().data("listUnSettled", listUnSettled).message("成功");
    }

    @ApiOperation("获取公共消费总额")
    @GetMapping("/listOurBillAccount")
    public R listOurBillAccount(HttpServletRequest request) {

        Long listOurBillAccount = billService.listOurBillAccount(request);

        return R.ok().data("listOurBillAccount", listOurBillAccount).message("数据列表成功");
    }

    @ApiOperation("获取公共消费金额")
    @GetMapping("/listOurBill")
    public R listOurBill(HttpServletRequest request) {

        List<Bill> listOurBill = billService.listOurBill(request);

        return R.ok().data("listOurBill", listOurBill).message("数据列表成功");
    }

    @ApiOperation("获取账单分页列表")
    @GetMapping("/listAll/{page}/{limit}")
    public R listPage(@ApiParam(value = "当前页码", required = true) @PathVariable Long page, @ApiParam(value = "每页记录数", required = true) @PathVariable Long limit) {
        Page<Bill> pageParam = new Page<>(page, limit);

        IPage<Bill> pageModel = billService.listPage(pageParam);
        return R.ok().data("pageModel", pageModel);
    }


    @ApiOperation(value = "根据id删除记录", notes = "逻辑删除数据记录")
    @DeleteMapping("/remove/{id}")
    public R removeById(@ApiParam(value = "数据id", example = "100", required = true) @PathVariable Long id) {
        boolean result = billService.removeById(id);
        if (result) {
            return R.ok().message("删除成功");
        } else {
            return R.error().message("删除失败");
        }
    }

    @ApiOperation("结算和未结算")
    @PutMapping("/lock/{id}/{status}")
    public R lock(
            @ApiParam(value = "用户名id", required = true)
            @PathVariable("id") Long id,
            @ApiParam(value = "锁定状态(0:锁定 1:正常)", required = true)
            @PathVariable("status") Integer status) {
        billService.lock(id, status);
        return R.ok().message(status == 1 ? "解锁成功" : "锁定成功");

    }
}

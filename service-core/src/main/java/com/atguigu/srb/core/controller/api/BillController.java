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

        billService.save(billVO, request);

        return R.ok().data("billVO", billVO);
    }

    @ApiOperation("账单列表")
    @GetMapping("/list")
    public R listAll(HttpServletRequest request) {

        List<Bill> listAll = billService.listAll(request);

        return R.ok().data("listAll", listAll).message("数据列表成功");
    }

    @ApiOperation(value = "根据id删除记录", notes = "逻辑删除数据记录")
    @DeleteMapping("/remove/{id}")
    public R removeById(
            @ApiParam(value = "数据id", example = "100", required = true)
            @PathVariable Long id) {
        boolean result = billService.removeById(id);
        if (result) {
            return R.ok().message("删除成功");
        } else {
            return R.error().message("删除失败");
        }
    }
}

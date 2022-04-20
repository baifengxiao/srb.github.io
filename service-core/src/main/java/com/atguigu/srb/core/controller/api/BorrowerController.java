package com.atguigu.srb.core.controller.api;


import com.atguigu.common.result.R;
import com.atguigu.srb.base.util.JwtUtils;
import com.atguigu.srb.core.pojo.vo.BorrowerVO;
import com.atguigu.srb.core.service.BorrowerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 借款人 前端控制器
 * </p>
 *
 * @author yupengtao
 * @since 2022-03-30
 */
@Api(tags = "身份认证")
@RestController
@RequestMapping("/api/core/borrower")
@Slf4j
public class BorrowerController {
    @Resource
    private BorrowerService borrowerService;
    @ApiOperation("保存申请人信息")
    @PostMapping("/auth/save")
    public R save(@RequestBody BorrowerVO borrowerVO, HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        borrowerService.saveBorrowerVOByUserId(borrowerVO, userId);
        return R.ok().message("信息提交成功");
    }
    @ApiOperation("获取申请人认证状态")
    @GetMapping("/auth/getBorrowerStatus")
    public R getBorrowStatus(HttpServletRequest request){
        String token=request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        Integer status=borrowerService.getStatusByUserid(userId);
        return R.ok().data("borrowerStatus",status);
    }
}


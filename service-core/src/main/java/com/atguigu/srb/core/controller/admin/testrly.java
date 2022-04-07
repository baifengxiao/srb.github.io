package com.atguigu.srb.core.controller.admin;

import com.atguigu.common.result.R;
import com.atguigu.srb.core.pojo.entity.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: baifengxiao
 * @date: 2022/4/7 10:56
 */
@Api(tags = "数据字典管理")
@RestController
@RequestMapping("/https://app.cloopen.com:8883")
@Slf4j
@CrossOrigin
public class testrly {

    @ApiOperation("测试容联云")
    @GetMapping("/listByParentId/{parentId}")
    public R testrly() {

        return R.ok();
    }

}

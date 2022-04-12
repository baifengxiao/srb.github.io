package com.atguigu.srb.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: baifengxiao
 * @date: 2022/4/11 23:47
 */
@Data
@ApiModel(description = "登录对象")
public class LoginVO {
    @ApiModelProperty(value = "用户类型")

    private Integer userType;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;

}

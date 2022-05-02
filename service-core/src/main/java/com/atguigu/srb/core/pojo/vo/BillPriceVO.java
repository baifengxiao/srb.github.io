package com.atguigu.srb.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author: baifengxiao
 * @date: 2022/4/29 11:47
 */
@Data
@ApiModel(description = "用户各类消费价格总和")
public class BillPriceVO {

    @ApiModelProperty(value = "公共开支")
    private Integer common;

    @ApiModelProperty(value = "交通出行")
    private Integer transport;

    @ApiModelProperty(value = "充值缴费")
    private Integer charge;

    @ApiModelProperty(value = "运动户外")
    private Integer outdoor;

    @ApiModelProperty(value = "文化休闲")
    private Integer wenhua;

    @ApiModelProperty(value = "日用百货")
    private Integer riyong;

    @ApiModelProperty(value = "餐饮美食")
    private Integer dinner;

    @ApiModelProperty(value = "亲友代付")
    private Integer qinyou;

    @ApiModelProperty(value = "美容美发")
    private Integer meirong;

    @ApiModelProperty(value = "其他")
    private Integer other;
}





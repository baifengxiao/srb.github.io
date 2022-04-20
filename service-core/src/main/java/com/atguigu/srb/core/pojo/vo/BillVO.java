package com.atguigu.srb.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: baifengxiao
 * @date: 2022/4/20 3:49
 */
@Data
@ApiModel(description="账单添加对象")
public class BillVO {
    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "金额")
    private Integer price;
    @ApiModelProperty(value = "描述")
    private String explain;
}

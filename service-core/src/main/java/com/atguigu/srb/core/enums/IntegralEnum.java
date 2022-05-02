package com.atguigu.srb.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IntegralEnum {

//    BORROWER_INFO(50, "申请人基本信息"),
    BORROWER_IDCARD(30, "身份证信息"),
    BORROWER_HOUSE(100, "房产信息"),
    BORROWER_CAR(60, "车辆信息"),
    ;

    private Integer integral;
    private String msg;
}

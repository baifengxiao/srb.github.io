package com.atguigu.srb.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * @author: baifengxiao
 * @date: 2022/4/20 3:07
 */
@TableName(value = "bill_type_")
public class BillType implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value ="id_", type = IdType.AUTO)
    public Long id;
    @TableField(value ="name_")
    public String name;
}


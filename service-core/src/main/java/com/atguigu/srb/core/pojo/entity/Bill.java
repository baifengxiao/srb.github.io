package com.atguigu.srb.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author: baifengxiao
 * @date: 2022/4/20 2:56
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bill_")
public class Bill implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value ="id_", type = IdType.AUTO)
    private Long id;

    @TableField(value ="title_")
    private String title;
    @TableField(value ="bill_time_")
    private LocalDateTime billTime;
    @TableField(value = "type_id_")
    private Integer typeId;
    @TableField(value = "price_")
    private Integer price;
    @TableField(value ="explain_")
    private String explain;
    @TableField(value ="home_id")
    private String home_id;
    @TableField(value = "user_id")
    private Long user_id;


    /**
     * 类别名称
     */
    @Transient
    private String typeName;
    /**
     * 开始时间:用于查询
     */
    @Transient
    private LocalDateTime date1;
    /**
     * 结束时间：用于查询
     */
    @Transient
    private LocalDateTime date2;

}


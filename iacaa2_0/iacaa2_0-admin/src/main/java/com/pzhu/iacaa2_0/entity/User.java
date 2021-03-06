package com.pzhu.iacaa2_0.entity;

import com.pzhu.iacaa2_0.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author ZhaoZezhong
 * @since 2021-01-15
 */
@Data
@EqualsAndHashCode()
@TableName("user")
public class User{

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private long id;

    @TableField("name")
    private String name;

    @TableField("password")
    private String password;


}

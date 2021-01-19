package com.pzhu.iacaa2_0.base;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class BaseEntity {
    /**
     * 唯一标识
     */
    //去除Myatisplus生成id
//    @TableId(value = "id",type = IdType.AUTO)
    private long id;
//
//    private int updateId;
//
//    private Date udpateTime;
//
//    private int createId;
//
//    private Date createTime;
}

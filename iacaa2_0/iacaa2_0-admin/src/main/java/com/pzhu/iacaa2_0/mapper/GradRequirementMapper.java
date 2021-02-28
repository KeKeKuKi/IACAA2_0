package com.pzhu.iacaa2_0.mapper;

import com.pzhu.iacaa2_0.entity.GradRequirement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ZhaoZezhong
 * @since 2021-01-15
 */
public interface GradRequirementMapper extends BaseMapper<GradRequirement> {
    List<GradRequirement> getByEntity(GradRequirement gradRequirement);

}

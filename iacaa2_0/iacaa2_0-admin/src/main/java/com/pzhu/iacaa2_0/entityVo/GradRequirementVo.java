package com.pzhu.iacaa2_0.entityVo;

import com.pzhu.iacaa2_0.entity.GradRequirement;
import lombok.Data;

/**
 * @author MrZhao
 */
@Data
public class GradRequirementVo extends GradRequirement {
    private Integer pageNum = 1;
    private Integer pageSize = 20;
    private String word;
}

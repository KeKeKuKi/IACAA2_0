package com.pzhu.iacaa2_0.entityVo;

import com.pzhu.iacaa2_0.entity.CourseTask;
import com.pzhu.iacaa2_0.entity.Target;
import lombok.Data;

@Data
public class CourseTaskVo extends CourseTask {
    Target target;
}

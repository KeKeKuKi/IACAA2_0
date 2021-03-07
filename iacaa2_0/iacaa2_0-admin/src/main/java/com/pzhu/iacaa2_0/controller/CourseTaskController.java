package com.pzhu.iacaa2_0.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pzhu.iacaa2_0.common.ActionResult;
import com.pzhu.iacaa2_0.entity.CourseTarget;
import com.pzhu.iacaa2_0.entity.CourseTask;
import com.pzhu.iacaa2_0.entityVo.CourseTargetVo;
import com.pzhu.iacaa2_0.service.ICourseTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ZhaoZezhong
 * @since 2021-01-15
 */
@RestController
@RequestMapping("/courseTask")
public class CourseTaskController {
    @Autowired
    ICourseTaskService courseTaskService;

    @RequestMapping("/voList")
    public ActionResult voList(@RequestBody CourseTask courseTask){
        return null;
    }
}

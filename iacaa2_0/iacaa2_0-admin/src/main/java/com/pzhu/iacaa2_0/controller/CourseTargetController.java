package com.pzhu.iacaa2_0.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pzhu.iacaa2_0.common.ActionResult;
import com.pzhu.iacaa2_0.entity.CourseTarget;
import com.pzhu.iacaa2_0.entity.GradRequirement;
import com.pzhu.iacaa2_0.entityVo.CourseTargetVo;
import com.pzhu.iacaa2_0.service.ICourseService;
import com.pzhu.iacaa2_0.service.ICourseTargetService;
import com.pzhu.iacaa2_0.service.ITargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
@RequestMapping("/courseTarget")
public class CourseTargetController {
    @Autowired
    ICourseTargetService courseTargetService;

    @RequestMapping("/list")
    public ActionResult list(@RequestBody CourseTarget courseTarget){
        QueryWrapper<CourseTarget> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(courseTarget.getTargetId())){
            wrapper.like("target_id",courseTarget.getTargetId());
        }
        if(!StringUtils.isEmpty(courseTarget.getCourseId())){
            wrapper.like("course_id",courseTarget.getCourseId());
        }
        List<CourseTarget> list = courseTargetService.list(wrapper);
        return ActionResult.ofSuccess(list);
    }

    @RequestMapping("/voList")
    public ActionResult voList(@RequestBody CourseTarget courseTarget){
        List<CourseTargetVo> volist = courseTargetService.volist(courseTarget);
        return ActionResult.ofSuccess(volist);
    }

    @RequestMapping("/saveOrUpdate")
    public ActionResult saveOrUpdate(@RequestBody List<CourseTargetVo> vos){
        List<CourseTarget> courseTargets = new ArrayList<>();
        vos.forEach(i -> {
            CourseTarget courseTarget = new CourseTarget();
            if(null != i.getId()){
                courseTarget.setId(i.getId());
                courseTarget.setUpdateDate(LocalDateTime.now());
            }else {
                courseTarget.setCreatedDate(LocalDateTime.now());
                courseTarget.setUpdateDate(LocalDateTime.now());
            }
            courseTarget.setCourseId(i.getCourse().getId());
            courseTarget.setTargetId(i.getTarget().getId());
            courseTarget.setMix(i.getMix());
            courseTargets.add(courseTarget);
        });

        courseTargetService.saveOrUpdateBatch(courseTargets);
        return ActionResult.ofSuccess();
    }
}

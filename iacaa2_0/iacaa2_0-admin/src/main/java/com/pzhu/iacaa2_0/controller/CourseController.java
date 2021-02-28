package com.pzhu.iacaa2_0.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pzhu.iacaa2_0.base.PageBaseController;
import com.pzhu.iacaa2_0.common.ActionResult;
import com.pzhu.iacaa2_0.entity.Course;
import com.pzhu.iacaa2_0.entityVo.CourseVo;
import com.pzhu.iacaa2_0.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/course")
public class CourseController extends PageBaseController {

    @Autowired
    ICourseService courseService;

    @RequestMapping("/list")
    public ActionResult list(@RequestParam(defaultValue = PageNum) int pageNum,
                             @RequestParam(defaultValue = PageSize) int pageSize) throws Exception{
        PageHelper.startPage(pageNum,pageSize);
        List<Course> list = courseService.list();
        PageInfo page = new PageInfo(list);
        return ActionResult.ofSuccess(page);
    }

    @RequestMapping("/voList")
    public ActionResult list(@RequestParam(defaultValue = PageNum) int pageNum,
                             @RequestParam(defaultValue = PageSize) int pageSize,Course course) throws Exception{
        PageHelper.startPage(pageNum,pageSize);
        List<CourseVo> list = courseService.voList(course);
        PageInfo page = new PageInfo(list);
        return ActionResult.ofSuccess(page);
    }
}

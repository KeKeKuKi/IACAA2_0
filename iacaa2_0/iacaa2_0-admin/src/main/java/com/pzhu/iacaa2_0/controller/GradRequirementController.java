package com.pzhu.iacaa2_0.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pzhu.iacaa2_0.base.PageBaseController;
import com.pzhu.iacaa2_0.common.ActionResult;
import com.pzhu.iacaa2_0.entity.Course;
import com.pzhu.iacaa2_0.entity.GradRequirement;
import com.pzhu.iacaa2_0.service.IGradRequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Wrapper;
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
@RequestMapping("/gradRequirement")
public class GradRequirementController extends PageBaseController {

    @Autowired
    IGradRequirementService gradRequirementService;

    @RequestMapping("/list")
    public ActionResult list(@RequestBody GradRequirement gradRequirement,
                             @RequestParam(defaultValue = PageNum) int pageNum,
                             @RequestParam(defaultValue = PageSize) int pageSize){

        PageHelper.startPage(pageNum,pageSize);
        List<GradRequirement> list = gradRequirementService.list(gradRequirement);
        PageInfo page = new PageInfo(list);
        return ActionResult.ofSuccess(page);
    }

    @RequestMapping("/update")
    public ActionResult update(@RequestBody GradRequirement gradRequirement){
        UpdateWrapper<GradRequirement> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",gradRequirement.getId());
        boolean update = gradRequirementService.update(gradRequirement, updateWrapper);
        return update ? ActionResult.ofSuccess() : ActionResult.ofFail(200,"更新失败");
    }

    @RequestMapping("/save")
    public ActionResult save(@RequestBody GradRequirement gradRequirement){
        boolean update = gradRequirementService.save(gradRequirement);
        return update ? ActionResult.ofSuccess() : ActionResult.ofFail(200,"添加失败");
    }
}

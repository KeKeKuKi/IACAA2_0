package com.pzhu.iacaa2_0.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pzhu.iacaa2_0.base.PageBaseController;
import com.pzhu.iacaa2_0.common.ActionResult;
import com.pzhu.iacaa2_0.entity.Course;
import com.pzhu.iacaa2_0.entity.GradRequirement;
import com.pzhu.iacaa2_0.entityVo.GradRequirementVo;
import com.pzhu.iacaa2_0.entityVo.IdsVo;
import com.pzhu.iacaa2_0.entityVo.PageVo;
import com.pzhu.iacaa2_0.service.IGradRequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;
import java.sql.Wrapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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
    public ActionResult list(@RequestBody GradRequirementVo vo){
        QueryWrapper<GradRequirement> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(vo.getWord())){
            wrapper.like("name",vo.getWord()).or()
                    .like("discrible",vo.getWord());
        }
        if(!StringUtils.isEmpty(vo.getYear())){
            wrapper.eq("year",vo.getYear());
        }
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<GradRequirement> list = gradRequirementService.list(wrapper);
        PageInfo page = new PageInfo(list);
        return ActionResult.ofSuccess(page);
    }

    @RequestMapping("/update")
    public ActionResult update(@RequestBody GradRequirement gradRequirement){
        gradRequirement.setUpdateDate(LocalDateTime.now());
        UpdateWrapper<GradRequirement> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",gradRequirement.getId());
        boolean update = gradRequirementService.update(gradRequirement, updateWrapper);
        return update ? ActionResult.ofSuccess() : ActionResult.ofFail(200,"更新失败");
    }

    @RequestMapping("/save")
    public ActionResult save(@RequestBody GradRequirement gradRequirement){
        if(gradRequirement.getYear() == null){
            gradRequirement.setYear(LocalDate.now().getYear());
        }
        gradRequirement.setCreatedDate(LocalDateTime.now());
        gradRequirement.setUpdateDate(LocalDateTime.now());
        boolean update = gradRequirementService.save(gradRequirement);
        return update ? ActionResult.ofSuccess() : ActionResult.ofFail(200,"添加失败");
    }

    @RequestMapping("/del")
    public ActionResult del(@RequestBody IdsVo ids){
        for (String id : ids.getIds()) {
            gradRequirementService.removeById(id);
        }
        return ActionResult.ofSuccess();
    }
}

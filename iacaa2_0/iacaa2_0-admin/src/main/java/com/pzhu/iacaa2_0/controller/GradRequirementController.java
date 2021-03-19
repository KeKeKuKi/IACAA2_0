package com.pzhu.iacaa2_0.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pzhu.iacaa2_0.base.PageBaseController;
import com.pzhu.iacaa2_0.common.ActionResult;
import com.pzhu.iacaa2_0.entity.Course;
import com.pzhu.iacaa2_0.entity.GradRequirement;
import com.pzhu.iacaa2_0.entity.Target;
import com.pzhu.iacaa2_0.entityVo.GradRequirementVo;
import com.pzhu.iacaa2_0.entityVo.IdsVo;
import com.pzhu.iacaa2_0.entityVo.PageVo;
import com.pzhu.iacaa2_0.service.IGradRequirementService;
import com.pzhu.iacaa2_0.service.ITargetService;
import com.pzhu.iacaa2_0.utils.EasyPoiUtils;
import com.pzhu.iacaa2_0.utils.ExportFileUtils;
import com.sun.deploy.net.HttpResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.*;
import java.net.URLEncoder;
import java.sql.Wrapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    ITargetService targetService;

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
        wrapper.orderByDesc("year","update_date");
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<GradRequirement> list = gradRequirementService.list(wrapper);
        PageInfo page = new PageInfo(list);
        return ActionResult.ofSuccess(page);
    }


    @RequestMapping("/voList")
    public ActionResult voList(@RequestBody GradRequirementVo vo){
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<GradRequirementVo> list = gradRequirementService.voList(vo);
        PageInfo page = new PageInfo(list);
        return ActionResult.ofSuccess(page);
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping("/update")
    public ActionResult update(@RequestBody GradRequirementVo vo){
        List<Target> targets = vo.getTargets();
        UpdateWrapper<Target> targetUpdateWrapper = new UpdateWrapper<>();
        targetUpdateWrapper.eq("req_id",vo.getId());
        targetUpdateWrapper.eq("year",LocalDate.now().getYear());
        targetService.remove(targetUpdateWrapper);
        targets.forEach(i ->{
            i.setYear(LocalDate.now().getYear());
            i.setCreatedDate(LocalDateTime.now());
            i.setUpdateDate(LocalDateTime.now());
            targetService.save(i);
        });
        vo.setUpdateDate(LocalDateTime.now());
        UpdateWrapper<GradRequirement> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",vo.getId());
        boolean update = gradRequirementService.update(vo, updateWrapper);
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

    @RequestMapping("/exportTemplate")
    public void exportTemplate(HttpServletResponse response, HttpServletRequest request) throws IOException {
        File file = new File("D:/doc/"+"import"+".xlsx");
        if(!file.exists()){
            throw new IOException(file.getPath()+"文件不存在！");
        }
        InputStream fis = null;
        fis = new BufferedInputStream(new FileInputStream(file));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        response.reset();
        response.setContentType("application/octet-stream");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("指标点模板" + ".xlsx", "UTF-8"));
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        toClient.write(buffer);
        toClient.flush();
        toClient.close();
        fis.close();

//        ExportFileUtils.export(new HashMap<>(0),"classpath:/doc","import.xlsx",response);
    }
}

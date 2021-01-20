package com.pzhu.iacaa2_0.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pzhu.iacaa2_0.entity.Course;
import com.pzhu.iacaa2_0.entityVo.CourseVo;
import com.pzhu.iacaa2_0.mapper.CourseMapper;
import com.pzhu.iacaa2_0.mapper.UserMapper;
import com.pzhu.iacaa2_0.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pzhu.iacaa2_0.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ZhaoZezhong
 * @since 2021-01-15
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {
    @Autowired
    IUserService userService;

    @Override
    public List<CourseVo> voList(Course course) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        List<Course> courses = baseMapper.selectList(null);
        List<CourseVo> coursesVos = new ArrayList<>();
        courses.forEach(i -> {
            CourseVo courseVo = new CourseVo();
            courseVo.setName(i.getName());
            courseVo.setImage(i.getImage());
            courseVo.setId(i.getId());
            if(i.getEditUserId() != null){
                courseVo.setEditUser(userService.getById(i.getEditUserId()));
            }
            coursesVos.add(courseVo);
        });
        return coursesVos;
    }
}

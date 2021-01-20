package com.pzhu.iacaa2_0.entityVo;

import com.pzhu.iacaa2_0.entity.Course;
import com.pzhu.iacaa2_0.entity.User;
import lombok.Data;

/**
 * @author MrZhao
 */
@Data
public class CourseVo extends Course {
    User editUser;
}

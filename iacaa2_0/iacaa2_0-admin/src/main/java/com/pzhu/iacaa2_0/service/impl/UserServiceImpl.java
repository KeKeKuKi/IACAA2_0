package com.pzhu.iacaa2_0.service.impl;

import com.pzhu.iacaa2_0.entity.User;
import com.pzhu.iacaa2_0.mapper.UserMapper;
import com.pzhu.iacaa2_0.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ZhaoZezhong
 * @since 2021-01-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}

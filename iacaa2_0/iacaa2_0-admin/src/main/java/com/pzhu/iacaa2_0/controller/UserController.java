package com.pzhu.iacaa2_0.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pzhu.iacaa2_0.common.ActionResult;
import com.pzhu.iacaa2_0.config.token.PassToken;
import com.pzhu.iacaa2_0.config.token.TokenService;
import com.pzhu.iacaa2_0.entity.User;
import com.pzhu.iacaa2_0.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ZhaoZezhong
 * @since 2021-01-15
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userService;

    @Autowired
    TokenService tokenService;

    @ResponseBody
    @RequestMapping("/login")
    @PassToken
    public ActionResult list(@RequestBody User user, HttpServletRequest request) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        JSONObject jsonObject = new JSONObject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPassword());
        try{
            subject.login(token);
        }catch (AuthenticationException e){
            return ActionResult.ofFail(500,"用户名或密码错误！");
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",user.getName());
        User one = userService.getOne(queryWrapper);
        if (one == null){
            return ActionResult.ofFail(500,"后台异常，请稍后再试！");
        }

        String token1 = tokenService.getToken(one);
        jsonObject.put("token", token1);
        jsonObject.put("user", one.getName());
        return ActionResult.ofSuccess(jsonObject);
    }
}

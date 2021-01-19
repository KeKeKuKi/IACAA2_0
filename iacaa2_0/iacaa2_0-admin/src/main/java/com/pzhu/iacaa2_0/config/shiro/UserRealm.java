package com.pzhu.iacaa2_0.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
//    @Autowired
//    IUserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        System.out.println("执行认证");
//        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
//        wrapper.eq("name",token.getUsername());
//        User user = userService.getOne(wrapper);
//
//        if(user == null){
//            return null;
//        }
//        return new SimpleAuthenticationInfo("",user.getPassword(),"");
        return null;
    }
}

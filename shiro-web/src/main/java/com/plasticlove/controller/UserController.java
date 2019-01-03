package com.plasticlove.controller;

import com.plasticlove.pojo.User;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author plasticlove
 *         2018/11/2 21:31
 */
@Controller
public class UserController {

    @RequestMapping(value = "/subLogin", method = RequestMethod.POST,
            produces = "application/json;charset=utf-8")
    @ResponseBody
    public String subLogin(User user) {

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());

        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        if(subject.hasRole("admin")){
            return "有admin权限";
        }


        return "无admin权限";
    }



    // @RequiresPermissions("user:delete")
    @RequestMapping(value = "/testRole",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String testRole(){
        return "test Role 成功";
    }


    // @RequiresPermissions("user:update")
    @RequestMapping(value = "/testRole1",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String testRole1(){
        return "test Role1 成功";
    }


}

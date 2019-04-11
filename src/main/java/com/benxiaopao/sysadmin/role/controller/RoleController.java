package com.benxiaopao.sysadmin.role.controller;

import com.benxiaopao.common.supers.BaseController;
import com.benxiaopao.common.util.ViewResult;
import com.benxiaopao.sysadmin.user.service.UserService;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 角色模块请求控制层
 *
 * Created by liupoyang
 * 2019-04-06
 */
@Controller
@RequestMapping("/sysadmin/role")
@Slf4j
public class RoleController extends BaseController {
    /*@Autowired
    private UserService userService;

    @GetMapping("/login")
    public String preLogin() throws Exception {
        return "login";
    }

    @PostMapping("/doLogin")
    @ResponseBody
    public String login(String email, String password) throws Exception {
        try{
            Preconditions.checkArgument(Strings.isNullOrEmpty(email), "邮箱不能为空");
            Preconditions.checkArgument(Strings.isNullOrEmpty(password), "密码不能为空");
            userService.login(email, password);
            return ViewResult.newInstance().code(1).msg("登录成功").json();
        } catch (Exception e) {
            log.info("#登录出错：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).json();
        }
    }*/
}

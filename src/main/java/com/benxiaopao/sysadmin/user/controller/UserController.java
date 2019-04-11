package com.benxiaopao.sysadmin.user.controller;

import com.benxiaopao.common.aspect.ExcludeAuthorize;
import com.benxiaopao.common.supers.BaseController;
import com.benxiaopao.common.util.ThreadContent;
import com.benxiaopao.common.util.ViewResult;
import com.benxiaopao.sysadmin.user.service.UserService;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 用户成员模块请求控制层
 *
 * Created by liupoyang
 * 2019-04-03
 */
@Controller
@RequestMapping("/sysadmin/user")
@Slf4j
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    @ExcludeAuthorize
    public String preLogin() throws Exception {
        return "login";
    }

    @PostMapping("/doLogin")
    @ResponseBody
    @ExcludeAuthorize
    public String login(String email, String password) throws Exception {
        try{
            Preconditions.checkArgument(!Strings.isNullOrEmpty(email), "邮箱不能为空");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(password), "密码不能为空");
            userService.login(email, password);
            return ViewResult.newInstance().code(1).msg("登录成功").json();
        } catch (Exception e) {
            log.info("#登录出错：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).json();
        }
    }

    @GetMapping("/index")
    public String index() throws Exception {
        return "index";
    }

    /**
     * 进入用户修改自己的密码页面请求
     * @return
     * @throws Exception
     */
    @GetMapping(value="/updateMyPwd")
    public String preUpdateMyPwd() throws Exception{
        return "sysadmin/user/editMyPwd";
    }

    /**
     * 修改用户自己的密码请求<br />异步请求
     * @return
     * @throws Exception
     */
    @PostMapping(value="/doUpdateMyPwd")
    @ResponseBody
    public String updateMyPwd(String currentPwd, String newPwd) throws Exception{
        try{
            //检验数据
            Preconditions.checkArgument(!Strings.isNullOrEmpty(currentPwd), "当前密码不能为空");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(newPwd), "新密码不能为空");
            Preconditions.checkArgument(newPwd.length() > 5, "新密码不能少于6位");
            Preconditions.checkArgument(newPwd.length() < 17, "新密码不能多于16位");

            userService.updateMyPwd(currentPwd, newPwd);
            return ViewResult.newInstance().code(1).msg("密码修改成功!").json();
        } catch (Exception e) {
            log.info("#请求修改用户自己的密码时发生业务异常，异常信息为：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).json();
        }
    }

    @GetMapping("/logout")
    public String logout() throws Exception {
        ThreadContent.request().getSession().invalidate();
        return "login";
    }
}

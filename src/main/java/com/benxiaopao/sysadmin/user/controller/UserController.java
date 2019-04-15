package com.benxiaopao.sysadmin.user.controller;

import com.benxiaopao.common.aspect.ExcludeAuthorize;
import com.benxiaopao.common.supers.BaseController;
import com.benxiaopao.common.util.DateUtil;
import com.benxiaopao.common.util.ThreadContent;
import com.benxiaopao.common.util.ViewResult;
import com.benxiaopao.provider.dao.model.Role;
import com.benxiaopao.provider.dao.model.SysUser;
import com.benxiaopao.sysadmin.role.service.RoleService;
import com.benxiaopao.sysadmin.user.constant.UserConstant;
import com.benxiaopao.sysadmin.user.service.UserService;
import com.benxiaopao.sysadmin.user.vo.SysUserVo;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

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
    @Autowired
    private RoleService roleService;

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
     * 进入用户管理列表页面请求
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView listUserByWhere(SysUser user, @RequestParam(defaultValue="1")String page) throws Exception{
        try{
            //请求的当前页码
            int pageNum = Integer.parseInt(page);

            //分页查询用户列表
            List<SysUserVo> userVoList = userService.listUserByWherePage(user, pageNum, UserConstant.DEFAULT_PAGE_SIZE);
            //查询角色列表
            List<Role> roles = roleService.listAllRole();

            return ViewResult.newInstance()
                    .code(1).msg("进入用户管理列表页面成功")
                    .put("users", userVoList)
                    .put("user", user)
                    .put("roles", roles)
                    .view("sysadmin/user/listUser");
        } catch (Exception e) {
            log.info("#请求进入用户管理列表页面时发生业务异常，异常信息为：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).view("sysadmin/user/listUser");
        }
    }

    /**
     * 进入用户新增页面请求
     * @return
     * @throws Exception
     */
    @GetMapping("/insert")
    public ModelAndView preInsertUser() throws Exception{
        //查询角色列表
        List<Role> roles = roleService.listAllRole();
        return ViewResult.newInstance()
                .code(1).msg("进入用户新增页面成功")
                .put("roles", roles)
                .view("sysadmin/user/addUser");
    }

    /**
     * 新增用户请求<br />异步请求
     * @return
     * @throws Exception
     */
    @PostMapping("/doInsert")
    @ResponseBody
    public String insertUser(SysUser user) throws Exception {
        try{
            //检验数据
            Preconditions.checkNotNull(user, "用户成员数据不能为空");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(user.getEmail()), "成员邮箱不能为空");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(user.getPassword()), "密码不能为空");
            Preconditions.checkArgument(user.getRoleId() != null && user.getRoleId() > 0, "成员角色数据非法");

            Date date = DateUtil.now();
            //创建时间
            user.setCreateTime(date);
            //更新时间
            user.setUpdateTime(date);

            userService.insertUser(user);
            return ViewResult.newInstance().code(1).msg("新增用户成功").json();
        } catch (Exception e) {
            log.info("#请求新增用户时发生业务异常，异常信息为：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).json();
        }
    }

    /**
     * 进入用户修改页面请求
     * @return
     * @throws Exception
     */
    @GetMapping("/update")
    public ModelAndView preUpdateUser(int userId) throws Exception {
        try{
            //检验数据
            Preconditions.checkArgument(userId > 0, "成员ID非法");

            SysUser user = userService.getUserById(userId);
            //查询角色列表
            List<Role> roles = roleService.listAllRole();
            return ViewResult.newInstance()
                    .code(1).msg("进入用户修改页面成功")
                    .put("user", user)
                    .put("roles", roles)
                    .view("sysadmin/user/editUser");
        } catch (Exception e) {
            log.info("#请求进入用户修改页面时发生业务异常，异常信息为：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).view("sysadmin/user/editUser");
        }
    }

    /**
     * 修改用户请求<br />异步请求
     * @return
     * @throws Exception
     */
    @PostMapping("/doUpdate")
    @ResponseBody
    public String updateUser(SysUser user) throws Exception{
        try{
            //检验数据
            Preconditions.checkNotNull(user, "用户成员数据不能为空");
            Preconditions.checkArgument(user.getUserId() != null && user.getUserId() > 0, "成员ID非法");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(user.getEmail()), "成员邮箱不能为空");
            Preconditions.checkArgument(user.getRoleId() != null && user.getRoleId() > 0, "成员角色数据非法");

            //更新时间
            user.setUpdateTime(DateUtil.now());

            userService.updateUser(user);
            return ViewResult.newInstance().code(1).msg("修改成员信息成功!").json();
        } catch (Exception e) {
            log.info("#请求修改用户时发生业务异常，异常信息为：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).json();
        }
    }

    /**
     * 修改用户密码请求<br />异步请求
     * @return
     * @throws Exception
     */
    @PostMapping("/updatePassword")
    @ResponseBody
    public String updatePassword(SysUser user) throws Exception{
        try{
            //检验数据
            Preconditions.checkNotNull(user, "用户成员数据不能为空");
            Preconditions.checkArgument(user.getUserId() != null && user.getUserId() > 0, "成员ID非法");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(user.getPassword()), "成员密码不能为空");
            Preconditions.checkArgument(user.getPassword().length() > 5, "成员密码不能少于6位");
            Preconditions.checkArgument(user.getPassword().length() < 17, "成员密码不能多于16位");

            userService.updatePassword(user.getPassword(), user.getUserId());
            return ViewResult.newInstance().code(1).msg("修改用户密码成功").json();
        } catch (Exception e) {
            log.info("#请求修改用户密码时发生业务异常，异常信息为：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).json();
        }
    }

    /**
     * 删除用户请求<br />异步请求
     * @return
     * @throws Exception
     */
    @PostMapping("/delete")
    @ResponseBody
    public String deleteUser(int userId) throws Exception{
        try{
            //检验数据
            Preconditions.checkArgument(userId > 0, "成员ID非法");

            userService.deleteUser(userId);
            return ViewResult.newInstance().code(1).msg("删除用户成功").json();
        } catch (Exception e) {
            log.info("#请求删除用户时发生业务异常，异常信息为：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).json();
        }
    }

    /**
     * 进入用户修改自己的密码页面请求
     * @return
     * @throws Exception
     */
    @GetMapping("/updateMyPwd")
    public String preUpdateMyPwd() throws Exception{
        return "sysadmin/user/editMyPwd";
    }

    /**
     * 修改用户自己的密码请求<br />异步请求
     * @return
     * @throws Exception
     */
    @PostMapping("/doUpdateMyPwd")
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

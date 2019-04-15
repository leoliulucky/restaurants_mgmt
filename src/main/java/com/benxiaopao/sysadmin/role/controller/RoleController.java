package com.benxiaopao.sysadmin.role.controller;

import com.benxiaopao.common.supers.BaseController;
import com.benxiaopao.common.util.ViewResult;
import com.benxiaopao.provider.dao.model.Role;
import com.benxiaopao.sysadmin.role.service.RoleService;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

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
    @Autowired
    private RoleService roleService;

    /**
     * 请求所有角色列表
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView listAllRole(String roleName) throws Exception{
        List<Role> roleList = roleService.listAllRole(roleName);
        return ViewResult.newInstance()
                .code(1).msg("请求所有角色列表成功")
                .put("roles", roleList)
                .put("roleName", roleName)
                .view("sysadmin/role/listRole");
    }

    /**
     * 新增角色请求<br />异步请求
     * @return
     * @throws Exception
     */
    @PostMapping("/insert")
    @ResponseBody
    public String insertRole(Role role) throws Exception{
        try{
            //检验数据
            Preconditions.checkNotNull(role, "角色数据不能为空");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(role.getRoleName()), "角色名称不能为空");

            roleService.insertRole(role);
            return ViewResult.newInstance().code(1).msg("新增角色成功").json();
        } catch (Exception e) {
            log.info("#请求所有角色列表时发生业务异常，异常信息为：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).json();
        }
    }

    /**
     * 修改角色请求<br />异步请求
     * @return
     * @throws Exception
     */
    @PostMapping("/update")
    @ResponseBody
    public String updateRole(Role role) throws Exception{
        try{
            //检验数据
            Preconditions.checkNotNull(role, "角色数据不能为空");
            Preconditions.checkArgument(role.getRoleId() > 0, "角色ID非法");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(role.getRoleName()), "角色名称不能为空");

            roleService.updateRole(role);
            return ViewResult.newInstance().code(1).msg("修改角色成功").json();
        } catch (Exception e) {
            log.info("#请求所有角色列表时发生业务异常，异常信息为：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).json();
        }
    }

    /**
     * 删除角色请求<br />异步请求
     * @return
     * @throws Exception
     */
    @PostMapping("/delete")
    @ResponseBody
    public String deleteRole(int roleId) throws Exception{
        try{
            //检验数据
            Preconditions.checkArgument(roleId > 0, "角色ID非法");

            roleService.deleteRole(roleId);
            return ViewResult.newInstance().code(1).msg("删除角色成功").json();
        } catch (Exception e) {
            log.info("#请求所有角色列表时发生业务异常，异常信息为：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).json();
        }
    }

    /**
     * 根据角色id获取角色对象请求<br />异步请求
     * @return
     * @throws Exception
     */
    @PostMapping("/get")
    @ResponseBody
    public String getRoleById(int roleId) throws Exception{
        try{
            //检验数据
            Preconditions.checkArgument(roleId > 0, "角色ID非法");

            Role role = roleService.getRoleById(roleId);
            return ViewResult.newInstance().code(1).msg("获取角色成功").put("role", role).json();
        } catch (Exception e) {
            log.info("#请求根据角色id获取角色对象时发生业务异常，异常信息为：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).json();
        }
    }

    /**
     * 请求为角色授权页面
     * @return
     * @throws Exception
     */
    @GetMapping("/listRole4Purview")
    public ModelAndView listRole4Purview() throws Exception{
        List<Role> roleList = roleService.listAllRole();
        return ViewResult.newInstance()
                .code(1).msg("请求为角色授权页面成功")
                .put("roles", roleList)
                .view("sysadmin/role/listRole4Purview");
    }

    /**
     * 授权角色菜单权限请求<br />异步请求
     * @return
     * @throws Exception
     */
    @PostMapping("/auth")
    @ResponseBody
    public String auth(int roleId, String menuIds) throws Exception{
        try{
            //检验数据
            Preconditions.checkArgument(roleId > 0, "请求参数非法");

            roleService.updateRoleMenuPurview(roleId, menuIds);
            return ViewResult.newInstance().code(1).msg("授权成功").json();
        } catch (Exception e) {
            log.info("#请求授权角色菜单权限时发生业务异常，异常信息为：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).json();
        }
    }
}

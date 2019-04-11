package com.benxiaopao.sysadmin.menu.controller;

import com.benxiaopao.common.supers.BaseController;
import com.benxiaopao.common.util.ViewResult;
import com.benxiaopao.provider.dao.model.Menu;
import com.benxiaopao.sysadmin.menu.service.MenuService;
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
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 菜单模块请求控制层
 *
 * Created by liupoyang
 * 2019-04-06
 */
@Controller
@RequestMapping("/sysadmin/menu")
@Slf4j
public class MenuController extends BaseController {
    @Autowired
    private MenuService menuService;

    /**
     * 获取用户可见菜单列表请求
     * @return
     * @throws Exception
     */
    @Deprecated
    @PostMapping("/listVisible")
    @ResponseBody
    public String listVisibleMenu() throws Exception {
        List<Menu> menuList = menuService.listVisibleMenu();
        return ViewResult.newInstance()
                .code(1).msg("获取用户可见菜单列表成功")
                .put("menus", menuList)
                .json();
    }

}

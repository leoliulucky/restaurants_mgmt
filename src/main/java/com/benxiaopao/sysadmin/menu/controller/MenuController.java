package com.benxiaopao.sysadmin.menu.controller;

import com.benxiaopao.common.supers.BaseController;
import com.benxiaopao.common.util.ViewResult;
import com.benxiaopao.provider.dao.enums.EitherEnum;
import com.benxiaopao.provider.dao.model.Menu;
import com.benxiaopao.sysadmin.menu.service.MenuService;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import java.util.Map;

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

    /**
     * 进入菜单管理页面请求
     * @return
     * @throws Exception
     */
    @GetMapping("/listAll")
    public String preListAllMenu() throws Exception {
        return "sysadmin/menu/listAllMenu";
    }

    /**
     * 请求所有菜单列表<br />异步请求
     * @return
     * @throws Exception
     */
    @PostMapping("/doListAll")
    @ResponseBody
    public String listAllMenu() throws Exception{
        try{
            List<Menu> menuList = menuService.listAllMenu();
            return ViewResult.newInstance().code(1).msg("请求所有菜单列表成功").put("menus", menuList).json();
        } catch (Exception e) {
            log.info("#请求所有菜单列表时发生业务异常，异常信息为：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).json();
        }
    }

    /**
     * 新增菜单请求<br />异步请求
     * @return
     * @throws Exception
     */
    @PostMapping("/insert")
    @ResponseBody
    public String insertMenu(Menu menu) throws Exception{
        try{
            //检验数据
            Preconditions.checkNotNull(menu, "菜单数据不能为空");
            Preconditions.checkArgument(menu.getParentId() >= 0, "菜单父节点数据非法");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(menu.getMenuName()), "菜单名称不能为空");
            Preconditions.checkArgument(menu.getShowOrder() >= 0, "菜单显示顺序数据非法");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(menu.getUrl()), "菜单URL不能为空");
            Preconditions.checkArgument((menu.getIsVisible() == EitherEnum.NO.getValue() || menu.getIsVisible() == EitherEnum.YES.getValue()), "菜单是否显示数据非法");

            menuService.insertMenu(menu);
            return ViewResult.newInstance().code(1).msg("新增菜单成功").json();
        } catch (Exception e) {
            log.info("#请求新增菜单时发生业务异常，异常信息为：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).json();
        }
    }

    /**
     * 修改菜单请求<br />异步请求
     * @return
     * @throws Exception
     */
    @PostMapping("/update")
    @ResponseBody
    public String updateMenu(Menu menu) throws Exception{
        try{
            //检验数据
            Preconditions.checkNotNull(menu, "菜单数据不能为空");
            Preconditions.checkArgument(menu.getMenuId() > 0, "菜单ID数据非法");
            Preconditions.checkArgument(menu.getParentId() >= 0, "菜单父节点数据非法");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(menu.getMenuName()), "菜单名称不能为空");
            Preconditions.checkArgument(menu.getShowOrder() >= 0, "菜单显示顺序数据非法");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(menu.getUrl()), "菜单URL不能为空");
            Preconditions.checkArgument((menu.getIsVisible() == EitherEnum.NO.getValue() || menu.getIsVisible() == EitherEnum.YES.getValue()), "菜单是否显示数据非法");

            menuService.updateMenu(menu);
            return ViewResult.newInstance().code(1).msg("修改菜单成功").json();
        } catch (Exception e) {
            log.info("#请求修改菜单时发生业务异常，异常信息为：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).json();
        }
    }

    /**
     * 删除菜单请求<br />异步请求
     * @return
     * @throws Exception
     */
    @PostMapping("/delete")
    @ResponseBody
    public String deleteMenu(int menuId) throws Exception{
        try{
            //检验数据
            Preconditions.checkArgument(menuId > 0, "菜单ID非法");

            menuService.deleteMenu(menuId);
            return ViewResult.newInstance().code(1).msg("删除菜单成功").json();
        } catch (Exception e) {
            log.info("#请求删除菜单时发生业务异常，异常信息为：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).json();
        }
    }

    /**
     * 根据角色id获取菜单权限列表请求<br />异步请求
     * @return
     * @throws Exception
     */
    @PostMapping("/listPurview")
    @ResponseBody
    public String listMenuPurviewByRoleId(int roleId) throws Exception{
        try{
            //检验数据
            Preconditions.checkArgument(roleId > 0, "请求参数非法");

            Map<String, Integer> menuIdMap = menuService.listMenuPurviewByRoleId(roleId);
            return ViewResult.newInstance().code(1).msg("获取菜单权限列表成功").put("menuIds", menuIdMap).json();
        } catch (Exception e) {
            log.info("#请求根据角色id获取角色对象时发生业务异常，异常信息为：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).json();
        }
    }

}

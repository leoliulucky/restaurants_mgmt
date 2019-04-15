package com.benxiaopao.sysadmin.menu.service;

import com.benxiaopao.common.supers.BaseService;
import com.benxiaopao.common.util.ThreadContent;
import com.benxiaopao.provider.dao.enums.EitherEnum;
import com.benxiaopao.provider.dao.map.MenuMapper;
import com.benxiaopao.provider.dao.map.RoleMapper;
import com.benxiaopao.provider.dao.map.RoleMenuPurviewMapper;
import com.benxiaopao.provider.dao.map.SysUserMapper;
import com.benxiaopao.provider.dao.model.*;
import com.benxiaopao.sysadmin.user.constant.UserConstant;
import com.benxiaopao.sysadmin.user.vo.SysUserVo;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * 菜单业务逻辑服务处理类
 *
 * Created by liupoyang
 * 2019-04-06
 */
@Service
@Slf4j
public class MenuService extends BaseService {

    @Resource
    private MenuMapper menuMapper;
    @Resource
    private RoleMenuPurviewMapper roleMenuPurviewMapper;

    /**
     * 获取用户可见菜单列表
     * @return List<Menu> 可见菜单列表
     */
    public List<Menu> listVisibleMenu() throws Exception {
        //当前登录用户
        SysUserVo user = (SysUserVo) currentUser();

        //查询可见菜单列表
        List<Menu> menus = this.listVisibleMenuByRoleId(user.getRoleId());
        return menus;
    }

    /**
     * 根据条件获取菜单列表
     *
     * @param menu 查询条件参数对象
     * @return List<Menu> 菜单列表
     */
    public List<Menu> listMenuByWhere(Menu menu) throws Exception {
        MenuExample example = new MenuExample();
        MenuExample.Criteria criteria = example.createCriteria();

        //菜单名称
        if(menu != null && StringUtils.hasText(menu.getMenuName())){
            criteria.andMenuNameLike(menu.getMenuName() + "%");
        }
        //URL
        if(menu != null && StringUtils.hasText(menu.getUrl())){
            criteria.andUrlEqualTo(menu.getUrl());
        }

        return menuMapper.selectByExample(example);
    }

    /**
     * 根据条件获取角色菜单权限对象
     *
     * @param roleMenuPurviewKey 查询条件参数对象
     * @return List<RoleMenuPurviewKey> 角色菜单权限列表
     */
    public List<RoleMenuPurviewKey> listRoleMenuPurviewByWhere(
            RoleMenuPurviewKey roleMenuPurviewKey) throws Exception {
        //角色id
        int roleId = 0;
        if(roleMenuPurviewKey.getRoleId() != null && roleMenuPurviewKey.getRoleId() > 0){
            roleId = roleMenuPurviewKey.getRoleId();
        }
        //菜单id
        int menuId = 0;
        if(roleMenuPurviewKey.getMenuId() != null && roleMenuPurviewKey.getMenuId() > 0){
            menuId = roleMenuPurviewKey.getMenuId();
        }
//        //读取缓存菜单对象
//        String key = String.format("%s:MenuServiceImpl:listRoleMenuPurviewByWhere:%d:%d", JedisUtil.getInstance().getKeyPrefix(), roleId, menuId);
//        List<RoleMenuPurviewKey> roleMenuPurviewKeyList = (List<RoleMenuPurviewKey>) JedisUtil.getInstance().get(key);
//        if(!CollectionUtils.isEmpty(roleMenuPurviewKeyList)){
//            return roleMenuPurviewKeyList;
//        }

        //缓存读取角色菜单权限表数据
        RoleMenuPurviewExample example = new RoleMenuPurviewExample();
        com.benxiaopao.provider.dao.model.RoleMenuPurviewExample.Criteria criteria = example.createCriteria();
        //角色id
        if(roleId > 0){
            criteria.andRoleIdEqualTo(roleId);
        }
        //菜单id
        if(menuId > 0){
            criteria.andMenuIdEqualTo(menuId);
        }
        List<RoleMenuPurviewKey> roleMenuPurviewKeyList = roleMenuPurviewMapper.selectByExample(example);
//        //设置缓存
//        JedisUtil.getInstance().set(key, roleMenuPurviewKeyList, GlobalConstant.DEFAULT_CACHE_TIME);
        return roleMenuPurviewKeyList;
    }

    /**
     * 根据角色ID获取可见菜单列表
     *
     * @param roleId 角色id
     * @return List<Menu> 可见菜单列表
     */
    private List<Menu> listVisibleMenuByRoleId(int roleId) throws Exception {
//        //读取缓存角色菜单权限数据
//        String key = String.format("%s:MenuServiceImpl:listVisibleMenuByRoleId:%d", JedisUtil.getInstance().getKeyPrefix(), roleId);
//        List<Menu> menus = (List<Menu>) JedisUtil.getInstance().get(key);
//        if(!CollectionUtils.isEmpty(menus)){
//            log.debug("#读了缓存....");
//            return menus;
//        }
        log.debug("#未读缓存....");

        //读取角色菜单权限表数据
        RoleMenuPurviewKey roleMenuPurviewKey = new RoleMenuPurviewKey();
        roleMenuPurviewKey.setRoleId(roleId);
        List<RoleMenuPurviewKey> roleMenuPurviews = this.listRoleMenuPurviewByWhere(roleMenuPurviewKey);

        List<Menu> menus = new ArrayList<Menu>();
        Map<Integer, Menu> menuMap = new HashMap<Integer, Menu>();
        for (RoleMenuPurviewKey item : roleMenuPurviews) {
            //读取菜单信息
            Menu menu = this.getMenuById(item.getMenuId());
            if (menu != null && menu.getIsVisible() == EitherEnum.YES.getValue()) {
                menuMap.put(menu.getMenuId(), menu);
                menuMap = loadParentId(menu, menuMap);
            }
        }

        menus.addAll(menuMap.values());
        Collections.sort(menus, new Comparator<Menu>() {

            @Override
            public int compare(Menu menu1, Menu menu2) {
                int cr = 0;
                int a = menu1.getParentId() - menu2.getParentId();
                if(a != 0){
                    cr = (a>0)?2:-1;
                }else{
                    a = menu1.getShowOrder() - menu2.getShowOrder();
                    if(a != 0){
                        cr = (a>0)?1:-2;
                    }
                }
                return cr;
            }
        });
//        //设置缓存
//        JedisUtil.getInstance().set(key, menus, GlobalConstant.DEFAULT_CACHE_TIME);
        return menus;
    }

    /**
     * 根据菜单ID获取菜单
     *
     * @param menuId 菜单ID
     * @return Menu 菜单
     */
    private Menu getMenuById(int menuId) throws Exception {
//        //读取缓存菜单对象
//        String key = String.format("%s:MenuServiceImpl:getMenuById:%d", JedisUtil.getInstance().getKeyPrefix(), menuId);
//        Menu menu = (Menu) JedisUtil.getInstance().get(key);
//        if(menu != null){
//            return menu;
//        }

        Menu menu = menuMapper.selectByPrimaryKey(menuId);
//        //设置缓存
//        JedisUtil.getInstance().set(key, menu, GlobalConstant.DEFAULT_CACHE_TIME);
        return menu;
    }

    /**
     * 查询可见菜单时用到的递归方法<br />私有方法
     * @param menu
     * @param menuMap
     * @return
     */
    private Map<Integer, Menu> loadParentId(Menu menu, Map<Integer, Menu> menuMap) throws Exception {
        if (!menu.getParentId().equals(0) && !menuMap.containsKey(menu.getParentId())) {
            //缓存读取菜单信息
            Menu m = this.getMenuById(menu.getParentId());
            if (m.getIsVisible() == EitherEnum.YES.getValue()) {
                menuMap.put(m.getMenuId(), m);
                return loadParentId(m, menuMap);
            }
        }
        return menuMap;
    }

    /**
     * 获取所有菜单列表
     * @return List<Menu> 菜单列表
     */
    public List<Menu> listAllMenu() throws Exception {
        //查询所有菜单列表
        List<Menu> menus = this.listMenuByWhere(null);
        return menus;
    }

    /**
     * 新增菜单
     * @param menu 要插入的菜单数据对象
     */
    public void insertMenu(Menu menu) throws Exception {
        //清除缓存
//        String key = String.format("%s:MenuServiceImpl:listVisibleMenuByRoleId:*", JedisUtil.getInstance().getKeyPrefix());
//        JedisUtil.getInstance().delBatch(key);
//        key = String.format("%s:MenuServiceImpl:listRoleMenuPurviewByWhere:*:0", JedisUtil.getInstance().getKeyPrefix());
//        JedisUtil.getInstance().delBatch(key);

        //新增菜单
        log.info("#新增菜单，其中 menuName=" + menu.getMenuName());
        int records = menuMapper.insertSelective(menu);
        Preconditions.checkArgument(records > 0, "新增菜单失败");
    }

    /**
     * 修改菜单
     * @param menu 要更新的菜单数据对象
     */
    public void updateMenu(Menu menu) throws Exception {
        //清除缓存
//        String key = String.format("%s:MenuServiceImpl:getMenuById:%d", JedisUtil.getInstance().getKeyPrefix(), menu.getMenuId());
//        JedisUtil.getInstance().del(key);
//        key = String.format("%s:MenuServiceImpl:listVisibleMenuByRoleId:*", JedisUtil.getInstance().getKeyPrefix());
//        JedisUtil.getInstance().delBatch(key);

        //更新菜单
        log.info("#更新菜单，其中menuId=" + menu.getMenuId() + " menuName=" + menu.getMenuName());
        int records = menuMapper.updateByPrimaryKeySelective(menu);
        Preconditions.checkArgument(records > 0, "修改菜单失败");
    }

    /**
     * 删除菜单
     * @param menuId 菜单id
     */
    public void deleteMenu(int menuId) throws Exception {
        //检验是否包含有子菜单
        MenuExample example = new MenuExample();
        example.createCriteria().andParentIdEqualTo(menuId);
        if(menuMapper.countByExample(example) > 0){
            throw new IllegalAccessException("要删除的菜单包含有子菜单，不允许删除！");
        }

        //检验是否包含有相关菜单已授权
        RoleMenuPurviewExample examplePurview = new RoleMenuPurviewExample();
        examplePurview.createCriteria().andMenuIdEqualTo(menuId);
        if(roleMenuPurviewMapper.countByExample(examplePurview) > 0){
            throw new IllegalAccessException("要删除的菜单已授权角色，不允许删除！");
        }

        //清除缓存
//        String key = String.format("%s:MenuServiceImpl:getMenuById:%d", JedisUtil.getInstance().getKeyPrefix(), menuId);
//        JedisUtil.getInstance().del(key);
//        key = String.format("%s:MenuServiceImpl:listVisibleMenuByRoleId:*", JedisUtil.getInstance().getKeyPrefix());
//        JedisUtil.getInstance().delBatch(key);
//        key = String.format("%s:MenuServiceImpl:listRoleMenuPurviewByWhere:*:%d", JedisUtil.getInstance().getKeyPrefix(), menuId);
//        JedisUtil.getInstance().delBatch(key);

        //删除菜单
        log.info("#删除菜单，其中menuId=" + menuId);
        int records = menuMapper.deleteByPrimaryKey(menuId);
        Preconditions.checkArgument(records > 0, "删除菜单失败");
    }

    /**
     * 根据角色id获取菜单权限列表
     * @param roleId 角色id
     * @return Map<String, Integer> 菜单id键值对
     */
    public Map<String, Integer> listMenuPurviewByRoleId(int roleId) throws Exception {
        //查询菜单权限列表
        RoleMenuPurviewKey roleMenuPurviewKey = new RoleMenuPurviewKey();
        roleMenuPurviewKey.setRoleId(roleId);
        List<RoleMenuPurviewKey> rmpkList = this.listRoleMenuPurviewByWhere(roleMenuPurviewKey);

        Map<String, Integer> menuIds = new HashMap<String, Integer>();
        for(RoleMenuPurviewKey item : rmpkList){
            menuIds.put(String.valueOf(item.getMenuId()), item.getMenuId());
        }
        return menuIds;
    }
}

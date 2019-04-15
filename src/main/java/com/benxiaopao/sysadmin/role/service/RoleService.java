package com.benxiaopao.sysadmin.role.service;

import com.benxiaopao.common.supers.BaseService;
import com.benxiaopao.provider.dao.map.RoleMapper;
import com.benxiaopao.provider.dao.map.RoleMenuPurviewMapper;
import com.benxiaopao.provider.dao.model.*;
import com.benxiaopao.sysadmin.menu.service.MenuService;
import com.benxiaopao.sysadmin.user.service.UserService;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.NumberUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色业务逻辑服务处理类
 *
 * Created by liupoyang
 * 2019-04-06
 */
@Service
@Slf4j
public class RoleService extends BaseService {

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleMenuPurviewMapper roleMenuPurviewMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;

    /**
     * 获取所有成员角色列表
     * @return List<Role> 角色列表
     */
    public List<Role> listAllRole() throws Exception {
        return this.listAllRole(null);
    }

    /**
     * 获取所有成员角色列表
     * @param roleName
     * @return List<Role> 角色列表
     */
    public List<Role> listAllRole(String roleName) throws Exception {
        RoleExample example = new RoleExample();
        if(!Strings.isNullOrEmpty(roleName)){
            example.createCriteria().andRoleNameLike("%" + roleName + "%");
        }
        return roleMapper.selectByExample(example);
    }

    /**
     * 新增角色
     * @param role 要插入的角色数据对象
     * @return int 插入记录数
     */
    public void insertRole(Role role) throws Exception {
        log.info("#新增角色，其中 roleName=" + role.getRoleName());
        int records = roleMapper.insert(role);
        Preconditions.checkArgument(records > 0, "新增角色失败");
    }

    /**
     * 修改角色
     * @param role 要更新的角色数据对象
     * @return int 更新记录数
     */
    public void updateRole(Role role) throws Exception {
        log.info("#更新角色，其中roleId=" + role.getRoleId() + " roleName=" + role.getRoleName());
        int records = roleMapper.updateByPrimaryKeySelective(role);
        Preconditions.checkArgument(records > 0, "修改角色失败");
    }

    /**
     * 删除角色
     * @param roleId 角色id
     */
    public void deleteRole(int roleId) throws Exception {
        //检验是否关联了用户
        SysUser user = new SysUser();
        user.setRoleId(roleId);
        Preconditions.checkArgument(CollectionUtils.isEmpty(userService.listUserByWhere(user, 0, -1)), "该角色关联了用户，不允许删除！");

        //检验是否关联菜单
        RoleMenuPurviewKey roleMenuPurviewKey = new RoleMenuPurviewKey();
        roleMenuPurviewKey.setRoleId(roleId);
        Preconditions.checkArgument(CollectionUtils.isEmpty(menuService.listRoleMenuPurviewByWhere(roleMenuPurviewKey)), "该角色已经关联菜单，不允许删除！");

        //删除角色
        log.info("#删除角色，其中roleId=" + roleId);
        int records = roleMapper.deleteByPrimaryKey(roleId);
        Preconditions.checkArgument(records > 0, "删除角色失败");
    }

    /**
     * 根据角色id获取角色对象
     * @param roleId 角色id
     */
    public Role getRoleById(int roleId) throws Exception {
        //根据角色id查询角色对象
        Role role = roleMapper.selectByPrimaryKey(roleId);
        Preconditions.checkNotNull(role, "获取角色失败");
        return role;
    }

    /**
     * 修改角色的权限，先删除由于原有权限，再加入新权限
     * @param roleId 要更新的角色id
     * @param menuIdsStr 新加入的菜单字符串
     */
    public void updateRoleMenuPurview(int roleId, String menuIdsStr) throws Exception {
        int[] menuIdArr = null;
        if(!Strings.isNullOrEmpty(menuIdsStr)){
            //解析字符串
            String[] menuIdStrArr = menuIdsStr.split(",");
            menuIdArr = new int[menuIdStrArr.length];
            for(int i = 0; i < menuIdStrArr.length; i++){
                if (menuIdStrArr[i].trim().equals("")){
                    continue;
                }
                menuIdArr[i] = NumberUtils.parseNumber(menuIdStrArr[i], Integer.class);
            }
        }
        //更新角色菜单权限
        this.updateRoleMenuPurview(roleId, menuIdArr);
    }

    private void updateRoleMenuPurview(int roleId, int[] menuIdArray) throws Exception {
//        //清除缓存
//        String key = String.format("%s:MenuServiceImpl:listVisibleMenuByRoleId:%d", JedisUtil.getInstance().getKeyPrefix(), roleId);
//        JedisUtil.getInstance().delBatch(key);
//        key = String.format("%s:MenuServiceImpl:listRoleMenuPurviewByWhere:%d:*", JedisUtil.getInstance().getKeyPrefix(), roleId);
//        JedisUtil.getInstance().delBatch(key);

        //删除由于原有权限
        RoleMenuPurviewExample example = new RoleMenuPurviewExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        int deleteRecords = roleMenuPurviewMapper.deleteByExample(example);
        log.info("#删除角色菜单权限，其中 roleId=" + roleId + "，删除的记录条数为" + deleteRecords);

        if(menuIdArray != null && menuIdArray.length > 0){
            //遍历插入新权限
            RoleMenuPurviewKey purview = null;
            int insertResult = 0;
            for(int item : menuIdArray){
                purview = new RoleMenuPurviewKey();
                purview.setRoleId(roleId);
                purview.setMenuId(item);
                insertResult = roleMenuPurviewMapper.insert(purview);
                log.info("#插入角色菜单权限，其中 roleId=" + roleId + "，menuId=" + item + "，插入返回结果为" + insertResult);
            }
        }
    }

}

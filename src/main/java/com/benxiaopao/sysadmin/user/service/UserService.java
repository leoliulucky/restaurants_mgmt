package com.benxiaopao.sysadmin.user.service;

import com.benxiaopao.biz.restaurant.service.RestaurantService;
import com.benxiaopao.biz.restaurant.vo.RestaurantVO;
import com.benxiaopao.common.supers.BaseService;
import com.benxiaopao.common.util.Pagination;
import com.benxiaopao.common.util.ThreadContent;
import com.benxiaopao.provider.dao.map.RoleMapper;
import com.benxiaopao.provider.dao.map.SysUserMapper;
import com.benxiaopao.provider.dao.model.Role;
import com.benxiaopao.provider.dao.model.SysUser;
import com.benxiaopao.provider.dao.model.SysUserExample;
import com.benxiaopao.sysadmin.role.service.RoleService;
import com.benxiaopao.sysadmin.user.constant.UserConstant;
import com.benxiaopao.sysadmin.user.vo.SysUserVo;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户成员业务逻辑服务处理类
 *
 * Created by liupoyang
 * 2019-04-03
 */
@Service
@Slf4j
public class UserService extends BaseService {

    @Resource
    private SysUserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RestaurantService restaurantService;

    /**
     * 用户登录
     * @param email 邮箱
     * @param password 密码
     */
    public void login(String email, String password) throws Exception {
        log.info("#用户开始登录，其中 email={}", email);
        log.info("#用户开始登录，其中 password={}", password);
        password = DigestUtils.md5DigestAsHex((password + UserConstant.PASSWORD_MD5_KEY).getBytes());
        log.info("#用户开始登录，其中 password md5={}", password);

        SysUserExample example = new SysUserExample();
        example.createCriteria().andEmailEqualTo(email);
        List<SysUser> list = userMapper.selectByExample(example);
        Preconditions.checkArgument(!CollectionUtils.isEmpty(list), "邮箱不正确");
        SysUser user = list.get(0);
        Preconditions.checkArgument(user.getPassword().equals(password), "密码不正确");

        Role role = roleMapper.selectByPrimaryKey(user.getRoleId());
        Preconditions.checkNotNull(role, "邮用户无角色信息，数据不正确箱不正确");

        SysUserVo vo = new SysUserVo();
        vo.copy(user).setRoleName(role.getRoleName());

        ThreadContent.request().getSession().setAttribute(UserConstant.SESSION_USER_OBJ, vo);
    }

    /**
     * 根据条件获取用户列表
     * @param user 条件参数用户对象
     * @return List<User> 成员列表
     */
    public List<SysUser> listUserByWhere(SysUser user) throws Exception {
        //查询用户列表
        List<SysUser> users = this.listUserByWhere(user, null, null);
        return users;
    }

    /**
     * 根据条件获取用户列表，带分页
     * @param user 条件参数用户对象
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @return List<UserVO> 成员列表
     */
    public List<SysUserVo> listUserByWherePage(SysUser user, int pageNum, int pageSize) throws Exception {
        Pagination pagination = Pagination.currentPagination(pageNum, pageSize);
        //查询用户列表
        List<SysUser> users = this.listUserByWhere(user, pagination.start(), pagination.getPageSize());
        int count = this.countUserByWhere(user);
        pagination.setTotalCount(count);

        //当前登录用户
        SysUserVo userSession = (SysUserVo) currentUser();

        //查询餐馆列表
        List<RestaurantVO> restaurants = restaurantService.listRestaurantByWhere(null);
        Map<Integer, String> restaurantNameMap = Maps.newHashMap();
        for(RestaurantVO restaurant : restaurants){
            restaurantNameMap.put(restaurant.getRestaurantId(), restaurant.getRestaurantName());
        }

        //遍历用户列表，以转换相应属性呈现到前端
        List<SysUserVo> result = new ArrayList<SysUserVo>();
        Role role = null;
        SysUserVo vo = null;
        for(SysUser item : users){
            role = roleService.getRoleById(item.getRoleId());

            vo = new SysUserVo();
            vo.copy(item).setRoleName(role == null ? "" : role.getRoleName());
            vo.setAllowOperate(!userSession.getUserId().equals(item.getUserId()));
            vo.setRestaurantName(restaurantNameMap.get(item.getOrgId()) == null ? "" : restaurantNameMap.get(item.getOrgId()));
            result.add(vo);
        }
        return result;
    }

    public List<SysUser> listUserByWhere(SysUser user, Integer start, Integer offset)
            throws Exception {
        SysUserExample example = _buildExampleByWhere(user);

        //由参数决定是否进行分页
        if(start != null && start >= 0 && offset != null && offset > 0){
            //排序条件、限制查询记录数
            example.setOrderByClause("userId asc limit " + start + ", " + offset);
        }

        return userMapper.selectByExample(example);
    }

    public int countUserByWhere(SysUser user) throws Exception {
        SysUserExample example = _buildExampleByWhere(user);
        long count = userMapper.countByExample(example);
        return Integer.valueOf(String.valueOf(count));
    }

    /**
     * 构建查询条件对象<br />私有方法
     * @param user 查询条件参数对象
     * @return UserExample
     */
    private SysUserExample _buildExampleByWhere(SysUser user){
        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria();

        //邮箱
        if(user != null && StringUtils.hasText(user.getEmail())){
            criteria.andEmailEqualTo(user.getEmail());
        }
        //成员姓名
        if(user != null && StringUtils.hasText(user.getRealName())){
            criteria.andRealNameLike(user.getRealName() + "%");
        }
        //成员角色
        if(user != null && user.getRoleId() != null && user.getRoleId() > 0){
            criteria.andRoleIdEqualTo(user.getRoleId());
        }
        //状态
        if(user != null && user.getStatus() != null && user.getStatus() >= 0){
            criteria.andStatusEqualTo(user.getStatus());
        }
        return example;
    }

    /**
     * 新增用户
     * @param user 要插入的用户数据对象
     */
    public void insertUser(SysUser user) throws Exception {
        //检验角色
        Role role = roleService.getRoleById(user.getRoleId());
        Preconditions.checkNotNull(role, "添加用户对应角色不存在");

        //检验邮箱是否已存在
        SysUser temp = this.getUserByEmail(user.getEmail());
        Preconditions.checkArgument(temp == null, "邮箱已存在");

        //密码md5加密
        String password = DigestUtils.md5DigestAsHex((user.getPassword() + UserConstant.PASSWORD_MD5_KEY).getBytes());
        user.setPassword(password);

        //新增用户
        log.info("#新增用户，其中 email=" + user.getEmail() + " realName=" + user.getRealName());
        int records = userMapper.insert(user);
        Preconditions.checkArgument(records > 0, "新增用户失败");
    }

    /**
     * 修改用户
     * @param user 要更新的用户数据对象
     */
    public void updateUser(SysUser user) throws Exception {
        //当前登录用户
        SysUserVo userSession = (SysUserVo) currentUser();
        //检验 不允许修改自己
        Preconditions.checkArgument(user.getUserId() != userSession.getUserId(), "用户 " + user.getEmail() + " 不允许被修改");

        //检验角色
        Role role = roleService.getRoleById(user.getRoleId());
        Preconditions.checkNotNull(role, "修改用户对应角色不存在");

        //检验邮箱是否已存在
        SysUser temp = new SysUser();
        temp.setEmail(user.getEmail());
        List<SysUser> userList = this.listUserByWhere(temp, null, null);
        Preconditions.checkArgument(CollectionUtils.isEmpty(userList) || (userList.size() == 1 && userList.get(0).getUserId().equals(user.getUserId())), "邮箱已存在");

        if(StringUtils.hasText(user.getPassword())){
            //密码md5加密
            String password = DigestUtils.md5DigestAsHex((user.getPassword() + UserConstant.PASSWORD_MD5_KEY).getBytes());
            user.setPassword(password);
        }

        //更新用户
        log.info("#更新用户，其中userId=" + user.getUserId() + " email=" + user.getEmail() + " realName=" + user.getRealName());
        int records = userMapper.updateByPrimaryKeySelective(user);
        Preconditions.checkArgument(records > 0, "修改用户失败");
    }

    /**
     * 删除用户
     * @param userId 用户id
     */
    public void deleteUser(int userId) throws Exception {
        //当前登录用户
        SysUserVo userSession = (SysUserVo) currentUser();
        //检验 不允许删除自己
        Preconditions.checkArgument(userId != userSession.getUserId(), "用户 " + userId + " 不允许被修改");

        //检验角色
        SysUser queryUser = this.getUserById(userId);
        Preconditions.checkNotNull(queryUser, "要删除的用户不存在");
        Role role = roleService.getRoleById(queryUser.getRoleId());
        Preconditions.checkNotNull(role, "要删除的用户数据错误，不允许删除，请联系管理员");

        //删除用户
        log.info("#删除用户，其中userId=" + userId);
        int records = userMapper.deleteByPrimaryKey(userId);
        Preconditions.checkArgument(records > 0, "删除用户失败");
    }

    /**
     * 根据用户id获取用户对象
     * @param userId 用户id
     */
    public SysUser getUserById(int userId) throws Exception {
        //根据用户id查询用户对象
        SysUser user = userMapper.selectByPrimaryKey(userId);
        Preconditions.checkNotNull(user, "获取用户成员失败");
        return user;
    }

    /**
     * 修改用户自己的密码
     * @param currentPwd 用户输入的当前密码
     * @param newPwd 用户输入的新密码
     */
    public void updateMyPwd(String currentPwd, String newPwd) throws Exception {
        //当前登录用户
        SysUserVo userSession = (SysUserVo) currentUser();
        //查询当前登录用户信息
        SysUser user = this.getUserById(userSession.getUserId());
        //密码md5加密
        String md5Pwd = DigestUtils.md5DigestAsHex((currentPwd + UserConstant.PASSWORD_MD5_KEY).getBytes());
        Preconditions.checkArgument(md5Pwd.equals(user.getPassword()), "密码错误");

        //密码md5加密
        newPwd = DigestUtils.md5DigestAsHex((newPwd + UserConstant.PASSWORD_MD5_KEY).getBytes());
        user.setPassword(newPwd);

        //更新用户
        log.info("#更新用户，其中userId=" + user.getUserId() + " email=" + user.getEmail() + " realName=" + user.getRealName());
        int records = userMapper.updateByPrimaryKeySelective(user);
        Preconditions.checkArgument(records > 0, "修改密码失败");
    }

    public SysUser getUserByEmail(String email) throws Exception {
        SysUserExample example = new SysUserExample();
        example.createCriteria().andEmailEqualTo(email);
        List<SysUser> list = userMapper.selectByExample(example);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    /**
     * 修改用户密码
     * @param newPwd 输入的新密码
     * @param userId 用户id
     */
    public void updatePassword(String newPwd, int userId) throws Exception {
        SysUser user = new SysUser();
        user.setUserId(userId);
        //密码md5加密
        newPwd = DigestUtils.md5DigestAsHex((newPwd + UserConstant.PASSWORD_MD5_KEY).getBytes());
        user.setPassword(newPwd);

        //更新用户
        log.info("#更新用户，其中userId=" + user.getUserId() + " email=" + user.getEmail() + " realName=" + user.getRealName());
        int records = userMapper.updateByPrimaryKeySelective(user);
        Preconditions.checkArgument(records > 0, "修改密码失败");
    }
}

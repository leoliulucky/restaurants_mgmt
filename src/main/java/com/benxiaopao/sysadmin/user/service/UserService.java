package com.benxiaopao.sysadmin.user.service;

import com.benxiaopao.common.supers.BaseService;
import com.benxiaopao.common.util.ThreadContent;
import com.benxiaopao.provider.dao.map.RoleMapper;
import com.benxiaopao.provider.dao.map.SysUserMapper;
import com.benxiaopao.provider.dao.model.Role;
import com.benxiaopao.provider.dao.model.SysUser;
import com.benxiaopao.provider.dao.model.SysUserExample;
import com.benxiaopao.sysadmin.user.constant.UserConstant;
import com.benxiaopao.sysadmin.user.vo.SysUserVo;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;

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
}

package com.benxiaopao.sysadmin.role.service;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

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

    /*@Resource
    private SysUserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;

    *//**
     * 用户登录
     * @param email 邮箱
     * @param password 密码
     *//*
    public void login(String email, String password) throws Exception {
        log.info("#用户开始登录，其中 email={}", email);
        password = DigestUtils.md5DigestAsHex((password + UserConstant.PASSWORD_MD5_KEY).getBytes());

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
    }*/

}

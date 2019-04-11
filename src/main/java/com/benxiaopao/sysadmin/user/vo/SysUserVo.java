package com.benxiaopao.sysadmin.user.vo;

import com.benxiaopao.provider.dao.model.SysUser;

/**
 * 用户视图对象模型类<br />扩展com.benxiaopao.provider.dao.model.User属性
 * 
 * @author liupoyang
 * @since 2019-04-04
 */
public class SysUserVo extends SysUser {
	/**
	 * 角色id对应的名称
	 */
	private String roleName;
	/**
	 * 用户是否允许操作(修改、删除)
	 */
	private boolean allowOperate;
	
	/**
	 * 从父类赋值
	 * @return
	 */
	public SysUserVo copy(SysUser o){
		this.setUserId(o.getUserId());
		this.setEmail(o.getEmail());
		this.setPassword(o.getPassword());
		this.setRealName(o.getRealName());
		this.setRoleId(o.getRoleId());
		this.setCreateTime(o.getCreateTime());
		this.setUpdateTime(o.getUpdateTime());
		this.setStatus(o.getStatus());
		return this;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public boolean isAllowOperate() {
		return allowOperate;
	}

	public void setAllowOperate(boolean allowOperate) {
		this.allowOperate = allowOperate;
	}
	
	

}

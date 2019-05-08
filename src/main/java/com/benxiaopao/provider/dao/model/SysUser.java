package com.benxiaopao.provider.dao.model;

import java.util.Date;

public class SysUser {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.userId
     *
     * @mbg.generated Wed Apr 03 23:28:16 CST 2019
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.email
     *
     * @mbg.generated Wed Apr 03 23:28:16 CST 2019
     */
    private String email;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.password
     *
     * @mbg.generated Wed Apr 03 23:28:16 CST 2019
     */
    private String password;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.realName
     *
     * @mbg.generated Wed Apr 03 23:28:16 CST 2019
     */
    private String realName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.roleId
     *
     * @mbg.generated Wed Apr 03 23:28:16 CST 2019
     */
    private Integer roleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.createTime
     *
     * @mbg.generated Wed Apr 03 23:28:16 CST 2019
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.updateTime
     *
     * @mbg.generated Wed Apr 03 23:28:16 CST 2019
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.status
     *
     * @mbg.generated Wed Apr 03 23:28:16 CST 2019
     */
    private Short status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.userId
     *
     * @mbg.generated Wed Apr 03 23:28:16 CST 2019
     */
    private Integer orgId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.userId
     *
     * @return the value of sys_user.userId
     *
     * @mbg.generated Wed Apr 03 23:28:16 CST 2019
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.userId
     *
     * @param userId the value for sys_user.userId
     *
     * @mbg.generated Wed Apr 03 23:28:16 CST 2019
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.email
     *
     * @return the value of sys_user.email
     *
     * @mbg.generated Wed Apr 03 23:28:16 CST 2019
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.email
     *
     * @param email the value for sys_user.email
     *
     * @mbg.generated Wed Apr 03 23:28:16 CST 2019
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.password
     *
     * @return the value of sys_user.password
     *
     * @mbg.generated Wed Apr 03 23:28:16 CST 2019
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.password
     *
     * @param password the value for sys_user.password
     *
     * @mbg.generated Wed Apr 03 23:28:16 CST 2019
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.realName
     *
     * @return the value of sys_user.realName
     *
     * @mbg.generated Wed Apr 03 23:28:16 CST 2019
     */
    public String getRealName() {
        return realName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.realName
     *
     * @param realName the value for sys_user.realName
     *
     * @mbg.generated Wed Apr 03 23:28:16 CST 2019
     */
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.roleId
     *
     * @return the value of sys_user.roleId
     *
     * @mbg.generated Wed Apr 03 23:28:16 CST 2019
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.roleId
     *
     * @param roleId the value for sys_user.roleId
     *
     * @mbg.generated Wed Apr 03 23:28:16 CST 2019
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.createTime
     *
     * @return the value of sys_user.createTime
     *
     * @mbg.generated Wed Apr 03 23:28:16 CST 2019
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.createTime
     *
     * @param createTime the value for sys_user.createTime
     *
     * @mbg.generated Wed Apr 03 23:28:16 CST 2019
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.updateTime
     *
     * @return the value of sys_user.updateTime
     *
     * @mbg.generated Wed Apr 03 23:28:16 CST 2019
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.updateTime
     *
     * @param updateTime the value for sys_user.updateTime
     *
     * @mbg.generated Wed Apr 03 23:28:16 CST 2019
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.status
     *
     * @return the value of sys_user.status
     *
     * @mbg.generated Wed Apr 03 23:28:16 CST 2019
     */
    public Short getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.status
     *
     * @param status the value for sys_user.status
     *
     * @mbg.generated Wed Apr 03 23:28:16 CST 2019
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.userId
     *
     * @return the value of sys_user.orgId
     *
     * @mbg.generated Wed Apr 03 23:28:16 CST 2019
     */
    public Integer getOrgId() {
        return orgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.userId
     *
     * @param orgId the value for sys_user.orgId
     *
     * @mbg.generated Wed Apr 03 23:28:16 CST 2019
     */
    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }
}
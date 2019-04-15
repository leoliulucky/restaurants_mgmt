<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>管理中心|添加新成员</title>
<#include "../../inc/inc.ftl">
<script type="text/javascript">
/**
 * 页面加载后执行
 */
$(function(){
    //初始化页面相应元素绑定事件
    userManager.init();
});

/**
 * 新增用户管理控制对象
 *
 * @author liupoyang
 * @since 2019-04-15
 *
 */
var userManager = function() {

    /**
     * 邮箱账号
     */
    var _$email;
    /**
     * 姓名
     */
    var _$realName;
    /**
     * 密码
     */
    var _$password;
    /**
     * 确认密码
     */
    var _$rePassword;
    /**
     * 角色权限
     */
    var _$roleId;
    /**
     * 所选角色权限
     */
    var _role;

    /**
     * 检验邮箱
     */
    var _fnValidateEmail = function(){
        if(_$email.val() == null || _$email.val().length <= 0){
            $("#emailError").html("请输入账号邮箱").show();
            return false;
        }
        var regEmail = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
        if(!regEmail.test(_$email.val())){
            $("#emailError").html("邮箱格式不正确，请输入正确的账号邮箱").show();
            return false;
        }
        $("#emailError").hide();
        return true;
    };

    /**
     * 检验密码
     */
    var _fnValidatePassword = function(){
        if(_$password.val() == null || _$password.val().length <= 0){
            $("#passwordError").show();
            return false;
        }
        $("#passwordError").hide();
        return true;
    };

    /**
     * 检验确认密码
     */
    var _fnValidateRePassword = function(){
        if(_$rePassword.val() == null || _$rePassword.val().length <= 0){
            $("#rePasswordError").html("请输入确认密码").show();
            return false;
        }
        if(_$password.val() != _$rePassword.val()){
            $("#rePasswordError").html("两次密码不一致").show();
            return false;
        }
        $("#rePasswordError").hide();
        return true;
    };

    /**
     * 检验角色权限
     */
    var _fnValidateRole = function(){
        if(_$roleId.val() == null || _$roleId.val().length <= 0){
            $("#roleIdError").html("请选择角色权限").show();
            return false;
        }
        _role = _$roleId.val();
        return true;
    };


    return {
        /**
         * 初始化
         */
        init: function(){
            //赋值
            _$email = $("#email");
            _$realName = $("#realName");
            _$password = $("#password");
            _$rePassword = $("#rePassword");
            _$roleId = $("#roleId");

            //邮箱检验
            _$email.on("blur", _fnValidateEmail);
            //密码检验
            _$password.on("blur", _fnValidatePassword);
            //确认密码检验
            _$rePassword.on("blur", _fnValidateRePassword);
        },

        /**
         * 新增用户
         */
        add: function(){
            //检验邮箱
            if(!_fnValidateEmail()){
                return false;
            }
            //检验密码
            if(!_fnValidatePassword()){
                return false;
            }
            //检验确认密码
            if(!_fnValidateRePassword()){
                return false;
            }
            //检验角色
            if(!_fnValidateRole()){
                return false;
            }

            //异步请求
            $ajax({
                type: 'POST',
                url: "/sysadmin/user/doInsert",
                data: {
                    "email": _$email.val(),
                    "realName": _$realName.val(),
                    "password": _$password.val(),
                    "roleId": _role
                },
                success: function(data) {
                    //错误等信息提示
                    if(data.code < 0){
                        alert(data.msg);
                        return false;
                    }

                    //提示新增成功
                    floatTips({
                        content: "添加新成员成功！",
                        fun: function () {
                            window.location.href = "/sysadmin/user/list";
                        }
                    });
                }
            });//End...$ajax
        }
    };//End...return
} ();//End...var userManager
</script>
</head>

<body class="fixed-nav sticky-footer bg-dark" id="page-top">
<#include "../../inc/header.ftl">
<div class="content-wrapper">
    <div class="container-fluid">
        <!-- Breadcrumbs-->
        <ol class="breadcrumb">
            <li class="breadcrumb-item">
                <a href="/sysadmin/user/index">管理中心</a>
            </li>
            <li class="breadcrumb-item">
                <a href="/sysadmin/user/list">成员列表</a>
            </li>
            <li class="breadcrumb-item active">添加新成员</li>
        </ol>
        <div class="row">
            <div class="col-12">
                <div class="card mb-3">
                    <div class="card-header">
                        添加新成员
                        <a href="/sysadmin/user/list"><span class="float-right"><i class="fa fa-share"></i>返回</span></a>
                    </div>
                    <div class="card-body">

                    <#-- content -->

                        <form>
                            <div class="form-group row">
                                <label for="currentPwd" class="col-sm-2 col-form-label">邮箱账号<span class="text-danger">*</span></label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="email" placeholder="邮箱账号">
                                    <div id="emailError" class="invalid-feedback">请输入账号邮箱</div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="newPwd" class="col-sm-2 col-form-label">姓名</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="realName" placeholder="姓名">
                                    <div id="realNameError" class="invalid-feedback">提示信息</div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="reNewPwd" class="col-sm-2 col-form-label">密码<span class="text-danger">*</span></label>
                                <div class="col-sm-10">
                                    <input type="password" class="form-control" id="password" placeholder="密码">
                                    <div id="passwordError" class="invalid-feedback">请输入密码</div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="reNewPwd" class="col-sm-2 col-form-label">确认密码<span class="text-danger">*</span></label>
                                <div class="col-sm-10">
                                    <input type="password" class="form-control" id="rePassword" placeholder="密码">
                                    <div id="rePasswordError" class="invalid-feedback">两次密码不一致</div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="reNewPwd" class="col-sm-2 col-form-label">角色权限<span class="text-danger">*</span></label>
                                <div class="col-sm-10">
                                    <select class="form-control custom-select" id="roleId">
                                        <#list data.roles as role>
                                            <option value="${role.roleId}">${role.roleName}</option>
                                        </#list>
                                    </select>
                                    <div id="roleIdError" class="invalid-feedback">请选择角色权限</div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="col-sm-10">
                                    <button type="button" class="btn btn-primary" onclick="return userManager.add();">确定添加</button>&nbsp;&nbsp;
                                    <button type="button" class="btn btn-secondary" onclick="javascript:window.location.href='/sysadmin/user/list';">返回</button>
                                </div>
                            </div>
                        </form>

                    <#-- /content -->

                    </div>
                </div>
            </div>
        </div>
    </div>

<#include "../../inc/footer.ftl">
</div>
</body>
</html>

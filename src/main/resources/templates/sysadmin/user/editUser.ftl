<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>管理中心|修改成员信息</title>
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
 * 修改用户管理控制对象
 *
 * @author liupoyang
 * @since 2019-04-16
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
     * 用户id
     */
    var _userId;

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
            $("#passwordError").html("请输入新密码").show();
            return false;
        }
        if(_$password.val().length < 6){
            $("#passwordError").html("新密码不能少于6位").show();
            return false;
        }
        if(_$password.val().length > 16){
            $("#passwordError").html("新密码不能多于16位").show();
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
            $("#rePasswordError").html("两次输入不一致，请重新输入").show();
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
            _userId = $("#userId").val();

            //隐藏提示信息
            $(".error").hide();
            //角色权限绑定选择后事件，限制只能单选
            $(".yCheck").on("click", function(){
                _$icoRoles.removeClass("checked");
                $(this).find(".ico").addClass("checked");
            });

            //邮箱检验
            _$email.on("blur", _fnValidateEmail);
            //密码检验
            _$password.on("blur", _fnValidatePassword);
            //确认密码检验
            _$rePassword.on("blur", _fnValidateRePassword);
        },

        /**
         * 修改用户
         */
        edit: function(){
            //检验邮箱
            if(!_fnValidateEmail()){
                return false;
            }
            //检验角色
            if(!_fnValidateRole()){
                return false;
            }
            //检验用户id
            if(_userId == null || _userId.length <= 0){
                alert("要修改的成员不存在");
                return false;
            }

            //异步请求
            $ajax({
                type: 'POST',
                url: "/sysadmin/user/doUpdate",
                data: {
                    "email": _$email.val(),
                    "realName": _$realName.val(),
                    "roleId": _role,
                    "userId": _userId
                },
                success: function(data) {
                    //错误等信息提示
                    if(data.code < 0){
                        alertTips("修改成员", data.msg);
                        return false;
                    }

                    //提示修改成功
                    floatTips({
                        content: "修改成员信息成功！",
                        fun: function () {
                            window.location.href = "/sysadmin/user/list";
                        }
                    });
                }
            });//End...$.ajax
        },

        /**
         * 修改密码
         */
        pwd: function(){
            //检验密码
            if(!_fnValidatePassword()){
                return false;
            }
            //检验确认密码
            if(!_fnValidateRePassword()){
                return false;
            }
            //检验用户id
            if(_userId == null || _userId.length <= 0){
                alert("要修改的成员不存在");
                return false;
            }

            //异步请求
            $ajax({
                type: 'POST',
                url: "/sysadmin/user/updatePassword",
                data: {
                    "password": _$password.val(),
                    "userId": _userId
                },
                success: function(data) {
                    //错误等信息提示
                    if(data.code < 0){
                        alertTips("修改密码", data.msg);
                        return false;
                    }

                    //提示修改成功
                    $('#pwdWinModal').modal('hide');
                    floatTips({
                        content: "修改密码成功！"
                    });
                }
            });//End...$ajax
        },

        /**
         * 打开修改密码窗体
         */
        openPwdWin: function(){
            _$password.val("");
            _$rePassword.val("");
            $('#pwdWinModal').modal('show');
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
            <li class="breadcrumb-item active">修改成员信息</li>
        </ol>
        <div class="row">
            <div class="col-12">
                <div class="card mb-3">
                    <div class="card-header">
                        修改成员信息
                        <a href="/sysadmin/user/list"><span class="float-right"><i class="fa fa-share"></i>返回</span></a>
                    </div>
                    <div class="card-body">

                    <#-- content -->

                        <form>
                            <div class="form-group row">
                                <label for="currentPwd" class="col-sm-2 col-form-label">邮箱账号<span class="text-danger">*</span></label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="email" placeholder="邮箱账号" value="${data.user.email }">
                                    <div id="emailError" class="invalid-feedback">请输入账号邮箱</div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="newPwd" class="col-sm-2 col-form-label">姓名</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="realName" placeholder="姓名" value="${data.user.realName }">
                                    <div id="realNameError" class="invalid-feedback">提示信息</div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="reNewPwd" class="col-sm-2 col-form-label">密码<span class="text-danger">*</span></label>
                                <div class="col-sm-10">
                                    <a href="javascript:;" onclick="userManager.openPwdWin();">修改密码</a>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="reNewPwd" class="col-sm-2 col-form-label">角色权限<span class="text-danger">*</span></label>
                                <div class="col-sm-10">
                                    <select class="form-control custom-select" id="roleId">
                                        <#list data.roles as role>
                                            <#assign selectedStr=''>
                                                <#if data.user.roleId == role.roleId>
                                                    <#assign selectedStr=' selected="selected"'>
                                                </#if>
                                            <option value="${role.roleId}"${selectedStr}>${role.roleName}</option>
                                        </#list>
                                    </select>
                                    <div id="roleIdError" class="invalid-feedback">请选择角色权限</div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="col-sm-10">
                                    <#-- 隐藏域 -->
                                    <input type="hidden" id="userId" value="${data.user.userId}" />
                                    <#-- /隐藏域 -->
                                    <button type="button" class="btn btn-primary" onclick="return userManager.edit();">保存修改</button>&nbsp;&nbsp;
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

<#-- 修改密码弹出窗体 -->
<div class="modal fade" id="pwdWinModal" tabindex="-1" role="dialog" aria-labelledby="pwdWinModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">修改密码</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group row">
                        <label for="reNewPwd" class="col-sm-2 col-form-label">密码<span class="text-danger">*</span></label>
                        <div class="col-sm-10">
                            <input type="password" class="form-control" id="password">
                            <div id="passwordError" class="invalid-feedback">请输入密码</div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="reNewPwd" class="col-sm-2 col-form-label">确认密码<span class="text-danger">*</span></label>
                        <div class="col-sm-10">
                            <input type="password" class="form-control" id="rePassword">
                            <div id="rePasswordError" class="invalid-feedback">两次密码不一致</div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="return userManager.pwd();">确认</button>
            </div>
        </div>
    </div>
</div>
<#-- /修改密码弹出窗体 -->

</body>
</html>

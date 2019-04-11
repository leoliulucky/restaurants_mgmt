<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>管理中心|修改密码</title>
<#include "../../inc/inc.ftl">
<link href="/toastr/toastr.min.css" rel="stylesheet" />
<script src="/toastr/toastr.min.js"></script>
<script type="text/javascript">

    /**
     * 页面加载后执行
     */
    $(function(){
        toastr.options.positionClass = 'toast-top-center';

        //当前密码检验
        $("#currentPwd").on("blur", validateCurrentPwd);
        //新密码检验
        $("#newPwd").on("blur", validateNewPwd);
        //确认新密码检验
        $("#reNewPwd").on("blur", validateReNewPwd);
    });

    /**
     * 检验当前密码
     */
    function validateCurrentPwd(){
        if($("#currentPwd").val() == null || $("#currentPwd").val().length <= 0){
            $("#currentPwdError").html("请输入当前密码").show();
            return false;
        }
        $("#currentPwdError").hide();
        return true;
    }

    /**
     * 检验新密码
     */
    function validateNewPwd(){
        if($("#newPwd").val() == null || $("#newPwd").val().length <= 0){
            $("#newPwdError").html("请输入新密码").show();
            return false;
        }
        if($("#newPwd").val().length < 6){
            $("#newPwdError").html("新密码不能少于6位").show();
            return false;
        }
        if($("#newPwd").val().length > 16){
            $("#newPwdError").html("新密码不能多于16位").show();
            return false;
        }
        $("#newPwdError").hide();
        return true;
    }

    /**
     * 检验确认新密码
     */
    function validateReNewPwd(){
        if($("#reNewPwd").val() == null || $("#reNewPwd").val().length <= 0){
            $("#reNewPwdError").html("请输入确认新密码").show();
            return false;
        }
        if($("#newPwd").val() != $("#reNewPwd").val()){
            $("#reNewPwdError").html("两次密码不一致").show();
            return false;
        }
        $("#reNewPwdError").hide();
        return true;
    }

    /**
     * 修改密码
     */
    function editPwd(){
        //检验当前密码
        if(!validateCurrentPwd()){
            return false;
        }
        //检验新密码
        if(!validateNewPwd()){
            return false;
        }
        //检验确认新密码
        if(!validateReNewPwd()){
            return false;
        }

        //异步请求
        $ajax({
            type: 'POST',
            url: "/sysadmin/user/doUpdateMyPwd",
            data: {
                "currentPwd": $("#currentPwd").val(),
                "newPwd": $("#newPwd").val()
            },
            success: function(data) {
                //错误等信息提示
                if(data.code < 0){
                    $("#currentPwdError").html(data.msg).show();
                    return false;
                }

                //提示修改成功
                toastr.success('密码修改成功！');
                setTimeout(function(){
                    window.location.href = "/sysadmin/user/index";
                }, 2000);
            }
        });//End...$.ajax
    }

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
            <li class="breadcrumb-item active">修改密码</li>
        </ol>
        <div class="row">
            <div class="col-12">
                <div class="card mb-3">
                    <div class="card-header">
                        修改密码
                        <a href="/sysadmin/user/index"><span style="float: right"><i class="fa fa-share"></i>返回</span></a>
                    </div>
                    <div class="card-body">

                    <#-- content -->

                        <form>
                            <div class="form-group row">
                                <label for="currentPwd" class="col-sm-2 col-form-label">当前密码</label>
                                <div class="col-sm-10">
                                    <input type="password" class="form-control" id="currentPwd" placeholder="当前密码">
                                    <div id="currentPwdError" class="invalid-feedback">密码错误</div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="newPwd" class="col-sm-2 col-form-label">新密码</label>
                                <div class="col-sm-10">
                                    <input type="password" class="form-control" id="newPwd" placeholder="新密码">
                                    <div id="newPwdError" class="invalid-feedback">不少于6位</div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="reNewPwd" class="col-sm-2 col-form-label">确认新密码</label>
                                <div class="col-sm-10">
                                    <input type="password" class="form-control" id="reNewPwd" placeholder="确认新密码">
                                    <div id="reNewPwdError" class="invalid-feedback">密码不一致</div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="col-sm-10">
                                    <button type="button" class="btn btn-primary" onclick="return editPwd();">确定</button>
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

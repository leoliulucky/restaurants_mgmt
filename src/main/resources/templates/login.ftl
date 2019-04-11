<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>管理中心|登录</title>
<#include "./inc/inc.ftl">
<script type="text/javascript">

    /**
     * 页面加载后执行
     */
    $(function(){
        //隐藏提示信息
        $("#errorMsg").hide();
        //密码绑定键盘按下后事件
        $("#password").bind("keypress", function(event){
            login(event);
        });
        //输入时隐藏提示信息
        $("#email, #password").on("focus", function(){
            $("#errorMsg").hide();
        });
    });

    /**
     * 键盘按下后事件响应处理
     * @param event
     */
    function login(event) {
        if (event.keyCode == 13) {
            ajaxLogin();
        }
    }

    /**
     * 登录请求方法
     */
    function ajaxLogin() {
        var email = $('#email').val();
        var password = $('#password').val();
        //检验邮箱
        if (email == null || email == '') {
            $("#errorMsg").html('邮箱不能为空');
            $("#errorMsg").show();
            return false;
        }
        var regEmail = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
        if(!regEmail.test(email)){
            $("#errorMsg").html('邮箱格式不正确');
            $("#errorMsg").show();
            return false;
        }
        //检验密码
        if (password == null || password == '') {
            $("#errorMsg").html('密码不能为空');
            $("#errorMsg").show();
            return false;
        }

        //异步请求
        $ajax({
            type: 'POST',
            url: "/sysadmin/user/doLogin",
            data: {"email": email, "password": password},
            dataType: 'json',
            success: function(data) {
                //错误等信息提示
                if(data.code < 0){
                    $("#errorMsg").html(data.msg);
                    $("#errorMsg").show();
                    return false;
                }
                //登录成功，跳转主页
                window.location.href = "/sysadmin/user/index";
            }
        });//End...$ajax
    }

</script>
</head>

<body class="bg-dark">
<div class="container">
    <div class="card card-login mx-auto mt-5">
        <div class="card-header">登录</div>
        <div class="card-body">
            <form>
                <div class="form-group">
                    <label for="exampleInputEmail1">邮箱</label>
                    <input class="form-control" id="email" type="email" aria-describedby="emailHelp" placeholder="邮箱">
                </div>
                <div class="form-group">
                    <label for="exampleInputPassword1">密码</label>
                    <input class="form-control" id="password" type="password" placeholder="密码">
                </div>
                <#--<div class="form-group">
                    <div class="form-check">
                        <label class="form-check-label">
                            <input class="form-check-input" type="checkbox"> Remember Password</label>
                    </div>
                </div>-->
                <span id="errorMsg" style="color:red">密码不正确</span>
                <br />
                <a class="btn btn-primary btn-block" href="javascript:;" onclick="return ajaxLogin();">登录</a>
            </form>
            <#--<div class="text-center">
                <a class="d-block small mt-3" href="register.html">Register an Account</a>
                <a class="d-block small" href="forgot-password.html">Forgot Password?</a>
            </div>-->
        </div>
    </div>
</div>
</body>
</html>

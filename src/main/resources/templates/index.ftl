<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>管理中心 - 首页</title>
<#include "./inc/inc.ftl">
</head>

<body class="fixed-nav sticky-footer bg-dark" id="page-top">
<#include "./inc/header.ftl">
<div class="content-wrapper">
    <div class="container-fluid">
        <!-- Breadcrumbs-->
        <ol class="breadcrumb">
            <li class="breadcrumb-item">
                <a href="/sysadmin/user/index">管理中心</a>
            </li>
            <li class="breadcrumb-item active">首页</li>
        </ol>
        <div class="row">
            <div class="col-12">
                <h1>Blank</h1>
                <p>欢迎，${userObj.email }     姓名：${userObj.realName }</p>
            </div>
        </div>
    </div>

<#include "./inc/footer.ftl">
</div>
</body>
</html>

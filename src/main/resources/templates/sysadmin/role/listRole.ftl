<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>管理中心 - 角色列表</title>
<#include "../../inc/inc.ftl">
<link rel="stylesheet" href="/js/artDialog/skins/idialog.css" type="text/css" />
<script type="text/javascript" src="/js/artDialog/jquery.artDialog.js"></script>
<script type="text/javascript">
/**
 * 角色管理控制对象
 *
 * @author liupoyang
 * @since 2019-04-13
 *
 */
var roleManager = function() {

    /**
     * 刷新列表
     * @param msg 提示信息
     */
    var _refreshData = function(msg){
        //提示操作成功
        floatTips({
            content: msg,
            fun: function () {
                window.location.href = "/sysadmin/role/list";
            }
        });
    };

    /**
     * 角色新增、修改时弹出窗体的内容HTML
     */
    var popHtml =
            '<form id="roleForm" name="roleForm" method="post" action="">'+
            '	<table align="center">'+
            '		<tr>'+
            '			<td>角色名称:</td>'+
            '			<td>'+
            '				<input type="text" id="roleName" name="roleName" size="42" />'+
            '			</td>'+
            '		</tr>'+
            '		<tr>'+
            '			<td>角色备注:</td>'+
            '			<td>'+
            '				<input type="text" id="remark" name="remark" size="42" />'+
            '			</td>'+
            '		</tr>'+
            '	</table>'+
            '	<#-- 隐藏域 -->'+
            '	<input type="hidden" id="roleId" name="roleId" />'+
            '	<#-- /隐藏域 -->'+
            '</form>';

    /**
     * 新增/修改角色弹出窗体处理
     * @param requestUrl 请求地址
     * @param fun
     */
    var _doPopWin = function(requestUrl, fun){
        //弹出窗体供用户新增节点信息
        art.dialog({
            id: 'rolePopWinId',
            content: popHtml,
            button: [
                {
                    name: '提交',
                    callback: function () {
                        //校验数据
                        var roleId = $("#roleId").val();
                        var roleName = $("#roleName").val();
                        var remark = $("#remark").val();
                        if(roleName == null || roleName.length <= 0){
                            alert("角色名称不允许为空");
                            return false;
                        }
                        /*if(remark == null || remark.length <= 0){
                            alert("角色备注不允许为空");
                            return false;
                        }*/

                        //异步请求
                        $ajax({
                            type: 'POST',
                            url: requestUrl,
                            data: {"roleName": roleName, "remark": remark, "roleId": roleId},
                            success: function(data) {
                                //错误等信息提示
                                if(data.code < 0){
                                    alert(data.msg);
                                    return false;
                                }
                                //刷新列表
                                _refreshData(data.msg);
                            }
                        });//End...$ajax
                    },
                    focus:true
                },
                {
                    name: '取消'
                }
            ]
        });//End...art.dialog

        fun&fun();
    };


    return {
        /**
         * 新增
         */
        add: function(){
            _doPopWin("/sysadmin/role/insert", function(){
                return false;
            });
        },

        /**
         * 修改
         * @param roleId 角色id
         */
        edit: function(roleId){
            //异步请求
            $ajax({
                type: 'POST',
                url: "/sysadmin/role/get",
                data: {"roleId": roleId},
                success: function(data) {
                    //错误等信息提示
                    if(data.code < 0){
                        alert(data.msg);
                        return false;
                    }

                    _doPopWin("/sysadmin/role/update", function(){
                        //回显数据
                        $("#roleName").val(data.data.role.roleName);
                        $("#remark").val(data.data.role.remark);
                        $("#roleId").val(data.data.role.roleId);
                    });

                }
            });//End...$ajax
        },

        /**
         * 删除
         * @param roleId 角色id
         */
        remove: function(roleId){
            if(confirm("确定要删除该角色吗?")){
                //异步请求
                $ajax({
                    type: 'POST',
                    url: "/sysadmin/role/delete",
                    data: {"roleId": roleId},
                    success: function(data) {
                        //错误等信息提示
                        if(data.code < 0){
                            alert(data.msg);
                            return false;
                        }
                        //刷新列表
                        _refreshData(data.msg);
                    }
                });//End...$ajax
            }
        }
    };//End...return
} ();//End...var roleManager
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
            <li class="breadcrumb-item active">角色列表</li>
        </ol>
        <div class="row">
            <div class="col-12">
                <div class="card mb-3">
                    <div class="card-header">
                        角色列表
                        <a href="/sysadmin/user/index"><span class="float-right"><i class="fa fa-share"></i>返回</span></a>
                    </div>
                    <div class="card-body">

                    <#-- content -->

                        <form name="searchForm" action="/sysadmin/role/list" method="post">
                            <div class="form-row align-items-center">
                                <div class="col-auto">
                                    <div class="input-group mb-2">
                                        <div class="input-group-prepend">
                                            <div class="input-group-text">名称</div>
                                        </div>
                                        <input type="text" class="form-control" name="roleName" placeholder="角色名称" value="${data.roleName!}">
                                    </div>
                                </div>
                                <div class="col-auto">
                                    <button type="button" class="btn btn-primary mb-2" onclick="javascript:document.searchForm.submit();"><i class="fa fa-fw fa-search"></i>搜索</button>&nbsp;&nbsp;&nbsp;&nbsp;
                                    <button type="button" class="btn btn-success mb-2" onclick="return roleManager.add();"><i class="fa fa-fw fa-plus"></i>添加新角色</button>
                                </div>
                            </div>
                        </form>

                        <br/>
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover" id="dataTable" width="100%" cellspacing="0">
                                <thead class="thead-dark">
                                <tr>
                                    <th>角色名称</th>
                                    <th>备注</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list data.roles as role>
                                    <tr>
                                        <td>${role.roleName}</td>
                                        <td>${role.remark}</td>
                                        <td>
                                            <a href="javascript:;" onclick="return roleManager.edit(${role.roleId });" title="修改"><i class="fa fa-fw fa-edit"></i></a>
                                            <a href="javascript:;" onclick="return roleManager.remove(${role.roleId });" title="删除"><i class="fa fa-fw fa-remove"></i></a>
                                        </td>
                                    </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>

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

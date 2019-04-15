<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>管理中心 - 角色授权列表</title>
<#include "../../inc/inc.ftl">
<link rel="stylesheet" href="/js/ztree/zTreeStyle/zTreeStyle.css" type="text/css" />
<script type="text/javascript" src="/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="/js/ztree/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript">

/**
 * 页面加载后执行
 */
$(function() {
    role4PurviewManager.init();
});

/**
 * 角色授权控制对象
 * 主要负责菜单树勾选展示及授权操作等控制
 *
 * @author liupoyang
 * @since 2019-04-13
 *
 */
var role4PurviewManager = function() {
    /**
     * 菜单树实例
     */
    var _zTree;
    /**
     * 所选要授权的角色id
     */
    var _roleId;

    /**
     * 配置
     */
    var _setting = {
        async:{
            //启用异步加载
            enable:true,
            //异步请求地址
            url:"/sysadmin/menu/doListAll",
            //数据类型 json、xml、html
            dataType:"json",
            dataFilter:function(treeId, parentNode, responseData){
                if (responseData.code < 0) {
                    alert(responseData.msg);
                    return "";
                }
                return responseData.data.menus;
            }
        },
        //类似checkbox复选框的效果
        check:{
            enable:true,
            //勾选框类型(checkbox 或 radio)
            chkStyle:"checkbox",
            //勾选 checkbox 对于父子节点的关联关系
            chkboxType: { "Y": "s", "N": "s" }
        },
        data:{
            key:{
                //当model类中name或其他字段不对应时可用此方法，但是zNodes节点中name的属性也要改成于model类一致
                name:"menuName",
                //节点链接的目标 URL 的属性名称,不想实现点击节点跳转的功能时，直接修改此属性为其他不存在的属性名称
                url:null
            },
            simpleData:{
                enable:true,
                idKey:"menuId",
                pIdKey:"parentId"
            }
        },
        view:{
            //设置是否显示节点图片，默认true显示
            showIcon:true,
            //节点之间的连线，默认为true有
            showLine:true,
            //zTree节点展开、折叠时的动画速度，""表示关闭，默认fast，还有slow,normal,也可以直接输入数字
            expandSpeed:"show",
            //设置是否允许同时选中多个节点
            selectedMulti:false
        },
        callback:{
            //加载成功后调用
            onAsyncSuccess:function(){
                //展开全部节点
                _zTree.expandAll(true);
            }
        }
    };

    /**
     * 获得角色已有的权限信息
     * @param roleId 角色ID
     */
    var _getPurviewData = function(roleId) {
        _roleId = roleId;
        //异步请求
        $ajax({
            type: 'POST',
            url: "/sysadmin/menu/listPurview",
            data: {"roleId": _roleId},
            dataType:"json",
            success: function(data) {
                //错误等信息提示
                if(data.code < 0){
                    alert(data.msg);
                    return false;
                }

                //清空原来勾选情况
                _cleanCheckBox(_zTree.getNodes());

                //初始菜单权限打勾数据
                var checkedIds = data.data.menuIds;
                _check4Mark(_zTree.getNodes(), checkedIds);
            }
        });//End...$ajax
    };

    /**
     * 清空菜单权限打勾数据<br />递归调用
     * @param nodes 节点数组
     */
    var _cleanCheckBox = function(nodes){
        if(nodes == null || nodes.length <= 0){
            return;
        }

        $.each(nodes, function(i, obj){
            _zTree.checkNode(obj, false);
            _cleanCheckBox(obj.children);
        });
    };

    /**
     * 初始菜单权限打勾数据<br />递归调用
     * @param nodes 节点数组
     * @param checkedIds 已有的菜单权限对象
     */
    var _check4Mark = function(nodes, checkedIds){
        if(nodes == null || nodes.length <= 0){
            return;
        }

        $.each(nodes, function(i, obj){
            if(checkedIds && obj.menuId == checkedIds[obj.menuId]){
                _zTree.checkNode(obj, true);
            }

            _check4Mark(obj.children, checkedIds);
        });
    };

    /**
     * 授权角色菜单处理
     */
    var _auth = function(){
        var menuIds = '';
        //勾选节点
        var nodes = _zTree.getCheckedNodes(true);
        $.each(nodes, function(i, item){
            menuIds += item.menuId + ",";
        });

        //异步请求
        $ajax({
            type: 'POST',
            url: "/sysadmin/role/auth",
            data: {"roleId": _roleId, "menuIds": menuIds},
            dataType:"json",
            success: function(data) {
                //错误等信息提示
                if(data.code < 0){
                    alert(data.msg);
                    return false;
                }
                //提示授权成功
                alert(data.msg);
            }
        });//End...$ajax
    };

    return {
        /**
         * 初始化
         */
        init: function(){
            _zTree = $.fn.zTree.init($("#menuTree"), _setting);
        },

        /**
         * 获得角色已有的权限信息
         * @param roleId 角色ID
         */
        getPurviewData:_getPurviewData,

        /**
         * 授权角色菜单处理
         */
        auth: _auth

    };//End...return
} ();//End...var role4PurviewManager
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
            <li class="breadcrumb-item active">角色授权列表</li>
        </ol>
        <div class="row">
            <div class="col-12">
                <div class="card mb-3">
                    <div class="card-header">
                        角色授权列表
                        <a href="/sysadmin/user/index"><span class="float-right"><i class="fa fa-share"></i>返回</span></a>
                    </div>
                    <div class="card-body">

                    <#-- content -->

                        <div class="col-lg-6" style="width:45%;float:left;">
                            <div class="table-responsive">
                                <table class="table table-bordered table-hover" id="dataTable" width="100%" cellspacing="0">
                                    <thead class="thead-dark">
                                    <tr>
                                        <th>角色名称</th>
                                        <th>备注</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <#list data.roles as role>
                                            <tr>
                                                <td><a href="javascript:;" onclick="return role4PurviewManager.getPurviewData(${role.roleId });">${role.roleName}</a></td>
                                                <td title="${role.remark}">
                                                    <#if role.remark?length gt 20>
                                                        ${role.remark?substring(0,20)}...
                                                    <#else>
                                                        ${role.remark!}
                                                    </#if>
                                                </td>
                                            </tr>
                                        </#list>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="col-lg-6" style="width:45%;float:right;">
                            <div class="card mb-3">
                                <div class="table-responsive">
                                    <#-- 菜单树列表 -->
                                    <ul id="menuTree" class="ztree"></ul>
                                </div>
                            </div>
                            <button type="button" class="btn btn-primary mb-2" onclick="return role4PurviewManager.auth();"><i class="fa fa-fw fa-save"></i>保  存</button>
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

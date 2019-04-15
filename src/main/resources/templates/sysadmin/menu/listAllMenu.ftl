<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>管理中心 - 菜单树列表</title>
<#include "../../inc/inc.ftl">
<link rel="stylesheet" href="/js/ztree/zTreeStyle/zTreeStyle.css" type="text/css" />
<style type="text/css">
    .ztree li span.button.add {margin-right:2px; background-position:-143px 0px; vertical-align:top; *vertical-align:middle}
</style>
<script type="text/javascript" src="/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="/js/ztree/jquery.ztree.exedit-3.5.min.js"></script>
<link rel="stylesheet" href="/js/artDialog/skins/idialog.css" type="text/css" />
<script type="text/javascript" src="/js/artDialog/jquery.artDialog.js"></script>
<script type="text/javascript">
/**
 * 页面加载后执行
 */
$(function() {
    menuManager.init();
});

/**
 * 菜单管理控制对象
 * 主要负责菜单树新增、修改、删除、查看等操作控制
 *
 * @author liupoyang
 * @since 2019-04-13
 *
 */
var menuManager = function() {
    /**
     * 菜单树实例
     */
    var _zTree;

    /**
     * 菜单新增、修改时弹出窗体的内容HTML
     */
    var popHtml =
            '<form id="menuForm" name="menuForm" method="post" action="">'+
            '	<table align="center">'+
            '		<tr>'+
            '			<td>父节点:</td>'+
            '			<td id="parentName"></td>'+
            '		</tr>'+
            '		<tr>'+
            '			<td>名称:</td>'+
            '			<td>'+
            '				<input type="text" id="menuName" name="menuName" maxLength="32" size="42" />'+
            '			</td>'+
            '		</tr>'+
            '		<tr>'+
            '			<td>路径:</td>'+
            '			<td>'+
            '				<input type="text" id="url" name="url" maxLength="514" size="42" />'+
            '			</td>'+
            '		</tr>'+
            '		<tr>'+
            '			<td>是否可见:</td>'+
            '			<td>'+
            '				<input type="radio" id="isVisiable1" name="isVisiable" value="1" />是&nbsp;'+
            '				<input type="radio" id="isVisiable0" name="isVisiable" value="0" checked="checked" />否'+
            '			</td>'+
            '		</tr>'+
            '	</table>'+
            '	<#-- 隐藏域 -->'+
            '	<input type="hidden" id="menuId" name="menuId" />'+
            '	<#-- /隐藏域 -->'+
            '</form>';


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
        edit:{
            enable:true,
            //删除按钮的Title辅助信息
            removeTitle:"删除菜单",
            //编辑名称按钮的Title辅助信息
            renameTitle:"编辑菜单名称",
            //节点编辑名称input初次显示时，设置txt内容是否为全选状态
            editNameSelectAll:true
        },
        view:{
            //设置是否显示节点图片，默认true显示
            showIcon:true,
            //节点之间的连线，默认为true有
            showLine:true,
            //zTree节点展开、折叠时的动画速度，""表示关闭，默认fast，还有slow,normal,也可以直接输入数字
            expandSpeed:"show",
            //用于当鼠标移动到节点上时，显示用户自定义控件，显示隐藏状态同zTree
            addHoverDom:_addHoverDom,
            //用于鼠标移出节点时，隐藏用户自定义控件，显示隐藏状态同zTree内容的编辑、删除按钮
            removeHoverDom:_removeHoverDom,
            //设置是否允许同时选中多个节点
            selectedMulti:false
        },
        callback:{
            //加载成功后调用
            onAsyncSuccess:function(){
                //展开全部节点
                _zTree.expandAll(true);
            },
            //点击删除时触发，用来提示用户是否确定删除
            beforeRemove:function(treeId, treeNode){
                if(confirm("删除菜单将会级联删除其子菜单，你确定要删除吗？")){
                    //检验数据
                    if(treeNode.isParent){
                        alert("所删除菜单包含子菜单，不允许删除！");
                        return false;
                    }

                    //异步请求 删除菜单
                    $.ajax({
                        dataType: 'json',
                        url: "/sysadmin/menu/delete",
                        data: {"menuId": treeNode.menuId},
                        type: 'POST',
                        beforeSend: function() {
                        },
                        success: function(data) {
                            //错误等信息提示
                            if(data.code < 0){
                                alert(data.msg);
                                return false;
                            }

                            //删除树节点
                            _zTree.removeNode(treeNode);
                            alert(data.msg);
                            return true;
                        },
                        error: function() {
                        }
                    });//End...$.ajax
                }//End...if(confirm())
                return false;
            },
            //点击编辑时触发，用来判断该节点是否能编辑
            beforeEditName:function(treeId, treeNode){
                _doPopWin(treeNode, "/sysadmin/menu/update", function(){
                    //赋值
                    var parentName = "空";
                    if(treeNode.parentId != null && treeNode.parentId > 0){
                        parentName = treeNode.getParentNode().menuName;
                    }
                    $("#parentName").html(parentName);
                    $("#menuId").val(treeNode.menuId);
                    $("#parentId").val(treeNode.parentId);
                    $("#menuName").val(treeNode.menuName);
                    $("#showOrder").val(treeNode.showOrder);
                    $("#url").val(treeNode.url);
                    $("#isVisiable" + treeNode.isVisible).attr("checked",true);
                }, (treeNode.parentId == null? 0:treeNode.parentId), treeNode.showOrder);

                //表示禁止编辑节点名称
                return false;
            }
        }
    };

    /**
     * 用于当鼠标移动到节点上时，显示用户自定义控件
     */
    function _addHoverDom(treeId, treeNode){
        var sObj = $("#" + treeNode.tId + "_span");
        if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
                + "' title='添加子节点' onfocus='this.blur();'></span>";
        sObj.after(addStr);
        var btn = $("#addBtn_"+treeNode.tId);
        if (btn) btn.bind("click", function(){
            var showOrder = (typeof treeNode.children == 'undefined' ? 0 : treeNode.children.length);
            _doPopWin(treeNode, "/sysadmin/menu/insert", function(){
                $("#parentName").html(treeNode.menuName);
            }, treeNode.menuId, showOrder);
            return false;
        });
    }

    /**
     * 用于鼠标移出节点时，隐藏用户自定义控件
     */
    function _removeHoverDom(treeId,treeNode){
        $("#addBtn_"+treeNode.tId).unbind().remove();
    }

    /**
     * 新增/修改菜单弹出窗体处理
     * @param treeNode 选中的树节点
     * @param requestUrl 请求地址
     * @param fun
     * @param pid 父id
     * @param sOrder 显示顺序
     */
    var _doPopWin = function(treeNode, requestUrl, fun, pid, sOrder){
        //弹出窗体供用户新增节点信息
        art.dialog({
            id: 'treeNodePopWinId',
            content: popHtml,
            button: [
                {
                    name: '提交',
                    callback: function () {
                        //检验数据
                        var menuId = $("#menuId").val();
                        var menuName = $("#menuName").val();
                        var url = $("#url").val();
                        var isVisible = $("input:radio[name='isVisiable']:checked").val();
                        var parentId = pid;
                        var showOrder = sOrder;
                        if(menuName == null || menuName.length <= 0){
                            alert("菜单名称不允许为空");
                            return false;
                        }
                        if(url == null || url.length <= 0){
                            alert("菜单URL不允许为空");
                            return false;
                        }
                        if(isVisible == null || isVisible.length <= 0){
                            alert("菜单是否可见不允许为空");
                            return false;
                        }

                        //异步请求 新增、修改菜单
                        $.ajax({
                            dataType: 'json',
                            url: requestUrl,
                            data: {
                                "menuId": menuId,
                                "parentId": parentId,
                                "menuName": menuName,
                                "url": url,
                                "isVisible": isVisible,
                                "showOrder": showOrder
                            },
                            type: 'POST',
                            beforeSend: function() {
                            },
                            success: function(data) {
                                //错误等信息提示
                                if(data.code < 0){
                                    alert(data.msg);
                                    return false;
                                }

                                //添加树节点
                                //zTree.addNodes(treeNode, {id:(100), pId:treeNode.menuId, name:"新建子节点"});
                                //刷新树
                                _zTree.reAsyncChildNodes(null, "refresh");
                                alert(data.msg);
                            },
                            error: function() {
                            }
                        });//End...$.ajax
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

    /**
     * 新增顶级菜单树节点
     */
    var _addTopNode = function(){
        _doPopWin(null, "/sysadmin/menu/insert", function(){
            //赋值
            $("#parentName").html("空");
        }, 0, _zTree.getNodes().length);
    }

    return {
        /**
         * 初始化
         */
        init: function(){
            _zTree = $.fn.zTree.init($("#menuTree"), _setting);
        },

        /**
         * 新增顶级菜单树节点
         */
        addTopNode: _addTopNode

    };//End...return
} ();//End...var menuManager
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
            <li class="breadcrumb-item active">菜单树列表</li>
        </ol>
        <div class="row">
            <div class="col-12">
                <div class="card mb-3">
                    <div class="card-header">
                        菜单树列表
                        <a href="/sysadmin/user/index"><span class="float-right"><i class="fa fa-share"></i>返回</span></a>
                    </div>
                    <div class="card-body">

                    <#-- content -->

                        <button type="button" class="btn btn-success mb-2" onclick="return menuManager.addTopNode();"><i class="fa fa-fw fa-plus"></i>添加一级菜单</button>
                        <div class="table-responsive">
                            <#-- 菜单树列表 -->
                            <ul id="menuTree" class="ztree"></ul>
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

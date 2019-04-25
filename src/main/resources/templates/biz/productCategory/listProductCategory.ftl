<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>管理中心 - 品类列表</title>
<#include "../../inc/inc.ftl">
<script type="text/javascript">

var _categoryId;

/**
 * 移除品类
 * @param categoryId
 */
function removeCategory(categoryId){
    confirmTips("确认提示", "你确定要删除该品类吗？", function(){
        _categoryId = categoryId;
        doRemove();
    })
}

/**
 * 移除品类
 */
function doRemove(){
    if(_categoryId == null || _categoryId == ''){
        alert("参数有误，请检查并重新操作");
        return false;
    }

    $ajax({
        type: 'POST',
        url: "/biz/category/delete",
        data: {"categoryId": _categoryId},
        success: function(data) {
            //错误等信息提示
            if(data.code < 0){
                alertTips("错误提示", data.msg);
                return false;
            }

            floatTips({
                content: data.msg,
                fun: function () {
                    window.location.href = "/biz/category/list";
                }
            });
        }
    });//End...$ajax
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
            <li class="breadcrumb-item active">品类列表</li>
        </ol>
        <div class="row">
            <div class="col-12">
                <div class="card mb-3">
                    <div class="card-header">
                        品类列表
                        <a href="/sysadmin/user/index"><span class="float-right"><i class="fa fa-share"></i>返回</span></a>
                    </div>
                    <div class="card-body">

                    <#-- content -->

                        <form name="searchForm" action="/biz/restaurant/list" method="post">
                            <div class="form-row align-items-center">
                                <div class="col-auto">
                                    <div class="input-group mb-2">
                                        <div class="input-group-prepend">
                                            <div class="input-group-text">品类名称</div>
                                        </div>
                                        <input type="text" class="form-control" name="categoryName" placeholder="品类名称" value="${data.productCategory.categoryName!}">
                                    </div>
                                </div>

                                <div class="col-auto">
                                    <button type="button" class="btn btn-primary mb-2" onclick="javascript:document.searchForm.submit();"><i class="fa fa-fw fa-search"></i>搜索</button>&nbsp;&nbsp;&nbsp;&nbsp;
                                    <button type="button" class="btn btn-success mb-2" onclick="javascript:window.location.href='/biz/category/insert';"><i class="fa fa-fw fa-plus"></i>添加新品类</button>
                                </div>
                            </div>
                        </form>

                        <br/>
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover" id="dataTable" width="100%" cellspacing="0">
                                <thead class="thead-dark">
                                <tr>
                                    <th>品类ID</th>
                                    <th>名称</th>
                                    <th>品类编号</th>
                                    <th>所属餐馆</th>
                                    <th>创建时间</th>
                                    <th>修改时间</th>
                                    <th>备注</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <#list data.productCategorys as vo>
                                        <tr>
                                            <td>${vo.categoryId}</td>
                                            <td>${vo.categoryName}</td>
                                            <td>${vo.categoryType!}</td>
                                            <td>${vo.restaurantName}</td>
                                            <td>${vo.createTime?string('yyyy-MM-dd HH:mm')}</td>
                                            <td>${vo.updateTime?string('yyyy-MM-dd HH:mm')}</td>
                                            <td>
                                                <a href="/biz/category/update?categoryId=${vo.categoryId}" title="修改"><i class="fa fa-fw fa-edit"></i></a>
                                                <a href="javascript:;" onclick="return removeCategory(${vo.categoryId});" title="删除"><i class="fa fa-fw fa-remove"></i></a>
                                            </td>
                                        </tr>
                                    </#list>
                                </tbody>
                            </table>
                        </div>
                        <#include "../../inc/pager.ftl">

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

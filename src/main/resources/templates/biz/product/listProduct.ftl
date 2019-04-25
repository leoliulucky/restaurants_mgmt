<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>管理中心 - 菜品列表</title>
<#include "../../inc/inc.ftl">
<script type="text/javascript">

var _productId;

/**
 * 移除菜品
 * @param productId
 */
function removeProduct(productId){
    confirmTips("确认提示", "你确定要删除该菜品吗？", function(){
        _productId = productId;
        doRemove();
    })
}

/**
 * 移除菜品
 */
function doRemove(){
    if(_productId == null || _productId == ''){
        alert("参数有误，请检查并重新操作");
        return false;
    }

    $ajax({
        type: 'POST',
        url: "/biz/product/delete",
        data: {"productId": _productId},
        success: function(data) {
            //错误等信息提示
            if(data.code < 0){
                alertTips("错误提示", data.msg);
                return false;
            }

            floatTips({
                content: data.msg,
                fun: function () {
                    window.location.href = "/biz/product/list";
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
            <li class="breadcrumb-item active">菜品列表</li>
        </ol>
        <div class="row">
            <div class="col-12">
                <div class="card mb-3">
                    <div class="card-header">
                        菜品列表
                        <a href="/sysadmin/user/index"><span class="float-right"><i class="fa fa-share"></i>返回</span></a>
                    </div>
                    <div class="card-body">

                    <#-- content -->

                        <form name="searchForm" action="/biz/product/list" method="post">
                            <div class="form-row align-items-center">
                                <div class="col-auto">
                                    <div class="input-group mb-2">
                                        <div class="input-group-prepend">
                                            <div class="input-group-text">菜品名称</div>
                                        </div>
                                        <input type="text" class="form-control" name="productName" placeholder="菜品名称" value="${data.product.productName!}">
                                    </div>
                                </div>

                                <div class="col-auto">
                                    <button type="button" class="btn btn-primary mb-2" onclick="javascript:document.searchForm.submit();"><i class="fa fa-fw fa-search"></i>搜索</button>&nbsp;&nbsp;&nbsp;&nbsp;
                                    <button type="button" class="btn btn-success mb-2" onclick="javascript:window.location.href='/biz/product/insert';"><i class="fa fa-fw fa-plus"></i>添加新菜品</button>
                                </div>
                            </div>
                        </form>

                        <br/>
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover" id="dataTable" width="100%" cellspacing="0">
                                <thead class="thead-dark">
                                <tr>
                                    <th>菜品ID</th>
                                    <th>名称</th>
                                    <th>图片</th>
                                    <th>单价</th>
                                    <th>库存</th>
                                    <th>描述</th>
                                    <th>类目</th>
                                    <th>创建时间</th>
                                    <th>更新时间</th>
                                    <th>备注</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <#list data.products as vo>
                                        <tr>
                                            <td>${vo.productId}</td>
                                            <td>${vo.productName}</td>
                                            <td><img src="${vo.icon}" width="100" height="100" /></td>
                                            <td>${vo.price?string('#.##')}</td>
                                            <td>${vo.stock}</td>
                                            <td title="${vo.description}"><#if (vo.description?length > 20)>${vo.description?substring(0,20)}...<#else>${vo.description!}</#if></td>
                                            <td>${vo.categoryType}</td>
                                            <td>${vo.createTime?string('yyyy-MM-dd HH:mm')}</td>
                                            <td>${vo.updateTime?string('yyyy-MM-dd HH:mm')}</td>
                                            <td>
                                                <a href="/biz/product/update?productId=${vo.productId}" title="修改"><i class="fa fa-fw fa-edit"></i></a>
                                                <a href="javascript:;" onclick="return removeProduct('${vo.productId}');" title="删除"><i class="fa fa-fw fa-remove"></i></a>
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

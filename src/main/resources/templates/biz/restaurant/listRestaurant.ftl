<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>管理中心 - 餐馆列表</title>
<#include "../../inc/inc.ftl">
<script type="text/javascript">

var _restaurantId;

/**
 * 移除餐馆
 * @param restaurantId
 */
function removeRestaurant(restaurantId){
    confirmTips("确认提示", "你确定要删除该餐馆吗？", function(){
        _restaurantId = restaurantId;
        doRemove();
    })
}

/**
 * 移除成员
 */
function doRemove(){
    if(_restaurantId == null || _restaurantId == ''){
        alert("参数有误，请检查并重新操作");
        return false;
    }

    $ajax({
        type: 'POST',
        url: "/biz/restaurant/delete",
        data: {"restaurantId": _restaurantId},
        success: function(data) {
            //错误等信息提示
            if(data.code < 0){
                alertTips("错误提示", data.msg);
                return false;
            }

            floatTips({
                content: data.msg,
                fun: function () {
                    window.location.href = "/biz/restaurant/list";
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
            <li class="breadcrumb-item active">餐馆列表</li>
        </ol>
        <div class="row">
            <div class="col-12">
                <div class="card mb-3">
                    <div class="card-header">
                        餐馆列表
                        <a href="/sysadmin/user/index"><span class="float-right"><i class="fa fa-share"></i>返回</span></a>
                    </div>
                    <div class="card-body">

                    <#-- content -->

                        <form name="searchForm" action="/biz/restaurant/list" method="post">
                            <div class="form-row align-items-center">
                                <div class="col-auto">
                                    <div class="input-group mb-2">
                                        <div class="input-group-prepend">
                                            <div class="input-group-text">餐馆名称</div>
                                        </div>
                                        <input type="text" class="form-control" name="restaurantName" placeholder="餐馆名称" value="${data.restaurant.restaurantName!}">
                                    </div>
                                </div>

                                <div class="col-auto">
                                    <button type="button" class="btn btn-primary mb-2" onclick="javascript:document.searchForm.submit();"><i class="fa fa-fw fa-search"></i>搜索</button>&nbsp;&nbsp;&nbsp;&nbsp;
                                    <button type="button" class="btn btn-success mb-2" onclick="javascript:window.location.href='/biz/restaurant/insert';"><i class="fa fa-fw fa-plus"></i>添加新餐馆</button>
                                </div>
                            </div>
                        </form>

                        <br/>
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover" id="dataTable" width="100%" cellspacing="0">
                                <thead class="thead-dark">
                                <tr>
                                    <th>餐馆ID</th>
                                    <th>名称</th>
                                    <th>图片</th>
                                    <th>地址</th>
                                    <th>电话</th>
                                    <th>标签</th>
                                    <th>加入时间</th>
                                    <th>修改时间</th>
                                    <th>备注</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <#list data.restaurants as vo>
                                        <tr>
                                            <td>${vo.restaurantId}</td>
                                            <td>${vo.restaurantName}</td>
                                            <td><img src="${vo.icon}" width="100" height="100" /></td>
                                            <td title="${vo.address}"><#if (vo.address?length > 20)>${vo.address?substring(0,20)}...<#else>${vo.address!}</#if></td>
                                            <td>${vo.tel}</td>
                                            <td>${vo.tags}</td>
                                            <td>${vo.createTime?string('yyyy-MM-dd HH:mm')}</td>
                                            <td>${vo.updateTime?string('yyyy-MM-dd HH:mm')}</td>
                                            <td>
                                                <a href="/biz/restaurant/update?restaurantId=${vo.restaurantId}" title="修改"><i class="fa fa-fw fa-edit"></i></a>
                                                <a href="javascript:;" onclick="return removeRestaurant(${vo.restaurantId});" title="删除"><i class="fa fa-fw fa-remove"></i></a>
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

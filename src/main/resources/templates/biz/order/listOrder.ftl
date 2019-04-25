<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>管理中心 - 订单列表</title>
<#include "../../inc/inc.ftl">
<script type="text/javascript">

var _userId;

/**
 * 移除成员
 * @param userId 用户id
 */
function removeUser(userId){
    _userId = userId;
}

/**
 * 移除成员
 */
function doRemove(){
    //检验数据
    if(_userId == null || _userId == ''){
        alert("参数有误，请检查并重新操作");
        return false;
    }

    //异步请求 删除成员
    $ajax({
        type: 'POST',
        url: "/sysadmin/user/delete",
        data: {"userId": _userId},
        success: function(data) {
            //错误等信息提示
            if(data.code < 0){
                alert(data.msg);
                return false;
            }

            floatTips({
                content: data.msg,
                fun: function () {
                    window.location.href = "/sysadmin/user/list";
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
            <li class="breadcrumb-item active">订单列表</li>
        </ol>
        <div class="row">
            <div class="col-12">
                <div class="card mb-3">
                    <div class="card-header">
                        订单列表
                        <a href="/sysadmin/user/index"><span class="float-right"><i class="fa fa-share"></i>返回</span></a>
                    </div>
                    <div class="card-body">

                    <#-- content -->

                        <form name="searchForm" action="/biz/restaurant/list" method="post">
                            <div class="form-row align-items-center">
                                <div class="col-auto">
                                    <div class="input-group mb-2">
                                        <div class="input-group-prepend">
                                            <div class="input-group-text">订单号</div>
                                        </div>
                                        <input type="text" class="form-control" name="orderId" placeholder="订单号" value="${data.order.orderId!}">
                                    </div>
                                </div>

                                <div class="col-auto">
                                    <button type="button" class="btn btn-primary mb-2" onclick="javascript:document.searchForm.submit();"><i class="fa fa-fw fa-search"></i>搜索</button>
                                    <#--&nbsp;&nbsp;&nbsp;&nbsp;-->
                                    <#--<button type="button" class="btn btn-success mb-2" onclick="javascript:window.location.href='/sysadmin/user/insert';"><i class="fa fa-fw fa-plus"></i>添加新餐馆</button>-->
                                </div>
                            </div>
                        </form>

                        <br/>
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover" id="dataTable" width="100%" cellspacing="0">
                                <thead class="thead-dark">
                                <tr>
                                    <th>订单ID</th>
                                    <th>姓名</th>
                                    <th>手机号</th>
                                    <th>地址</th>
                                    <th>金额</th>
                                    <th>订单状态</th>
                                    <th>创建时间</th>
                                    <th>更新时间</th>
                                    <th>备注</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <#list data.orders as vo>
                                        <tr>
                                            <td>${vo.orderId}</td>
                                            <td>${vo.consignee}</td>
                                            <td>${vo.tel}</td>
                                            <td>${vo.address}</td>
                                            <td>${vo.realTotalAmout}</td>
                                            <td>${vo.orderStatus}</td>
                                            <td>${vo.createTime?string('yyyy-MM-dd HH:mm')}</td>
                                            <td>${vo.updateTime?string('yyyy-MM-dd HH:mm')}</td>
                                            <td>
                                                <a href="/biz/restaurant/update?restaurantId=${vo.orderId}" title="修改"><i class="fa fa-fw fa-edit"></i></a>
                                                <a href="javascript:;" data-toggle="modal" data-target="#confirmModal" onclick="return removeUser(${vo.orderId});" title="删除"><i class="fa fa-fw fa-remove"></i></a>
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

<!-- Comfirm Modal-->
<div class="modal fade" id="confirmModal" tabindex="-1" role="dialog" aria-labelledby="confirmModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmModalLabel">移除成员</h5>
                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-body">你确定要删除该用户成员吗？</div>
            <div class="modal-footer">
                <button class="btn btn-secondary" type="button" data-dismiss="modal">取消</button>
                <a class="btn btn-primary" href="javascript:;" onclick="return doRemove();">确定</a>
            </div>
        </div>
    </div>
</div>

</body>
</html>

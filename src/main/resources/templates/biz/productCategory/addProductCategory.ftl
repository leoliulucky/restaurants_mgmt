<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>管理中心|添加新品类</title>
<#include "../../inc/inc.ftl">
<script type="text/javascript">
/**
 * 页面加载后执行
 */
$(function(){
    //初始化页面相应元素绑定事件
    categoryManager.init();
});

/**
 * 新增品类管理控制对象
 *
 * @author liupoyang
 * @since 2019-04-24
 *
 */
var categoryManager = function() {

    var _$categoryName;
    var _$categoryType;
    var _$restaurantId;

    var _fnValidateCategoryName = function(){
        if(_$categoryName.val() == null || _$categoryName.val().length <= 0){
            $("#categoryNameError").show();
            return false;
        }
        $("#categoryNameError").hide();
        return true;
    };

    var _fnValidateCategoryType = function(){
        if(_$categoryType.val() == null || _$categoryType.val().length <= 0){
            $("#categoryTypeError").show();
            return false;
        }
        $("#categoryTypeError").hide();
        return true;
    };

    var _fnValidateRestaurantId = function(){
        if(_$restaurantId.val() == null || _$restaurantId.val().length <= 0){
            $("#restaurantIdError").show();
            return false;
        }
        $("#restaurantIdError").hide();
        return true;
    };


    return {
        init: function(){
            _$categoryName = $("#categoryName");
            _$categoryType = $("#categoryType");
            _$restaurantId = $("#restaurantId");

            _$categoryName.on("blur", _fnValidateCategoryName);
            _$categoryType.on("blur", _fnValidateCategoryType);
        },
        add: function(){
            if(!_fnValidateCategoryName()){
                return false;
            }
            if(!_fnValidateCategoryType()){
                return false;
            }
            if(!_fnValidateRestaurantId()){
                return false;
            }

            $ajax({
                type: 'POST',
                url: "/biz/category/doInsert",
                data: {
                    "categoryName": _$categoryName.val(),
                    "categoryType": _$categoryType.val(),
                    "restaurantId": _$restaurantId.val()
                },
                success: function(data) {
                    //错误等信息提示
                    if(data.code < 0){
                        alertTips("新增品类", data.msg);
                        return false;
                    }

                    //提示新增成功
                    floatTips({
                        content: "添加新品类成功！",
                        fun: function () {
                            window.location.href = "/biz/category/list";
                        }
                    });
                }
            });//End...$ajax
        }
    };//End...return
} ();//End...var categoryManager
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
            <li class="breadcrumb-item">
                <a href="/biz/category/list">品类列表</a>
            </li>
            <li class="breadcrumb-item active">添加新品类</li>
        </ol>
        <div class="row">
            <div class="col-12">
                <div class="card mb-3">
                    <div class="card-header">
                        添加新品类
                        <a href="/biz/category/list"><span class="float-right"><i class="fa fa-share"></i>返回</span></a>
                    </div>
                    <div class="card-body">

                    <#-- content -->

                        <form>
                            <div class="form-group row">
                                <label for="currentPwd" class="col-sm-2 col-form-label">品类名称<span class="text-danger">*</span></label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="categoryName" placeholder="品类名称">
                                    <div id="categoryNameError" class="invalid-feedback">请输入品类名称</div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="newPwd" class="col-sm-2 col-form-label">品类编号<span class="text-danger">*</span></label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="categoryType" placeholder="品类编号">
                                    <div class="text-muted">品类编号是整数的格式，示例：1001</div>
                                    <div id="categoryTypeError" class="invalid-feedback">请输入品类编号</div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="reNewPwd" class="col-sm-2 col-form-label">餐馆<span class="text-danger">*</span></label>
                                <div class="col-sm-10">
                                    <select class="form-control custom-select" id="restaurantId">
                                        <#list data.restaurants as restaurant>
                                            <option value="${restaurant.restaurantId}">${restaurant.restaurantName}</option>
                                        </#list>
                                    </select>
                                    <div id="restaurantIdError" class="invalid-feedback">请选择所属餐馆</div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="col-sm-10">
                                    <button type="button" class="btn btn-primary" onclick="return categoryManager.add();">确定添加</button>&nbsp;&nbsp;
                                    <button type="button" class="btn btn-secondary" onclick="javascript:window.location.href='/biz/category/list';">返回</button>
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

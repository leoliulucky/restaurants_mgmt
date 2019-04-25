<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>管理中心|修改菜品信息</title>
<#include "../../inc/inc.ftl">
<script type="text/javascript">
/**
 * 页面加载后执行
 */
$(function(){
    //初始化页面相应元素绑定事件
    productManager.init();
});

/**
 * 修改菜品管理控制对象
 *
 * @author liupoyang
 * @since 2019-04-24
 *
 */
var productManager = function() {

    var _$productName;
    var _$price;
    var _$stock;
    var _$description;
    var _$icon;
    var _$categoryType;
    var _productId;

    var _fnValidateProductName = function(){
        if(_$productName.val() == null || _$productName.val().length <= 0){
            $("#productNameError").show();
            return false;
        }
        $("#productNameError").hide();
        return true;
    };

    var _fnValidatePrice = function(){
        if(_$price.val() == null || _$price.val().length <= 0){
            $("#priceError").show();
            return false;
        }
        $("#priceError").hide();
        return true;
    };

    var _fnValidateStock = function(){
        if(_$stock.val() == null || _$stock.val().length <= 0){
            $("#stockError").show();
            return false;
        }
        $("#stockError").hide();
        return true;
    };

    var _fnValidateDescription = function(){
        if(_$description.val() == null || _$description.val().length <= 0){
            $("#descriptionError").show();
            return false;
        }
        $("#descriptionError").hide();
        return true;
    };

    var _fnValidateIcon = function(){
        if(_$icon.val() == null || _$icon.val().length <= 0){
            $("#iconError").show();
            return false;
        }
        $("#iconError").hide();
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

    return {
        init: function(){
            _$productName = $("#productName");
            _$price = $("#price");
            _$stock = $("#stock");
            _$description = $("#description");
            _$icon = $("#icon");
            _$categoryType = $("#categoryType");
            _productId = $("#productId").val();

            _$productName.on("blur", _fnValidateProductName);
            _$price.on("blur", _fnValidatePrice);
            _$stock.on("blur", _fnValidateStock);
            _$description.on("blur", _fnValidateDescription);
            _$icon.on("blur", _fnValidateIcon);
        },
        edit: function(){
            if(!_fnValidateProductName()){
                return false;
            }
            if(!_fnValidatePrice()){
                return false;
            }
            if(!_fnValidateStock()){
                return false;
            }
            if(!_fnValidateDescription()){
                return false;
            }
            if(!_fnValidateIcon()){
                return false;
            }
            if(!_fnValidateCategoryType()){
                return false;
            }
            if(_productId == null || _productId.length <= 0){
                alertTips("修改菜品", "要修改的菜品不存在");
                return false;
            }

            $ajax({
                type: 'POST',
                url: "/biz/product/doUpdate",
                data: {
                    "productName": _$productName.val(),
                    "price": _$price.val(),
                    "stock": _$stock.val(),
                    "description": _$description.val(),
                    "icon": _$icon.val(),
                    "categoryType": _$categoryType.val(),
                    "productId": _productId
                },
                success: function(data) {
                    //错误等信息提示
                    if(data.code < 0){
                        alertTips("修改菜品", data.msg);
                        return false;
                    }

                    //提示修改成功
                    floatTips({
                        content: "修改菜品信息成功！",
                        fun: function () {
                            window.location.href = "/biz/product/list";
                        }
                    });
                }
            });//End...$ajax
        }
    };//End...return
} ();//End...var productManager
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
                <a href="/biz/product/list">菜品列表</a>
            </li>
            <li class="breadcrumb-item active">修改菜品信息</li>
        </ol>
        <div class="row">
            <div class="col-12">
                <div class="card mb-3">
                    <div class="card-header">
                        修改菜品信息
                        <a href="/biz/product/list"><span class="float-right"><i class="fa fa-share"></i>返回</span></a>
                    </div>
                    <div class="card-body">

                    <#-- content -->

                        <form>
                            <div class="form-group row">
                                <label for="reNewPwd" class="col-sm-2 col-form-label">餐馆<span class="text-danger">*</span></label>
                                <div class="col-sm-10">
                                    <div id="restaurantId">${data.restaurant.restaurantName}</div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="currentPwd" class="col-sm-2 col-form-label">菜品名称<span class="text-danger">*</span></label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="productName" placeholder="菜品名称" value="${data.product.productName }">
                                    <div id="productNameError" class="invalid-feedback">请输入菜品名称</div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="newPwd" class="col-sm-2 col-form-label">单价<span class="text-danger">*</span></label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="price" placeholder="单价" value="${data.product.price?string('#.##')}">
                                    <div id="priceError" class="invalid-feedback">请输入单价</div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="reNewPwd" class="col-sm-2 col-form-label">库存<span class="text-danger">*</span></label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="stock" placeholder="库存" value="${data.product.stock }">
                                    <div id="stockError" class="invalid-feedback">请输入库存</div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="reNewPwd" class="col-sm-2 col-form-label">描述<span class="text-danger">*</span></label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="description" placeholder="描述" value="${data.product.description }">
                                    <div id="descriptionError" class="invalid-feedback">请输入描述</div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="reNewPwd" class="col-sm-2 col-form-label">图片<span class="text-danger">*</span></label>
                                <div class="col-sm-10">
                                    <img src="${data.product.icon}" style="padding-bottom:5px;width: 200px; height: 200px" />
                                    <br />
                                    <input type="text" class="form-control" id="icon" placeholder="图片" value="${data.product.icon}">
                                    <div id="iconError" class="invalid-feedback">请输入图片</div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="reNewPwd" class="col-sm-2 col-form-label">品类<span class="text-danger">*</span></label>
                                <div class="col-sm-10">
                                    <select class="form-control custom-select" id="categoryType">
                                        <#list data.productCategories as productCategory>
                                            <#assign selectedStr=''>
                                                <#if data.product.categoryType == productCategory.categoryType>
                                                    <#assign selectedStr=' selected="selected"'>
                                                </#if>
                                            <option value="${productCategory.categoryType}"${selectedStr}>${productCategory.categoryName}</option>
                                        </#list>
                                    </select>
                                    <div id="categoryTypeError" class="invalid-feedback">请选择所属品类</div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="col-sm-10">
                                    <#-- 隐藏域 -->
                                    <input type="hidden" id="productId" value="${data.product.productId}" />
                                    <#-- /隐藏域 -->
                                    <button type="button" class="btn btn-primary" onclick="return productManager.edit();">保存修改</button>&nbsp;&nbsp;
                                    <button type="button" class="btn btn-secondary" onclick="javascript:window.location.href='/biz/product/list';">返回</button>
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

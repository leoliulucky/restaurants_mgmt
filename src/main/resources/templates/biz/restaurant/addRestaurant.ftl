<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>管理中心|添加新餐馆</title>
<#include "../../inc/inc.ftl">
<script type="text/javascript">
/**
 * 页面加载后执行
 */
$(function(){
    //初始化页面相应元素绑定事件
    restaurantManager.init();
});

/**
 * 新增餐馆管理控制对象
 *
 * @author liupoyang
 * @since 2019-04-24
 *
 */
var restaurantManager = function() {

    var _$restaurantName;
    var _$icon;
    var _$address;
    var _$tel;
    var _$tags;

    var _fnValidateRestaurantName = function(){
        if(_$restaurantName.val() == null || _$restaurantName.val().length <= 0){
            $("#restaurantNameError").show();
            return false;
        }
        $("#restaurantNameError").hide();
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

    var _fnValidateAddress = function(){
        if(_$address.val() == null || _$address.val().length <= 0){
            $("#addressError").show();
            return false;
        }
        $("#addressError").hide();
        return true;
    };

    var _fnValidateTel = function(){
        if(_$tel.val() == null || _$tel.val().length <= 0){
            $("#telError").show();
            return false;
        }
        $("#telError").hide();
        return true;
    };

    var _fnValidateTags = function(){
        if(_$tags.val() == null || _$tags.val().length <= 0){
            $("#tagsError").show();
            return false;
        }
        $("#tagsError").hide();
        return true;
    };


    return {
        init: function(){
            _$restaurantName = $("#restaurantName");
            _$icon = $("#icon");
            _$address = $("#address");
            _$tel = $("#tel");
            _$tags = $("#tags");

            _$restaurantName.on("blur", _fnValidateRestaurantName);
            _$icon.on("blur", _fnValidateIcon);
            _$address.on("blur", _fnValidateAddress);
            _$tel.on("blur", _fnValidateTel);
            _$tags.on("blur", _fnValidateTags);
        },
        add: function(){
            if(!_fnValidateRestaurantName()){
                return false;
            }
            if(!_fnValidateIcon()){
                return false;
            }
            if(!_fnValidateAddress()){
                return false;
            }
            if(!_fnValidateTel()){
                return false;
            }
            if(!_fnValidateTags()){
                return false;
            }

            $ajax({
                type: 'POST',
                url: "/biz/restaurant/doInsert",
                data: {
                    "restaurantName": _$restaurantName.val(),
                    "icon": _$icon.val(),
                    "address": _$address.val(),
                    "tel": _$tel.val(),
                    "tags": _$tags.val()
                },
                success: function(data) {
                    //错误等信息提示
                    if(data.code < 0){
                        alertTips("错误提示", data.msg);
                        return false;
                    }

                    //提示新增成功
                    floatTips({
                        content: "添加新餐馆成功！",
                        fun: function () {
                            window.location.href = "/biz/restaurant/list";
                        }
                    });
                }
            });//End...$ajax
        }
    };//End...return
} ();//End...var restaurantManager
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
                <a href="/biz/restaurant/list">餐馆列表</a>
            </li>
            <li class="breadcrumb-item active">添加新餐馆</li>
        </ol>
        <div class="row">
            <div class="col-12">
                <div class="card mb-3">
                    <div class="card-header">
                        添加新餐馆
                        <a href="/biz/restaurant/list"><span class="float-right"><i class="fa fa-share"></i>返回</span></a>
                    </div>
                    <div class="card-body">

                    <#-- content -->

                        <form>
                            <div class="form-group row">
                                <label for="currentPwd" class="col-sm-2 col-form-label">餐馆名称<span class="text-danger">*</span></label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="restaurantName" placeholder="餐馆名称">
                                    <div id="restaurantNameError" class="invalid-feedback">请输入餐馆名称</div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="newPwd" class="col-sm-2 col-form-label">餐馆地址<span class="text-danger">*</span></label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="address" placeholder="餐馆地址">
                                    <div id="addressError" class="invalid-feedback">请输入餐馆地址</div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="reNewPwd" class="col-sm-2 col-form-label">电话<span class="text-danger">*</span></label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="tel" placeholder="电话">
                                    <div id="telError" class="invalid-feedback">请输入电话</div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="reNewPwd" class="col-sm-2 col-form-label">标签<span class="text-danger">*</span></label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="tags" placeholder="标签">
                                    <div class="text-muted">标签格式以 | 分隔，示例：五道口|火锅</div>
                                    <div id="tagsError" class="invalid-feedback">请输入标签</div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="reNewPwd" class="col-sm-2 col-form-label">图片<span class="text-danger">*</span></label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="icon" placeholder="图片">
                                    <div id="iconError" class="invalid-feedback">请输入图片</div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="col-sm-10">
                                    <button type="button" class="btn btn-primary" onclick="return restaurantManager.add();">确定添加</button>&nbsp;&nbsp;
                                    <button type="button" class="btn btn-secondary" onclick="javascript:window.location.href='/biz/restaurant/list';">返回</button>
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

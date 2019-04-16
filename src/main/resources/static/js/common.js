$(function(){
    $('body').toggleClass('fixed-nav');
    //$('nav').toggleClass('fixed-top static-top');
    $('#mainNav').toggleClass('fixed-top static-top');
});

/**
 * 封装jquery的ajax操作。 进行通用错误处理
 * @author liupoyang
 * @since 2019-04-07
 */
function $ajax(ajaxOption) {
    var _successFn = ajaxOption.success;
    ajaxOption.success = function(ddata) {
        try {
            if (_$ajaxResultExecute(ddata)) {
                if (_successFn) {
                    _successFn(ddata);
                }
            }
        } catch(e) {
            console.error(e);
        }
    };
    ajaxOption.data.asyn = true;
    var _ajax = $.extend(true, {}, _$ajaxDefaultOption, ajaxOption);
    $.ajax(_ajax);
}

/**
 * 处理ajax返回的数据，对错误进行操作
 * @author liupoyang
 * @since 2019-04-07
 * @return 没有错误返回true， 有错误返回false
 */
function _$ajaxResultExecute(data){if(data == null){alert("返回来的数据为空");return false;}if(data.code == -500||data.code == -501||data.code == -502){alert(data.msg);return false;}return true;}

/**
 * 默认的ajax参数
 */
var _$ajaxDefaultOption = {
    cache:false,
    dataType: 'json',
    error: function(xhr, errSta, errThr) {
        alert('服务器或网络异常，请稍后执行此操作！');
    },
    complete:function(){
        //hideLoading();
    },
    beforeSend: function() {
        //showLoading();
    }
};

/**
 * 弹出浮框方法
 * 示例 {content:"您有新消息了", type:"warning", position:"toast-top-center", milliSecond:2000, fun:function(){alert('ok')}}
 * @param content
 * @param type 提示方式，值有：
            info
            success
            warning
            error
 * @param position
            toast-top-left  顶端左边
            toast-top-right    顶端右边
            toast-top-center  顶端中间
            toast-top-full-width 顶端，宽度铺满整个屏幕
            toast-botton-right
            toast-bottom-left
            toast-bottom-center
            toast-bottom-full-width
 * @param milliSecond 表示过多少毫秒自动关闭浮框，小于0表示不自动关闭
 * @param fun 表示过多少毫秒后要执行的操作
 */
function floatTips(obj){
    if(obj && obj.position){
        toastr.options.positionClass = obj.position;
    }else{
        toastr.options.positionClass = "toast-top-center";
    }
    if(obj && obj.type){
        toastr[obj.type](obj.content);
    }else{
        toastr["success"](obj.content);
    }

    if(obj && obj.milliSecond > 0){
        setTimeout(function(){obj.fun&&obj.fun();}, obj.milliSecond);
    }else if(obj && obj.milliSecond < 0){
        obj.fun&&obj.fun();
    }else{
        setTimeout(function(){obj&&obj.fun&&obj.fun();}, 2000);
    }
}

/**
 * 信息弹出提示窗体方法
 * @param title
 * @param content
 */
function alertTips(title, content){
    doOpenWin(title, content);
}

/**
 * 确认弹出提示窗体方法
 * @param title
 * @param content
 * @param fun
 */
function confirmTips(title, content, fun){
    doOpenWin(title, content, fun, "confirm");
}

/**
 * 确认弹出提示窗体方法
 * @param title
 * @param content
 * @param fun
 */
function doOpenWin(title, content, fun, type){
    var $tipWin = $("#benxiaopao_confirmModal");
    if($tipWin.length == 0){
        var _popupHtml =
            '<div class="modal fade" id="benxiaopao_confirmModal" tabindex="-1" role="dialog" aria-labelledby="benxiaopao_confirmModalLabel" aria-hidden="true">' +
            '    <div class="modal-dialog" role="document">' +
            '        <div class="modal-content">' +
            '            <div class="modal-header">' +
            '                <h5 class="modal-title" id="benxiaopao_confirmModalLabel">' + title + '</h5>' +
            '                <button class="close" type="button" data-dismiss="modal" aria-label="Close">' +
            '                    <span aria-hidden="true">×</span>' +
            '                </button>' +
            '            </div>' +
            '            <div class="modal-body" id="benxiaopao_confirmModalContent">' + content + '</div>' +
            '            <div class="modal-footer">';

        if(type == "confirm"){
            _popupHtml += '                <button class="btn btn-secondary" type="button" data-dismiss="modal">取消</button>';
        }

        _popupHtml +=
            '                <a class="btn btn-primary" href="javascript:;">确定</a>' +
            '            </div>' +
            '        </div>' +
            '    </div>' +
            '</div>';

        $(document.body).append(_popupHtml);
        $tipWin = $("#benxiaopao_confirmModal");
        //绑定按钮事件
        $tipWin.find("a.btn-primary").unbind("click").on("click", function(){
            $tipWin.modal('hide');
            fun&&fun();
        });
    }else{
        $("#benxiaopao_confirmModalLabel").html(title);
        $("#benxiaopao_confirmModalContent").html(content);
        //绑定按钮事件
        $tipWin.find("a.btn-primary").unbind("click").on("click", function(){
            $tipWin.modal('hide');
            fun&&fun();
        });
    }
    $tipWin.modal('show');
}

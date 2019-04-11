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
<!-- Bootstrap core CSS-->
<link href="/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<!-- Custom fonts for this template-->
<link href="/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<!-- Custom styles for this template-->
<link href="/css/sb-admin.css" rel="stylesheet">

<!-- Bootstrap core JavaScript-->
<script src="/vendor/jquery/jquery.min.js"></script>
<script src="/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- Core plugin JavaScript-->
<script src="/vendor/jquery-easing/jquery.easing.min.js"></script>

<link href="/toastr/toastr.min.css" rel="stylesheet" />
<script src="/toastr/toastr.min.js"></script>

<script src="/js/common.js"></script>

<#if result??>
    <#assign code = result.getCode() />
    <#assign msg = result.getMsg() />
    <#assign data = result.data />
    <#-- 业务错误时，页面给出提醒 -->
    <#if (code < 0)>
        <script type="text/javascript">
            $(function () {
                alertTips("出错了", '${msg}');
            });
        </script>
    </#if>
</#if>

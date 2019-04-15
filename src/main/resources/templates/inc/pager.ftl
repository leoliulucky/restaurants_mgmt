<#-- 页面大小 -->
<#assign pageSize = data.pager.pageSize>
<#-- 总记录数 -->
<#assign totalCount = data.pager.totalCount>
<#-- 总页数 -->
<#assign pageCount = data.pager.pageCount>
<#-- 当前页码 -->
<#assign curPageNum = data.pager.pageNum>
<#if !curPageNum?? || curPageNum <= 0>
    <#assign curPageNum = 1>
</#if>
<#-- 分页连接页码数 -->
<#assign linkSize = 8>

<div class="col-sm-12 col-md-5 float-left">
    <div class="text-muted">
        共 ${pageCount} 页 ${totalCount} 条记录&nbsp;&nbsp;
        每页
        <select name="dataTable_length" class="form-control form-control-sm custom-select col-2">
            <option value="10" <#if pageSize==10>selected</#if>>10</option>
            <option value="25" <#if pageSize==25>selected</#if>>25</option>
            <option value="50" <#if pageSize==50>selected</#if>>50</option>
            <option value="100" <#if pageSize==100>selected</#if>>100</option>
        </select> 条
    </div>
</div>

<nav aria-label="Page navigation example float-right">
    <ul class="pagination justify-content-end">
        <#-- 首页显示 -->
        <#if curPageNum == 1>
            <li class="page-item disabled">
                <a class="page-link" href="javascript:;" title="首页">&lt;&lt;</a>
            </li>
        <#else>
            <li class="page-item">
                <a class="page-link" href="javascript:;" onclick="return $pager.go(1);" title="首页">&lt;&lt;</a>
            </li>
        </#if>

        <#-- 上一页显示 -->
        <#if curPageNum == 1>
            <li class="page-item disabled">
                <a class="page-link" href="javascript:;" title="上一页">&lt;</a>
            </li>
        <#else>
            <li class="page-item">
                <a class="page-link" href="javascript:;" onclick="return $pager.go(${curPageNum-1});" title="上一页">&lt;</a>
            </li>
        </#if>

        <#-- 开始---页码列表显示 -->

        <#-- 多于分页连接页码数 -->
        <#if pageCount gt linkSize>
            <#-- 当前页在第4页之前 -->
            <#if curPageNum <= 4>
                <#list 1..4 as item>
                    <#-- 当前页 -->
                    <#if item == curPageNum>
                        <li class="page-item active">
                            <a class="page-link" href="javascript:;">${item} <span class="sr-only">(current)</span></a>
                        </li>
                    <#else>
                        <li class="page-item">
                            <a class="page-link" href="javascript:;" onclick="return $pager.go(${item });">${item}</a>
                        </li>
                    </#if>
                </#list>
                <#if curPageNum == 3>
                    <li class="page-item">
                        <a class="page-link" href="javascript:;" onclick="return $pager.go(5);">5</a>
                    </li>
                </#if>
                <#if curPageNum == 4>
                    <li class="page-item">
                        <a class="page-link" href="javascript:;" onclick="return $pager.go(5);">5</a>
                    </li>
                    <li class="page-item">
                        <a class="page-link" href="javascript:;" onclick="return $pager.go(6);">6</a>
                    </li>
                </#if>
                <span>...</span>
                <li class="page-item">
                    <a class="page-link" href="javascript:;" onclick="return $pager.go(${pageCount});">${pageCount}</a>
                </li>
            <#-- 当前页在倒数第4页之后 -->
            <#elseif curPageNum > pageCount-4>
                <li class="page-item">
                    <a class="page-link" href="javascript:;" onclick="return $pager.go(1);">1</a>
                </li>
                <span>...</span>
                <#if curPageNum == pageCount-2>
                    <li class="page-item">
                        <a class="page-link" href="javascript:;" onclick="return $pager.go(${pageCount-4});">${pageCount-4}</a>
                    </li>
                </#if>
                <#if curPageNum == pageCount-3>
                    <li class="page-item">
                        <a class="page-link" href="javascript:;" onclick="return $pager.go(${pageCount-5});">${pageCount-5}</a>
                    </li>
                    <li class="page-item">
                        <a class="page-link" href="javascript:;" onclick="return $pager.go(${pageCount-4});">${pageCount-4}</a>
                    </li>
                </#if>
                <#list (pageCount-3)..pageCount as item>
                    <#-- 当前页 -->
                    <#if item == curPageNum>
                        <li class="page-item active">
                            <a class="page-link" href="javascript:;">${item} <span class="sr-only">(current)</span></a>
                        </li>
                    <#else>
                        <li class="page-item">
                            <a class="page-link" href="javascript:;" onclick="return $pager.go(${item});">${item}</a>
                        </li>
                    </#if>
                </#list>
            <#-- 当前页在第四页和倒数第四页之间 -->
            <#else>
                <li class="page-item">
                    <a class="page-link" href="javascript:;" onclick="return $pager.go(1);">1</a>
                </li>
                <span>...</span>
                <li class="page-item">
                    <a class="page-link" href="javascript:;" onclick="return $pager.go(${curPageNum-2});">${curPageNum-2}</a>
                </li>
                <li class="page-item">
                    <a class="page-link" href="javascript:;" onclick="return $pager.go(${curPageNum-1});">${curPageNum-1}</a>
                </li>
                <li class="page-item active">
                    <a class="page-link" href="javascript:;">${curPageNum} <span class="sr-only">(current)</span></a>
                </li>
                <li class="page-item">
                    <a class="page-link" href="javascript:;" onclick="return $pager.go(${curPageNum+1});">${curPageNum+1}</a>
                </li>
                <li class="page-item">
                    <a class="page-link" href="javascript:;" onclick="return $pager.go(${curPageNum+2});">${curPageNum+2}</a>
                </li>
                <span>...</span>
                <li class="page-item">
                    <a class="page-link" href="javascript:;" onclick="return $pager.go(${pageCount});">${pageCount}</a>
                </li>
            </#if>
        <#-- 不多于分页连接页码数 -->
        <#else>
            <#list 1..pageCount as item>
                <#-- 当前页 -->
                <#if item == curPageNum>
                    <li class="page-item active">
                        <a class="page-link" href="javascript:;">${item} <span class="sr-only">(current)</span></a>
                    </li>
                <#else>
                    <li class="page-item">
                        <a class="page-link" href="javascript:;" onclick="return $pager.go(${item});">${item}</a>
                    </li>
                </#if>
            </#list>
        </#if>

        <#-- 结束---页码列表显示 -->

        <#-- 下一页显示 -->
        <#if curPageNum == pageCount>
            <li class="page-item disabled">
                <a class="page-link" href="javascript:;" title="下一页">&gt;</a>
            </li>
        <#else>
            <li class="page-item">
                <a class="page-link" href="javascript:;" onclick="return $pager.go(${curPageNum+1});" title="下一页">&gt;</a>
            </li>
        </#if>

        <#-- 末页显示 -->
        <#if curPageNum == pageCount>
            <li class="page-item disabled">
                <a class="page-link" href="javascript:;" title="末页">&gt;&gt;</a>
            </li>
        <#else>
            <li class="page-item">
                <a class="page-link" href="javascript:;" onclick="return $pager.go(${pageCount});" title="末页">&gt;&gt;</a>
            </li>
        </#if>
    </ul>
</nav>
<script type="text/javascript">
    //分页js形式带参请求 liupoyang 2019-04-14
    var $pager=(function(manager){manager.go=function(page){$(document.searchForm).append('<input type="hidden" name="page" value="'+page+'" />').submit();};return manager;}($pager || {}));
</script>
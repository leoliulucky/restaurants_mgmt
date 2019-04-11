<ul class="navbar-nav navbar-sidenav" id="exampleAccordion">
<#--<li class="nav-item" data-toggle="tooltip" data-placement="right" title="Dashboard">
    <a class="nav-link" href="index.html">
        <i class="fa fa-fw fa-dashboard"></i>
        <span class="nav-link-text">Dashboard</span>
    </a>
</li>
<li class="nav-item" data-toggle="tooltip" data-placement="right" title="Charts">
    <a class="nav-link" href="charts.html">
        <i class="fa fa-fw fa-area-chart"></i>
        <span class="nav-link-text">Charts</span>
    </a>
</li>
<li class="nav-item" data-toggle="tooltip" data-placement="right" title="Tables">
    <a class="nav-link" href="tables.html">
        <i class="fa fa-fw fa-table"></i>
        <span class="nav-link-text">Tables</span>
    </a>
</li>-->

    <#--遍历展示父菜单-->
    <#list result.data.menus as menu>
        <#if menu.parentId == 0>

            <li class="nav-item" data-toggle="tooltip" data-placement="right" title="${menu.menuName}">
                <a class="nav-link nav-link-collapse collapsed" data-toggle="collapse" href="#menu${menu.menuId}" data-parent="#exampleAccordion">
                    <i class="fa fa-fw fa-sitemap"></i>
                    <span class="nav-link-text">${menu.menuName}</span>
                </a>
                <ul class="sidenav-second-level collapse" id="menu${menu.menuId}">

                    <#--再次遍历展示子菜单-->
                    <#list result.data.menus as subMenu>
                        <#if subMenu.parentId == menu.menuId>
                            <li>
                                <a href="${subMenu.url }">${subMenu.menuName }</a>
                            </li>
                        </#if>
                    </#list>

                </ul>
            </li>

        </#if>
    </#list>

<#--<li class="nav-item" data-toggle="tooltip" data-placement="right" title="Components">
    <a class="nav-link nav-link-collapse collapsed" data-toggle="collapse" href="#collapseComponents" data-parent="#exampleAccordion">
        <i class="fa fa-fw fa-wrench"></i>
        <span class="nav-link-text">Components</span>
    </a>
    <ul class="sidenav-second-level collapse" id="collapseComponents">
        <li>
            <a href="navbar.html">Navbar</a>
        </li>
        <li>
            <a href="cards.html">Cards</a>
        </li>
    </ul>
</li>
<li class="nav-item" data-toggle="tooltip" data-placement="right" title="Example Pages">
    <a class="nav-link nav-link-collapse collapsed" data-toggle="collapse" href="#collapseExamplePages" data-parent="#exampleAccordion">
        <i class="fa fa-fw fa-file"></i>
        <span class="nav-link-text">Example Pages</span>
    </a>
    <ul class="sidenav-second-level collapse" id="collapseExamplePages">
        <li>
            <a href="login.html">Login Page</a>
        </li>
        <li>
            <a href="register.html">Registration Page</a>
        </li>
        <li>
            <a href="forgot-password.html">Forgot Password Page</a>
        </li>
        <li>
            <a href="blank.html">Blank Page</a>
        </li>
    </ul>
</li>
<li class="nav-item" data-toggle="tooltip" data-placement="right" title="Menu Levels">
    <a class="nav-link nav-link-collapse collapsed" data-toggle="collapse" href="#collapseMulti" data-parent="#exampleAccordion">
        <i class="fa fa-fw fa-sitemap"></i>
        <span class="nav-link-text">Menu Levels</span>
    </a>
    <ul class="sidenav-second-level collapse" id="collapseMulti">
        <li>
            <a href="#">Second Level Item</a>
        </li>
        <li>
            <a href="#">Second Level Item</a>
        </li>
        <li>
            <a href="#">Second Level Item</a>
        </li>
        <li>
            <a class="nav-link-collapse collapsed" data-toggle="collapse" href="#collapseMulti2">Third Level</a>
            <ul class="sidenav-third-level collapse" id="collapseMulti2">
                <li>
                    <a href="#">Third Level Item</a>
                </li>
                <li>
                    <a href="#">Third Level Item</a>
                </li>
                <li>
                    <a href="#">Third Level Item</a>
                </li>
            </ul>
        </li>
    </ul>
</li>-->
<#--<li class="nav-item" data-toggle="tooltip" data-placement="right" title="Link">
    <a class="nav-link" href="#">
        <i class="fa fa-fw fa-link"></i>
        <span class="nav-link-text">Link</span>
    </a>
</li>-->

<#--<script type="text/javascript">-->
    <#--$(function(){-->
        <#--$ajax({-->
            <#--type: 'POST',-->
            <#--url: "/sysadmin/menu/listVisible",-->
            <#--data: {},-->
            <#--dataType: 'json',-->
            <#--success: function(data) {-->
                <#--//错误等信息提示-->
                <#--if(data.code < 0){-->
                    <#--alert(data.msg);-->
                    <#--return false;-->
                <#--}-->
                <#--var menus = data.data.menus;-->

                <#--var _htmlContent = '';-->
                <#--// 遍历展示父菜单-->
                <#--$.each(menus, function (i, menu) {-->
                    <#--if(menu.parentId == 0){-->
                        <#--_htmlContent += '<li class="nav-item" data-toggle="tooltip" data-placement="right" title="' + menu.menuName + '>';-->
                        <#--_htmlContent += '   <a class="nav-link nav-link-collapse collapsed" data-toggle="collapse" href="#menu' + menu.menuId + '" data-parent="#exampleAccordion">';-->
                        <#--_htmlContent += '       <i class="fa fa-fw fa-wrench"></i>';-->
                        <#--_htmlContent += '       <span class="nav-link-text">' + menu.menuName + '</span>';-->
                        <#--_htmlContent += '   </a>';-->
                        <#--_htmlContent += '   <ul class="sidenav-second-level collapse" id="menu' + menu.menuId + '">';-->

                        <#--// 再次遍历展示子菜单-->
                        <#--$.each(menus, function (j, subMenu) {-->
                            <#--if(subMenu.parentId == menu.menuId) {-->
                                <#--_htmlContent += '<li>';-->
                                <#--_htmlContent += '   <a href="' + subMenu.url + '">' + subMenu.menuName + '</a>';-->
                                <#--_htmlContent += '</li>';-->
                            <#--}-->
                        <#--});-->

                        <#--_htmlContent += '   </ul>';-->
                        <#--_htmlContent += '</li>';-->
                    <#--}-->
                <#--});-->


                <#--$("#exampleAccordion").html(_htmlContent);-->
                <#--// document.write(_htmlContent)-->
                <#--$("#exampleAccordion").trigger("create");-->
            <#--}-->
        <#--});//End...$ajax-->
    <#--});-->
<#--</script>-->
</ul>
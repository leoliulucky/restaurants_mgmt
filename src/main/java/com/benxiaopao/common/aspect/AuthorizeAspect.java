package com.benxiaopao.common.aspect;

import com.benxiaopao.common.supers.BaseService;
import com.benxiaopao.common.util.ThreadContent;
import com.benxiaopao.common.util.ViewResult;
import com.benxiaopao.provider.dao.model.Menu;
import com.benxiaopao.provider.dao.model.RoleMenuPurviewKey;
import com.benxiaopao.sysadmin.menu.service.MenuService;
import com.benxiaopao.sysadmin.user.vo.SysUserVo;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * 权限处理<br />
 * 对所有用户进行路径权限判断，如果没有权限，则返回登陆页面；否则，继续请求响应
 *
 * Created by liupoyang
 * 2019-04-03
 */
@Aspect
@Component
@Slf4j
public class AuthorizeAspect extends BaseService {
    /**
     * 菜单 逻辑组件
     */
    @Autowired
    private MenuService menuService;

    /**
     * 定义切点
     */
    @Pointcut("execution(public * com.benxiaopao.*.*.controller.*Controller.*(..))")
    public void authorize() {
    }

    /**
     * 权限环绕通知
     * @param proceedingJoinPoint
     */
    @Around("authorize()")
    public Object doAuthorize(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        loadRequestAndResponse();

        //判断用户请求是否是可放行通过的特殊请求
        if (isExclude(proceedingJoinPoint)) {
            return proceedingJoinPoint.proceed();
        }

        HttpServletRequest request = ThreadContent.request();
        HttpServletResponse response = ThreadContent.response();

        String asynStr = request.getParameter("asyn");
        //是否异步请求
        boolean asyn = false;
        if(!Strings.isNullOrEmpty(asynStr)){
            asyn = Boolean.valueOf(asynStr);
        }

        //当前登录用户
        SysUserVo user = (SysUserVo) currentUser();
        if(user == null){
            log.info("#用户未登录");
            if(asyn){
                out(response, ViewResult.newInstance().code(-500).msg("您还没有登录").json());
                return null;
            }
            response.sendRedirect(basePath(request) + "/sysadmin/user/login");
            return null;
        }

        String url = request.getServletPath();
        //查询用户请求所对应的菜单
        Menu menu = new Menu();
        menu.setUrl(url);
        List<Menu> menuList = menuService.listMenuByWhere(menu);

        if(!CollectionUtils.isEmpty(menuList)){
            //判断用户请求所对应对应的菜单的权限
            for(Menu item : menuList){
                RoleMenuPurviewKey roleMenuPurviewKey = new RoleMenuPurviewKey();
                roleMenuPurviewKey.setRoleId(user.getRoleId());
                roleMenuPurviewKey.setMenuId(item.getMenuId());
                if(!CollectionUtils.isEmpty(menuService.listRoleMenuPurviewByWhere(roleMenuPurviewKey))){
                    //将当前请求的url的菜单传回前端，用以处理左侧菜单选中样式问题
                    request.setAttribute("currentMenu", item);
                    Object proccedResult = proceedingJoinPoint.proceed();
                    handleVisibleMenus(proceedingJoinPoint, request);
                    return proccedResult;
                }
            }

            log.info("#no authorize 用户【" + user.getUserId() + "】没有权限访问：" + url);
            ViewResult result = ViewResult.newInstance().code(-502).msg("您没有权限访问");
            if(asyn){
                out(response, result.json());
                return null;
            }
            request.setAttribute("result", result);
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
            return null;
        }

        Object proccedResult = proceedingJoinPoint.proceed();
        handleVisibleMenus(proceedingJoinPoint, proccedResult);
        return proccedResult;
    }

    /**
     * 判断用户请求是否是可放行通过的特殊请求<br />私有方法
     * @param proceedingJoinPoint
     * @return
     */
    private boolean isExclude(ProceedingJoinPoint proceedingJoinPoint) {
        //获取访问目标方法
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();
        return targetMethod.isAnnotationPresent(ExcludeAuthorize.class);
    }

    private void loadRequestAndResponse() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        ThreadContent.set(request, response);
    }

    /**
     * 获取项目访问基路径<br />私有方法
     * @param request HttpServletRequest对象
     * @return
     * @throws MalformedURLException
     * @throws URISyntaxException
     */
    private String basePath(HttpServletRequest request) throws MalformedURLException, URISyntaxException {
        //访问基路径
        String basePath = "http" + "://"+ request.getServerName() + ":"+ request.getServerPort() + request.getContextPath();
        URL urlObj = new URL(basePath);
        if (urlObj.getPort() == urlObj.getDefaultPort()) {
            urlObj = new URL(urlObj.getProtocol(), urlObj.getHost(), -1, urlObj.getPath());
        }
        basePath = urlObj.toURI().toString();
        return basePath;
    }

    /**
     * 输出Json串到前端<br />私有方法
     * @param response 响应对象
     * @param content Json串内容
     */
    private void out(HttpServletResponse response, String content){
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");
        try {
            response.getWriter().write(content);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理可见菜单<br />私有方法
     * @param proceedingJoinPoint
     * @param proccedResult
     * @throws Exception
     */
    private void handleVisibleMenus(ProceedingJoinPoint proceedingJoinPoint, Object proccedResult) throws Exception {
        //当前登录用户
        SysUserVo user = (SysUserVo) currentUser();
        if(user == null){
            return;
        }

        //获取访问目标方法
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();
        boolean isAjaxRequest = targetMethod.isAnnotationPresent(ResponseBody.class);
        if(!isAjaxRequest){
            ViewResult vr = null;
            Class<?> clazz = targetMethod.getReturnType();
            if(clazz.getName().equals("org.springframework.web.servlet.ModelAndView")){
                ModelAndView mav = ThreadContent.getData("mav");
                vr = (ViewResult) mav.getModelMap().get("result");
            }else{
                vr = ViewResult.newInstance()
                        .code(1).msg("获取用户可见菜单列表成功");
                ThreadContent.request().setAttribute("result", vr);
            }
            List<Menu> menuList = menuService.listVisibleMenu();
            vr.put("menus", menuList);
        }
    }

}

package com.benxiaopao.common.supers;

import com.benxiaopao.common.util.ThreadContent;

/**
 * 所有控制器的基类<br />多态扩展控制器公共功用
 *
 * Created by liupoyang
 * 2019-04-03
 */
public class BaseController {
    /**
     * 获取当前登录用户信息
     * @return
     */
    public Object currentUser() {
        return ThreadContent.request().getSession().getAttribute(BaseConstant.SESSION_USER_OBJ);
    }
}

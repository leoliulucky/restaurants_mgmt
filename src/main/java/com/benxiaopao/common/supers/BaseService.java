package com.benxiaopao.common.supers;

import com.benxiaopao.common.util.ThreadContent;

/**
 * 所有Service的基类<br />多态扩展业务逻辑服务类公共功用
 *
 * Created by liupoyang
 * 2019-04-03
 */
public class BaseService {
    /**
     * 获取当前登录用户信息
     * @return
     */
    public Object currentUser() {
        return ThreadContent.request().getSession().getAttribute(BaseConstant.SESSION_USER_OBJ);
    }

    /**
     * 重新载入用户信息到Session
     * @param user 用户对象
     */
    public void loadUser2Session(Object user) {
        ThreadContent.request().getSession().setAttribute(BaseConstant.SESSION_USER_OBJ, user);
    }
}

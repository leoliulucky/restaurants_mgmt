package com.benxiaopao.common.aspect;

import java.lang.annotation.*;

/**
 * 自定义注解：指定不需要认证权限的请求方法注解
 *
 * Created by liupoyang
 * 2019-04-06
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcludeAuthorize {
    String value() default "";
}

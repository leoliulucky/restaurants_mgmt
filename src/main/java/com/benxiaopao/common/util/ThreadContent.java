package com.benxiaopao.common.util;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 存放一些线程变量级的重要数据
 * 
 * @author liupoyang
 * @since 2019-04-04
 */
public class ThreadContent {
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(ThreadContent.class);

	/**
	 * 使用ThreadLocal保存HttpServletRequest变量
	 */
	private static final ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();
	/**
	 * 使用ThreadLocal保存HttpServletResponse变量
	 */
	private static final ThreadLocal<HttpServletResponse> response = new ThreadLocal<HttpServletResponse>();
	/**
	 * 使用ThreadLocal保存Pagination变量
	 */
	private static final ThreadLocal<Pagination> pagination = new ThreadLocal<Pagination>();
	/**
	 * 其他附加数据
	 */
	private static final ThreadLocal<Map<String, Object>> data = new ThreadLocal<Map<String, Object>>();
	
	/**
	 * 设置数据
	 * 
	 * @param servletRequest 请求对象
	 * @param servletResponse 响应对象
	 */
	public static void set(ServletRequest servletRequest, ServletResponse servletResponse) {
		if (servletRequest instanceof HttpServletRequest) {
			request.set((HttpServletRequest)servletRequest);
		}
		if (servletResponse instanceof HttpServletResponse) {
			response.set((HttpServletResponse)servletResponse);
		}
		pagination.set(null);
		data.set(new HashMap<String, Object>());
	}
	
	/**
	 * 得到servlet请求中的 request
	 * 
	 * @return HttpServletRequest
	 */
	public static HttpServletRequest request() {
		return request.get();
	}
	
	/**
	 * 得到servlet请求中的 response
	 * 
	 * @return HttpServletResponse
	 */
	public static HttpServletResponse response() {
		return response.get();
	}
	
	/**
	 * 设置分页数据
	 * 
	 * @param page 分页对象
	 */
	public static void pagination(Pagination page) {
		pagination.set(page);
	}
	
	/**
	 * 获取分页数据
	 * 
	 * @param Pagination
	 */
	public static Pagination pagination() {
		return pagination.get();
	}
	
	/**
	 * 从线程对象中获取数据
	 * 
	 * @param key
	 * @return
	 */
	public static <T> T getData(String key) {
		return (T) data.get().get(key);
	}
	
	/**
	 * 向线程对象中设置数据， 为保证安全，如果该对象本身有值时会抛出错误
	 * @param key 关键字
	 * @param obj 要设置的对象
	 */
	public static void addData(String key, Object obj) {
		Object oldValue = data.get().put(key, obj);
		if (oldValue != null){
			throw new IllegalArgumentException("数据已存在不能重复设置");
		}
	}

	/**
	 * 从线程中移除对象
	 * @param key 要移除的对象名
	 */
	public static <T> T removeData(String key) {
		return (T) data.get().remove(key);
	}
	
}

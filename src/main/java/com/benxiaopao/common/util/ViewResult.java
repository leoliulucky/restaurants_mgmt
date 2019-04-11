package com.benxiaopao.common.util;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.servlet.ModelAndView;
import com.google.gson.GsonBuilder;

/**
 * 控制层返回视图层结果抽象类
 * 
 * @author liupoyang
 * @since 2019-04-04
 */
public class ViewResult {
	/**
	 * 视图结果代码，小于0表示结果异常，大于或等于0表示结果正常
	 */
	private int code;
	/**
	 * 视图结果提示信息
	 */
	private String msg;
//	/**
//	 * 视图结果业务数据对象
//	 */
//	private T data;
	/**
	 * 视图结果扩展数据(键值存放)对象
	 */
	private Map<String, Object> data;
	/**
	 * 页面视图结果返回对象
	 */
	private ModelAndView mav;
	
	/**
	 * 自定义的json解析器
	 */
	private static GsonBuilder GSON_BUILDER = new GsonBuilder();
	
//	/**
//	 * 创建一个实例
//	 * 
//	 * @return ViewResult
//	 */
//	public static <T extends Object> ViewResult<T> newInstance(T data) {
//		ViewResult<T> instance = new ViewResult<T>(data);
//		//分页
//		if(ThreadContent.pagination() != null){
//			instance.put("pager", ThreadContent.pagination());
//		}
//		return instance;
//	}
	
	/**
	 * 创建一个实例
	 * 
	 * @return ViewResult
	 */
	public static ViewResult newInstance() {
		ViewResult instance = new ViewResult();
		//分页
		if(ThreadContent.pagination() != null){
			instance.put("pager", ThreadContent.pagination());
		}
		return instance;
	}
	
//	/**
//	 * 创建一个实例<br />请求带返回数据的页面时调用此方法
//	 * 
//	 * @param data 业务数据对象
//	 * @param pageName 返回页面的路径名 例如:user/addUser、index
//	 * @return ViewResult
//	 */
//	public static <T extends Object> ViewResult<T> newInstance(T data, String pageName) {
//		ViewResult<T> instance = new ViewResult<T>(data, pageName);
//		//分页
//		if(ThreadContent.pagination() != null){
//			instance.put("pager", ThreadContent.pagination());
//		}
//		return instance;
//	}
	
//	/**
//	 * 构造方法
//	 */
//	public ViewResult(T data){
//		this.data = data;
//	}
	
//	/**
//	 * 构造方法
//	 * @param data 业务数据对象
//	 * @param pageName 返回页面的路径名 例如:user/addUser、index
//	 */
//	public ViewResult(T data, String pageName){
//		this.data = data;
//		this.mav = new ModelAndView(pageName);
//	}
	
//	/**
//	 * 构造方法
//	 */
//	public ViewResult(int code, String msg){
//		this.code = code;
//		this.msg = msg;
//	}
//	
//	/**
//	 * 构造方法
//	 */
//	public ViewResult(int code, String msg, T data){
//		this.code = code;
//		this.msg = msg;
//		this.data = data;
//	}
	
	/**
	 * 视图结果代码Get方法
	 * @return
	 */
	public int getCode() {
		return code;
	}
	/**
	 * 视图结果提示信息Get方法
	 * @return
	 */
	public String getMsg() {
		return msg;
	}
//	/**
//	 * 视图结果业务数据对象Get方法
//	 * @return
//	 */
//	public T getData() {
//		return data;
//	}
	/**
	 * 视图结果扩展数据对象Get方法
	 * @return
	 */
	public Map<String, Object> getData() {
		return data;
	}
	
	/**
	 * 设置结果代码<br />约定：小于0 表示业务失败；大于0 表示业务正常
	 * @param code 结果代码
	 * @return
	 */
	public ViewResult code(int code) {
		this.code = code;
		return this;
	}
	
	/**
	 * 设置结果提示信息
	 * @param msg 结果代码
	 * @return
	 */
	public ViewResult msg(String msg) {
		this.msg = msg;
		return this;
	}
	
	/**
	 * 设置结果扩展数据
	 * @param msg 结果代码
	 * @return
	 */
	public ViewResult put(String key, Object value) {
		if (data == null) {
			data = new HashMap<String, Object>();
		}
		data.put(key, value);
		return this;
	}
	
	/**
	 * 输出成json格式
	 * @return
	 */
	public String json(){
		return GSON_BUILDER.create().toJson(this);
	}
	
	/**
	 * 输出成页面格式
	 * @return
	 */
	public ModelAndView view(String pageName){
		if(mav == null){
			mav = new ModelAndView(pageName);
		}
		this.mav.addObject("result", this);
		return mav;
	}
	
	/**
	 * 重定向输出
	 * @return
	 */
	public String redirect(String url){
		return "redirect:" + url;
	}
	
	/**
	 * 转发输出
	 * @return
	 */
	public String forward(String url){
		return "forward:" + url;
	}
	
//	/**
//	 * 统一错误页面输出
//	 * @return
//	 */
//	public ModelAndView error() {
//		//ThreadContent.request().setAttribute("result", this);
//		//this.forward("/views/error.jsp");
//		//ThreadContent.request().getRequestDispatcher("/views/error.jsp").forward(ThreadContent.request(), ThreadContent.response());
//		return this.view("error404");
//	}
	
}

package com.benxiaopao.common.exception;


/**
 * 业务异常类<br />涉及业务相关异常或业务错误提示可用此类抛出异常
 * 
 * @author liupoyang
 * @since 2019-04-04
 */
public class BizException extends RuntimeException {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -2604653882050707909L;

	/**
	 * 构造方法
	 */
	public BizException() {
	}
	
	/**
	 * 构造方法
	 * @param message
	 */
	public BizException(String message) {
		super(message);
	}
	
	/**
	 * 构造方法
	 * @param cause
	 */
	public BizException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * 构造方法
	 * @param message
	 * @param cause
	 */
	public BizException(String message, Throwable cause) {
		super(message, cause);
	}

}

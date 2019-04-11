package com.benxiaopao.provider.dao.enums;

/**
 * 是非枚举类
 * 
 * @author liupoyang
 * @since 2019-04-03
 */
public enum EitherEnum {
	/**
	 * 假状态
	 */
	NO((byte)0),
	/**
	 * 真状态
	 */
	YES((byte)1);
	
	/**
	 * 状态值
	 */
	byte value;
	
	/**
	 * 构造方法
	 * @param value
	 */
	private EitherEnum(byte value){
		this.value=value;
	}
	
	/**
	 * 获得枚举的值
	 * @return
	 */
	public byte getValue(){
		return this.value;
	}
}

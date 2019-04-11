package com.benxiaopao.common.supers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 常量基类<br />建议所有常量类继承该类
 * 
 * @author liupoyang
 * @since 2019-04-04
 */
public class BaseConstant {

	/**
	 * 用户登录成功后存放到session中user对象的key
	 */
	public static final String SESSION_USER_OBJ = "userObj";

	/**
	 * 默认列表每页显示数量
	 */
	public static final int DEFAULT_PAGE_SIZE = 10;
	
	/**
	 * 存放到session中验证码的key
	 */
	public static final String SESSION_CAPTCHA = "_captcha";
	
//	/**
//	 * 网站中文名
//	 */
//	public static final String WEBSITE_NAME_CN = "xx网";
//	/**
//	 * 网站英文名
//	 */
//	public static final String WEBSITE_NAME_EN = "baolly.com";
//	/**
//	 * 400电话
//	 */
//	public static final String SERVICE_PHONE = "400-890-3980";
	
//	/**
//	 * 城市容器
//	 */
//	public static Map<String,String> cityMap = null;
//
//	/**
//	 * 设置城市相关配置
//	 *
//	 * @param readCityConfig 城市配置读取对象
//	 */
//	public void setReadCityConfig(ReadCityConfig readCityConfig){
//		cityMap = readCityConfig.getCityMap();
//	}
//
//	/**
//	 * 拼音容器
//	 */
//	public static List<String[]> pinYinList = null;
//
//	/**
//	 * 设置拼音相关配置
//	 *
//	 * @param readPinYinConfig 拼音配置读取对象
//	 */
//	public void setReadPinYinConfig(ReadPinYinConfig readPinYinConfig){
//		pinYinList = readPinYinConfig.getPinYinList();
//	}
	
}

///**
// * 读取城市配置类
// * @author liupoyang
// * @since 2014-10-16
// */
//class ReadCityConfig{
//	/**
//	 * 日志对象
//	 */
//	private static Logger logger = LoggerFactory.getLogger(ReadCityConfig.class);
//	/**
//	 * 配置文件名称
//	 */
//	private String fileName;
//	/**
//	 * 城市容器
//	 */
//	private static Map<String,String> cityMap = new HashMap<String,String>();
//
//	/**
//	 * 设置配置文件名称
//	 * @param fileName 配置文件名称
//	 */
//	public void setFileName(String fileName) {
//		this.fileName = fileName;
//		logger.info("#设置配置文件名称："+fileName);
//	}
//
//	/**
//	 * 获得城市容器
//	 */
//	public Map<String,String> getCityMap() {
//		return cityMap;
//	}
//
//	/**
//	 * 初始化配置文件
//	 */
//	public void initConfig() {
//		BufferedReader br = null;
//		InputStream in = null;
//		try {
//			//in = ReadCityConfig.class.getResourceAsStream("WEB-INF" + File.separator + "resources"+ File.separator + fileName);
//			//br = new BufferedReader(new InputStreamReader(in));
//			String path= this.getClass().getResource("/").getPath() + fileName;
////			File city = new File(path);
////			br = new BufferedReader(new FileReader(city));
//			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
//			String keyword = null;
//			while ((keyword = br.readLine()) != null) {
//	            if (!keyword.trim().isEmpty()) {
//	            	String[] array = keyword.split("=");
//	            	cityMap.put(array[0], array[1]);
//	            }
//	        }
//			logger.info("#初始化配置文件成功：" + fileName);
//		} catch (Exception e) {
//			logger.error("#设置配置文件失败：" + fileName, e);
//		}
//		finally{
//			try {
//				if(in!=null) in.close();
//				if(br!=null) br.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//}
//
///**
// * 读取拼音配置类
// * @author liupoyang
// * @since 2014-10-16
// */
//class ReadPinYinConfig{
//	/**
//	 * 日志对象
//	 */
//	private static Logger logger = LoggerFactory.getLogger(ReadCityConfig.class);
//	/**
//	 * 配置文件名称
//	 */
//	private String fileName;
//	/**
//	 * 拼音容器
//	 */
//	private static List<String[]> pinYinList = new ArrayList<String[]>();
//
//	/**
//	 * 设置配置文件名称
//	 * @param fileName 配置文件名称
//	 */
//	public void setFileName(String fileName) {
//		this.fileName = fileName;
//		logger.info("#设置配置文件名称："+fileName);
//	}
//
//	/**
//	 * 获得拼音容器
//	 */
//	public List<String[]> getPinYinList() {
//		return pinYinList;
//	}
//
//	/**
//	 * 初始化配置文件
//	 */
//	public void initConfig() {
//		BufferedReader br = null;
//		InputStream in = null;
//		try {
//			//in = ReadCityConfig.class.getResourceAsStream("WEB-INF" + File.separator + "resources"+ File.separator + fileName);
//			//br = new BufferedReader(new InputStreamReader(in));
//			String path= this.getClass().getResource("/").getPath() + fileName;
////			File city = new File(path);
////			br = new BufferedReader(new FileReader(city));
//			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
//			String keyword = null;
//			while ((keyword = br.readLine()) != null) {
//	            if (!keyword.trim().isEmpty()) {
//	            	String[] array = keyword.split(",");
//	            	//pinYinList.add(new SimpleEntry<String, String>(array[0], array[1]));
//	            	pinYinList.add(array);
//	            }
//	        }
//			logger.info("#初始化配置文件成功：" + fileName);
//		} catch (Exception e) {
//			logger.error("#设置配置文件失败：" + fileName, e);
//		}
//		finally{
//			try {
//				if(in!=null) in.close();
//				if(br!=null) br.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//}

package com.reagan.core.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * <p>Description: 读取配置文件</p>
 * @date 2013年10月23日
 * @author reagan
 * @version 1.0
 * <p>Company:reagan</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class PropertiesUtil {
	
	/**
	 * 读取配置文件对象
	 */
	private static Properties proper; 
	
	/**
	 * 初始化类
	 * @param configName 配置文件地址路径与名称的组合
	 */
	public static void init(String configName) {
		try {
			proper = new Properties();
			String path = PropertiesUtil.class.getResource("/").getPath();                                  
			String websiteUrl = path.replace("classes","");
			FileInputStream fin = new FileInputStream(websiteUrl + "config/" + configName);
			proper.load(fin);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 方法用途: 获取配置信息<br>
	 * 实现步骤: <br>
	 * @param key 配置信息的键值
	 * @return
	 */
	public static String getProperty(String key) {
		return proper.getProperty(key);
	}

}

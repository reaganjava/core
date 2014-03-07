package com.reagan.core.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.reagan.core.data.beans.sys.Process;
import com.reagan.core.exception.ConfigException;
import com.reagan.util.LoggerUtil;

/**
 * <p>Description: </p>
 * @date 2013年11月18日
 * @author rr
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class SysXmlConfig extends DataConfig {
		
	public static final String PROCESSES = "processes";

	private static Map<String, Map<String, Process>> configInfo = null;
	
	private static LoggerUtil loggerUtil = new LoggerUtil(SysXmlConfig.class);
		
	public static Map<String, Map<String, Process>> getConfigInfo() {
		return configInfo;
	}

	public static void SysConfigInit() {
		configInfo = new HashMap<String, Map<String, Process>>();
		try {
			loggerUtil.debug("系统配置加载");
			configInfo.put(PROCESSES, DataConfig.processesFactory(getRootElement()));
		} catch (ConfigException e) {
			loggerUtil.error(e.getMessage());
		}
	}
	
	private static Document getDocument() {
		SAXReader saxReader = new SAXReader();
		Document doc = null;
		try {
			loggerUtil.debug("加载DATA.XML配置文档");
			PropertiesUtil.init("sysconfig.properties");
			doc = saxReader.read(new File(PropertiesUtil.getProperty("sys.data.path")));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return doc;
	}

	private static Element getRootElement() {
		loggerUtil.debug("返回根节点");
		return getDocument().getRootElement();
	}
}

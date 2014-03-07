package com.reagan.core.exception;

/**
 * <p>Description: </p>
 * @date 2013年11月19日
 * @author rr
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class ConfigException extends Exception {

	private static final long serialVersionUID = -3540302058970475292L;
	
	public ConfigException(String message) {
		super(message);
	}
	
	public ConfigException(Throwable cause) {
		super(cause);
	}
	
	public ConfigException(String message, Throwable cause) {
		super(message, cause);
	}

}

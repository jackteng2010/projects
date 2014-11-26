package com.core.exception;

public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String logMessage;//详细错误信息,用于后台日志

	public BaseException() {
		super();
	}

	/**
	 * @param message 错误信息，用于前端展示
	 * @param logMessage 详细错误信息，用于后端日志
	 * */
	public BaseException(String message, String logMessage) {
		super(message);
		this.logMessage = logMessage;
	}

	public String getLogMessage() {
		return logMessage;
	}

	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}
	
}

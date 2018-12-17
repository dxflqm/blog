package org.panzhi.blog.common.exception;

import org.panzhi.blog.common.error.ErrorMsg;

public class CommonException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private int errCode;
	
	private String errMsg;

	public int getErrCode() {
		return this.errCode;
	}

	public String getErrMsg() {
		return this.errMsg;
	}
	
	
	
	public CommonException(String errMsg) {
		super(errMsg);
		this.errMsg = errMsg;
	}
	
	public CommonException(int errCode, String errMsg) {
		super(errCode + ":" + errMsg);
		this.errCode = errCode;
		this.errMsg = errMsg;
	}
	
	public CommonException(int errCode, String errMsg, Throwable cause) {
		super(errCode + ":" + errMsg, cause);
		this.errCode = errCode;
		this.errMsg = errMsg;
	}
	
	public CommonException(ErrorMsg errorMsg) {
		super(errorMsg.getCode() + ":" + errorMsg.getMessage());
		this.errCode = errorMsg.getCode();
		this.errMsg = errorMsg.getMessage();
	}
	
	public CommonException(ErrorMsg errorMsg, Throwable cause) {
		super(errorMsg.getCode() + ":" + errorMsg.getMessage(), cause);
		this.errCode = errorMsg.getCode();
		this.errMsg = errorMsg.getMessage();
	}

}

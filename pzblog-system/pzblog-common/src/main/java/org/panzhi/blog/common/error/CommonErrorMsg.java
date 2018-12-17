package org.panzhi.blog.common.error;

public enum CommonErrorMsg implements ErrorMsg{
	/**9999,标识系统运行异常*/
	SYSTEM_ERROR(9999,"系统运行异常"),
	/**9998,数据库操作异常*/
	OPERATE_DB_ERROR(9998,"操作失败"),
	
	SUCCESS_CODE(200,"成功"),
	REQUEST_ERROR(202,"请求异常,请稍后再试"),
	ILLEGE_ERROR(204,"非法请求"),
	SRC_ERROR(404,"地址不存在"),
	CUSTOM_ERROR(202,"请求异常,请稍后再试")
	;
	/**标识*/
	private int code;
	/**信息*/
	private String message;
	
	
	public void setCode(int code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int getCode() {
		// TODO Auto-generated method stub
		return this.code;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return this.message;
	}
	
	private CommonErrorMsg(int code, String message) {
		this.code = code;
		this.message = message;
	}

}

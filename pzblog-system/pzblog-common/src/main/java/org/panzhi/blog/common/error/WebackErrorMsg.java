package org.panzhi.blog.common.error;

public enum WebackErrorMsg implements ErrorMsg {
	 ACCOUNT_ERROR(204,"用户名或密码错误，请确认之后在登录！！！"),
	 IMAGECODE_ERROR(204,"验证码输入错误，请确认在提交！！！")
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

	private WebackErrorMsg(int code, String message) {
		this.code = code;
		this.message = message;
	}

}

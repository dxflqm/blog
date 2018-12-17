package org.panzhi.blog.dao.entity;

import org.panzhi.blog.common.util.IdGen;

public class Log extends BaseDao{
	
	private String logId;
	
	private String visitIp;
	
	private String ipAddress;
	
	private String path;
	
	private String device;
	
	private String browser;
	
	private String userAgent;

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getVisitIp() {
		return visitIp;
	}

	public void setVisitIp(String visitIp) {
		this.visitIp = visitIp;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public Log() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Log(String visitIp,String userAgent) {
		super();
		this.logId = IdGen.uuid();
		this.visitIp = visitIp;
		this.userAgent = userAgent;
	}
}

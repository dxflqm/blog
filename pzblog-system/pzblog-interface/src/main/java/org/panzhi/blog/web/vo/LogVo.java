package org.panzhi.blog.web.vo;

import org.panzhi.blog.common.util.DateFormatUtil;
import org.panzhi.blog.dao.entity.Log;

public class LogVo {
	
	private String visitIp;
	
	private String address;
	
	private String device;
	
	private String browser;
	
	private String visitTime;
	
	private String userAgent;

	public String getVisitIp() {
		return visitIp;
	}

	public void setVisitIp(String visitIp) {
		this.visitIp = visitIp;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
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

	public LogVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LogVo(Log log) {
		super();
		this.visitIp = log.getVisitIp();
		this.address = log.getIpAddress();
		this.device = log.getDevice();
		this.browser = log.getBrowser();
		this.userAgent = log.getUserAgent();
		this.visitTime = DateFormatUtil.dateFormatStr(log.getCreateTime(),DateFormatUtil.DATE_FORMAT);
	}
	
}

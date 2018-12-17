package org.panzhi.blog.service.async;

import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.common.servlet.util.IpAddressUtil;
import org.panzhi.blog.dao.impl.LogDaoImpl;
import org.panzhi.blog.web.dto.LogDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;
import nl.bitwalker.useragentutils.Version;

public class AsyncUserVisitService implements Runnable{
	
	private static final Logger logger = LoggerFactory.getLogger(AsyncUserVisitService.class);
	
	private LogDto logDto;
	
	@Override
	public void run() {
		try {
			if(this.logDto != null && this.logDto.getVisitIp() != null) {
				String address = IpAddressUtil.getLoginAddress(logDto.getVisitIp());
				this.logDto.setIpAddress(address);
				if(this.logDto.getUserAgent() != null) {
					UserAgent userAgent = UserAgent.parseUserAgentString(this.logDto.getUserAgent());
					if(userAgent != null) {
						//获取浏览器信息
						Browser browser = userAgent.getBrowser();
						if(browser != null) {
							Version version = browser.getVersion(this.logDto.getUserAgent());
							String browserVersion = browser.getName() + (version != null ? ("/" + version.getVersion()):"");
							this.logDto.setBrowser(browserVersion);
						}
						//获取系统信息
						OperatingSystem os = userAgent.getOperatingSystem();
						if(os != null) {
							this.logDto.setDevice(os.getName());
						}
					}
				}
				new LogDaoImpl().insert(logDto);
			}
		} catch (CommonException e) {
			logger.error("通过ip获取所在地址失败!",e);
		}
		
	}

	public AsyncUserVisitService() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AsyncUserVisitService(LogDto logDto) {
		super();
		this.logDto = logDto;
	}

}

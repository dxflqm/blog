package org.panzhi.blog.web.dto;

import org.panzhi.blog.dao.entity.Log;

public class LogDto extends Log{
	
	public LogDto(String visitIp,String userAgent) {
		super(visitIp,userAgent);
	}
	
}

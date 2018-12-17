package org.panzhi.blog.weback.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.panzhi.blog.common.annotation.Autowrited;
import org.panzhi.blog.common.annotation.Controller;
import org.panzhi.blog.common.annotation.RequestMapping;
import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.common.result.BuildResponseMsg;
import org.panzhi.blog.service.LogService;
import org.panzhi.blog.web.vo.LogVo;
import org.panzhi.blog.web.vo.PageVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/logMgr")
public class LogController {
	
	private static final Logger logger = LoggerFactory.getLogger(LogController.class);
	
	@Autowrited
	private LogService logService;
	
	@RequestMapping("/queryAll")
	public String queryAll(HttpServletRequest request, HttpServletResponse response){
		try {
			PageVo<LogVo> data = logService.findAllList(new PageVo<LogVo>(request,response));
			return BuildResponseMsg.buildCustomeMsg(data);
		} catch (CommonException e) {
			logger.error("日志控制层:查询所有日志信息异常,请求接口：queryAll",e);
			return BuildResponseMsg.buildCommonFailMsg(e.getErrMsg());
		}
	}

}

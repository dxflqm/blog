package org.panzhi.blog.service;

import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.dao.entity.Log;
import org.panzhi.blog.web.vo.LogVo;
import org.panzhi.blog.web.vo.PageVo;

public interface LogService {
	
	PageVo<LogVo> findAllList(PageVo<LogVo> page) throws CommonException;
	
	boolean insert(Log log) throws CommonException;
}

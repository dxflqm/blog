package org.panzhi.blog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.panzhi.blog.common.annotation.Autowrited;
import org.panzhi.blog.common.annotation.Service;
import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.dao.LogDao;
import org.panzhi.blog.dao.entity.Log;
import org.panzhi.blog.service.LogService;
import org.panzhi.blog.web.vo.LogVo;
import org.panzhi.blog.web.vo.PageVo;

@Service
public class LogServiceImpl implements LogService{
	
	@Autowrited
	private LogDao logDao;

	@Override
	public PageVo<LogVo> findAllList(PageVo<LogVo> page) throws CommonException {
		List<LogVo> data = new ArrayList<LogVo>();
		int count = logDao.count(page);
		page.setCount(count);
		List<Log> srcList = logDao.findList(page);
		for (Log src : srcList) {
			data.add(new LogVo(src));
		}
		page.setData(data);
		return page;
	}

	@Override
	public boolean insert(Log log) throws CommonException {
		return logDao.insert(log) > 0;
	}

}

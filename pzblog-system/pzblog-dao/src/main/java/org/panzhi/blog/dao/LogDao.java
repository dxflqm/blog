package org.panzhi.blog.dao;

import java.util.List;

import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.dao.entity.Log;
import org.panzhi.blog.dao.entity.Page;

public interface LogDao {
	
	List<Log> findList(Page page) throws CommonException;
	
	int count(Page page) throws CommonException;
	
	int insert(Log log) throws CommonException;
}

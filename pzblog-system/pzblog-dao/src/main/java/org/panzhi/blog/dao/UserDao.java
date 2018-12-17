package org.panzhi.blog.dao;

import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.dao.entity.User;

public interface UserDao {
	
	int insert(User user) throws CommonException;
	
}

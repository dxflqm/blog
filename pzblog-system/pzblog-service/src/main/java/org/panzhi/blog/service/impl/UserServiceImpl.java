package org.panzhi.blog.service.impl;

import org.panzhi.blog.common.annotation.Autowrited;
import org.panzhi.blog.common.annotation.Service;
import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.dao.UserDao;
import org.panzhi.blog.dao.entity.User;
import org.panzhi.blog.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowrited
	private UserDao userDao;

	@Override
	public boolean insert(User user) throws CommonException {
		return userDao.insert(user) > 0;
	}

}

package org.panzhi.blog.service;

import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.dao.entity.User;

public interface UserService {

	boolean insert(User user) throws CommonException;
}

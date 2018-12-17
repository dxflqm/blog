package org.panzhi.blog.service.impl;

import java.util.List;

import org.panzhi.blog.common.annotation.Autowrited;
import org.panzhi.blog.common.annotation.Service;
import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.dao.CommentDao;
import org.panzhi.blog.dao.entity.Comment;
import org.panzhi.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowrited
	private CommentDao commentDao;

	@Override
	public List<Comment> findList(String articleId) throws CommonException {
		return commentDao.findList(articleId);
	}

	@Override
	public boolean insert(Comment comment) throws CommonException {
		return commentDao.insert(comment) > 0;
	}
}

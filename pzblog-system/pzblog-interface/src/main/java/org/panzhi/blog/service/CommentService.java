package org.panzhi.blog.service;

import java.util.List;

import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.dao.entity.Comment;

public interface CommentService {
	
	List<Comment> findList(String articleId) throws CommonException;
	
	boolean insert(Comment comment) throws CommonException;
}

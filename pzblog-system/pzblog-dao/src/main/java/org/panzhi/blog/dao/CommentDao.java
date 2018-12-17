package org.panzhi.blog.dao;

import java.util.List;

import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.dao.entity.Comment;

public interface CommentDao {
	
	List<Comment> findList(String articleId) throws CommonException;
	
	int insert(Comment comment) throws CommonException;

}

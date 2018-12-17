package org.panzhi.blog.service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.web.dto.ArticleDto;
import org.panzhi.blog.web.vo.ArticleVo;
import org.panzhi.blog.web.vo.PageVo;

public interface ArticleService {
	
	PageVo<ArticleVo> findAllList(PageVo<ArticleVo> page) throws CommonException, UnsupportedEncodingException;
	
	PageVo<ArticleVo> findList(PageVo<ArticleVo> page) throws CommonException, UnsupportedEncodingException;
	
	List<ArticleVo> findListByCategoryId(String categoryId) throws CommonException, UnsupportedEncodingException;
	
	ArticleVo findArticleById(String articleId) throws CommonException, UnsupportedEncodingException;
	
	boolean addArticle(ArticleDto articleDto) throws CommonException, SQLException;
	
	boolean updateBaseArticle(ArticleDto articleDto) throws CommonException;
	
	boolean updateArticleContent(ArticleDto articleDto) throws CommonException;
	
	boolean startArticle(String articleId) throws CommonException, SQLException;
	
	boolean stopArticel(String articleId) throws CommonException, SQLException;
	
	boolean delete(String articleId) throws CommonException;
	
}

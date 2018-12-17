package org.panzhi.blog.dao;

import java.util.List;

import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.dao.entity.ArticleVisit;

public interface ArticleVisitDao {
	
	/**
	 * 文章访问量
	 * @param articleId
	 * @return
	 * @throws CommonException
	 */
	ArticleVisit findByArticleId(String articleId) throws CommonException;
	
	/**
	 * 批量查询文章访问量
	 * @param list
	 * @return
	 * @throws CommonException
	 */
	List<ArticleVisit> findVisitByIds(List<String> list) throws CommonException;
	
	/**
	 * 新增访问量
	 * @param articleId
	 * @return
	 * @throws CommonException
	 */
	int insert(String articleId) throws CommonException;
	
	/**
	 * 修改访问量
	 * @param articleId
	 * @return
	 * @throws CommonException
	 */
	int update(String articleId) throws CommonException;

}

package org.panzhi.blog.dao;

import java.util.List;

import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.dao.entity.Article;
import org.panzhi.blog.dao.entity.Page;

public interface ArticleDao {
	
	/**
	 * 查询所有文章
	 * @param page
	 * @return
	 * @throws CommonException
	 */
	List<Article> findAllList(Page page) throws CommonException;
	
	/**
	 * 查询已发布文章
	 * @param page
	 * @return
	 * @throws CommonException
	 */
	List<Article> findList(Page page) throws CommonException;
	
	/**
	 * 查询总行数
	 */
	int count(Integer status,Page page) throws CommonException;
	
	/**
	 * 按照类别查询所有文章
	 * @param categoryId
	 * @return
	 * @throws CommonException
	 */
	List<Article> findListByCategoryId(String categoryId) throws CommonException;
	
	/**
	 * 通过文章ID查询文章信息
	 * @param articleId
	 * @return
	 * @throws CommonException
	 */
	Article findArticleById(String articleId) throws CommonException;
	
	/**
	 * 新增文章数据
	 * @param article
	 * @return
	 * @throws CommonException
	 */
	int insert(Article article) throws CommonException;
	
	/**
	 * 修改文章基础数据
	 * @param article
	 * @return
	 * @throws CommonException
	 */
	int updateInfo(Article article) throws CommonException;
	
	/**
	 * 修改文章内容数据
	 * @param article
	 * @return
	 * @throws CommonException
	 */
	int updateArticle(Article article) throws CommonException;
	
	/**
	 * 发布文章
	 * @param articleId
	 * @return
	 * @throws CommonException
	 */
	int startArticle(String articleId) throws CommonException;
	
	/**
	 * 停用文章
	 * @param articleId
	 * @return
	 * @throws CommonException
	 */
	int stopArticel(String articleId) throws CommonException;
	
	/**
	 * 删除文章
	 * @param articleId
	 * @return
	 * @throws CommonException
	 */
	int delete(String articleId) throws CommonException;
	
	/**
	 * 通过类别ID下线文章
	 * @param categoryId
	 * @return
	 * @throws CommonException
	 */
	int deleteByCategoryId(String categoryId) throws CommonException;
	
	/**
	 * 通过标签ID下线文章
	 * @param tagId
	 * @return
	 * @throws CommonException
	 */
	int deleteByTagId(String tagId) throws CommonException;

}

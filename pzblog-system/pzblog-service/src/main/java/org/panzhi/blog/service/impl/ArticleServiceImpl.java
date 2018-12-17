package org.panzhi.blog.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.panzhi.blog.common.annotation.Autowrited;
import org.panzhi.blog.common.annotation.Service;
import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.common.util.Assert;
import org.panzhi.blog.dao.ArticleDao;
import org.panzhi.blog.dao.ArticleVisitDao;
import org.panzhi.blog.dao.CategoryDao;
import org.panzhi.blog.dao.TagDao;
import org.panzhi.blog.dao.entity.Article;
import org.panzhi.blog.dao.entity.ArticleVisit;
import org.panzhi.blog.dao.entity.Category;
import org.panzhi.blog.dao.entity.Tag;
import org.panzhi.blog.dao.util.jdbc.JdbcUtils;
import org.panzhi.blog.service.ArticleService;
import org.panzhi.blog.web.dto.ArticleDto;
import org.panzhi.blog.web.vo.ArticleVo;
import org.panzhi.blog.web.vo.PageVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ArticleServiceImpl implements ArticleService {
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
	
	@Autowrited
	private ArticleDao articleDao;
	
	@Autowrited
	private ArticleVisitDao articleVisitDao;
	
	@Autowrited
	private CategoryDao categoryDao;
	
	@Autowrited
	private TagDao tagDao;
	
	@Override
	public PageVo<ArticleVo> findAllList(PageVo<ArticleVo> page) throws CommonException, UnsupportedEncodingException {
		int count = articleDao.count(null,page);
		page.setCount(count);
		List<Article> list = articleDao.findAllList(page);
		List<ArticleVo> data = warpArticleToView(list);
		page.setData(data);
		return page;
	}

	@Override
	public PageVo<ArticleVo> findList(PageVo<ArticleVo> page) throws CommonException, UnsupportedEncodingException {
		int count = articleDao.count(1,page);
		page.setCount(count);
		List<Article> list = articleDao.findList(page);
		List<ArticleVo> data = warpArticleToView(list);
		page.setData(data);
		return page;
	}

	@Override
	public List<ArticleVo> findListByCategoryId(String categoryId) throws CommonException, UnsupportedEncodingException {
		List<Article> list = articleDao.findListByCategoryId(categoryId);
		List<ArticleVo> data = warpArticleToView(list);
		return data;
	}

	@Override
	public ArticleVo findArticleById(String articleId) throws CommonException, UnsupportedEncodingException {
		ArticleVo targetObject = null;
		Article result = articleDao.findArticleById(articleId);
		if(result != null) {
			targetObject = new ArticleVo(result);
			if(StringUtils.isNotEmpty(targetObject.getArticleContent())) {
				targetObject.setArticleContent(URLDecoder.decode(targetObject.getArticleContent(), "UTF-8"));
			}
			ArticleVisit articleVisit = articleVisitDao.findByArticleId(result.getArticleId());
			if(articleVisit != null) {
				targetObject.setVisitCount(articleVisit.getVisitCount());
			}
			Category category = categoryDao.findById(result.getCategoryId());
			if(category != null) {
				targetObject.setCategoryName(category.getCategoryName());
			}
			Tag tag = tagDao.findById(result.getTagId());
			if(tag != null) {
				targetObject.setTagName(tag.getTagName());
			}
		}
		return targetObject;
	}

	@Override
	public boolean addArticle(ArticleDto articleDto) throws CommonException, SQLException {
		try {
			JdbcUtils.beginTransaction();
			articleDao.insert(articleDto);
			articleVisitDao.insert(articleDto.getArticleId());
			JdbcUtils.commitTransaction();
			return true;
		} catch (Exception e) {
			logger.error("新增文章失败",e);
			JdbcUtils.rollbackTransaction();
			throw new CommonException("新增文章失败");
		}
	}
	
	@Override
	public boolean updateBaseArticle(ArticleDto articleDto) throws CommonException {
		//判断是否下线，只有下线才能编辑文章基本信息
		Article article = articleDao.findArticleById(articleDto.getArticleId());
		Assert.state(article.getStatus() == 1, "文章已发布，请下线再修改信息！！！");
		return articleDao.updateInfo(articleDto) > 0;
	}

	@Override
	public boolean updateArticleContent(ArticleDto articleDto) throws CommonException {
		//判断是否下线，只有下线才能编辑文章内容
		Article article = articleDao.findArticleById(articleDto.getArticleId());
		Assert.state(article.getStatus() == 1, "文章已发布，请下线再修改文章！！！");
		return articleDao.updateArticle(articleDto) > 0;
	}

	@Override
	public boolean startArticle(String articleId) throws CommonException, SQLException {
		try {
			JdbcUtils.beginTransaction();
			Article article = articleDao.findArticleById(articleId);
			Assert.isEmpty(article.getArticleContent(), "文章内容为空，请编辑内容再发布！！！");
			Category category = categoryDao.findById(article.getCategoryId());
			Assert.isNull(category, "文章类别为空，请编辑目录再发布！！！");
			Tag tag = tagDao.findById(article.getTagId());
			Assert.isNull(tag, "文章标签为空，请编辑目录再发布！！！");
			articleDao.startArticle(articleId);
			categoryDao.updateAddCount(article.getCategoryId());
			JdbcUtils.commitTransaction();
			return true;
		} catch (CommonException e) {
			logger.error("启用文章失败",e);
			JdbcUtils.rollbackTransaction();
			throw e;
		} catch (Exception e) {
			logger.error("启用文章失败",e);
			JdbcUtils.rollbackTransaction();
			throw new CommonException("新增文章失败");
		}
	}

	@Override
	public boolean stopArticel(String articleId) throws CommonException, SQLException {
		try {
			JdbcUtils.beginTransaction();
			Article article = articleDao.findArticleById(articleId);
			articleDao.stopArticel(articleId);
			categoryDao.updateDeleteCount(article.getCategoryId());
			JdbcUtils.commitTransaction();
			return true;
		} catch (Exception e) {
			logger.error("停用文章失败",e);
			JdbcUtils.rollbackTransaction();
			throw new CommonException("新增文章失败");
		}
	}
	
	private List<ArticleVo> warpArticleToView(List<Article> list) throws CommonException, UnsupportedEncodingException{
		List<ArticleVo> data = new ArrayList<ArticleVo>();
		List<String> articleIds = new ArrayList<String>();
		List<String> categoryIds = new ArrayList<String>();
		List<String> tagIds = new ArrayList<String>();
		for (Article srcObject : list) {
			ArticleVo targetObject = new ArticleVo(srcObject);
			articleIds.add(srcObject.getArticleId());
			categoryIds.add(srcObject.getCategoryId());
			tagIds.add(srcObject.getTagId());
			data.add(targetObject);
		}
		//查询访问量
		if(!articleIds.isEmpty()) {
			List<ArticleVisit> visitList = articleVisitDao.findVisitByIds(articleIds);
			for (ArticleVo articleVo : data) {
				for (ArticleVisit articleVisit : visitList) {
					if(articleVo.getArticleId().equals(articleVisit.getArticleId())) {
						articleVo.setVisitCount(articleVisit.getVisitCount());
						break;
					}
				}
			}
		}
		//查询类别
		if(!categoryIds.isEmpty()) {
			List<Category>  categoryList = categoryDao.findCategoryByIds(categoryIds);
			for (ArticleVo articleVo : data) {
				for (Category category : categoryList) {
					if(articleVo.getCategoryId().equals(category.getCategoryId())) {
						articleVo.setCategoryName(category.getCategoryName());
						break;
					}
				}
			}
		}
		//查询标签
		if(!tagIds.isEmpty()) {
			List<Tag> tagList = tagDao.findTagByIds(tagIds);
			for (ArticleVo articleVo : data) {
				for (Tag tag : tagList) {
					if(articleVo.getTagId().equals(tag.getTagId())) {
						articleVo.setTagName(tag.getTagName());
						break;
					}
				}
			}
		}
		return data;
	}

	@Override
	public boolean delete(String articleId) throws CommonException {
		Article article = articleDao.findArticleById(articleId);
		Assert.state(article.getStatus() == 1, "文章已发布，请下线再删除文章！！！");
		return articleDao.delete(articleId) > 0;
	}
	
}

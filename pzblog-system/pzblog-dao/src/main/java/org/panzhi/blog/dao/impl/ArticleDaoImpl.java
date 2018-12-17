package org.panzhi.blog.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.panzhi.blog.common.annotation.Service;
import org.panzhi.blog.common.error.CommonErrorMsg;
import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.dao.ArticleDao;
import org.panzhi.blog.dao.entity.Article;
import org.panzhi.blog.dao.entity.Page;
import org.panzhi.blog.dao.util.jdbc.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

@Service
public class ArticleDaoImpl implements ArticleDao {
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleDaoImpl.class);
	
	@Override
	public List<Article> findAllList(Page page) throws CommonException {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String param = page.getInput() == null ? "":page.getInput();
			String sql = "select article_id AS articleId,article_title AS articleTitle,article_type AS articleType,reprint_href AS reprintHref,article_introduce AS articleIntroduce,category_id AS categoryId,tag_id AS tagId,status AS status,create_time AS createTime,update_time AS updateTime from tb_article where delete_flag = '0' and article_title like '" + param + "%' ORDER BY update_time DESC,status  LIMIT ?,?";
			List<Article> articleList = runner.query(sql, new BeanListHandler<Article>(Article.class), page.getCurrentRow(), page.getPageSize());
			return articleList;
		} catch (Exception e) {
			logger.error("分页查询所有文章信息数据失败!", e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}

	@Override
	public List<Article> findList(Page page) throws CommonException {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select article_id AS articleId,article_title AS articleTitle,article_type AS articleType,reprint_href AS reprintHref,article_introduce AS articleIntroduce,category_id AS categoryId,tag_id AS tagId, status AS status,create_time AS createTime,update_time AS updateTime from tb_article where delete_flag = '0' and status = '1' ORDER BY status,update_time DESC LIMIT ?,?";
			List<Article> articleList = runner.query(sql, new BeanListHandler<Article>(Article.class), page.getCurrentRow(), page.getPageSize());
			return articleList;
		} catch (Exception e) {
			logger.error("分页查询所有文章信息数据失败!", e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}
	
	@Override
	public int count(Integer status,Page page) throws CommonException {
		try {
			QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
			String param = page.getInput() == null ? "":page.getInput();
			String sql = "SELECT count(*) FROM tb_article WHERE delete_flag = '0' and article_title like '" + param + "%'" + (status == null ? "" : " and status  = '" + status + "'");
			@SuppressWarnings({ "unchecked", "rawtypes" })
			Object obj = queryRunner.query(sql, new ScalarHandler(1));
			return ((Long) obj).intValue();
		}  catch (Exception e) {
			logger.error("查询文章信息总行数失败!", e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}

	@Override
	public List<Article> findListByCategoryId(String categoryId) throws CommonException {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select article_id AS articleId,article_title AS articleTitle,article_type AS articleType,reprint_href AS reprintHref,article_introduce AS articleIntroduce,category_id AS categoryId,tag_id AS tagId,status AS status,create_time AS createTime,update_time AS updateTime from tb_article where delete_flag = '0' and status = '1' and category_id = ? order by update_time DESC";
			List<Article> articleList = runner.query(sql, new BeanListHandler<Article>(Article.class), categoryId);
			return articleList;
		} catch (Exception e) {
			logger.error("通过类别ID分页查询所有文章信息数据失败!类别ID："+categoryId, e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}

	

	@Override
	public Article findArticleById(String articleId) throws CommonException {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select article_id AS articleId,article_title AS articleTitle,article_type AS articleType,reprint_href AS reprintHref,article_introduce AS articleIntroduce,article_content AS articleContent,category_id AS categoryId,tag_id AS tagId,status AS status,create_time AS createTime,update_time AS updateTime from tb_article where article_id = ?";
			Article article = runner.query(sql, new BeanHandler<Article>(Article.class), articleId);
			return article;
		} catch (Exception e) {
			logger.error("通过ID查询文章信息数据失败!文章ID：" + articleId, e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}

	@Override
	public int insert(Article at) throws CommonException {
		try {
			QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "INSERT INTO `tb_article`(`article_id`, `article_title`, `article_type`, `reprint_href`, `article_introduce`, `article_content`, `category_id`, `tag_id`, `status`, `create_time`, `update_time`, `delete_flag`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 0, ?, ?, 0)";
			int result = queryRunner.update(sql, at.getArticleId(),at.getArticleTitle(),at.getArticleType(),at.getReprintHref(),at.getArticleIntroduce(),at.getArticleContent(),at.getCategoryId(),at.getTagId(),new Timestamp(new Date().getTime()),new Timestamp(new Date().getTime()));
			if(result <= 0){
				logger.warn("新增文章数据到数据库失败!数据：" + JSON.toJSONString(at));
			}
			return result;
		} catch (Exception e) {
			logger.error("新增文章数据到数据库失败!",e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}

	@Override
	public int updateInfo(Article at) throws CommonException {
		try {
			QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "UPDATE `tb_article` SET `article_title` = ?, `article_type` = ?, `reprint_href` = ?, `article_introduce` = ?, `category_id` = ?, `tag_id` = ?, `update_time` = ? WHERE `article_id` = ?";
			int result = queryRunner.update(sql,at.getArticleTitle(),at.getArticleType(),at.getReprintHref(),at.getArticleIntroduce(),at.getCategoryId(),at.getTagId(),new Timestamp(new Date().getTime()),at.getArticleId());
			if(result <= 0){
				logger.warn("修改文章数据到数据库失败!数据：" + JSON.toJSONString(at));
			}
			return result;
		} catch (Exception e) {
			logger.error("修改文章数据到数据库失败!",e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}
	
	@Override
	public int updateArticle(Article at) throws CommonException {
		try {
			QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "UPDATE `tb_article` SET `article_content` = ?, `update_time` = ? WHERE `article_id` = ?";
			int result = queryRunner.update(sql,at.getArticleContent(),new Timestamp(new Date().getTime()),at.getArticleId());
			if(result <= 0){
				logger.warn("修改文章数据到数据库失败!数据：" + JSON.toJSONString(at));
			}
			return result;
		} catch (Exception e) {
			logger.error("修改文章数据到数据库失败!",e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}

	@Override
	public int startArticle(String articleId) throws CommonException {
		try {
			QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "UPDATE `tb_article` SET `status` = '1' WHERE `article_id` = ?";
			int result = queryRunner.update(sql,articleId);
			if(result <= 0){
				logger.warn("发布文章数据到数据库失败!文章ID：" + articleId);
			}
			return result;
		} catch (Exception e) {
			logger.error("修改文章数据到数据库失败!",e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}

	@Override
	public int stopArticel(String articleId) throws CommonException {
		try {
			QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "UPDATE `tb_article` SET `status` = '2' WHERE `article_id` = ?";
			int result = queryRunner.update(sql,articleId);
			if(result <= 0){
				logger.warn("停用文章数据到数据库失败!文章ID：" + articleId);
			}
			return result;
		} catch (Exception e) {
			logger.error("修改文章数据到数据库失败!",e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}

	@Override
	public int delete(String articleId) throws CommonException {
		try {
			QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "UPDATE `tb_article` SET `delete_flag` = '1' WHERE `article_id` = ?";
			int result = queryRunner.update(sql,articleId);
			if(result <= 0){
				logger.warn("停用文章数据到数据库失败!文章ID：" + articleId);
			}
			return result;
		} catch (Exception e) {
			logger.error("修改文章数据到数据库失败!",e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}

	@Override
	public int deleteByCategoryId(String categoryId) throws CommonException {
		try {
			QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "UPDATE `tb_article` SET `status` = '2' WHERE `category_id` = ?";
			int result = queryRunner.update(sql,categoryId);
			if(result <= 0){
				logger.warn("停用文章数据到数据库失败!类别ID：" + categoryId);
			}
			return result;
		} catch (Exception e) {
			logger.error("修改文章数据到数据库失败!",e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}

	@Override
	public int deleteByTagId(String tagId) throws CommonException {
		try {
			QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "UPDATE `tb_article` SET `status` = '2' WHERE `tag_id` = ?";
			int result = queryRunner.update(sql,tagId);
			if(result <= 0){
				logger.warn("停用文章数据到数据库失败!标签ID：" + tagId);
			}
			return result;
		} catch (Exception e) {
			logger.error("修改文章数据到数据库失败!",e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}

}

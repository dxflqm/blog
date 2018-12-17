package org.panzhi.blog.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.panzhi.blog.common.annotation.Service;
import org.panzhi.blog.common.error.CommonErrorMsg;
import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.common.util.SQLValiateUtil;
import org.panzhi.blog.dao.ArticleVisitDao;
import org.panzhi.blog.dao.entity.ArticleVisit;
import org.panzhi.blog.dao.util.jdbc.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ArticleVisitDaoImpl implements ArticleVisitDao {
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleVisitDaoImpl.class);

	@Override
	public ArticleVisit findByArticleId(String articleId) throws CommonException {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select article_id AS articleId,visit_count AS visitCount from tb_article_visit where article_id = ?";
			ArticleVisit articleVisit = runner.query(sql, new BeanHandler<ArticleVisit>(ArticleVisit.class), articleId);
			return articleVisit;
		} catch (Exception e) {
			logger.error("查询文章访问信息数据失败!", e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}
	
	@Override
	public List<ArticleVisit> findVisitByIds(List<String> list) throws CommonException {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String ids = SQLValiateUtil.listConvertString(list);
			String sql = "select article_id AS articleId,visit_count AS visitCount from tb_article_visit where article_id in ("+ids+")";
			List<ArticleVisit> articleVisitList = runner.query(sql, new BeanListHandler<ArticleVisit>(ArticleVisit.class));
			return articleVisitList;
		} catch (Exception e) {
			logger.error("查询文章访问信息数据失败!", e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}

	@Override
	public int insert(String articleId) throws CommonException {
		try {
			QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "INSERT INTO `tb_article_visit`(`article_id`, `visit_count`) VALUES (?, 0)";
			int result = queryRunner.update(sql, articleId);
			if(result <= 0){
				logger.warn("新增文章访问数据到数据库失败!文章ID：" + articleId);
			}
			return result;
		} catch (Exception e) {
			logger.error("新增文章访问数据到数据库失败!",e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}

	@Override
	public int update(String articleId) throws CommonException {
		try {
			QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "UPDATE `tb_article_visit` SET `visit_count` = `visit_count` + 1 WHERE `article_id` = ?";
			int result = queryRunner.update(sql,articleId);
			if(result <= 0){
				logger.warn("修改文章访问数据到数据库失败!文章ID：" + articleId);
			}
			return result;
		} catch (Exception e) {
			logger.error("修改文章访问数据到数据库失败!",e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}

	

}

package org.panzhi.blog.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.panzhi.blog.common.annotation.Service;
import org.panzhi.blog.common.error.CommonErrorMsg;
import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.dao.CommentDao;
import org.panzhi.blog.dao.entity.Comment;
import org.panzhi.blog.dao.util.jdbc.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

@Service
public class CommentDaoImpl implements CommentDao{
	
	private static final Logger logger = LoggerFactory.getLogger(CategoryDaoImpl.class);

	@Override
	public List<Comment> findList(String articleId) throws CommonException {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select article_id AS articleId,user_id AS userId,comment AS comment,create_time AS createTime from tb_comment where delete_flag = '0' and article_id = ?";
			List<Comment> commentList = runner.query(sql, new BeanListHandler<Comment>(Comment.class), articleId);
			return commentList;
		} catch (Exception e) {
			logger.error("查询评论信息数据失败!", e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}

	@Override
	public int insert(Comment ct) throws CommonException {
		try {
			QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "INSERT INTO `tb_comment`(`article_id`, `user_id`, `comment`, `create_time`, `delete_flag`) VALUES (?, ?, ?, ?, 0)";
			int result = queryRunner.update(sql, ct.getArticleId(),ct.getUserId(),ct.getComment(),new Timestamp(new Date().getTime()));
			if(result <= 0){
				logger.warn("新增评论数据到数据库失败!数据：" + JSON.toJSONString(ct));
			}
			return result;
		} catch (Exception e) {
			logger.error("新增评论数据到数据库失败!",e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}

}

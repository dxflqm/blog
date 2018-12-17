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
import org.panzhi.blog.common.util.SQLValiateUtil;
import org.panzhi.blog.dao.TagDao;
import org.panzhi.blog.dao.entity.Page;
import org.panzhi.blog.dao.entity.Tag;
import org.panzhi.blog.dao.util.jdbc.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

@Service
public class TagDaoImpl implements TagDao {
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleDaoImpl.class);

	@Override
	public List<Tag> findAllList(Page page) throws CommonException {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String param = page.getInput() == null ? "":page.getInput();
			String sql = "select tag_id AS tagId,tag_name AS tagName,sort AS sort,remark AS remark,create_time AS createTime,update_time AS updateTime from tb_tag where delete_flag = '0' and tag_name like '" + param + "%' ORDER BY sort,update_time DESC LIMIT ? , ?";
			List<Tag> tagList = runner.query(sql, new BeanListHandler<Tag>(Tag.class), page.getCurrentRow(),page.getPageSize());
			return tagList;
		} catch (Exception e) {
			logger.error("查询标签信息数据失败!", e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}
	
	@Override
	public int count(Page page) throws CommonException {
		try {
			QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
			String param = page.getInput() == null ? "":page.getInput();
			String sql = "SELECT count(*) FROM tb_tag WHERE delete_flag = '0' and tag_name like '" + param + "%'";
			@SuppressWarnings({ "unchecked", "rawtypes" })
			Object obj = queryRunner.query(sql, new ScalarHandler(1));
			return ((Long) obj).intValue();
		}  catch (Exception e) {
			logger.error("查询标签信息总行数失败!", e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}

	@Override
	public List<Tag> findAllTag() throws CommonException {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select tag_id AS tagId,tag_name AS tagName from tb_tag where delete_flag = '0' order by sort";
			List<Tag> tagList = runner.query(sql, new BeanListHandler<Tag>(Tag.class));
			return tagList;
		} catch (Exception e) {
			logger.error("查询标签信息数据失败!", e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}
	
	@Override
	public List<Tag> findTagByIds(List<String> list) throws CommonException {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String ids = SQLValiateUtil.listConvertString(list);
			String sql = "select tag_id AS tagId,tag_name AS tagName,sort AS sort,remark AS remark,create_time AS createTime,update_time AS updateTime from tb_tag where delete_flag = '0' and tag_id in ("+ids+") order by sort";
			List<Tag> tagList = runner.query(sql, new BeanListHandler<Tag>(Tag.class));
			return tagList;
		} catch (Exception e) {
			logger.error("查询标签信息数据失败!", e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}

	@Override
	public Tag findById(String tagId) throws CommonException {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select tag_id AS tagId,tag_name AS tagName,sort AS sort,remark AS remark,create_time AS createTime,update_time AS updateTime from tb_tag where delete_flag = '0' and tag_id = ?";
			Tag tag = runner.query(sql, new BeanHandler<Tag>(Tag.class), tagId);
			return tag;
		} catch (Exception e) {
			logger.error("查询标签信息数据失败!", e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}

	@Override
	public int insert(Tag tg) throws CommonException {
		try {
			QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "INSERT INTO `tb_tag`(`tag_id`, `tag_name`, `sort`, `remark`, `create_time`, `update_time`, `delete_flag`) VALUES (?, ?, ?, ?, ?, ?, 0)";
			int result = queryRunner.update(sql, tg.getTagId(),tg.getTagName(),tg.getSort(),tg.getRemark(),new Timestamp(new Date().getTime()),new Timestamp(new Date().getTime()));
			if(result <= 0){
				logger.warn("新增标签数据到数据库失败!数据：" + JSON.toJSONString(tg));
			}
			return result;
		} catch (Exception e) {
			logger.error("新增标签数据到数据库失败!",e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}

	@Override
	public int update(Tag tg) throws CommonException {
		try {
			QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "UPDATE `tb_tag` SET `tag_name` = ?, `sort` = ?, `remark` = ?, `update_time` = ? WHERE `tag_id` = ?";
			int result = queryRunner.update(sql,tg.getTagName(),tg.getSort(),tg.getRemark(),new Timestamp(new Date().getTime()),tg.getTagId());
			if(result <= 0){
				logger.warn("修改标签数据到数据库失败!数据：" + JSON.toJSONString(tg));
			}
			return result;
		} catch (Exception e) {
			logger.error("修改标签数据到数据库失败!",e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}

	@Override
	public int delete(String tagId) throws CommonException {
		try {
			QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "UPDATE `tb_tag` SET `delete_flag` = 1 WHERE `tag_id` = ?";
			int result = queryRunner.update(sql,tagId);
			if(result <= 0){
				logger.warn("修改标签数据到数据库失败!类别ID：" + tagId);
			}
			return result;
		} catch (Exception e) {
			logger.error("修改标签数据到数据库失败!",e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}

	
}

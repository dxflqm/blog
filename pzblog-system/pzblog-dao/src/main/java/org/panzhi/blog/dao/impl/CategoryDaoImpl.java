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
import org.panzhi.blog.dao.CategoryDao;
import org.panzhi.blog.dao.entity.Category;
import org.panzhi.blog.dao.entity.Page;
import org.panzhi.blog.dao.util.jdbc.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

@Service
public class CategoryDaoImpl implements CategoryDao{
	
	private static final Logger logger = LoggerFactory.getLogger(CategoryDaoImpl.class);

	@Override
	public List<Category> findAllList(Page page) throws CommonException {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String param = page.getInput() == null ? "":page.getInput();
			String sql = "select category_id AS categoryId,category_name AS categoryName,category_count AS categoryCount,remark AS remark,sort AS sort,create_time AS createTime,update_time AS updateTime from tb_category where delete_flag = '0' and category_name like '" + param + "%' ORDER BY sort,update_time DESC LIMIT ?,?";
			List<Category> categoryList = runner.query(sql, new BeanListHandler<Category>(Category.class),page.getCurrentRow(),page.getPageSize());
			return categoryList;
		} catch (Exception e) {
			logger.error("查询所有类别信息数据失败!", e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}
	
	@Override
	public int count(Page page) throws CommonException {
		try {
			QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
			String param = page.getInput() == null ? "":page.getInput();
			String sql = "SELECT count(*) FROM tb_category WHERE delete_flag = '0' and category_name like '" + param + "%'";
			@SuppressWarnings({ "unchecked", "rawtypes" })
			Object obj = queryRunner.query(sql, new ScalarHandler(1));
			return ((Long) obj).intValue();
		}  catch (Exception e) {
			logger.error("查询类别信息总行数失败!", e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}
	
	@Override
	public List<Category> findValaiteCategory() throws CommonException {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select category_id AS categoryId,category_name AS categoryName,category_count AS categoryCount,remark AS remark,sort AS sort,update_time AS updateTime from tb_category where delete_flag = '0' and category_count > '0' order by sort";
			List<Category> categoryList = runner.query(sql, new BeanListHandler<Category>(Category.class));
			return categoryList;
		} catch (Exception e) {
			logger.error("查询所有类别信息数据失败!", e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}
	
	@Override
	public List<Category> findAllCategory() throws CommonException {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select category_id AS categoryId,category_name AS categoryName,category_count AS categoryCount,remark AS remark,sort AS sort,update_time AS updateTime from tb_category where delete_flag = '0' order by sort";
			List<Category> categoryList = runner.query(sql, new BeanListHandler<Category>(Category.class));
			return categoryList;
		} catch (Exception e) {
			logger.error("查询所有类别信息数据失败!", e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}
	
	@Override
	public List<Category> findCategoryByIds(List<String> list) throws CommonException {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String ids = SQLValiateUtil.listConvertString(list);
			String sql = "select category_id AS categoryId,category_name AS categoryName,category_count AS categoryCount,remark AS remark,sort AS sort,create_time AS createTime,update_time AS updateTime from tb_category where delete_flag = '0' and category_id in ("+ids+") order by sort";
			List<Category> categoryList = runner.query(sql, new BeanListHandler<Category>(Category.class));
			return categoryList;
		} catch (Exception e) {
			logger.error("查询所有类别信息数据失败!", e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}
	
	@Override
	public Category findById(String categoryId) throws CommonException {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select category_id AS categoryId,category_name AS categoryName,category_count AS categoryCount,remark AS remark,sort AS sort,update_time AS updateTime from tb_category where delete_flag = '0' and category_id = ?";
			Category categoryList = runner.query(sql, new BeanHandler<Category>(Category.class),categoryId);
			return categoryList;
		} catch (Exception e) {
			logger.error("查询所有类别信息数据失败!", e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}

	@Override
	public int insert(Category cg) throws CommonException {
		try {
			QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "INSERT INTO `tb_category`(`category_id`, `category_name`, `category_count`, `remark`, `sort`, `create_time`, `update_time`, `delete_flag`) VALUES (?, ?, 0, ?, ?, ?, ?, 0)";
			int result = queryRunner.update(sql, cg.getCategoryId(),cg.getCategoryName(),cg.getRemark(),cg.getSort(),new Timestamp(new Date().getTime()),new Timestamp(new Date().getTime()));
			if(result <= 0){
				logger.warn("新增类别数据到数据库失败!数据：" + JSON.toJSONString(cg));
			}
			return result;
		} catch (Exception e) {
			logger.error("新增类别数据到数据库失败!",e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}
	
	@Override
	public int update(Category cg) throws CommonException {
		try {
			QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "UPDATE `tb_category` SET `category_name` = ?, `remark` = ?, `sort` = ?, `update_time` = ? WHERE `category_id` = ?";
			int result = queryRunner.update(sql,cg.getCategoryName(),cg.getRemark(),cg.getSort(),new Timestamp(new Date().getTime()),cg.getCategoryId());
			if(result <= 0){
				logger.warn("修改类别数据到数据库失败!数据：" + JSON.toJSONString(cg));
			}
			return result;
		} catch (Exception e) {
			logger.error("修改类别数据到数据库失败!",e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}

	@Override
	public int updateAddCount(String categoryId) throws CommonException {
		try {
			QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "UPDATE `tb_category` SET `category_count` = category_count + 1  WHERE `category_id` = ?";
			int result = queryRunner.update(sql,categoryId);
			if(result <= 0){
				logger.warn("修改类别数据到数据库失败!类别ID：" + categoryId);
			}
			return result;
		} catch (Exception e) {
			logger.error("修改类别数据到数据库失败!",e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}

	@Override
	public int updateDeleteCount(String categoryId) throws CommonException {
		try {
			QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "UPDATE `tb_category` SET `category_count` = category_count - 1  WHERE `category_id` = ? and `category_count` > 0";
			int result = queryRunner.update(sql,categoryId);
			if(result <= 0){
				logger.warn("修改类别数据到数据库失败!类别ID：" + categoryId);
			}
			return result;
		} catch (Exception e) {
			logger.error("修改类别数据到数据库失败!",e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}

	@Override
	public int delete(String categoryId) throws CommonException {
		try {
			QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "UPDATE `tb_category` SET `delete_flag` = 1  WHERE `category_id` = ?";
			int result = queryRunner.update(sql,categoryId);
			if(result <= 0){
				logger.warn("修改类别数据到数据库失败!类别ID：" + categoryId);
			}
			return result;
		} catch (Exception e) {
			logger.error("修改类别数据到数据库失败!",e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}
	
}

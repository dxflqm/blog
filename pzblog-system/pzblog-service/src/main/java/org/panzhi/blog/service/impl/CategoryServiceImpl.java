package org.panzhi.blog.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.panzhi.blog.common.annotation.Autowrited;
import org.panzhi.blog.common.annotation.Service;
import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.dao.ArticleDao;
import org.panzhi.blog.dao.CategoryDao;
import org.panzhi.blog.dao.entity.Category;
import org.panzhi.blog.dao.util.jdbc.JdbcUtils;
import org.panzhi.blog.service.CategoryService;
import org.panzhi.blog.web.vo.CategoryVo;
import org.panzhi.blog.web.vo.PageVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
	
	@Autowrited
	private CategoryDao categoryDao;
	
	@Autowrited
	private ArticleDao articleDao;
	
	@Override
	public PageVo<CategoryVo> findAllList(PageVo<CategoryVo> page) throws CommonException {
		List<CategoryVo> data = new ArrayList<CategoryVo>();
		int count = categoryDao.count(page);
		page.setCount(count);
		List<Category> list = categoryDao.findAllList(page);
		for (Category srcObject : list) {
			data.add(new CategoryVo(srcObject));
		}
		page.setData(data);
		return page;
	}

	@Override
	public List<CategoryVo> findList() throws CommonException {
		List<CategoryVo> data = new ArrayList<CategoryVo>();
		List<Category> list = categoryDao.findValaiteCategory();
		for (Category src : list) {
			data.add(new CategoryVo(src.getCategoryId(),src.getCategoryName(),src.getCategoryCount()));
		}
		return data;
	}
	
	@Override
	public List<CategoryVo> findAllCategory() throws CommonException {
		List<CategoryVo> data = new ArrayList<CategoryVo>();
		List<Category> list = categoryDao.findAllCategory();
		for (Category src : list) {
			data.add(new CategoryVo(src.getCategoryId(),src.getCategoryName()));
		}
		return data;
	}
	
	@Override
	public CategoryVo findById(String categoryId) throws CommonException {
		Category result = categoryDao.findById(categoryId);
		return new CategoryVo(result);
	}

	@Override
	public boolean add(Category category) throws CommonException {
		return categoryDao.insert(category) > 0;
	}

	@Override
	public boolean update(Category category) throws CommonException {
		return categoryDao.update(category) > 0;
	}

	@Override
	public boolean updateAddCount(String categoryId) throws CommonException {
		return categoryDao.updateAddCount(categoryId) > 0;
	}

	@Override
	public boolean updateDeleteCount(String categoryId) throws CommonException {
		return categoryDao.updateDeleteCount(categoryId) > 0;
	}

	@Override
	public boolean delete(String categoryId) throws CommonException, SQLException {
		try {
			JdbcUtils.beginTransaction();
			//删除类别
			categoryDao.delete(categoryId);
			//下线文章
			articleDao.deleteByCategoryId(categoryId);
			JdbcUtils.commitTransaction();
			return true;
		} catch (CommonException e) {
			logger.error("删除类别失败",e);
			JdbcUtils.rollbackTransaction();
			throw e;
		} catch (Exception e) {
			logger.error("删除类别失败",e);
			JdbcUtils.rollbackTransaction();
			throw new CommonException("删除类别失败");
		}
	}
}

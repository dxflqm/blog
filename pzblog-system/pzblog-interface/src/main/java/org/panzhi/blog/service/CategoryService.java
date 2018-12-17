package org.panzhi.blog.service;

import java.sql.SQLException;
import java.util.List;

import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.dao.entity.Category;
import org.panzhi.blog.web.vo.CategoryVo;
import org.panzhi.blog.web.vo.PageVo;

public interface CategoryService {
	
	PageVo<CategoryVo> findAllList(PageVo<CategoryVo> page) throws CommonException;
	
	List<CategoryVo> findList() throws CommonException;
	
	List<CategoryVo> findAllCategory() throws CommonException;
	
	CategoryVo findById(String categoryId) throws CommonException;
	
	boolean add(Category category) throws CommonException;
	
	boolean update(Category category) throws CommonException;
	
	boolean updateAddCount(String categoryId) throws CommonException;
	
	boolean updateDeleteCount(String categoryId) throws CommonException;
	
	boolean delete(String categoryId) throws CommonException, SQLException;

}

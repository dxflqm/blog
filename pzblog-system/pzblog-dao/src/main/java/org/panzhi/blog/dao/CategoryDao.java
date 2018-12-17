package org.panzhi.blog.dao;

import java.util.List;

import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.dao.entity.Category;
import org.panzhi.blog.dao.entity.Page;

public interface CategoryDao {
	
	/**
	 * 分页查询所有类别信息
	 * @param page
	 * @return
	 * @throws CommonException
	 */
	List<Category> findAllList(Page page) throws CommonException;
	
	/**
	 * 查询类别信息总行数
	 * @return
	 * @throws CommonException
	 */
	int count(Page page) throws CommonException;
	
	/**
	 * 查询所有类别数据
	 * @return
	 * @throws CommonException
	 */
	List<Category> findValaiteCategory() throws CommonException;
	
	/**
	 * 查询所有类别数据
	 * @return
	 * @throws CommonException
	 */
	List<Category> findAllCategory() throws CommonException;
	
	/**
	 * 通过ids查询类别信息
	 * @param list
	 * @return
	 * @throws CommonException
	 */
	List<Category> findCategoryByIds(List<String> list) throws CommonException;
	
	/**
	 * 通过ID查询类别信息
	 * @param categoryId
	 * @return
	 * @throws CommonException
	 */
	Category findById(String categoryId) throws CommonException;
	
	/**
	 * 新增类别信息
	 * @param category
	 * @return
	 * @throws CommonException
	 */
	int insert(Category category) throws CommonException;
	
	/**
	 * 修改类别信息
	 * @param category
	 * @return
	 * @throws CommonException
	 */
	int update(Category category) throws CommonException;
	
	/**
	 * 新增类别文章数据
	 * @param categoryId
	 * @return
	 * @throws CommonException
	 */
	int updateAddCount(String categoryId) throws CommonException;
	
	/**
	 * 删除类别文章数据
	 * @param categoryId
	 * @return
	 * @throws CommonException
	 */
	int updateDeleteCount(String categoryId) throws CommonException;
	
	/**
	 * 删除文章
	 * @param categoryId
	 * @return
	 * @throws CommonException
	 */
	int delete(String categoryId) throws CommonException;

}

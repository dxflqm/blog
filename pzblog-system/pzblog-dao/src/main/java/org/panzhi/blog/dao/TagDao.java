package org.panzhi.blog.dao;

import java.util.List;

import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.dao.entity.Page;
import org.panzhi.blog.dao.entity.Tag;

public interface TagDao {
	
	/**
	 * 分页查询标签数据
	 * @param page
	 * @return
	 * @throws CommonException
	 */
	List<Tag> findAllList(Page page) throws CommonException;
	
	/**
	 * 查询标签总行数
	 * @return
	 * @throws CommonException
	 */
	int count(Page page) throws CommonException;
	
	/**
	 * 查询标签所有数据
	 * @return
	 * @throws CommonException
	 */
	List<Tag> findAllTag() throws CommonException;
	
	/**
	 * 通过ids查询标签数据
	 * @param list
	 * @return
	 * @throws CommonException
	 */
	List<Tag> findTagByIds(List<String> list) throws CommonException;
	
	/**
	 * 通过ID查询标签数据
	 * @param tagId
	 * @return
	 * @throws CommonException
	 */
	Tag findById(String tagId) throws CommonException;
	
	/**
	 * 新增标签数据
	 * @param tag
	 * @return
	 * @throws CommonException
	 */
	int insert(Tag tag) throws CommonException;
	
	/**
	 * 修改标签数据
	 * @param tag
	 * @return
	 * @throws CommonException
	 */
	int update(Tag tag) throws CommonException;
	
	/**
	 * 删除标签数据
	 * @param tagId
	 * @return
	 * @throws CommonException
	 */
	int delete(String tagId) throws CommonException;

}

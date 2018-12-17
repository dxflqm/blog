package org.panzhi.blog.service;

import java.sql.SQLException;
import java.util.List;

import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.dao.entity.Tag;
import org.panzhi.blog.web.vo.PageVo;
import org.panzhi.blog.web.vo.TagVo;

public interface TagService {
	
	PageVo<TagVo> findAllList(PageVo<TagVo> page) throws CommonException;
	
	List<TagVo> findAllTag() throws CommonException;
	
	TagVo findById(String tagId) throws CommonException;
	
	boolean add(Tag tag) throws CommonException;
	
	boolean update(Tag tag) throws CommonException;
	
	boolean delete(String tagId) throws CommonException, SQLException;

}

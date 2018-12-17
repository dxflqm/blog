package org.panzhi.blog.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.panzhi.blog.common.annotation.Autowrited;
import org.panzhi.blog.common.annotation.Service;
import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.dao.ArticleDao;
import org.panzhi.blog.dao.TagDao;
import org.panzhi.blog.dao.entity.Tag;
import org.panzhi.blog.dao.util.jdbc.JdbcUtils;
import org.panzhi.blog.service.TagService;
import org.panzhi.blog.web.vo.PageVo;
import org.panzhi.blog.web.vo.TagVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TagServiceImpl implements TagService {
	
	private static final Logger logger = LoggerFactory.getLogger(TagServiceImpl.class);
	
	@Autowrited
	private TagDao tagDao;
	
	@Autowrited
	private ArticleDao articleDao;

	@Override
	public PageVo<TagVo> findAllList(PageVo<TagVo> page) throws CommonException {
		List<TagVo> data = new ArrayList<TagVo>();
		int count = tagDao.count(page);
		page.setCount(count);
		List<Tag> list = tagDao.findAllList(page);
		for (Tag src : list) {
			data.add(new TagVo(src));
		}
		page.setData(data);
		return page;
	}

	@Override
	public List<TagVo> findAllTag() throws CommonException {
		List<TagVo> data = new ArrayList<TagVo>();
		List<Tag> list = tagDao.findAllTag();
		for (Tag src : list) {
			data.add(new TagVo(src.getTagId(),src.getTagName()));
		}
		return data;
	}
	
	@Override
	public TagVo findById(String tagId) throws CommonException {
		Tag tag = tagDao.findById(tagId);
		return new TagVo(tag);
	}

	@Override
	public boolean add(Tag tag) throws CommonException {
		return tagDao.insert(tag) > 0;
	}

	@Override
	public boolean update(Tag tag) throws CommonException {
		return tagDao.update(tag) > 0;
	}

	@Override
	public boolean delete(String tagId) throws CommonException, SQLException {
		try {
			JdbcUtils.beginTransaction();
			//删除类别
			tagDao.delete(tagId);
			//下线文章
			articleDao.deleteByTagId(tagId);
			JdbcUtils.commitTransaction();
			return true;
		} catch (CommonException e) {
			logger.error("删除标签失败",e);
			JdbcUtils.rollbackTransaction();
			throw e;
		} catch (Exception e) {
			logger.error("删除标签失败",e);
			JdbcUtils.rollbackTransaction();
			throw new CommonException("删除标签失败");
		}
	}

	

}

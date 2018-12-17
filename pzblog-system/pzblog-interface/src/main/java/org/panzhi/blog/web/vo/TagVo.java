package org.panzhi.blog.web.vo;

import org.panzhi.blog.common.util.DateFormatUtil;
import org.panzhi.blog.dao.entity.Tag;

public class TagVo {
	
	private String tagId;
	
	private String tagName;
	
	private Integer sort;
	
	private String remark;
	
	private String updateTime;

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public TagVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TagVo(Tag tag) {
		super();
		this.tagId = tag.getTagId();
		this.tagName = tag.getTagName();
		this.sort = tag.getSort();
		this.remark = tag.getRemark();
		this.updateTime = DateFormatUtil.dateFormatStr(tag.getUpdateTime(),DateFormatUtil.DATE_FORMAT);
	}
	
	public TagVo(String tagId,String tagName) {
		super();
		this.tagId = tagId;
		this.tagName = tagName;
	}

}

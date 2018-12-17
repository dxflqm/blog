package org.panzhi.blog.dao.entity;

import org.panzhi.blog.common.util.IdGen;

public class Tag extends BaseDao{
	
	private String tagId;
	
	private String tagName;
	
	private Integer sort;
	
	private String remark;

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

	public Tag() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Tag(String tagName, String sort, String remark) {
		super();
		this.tagId = IdGen.uuid();
		this.tagName = tagName;
		this.sort = Integer.valueOf(sort);
		this.remark = remark;
	}

	public Tag(String tagId, String tagName, String sort, String remark) {
		super();
		this.tagId = tagId;
		this.tagName = tagName;
		this.sort = Integer.valueOf(sort);
		this.remark = remark;
	}

}

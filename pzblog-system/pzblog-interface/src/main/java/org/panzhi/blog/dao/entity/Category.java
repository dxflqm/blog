package org.panzhi.blog.dao.entity;

import org.panzhi.blog.common.util.IdGen;

public class Category extends BaseDao{
	
	private String categoryId;
	
	private String categoryName;
	
	private Integer categoryCount;
	
	private String remark;
	
	private Integer sort;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getCategoryCount() {
		return categoryCount;
	}

	public void setCategoryCount(Integer categoryCount) {
		this.categoryCount = categoryCount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Category(String categoryName, String remark, String sort) {
		super();
		this.categoryId = IdGen.uuid();
		this.categoryName = categoryName;
		this.remark = remark;
		this.sort = Integer.valueOf(sort);
	}

	public Category(String categoryId, String categoryName, String remark, String sort) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.remark = remark;
		this.sort = Integer.valueOf(sort);
	}
	
}

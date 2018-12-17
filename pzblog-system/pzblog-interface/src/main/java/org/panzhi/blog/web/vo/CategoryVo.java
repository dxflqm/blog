package org.panzhi.blog.web.vo;

import org.panzhi.blog.common.util.DateFormatUtil;
import org.panzhi.blog.dao.entity.Category;

public class CategoryVo {
	
	private String categoryId;
	
	private String categoryName;
	
	private Integer categoryCount;
	
	private String remark;
	
	private Integer sort;
	
	private String updateTime;

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

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public CategoryVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CategoryVo(Category category) {
		super();
		this.categoryId = category.getCategoryId();
		this.categoryName = category.getCategoryName();
		this.categoryCount = category.getCategoryCount();
		this.remark = category.getRemark();
		this.sort = category.getSort();
		this.updateTime = DateFormatUtil.dateFormatStr(category.getUpdateTime(),DateFormatUtil.DATE_FORMAT);
	}
	
	public CategoryVo(String categoryId,String categoryName) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}
	
	public CategoryVo(String categoryId,String categoryName,Integer categoryCount) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.categoryCount = categoryCount;
	}
}

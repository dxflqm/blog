package org.panzhi.blog.web.dto;

import org.panzhi.blog.dao.entity.Category;

public class CategoryDto extends Category{

	public CategoryDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CategoryDto(String categoryName, String remark, String sort) {
		super(categoryName, remark, sort);
		// TODO Auto-generated constructor stub
	}

	public CategoryDto(String categoryId, String categoryName, String remark, String sort) {
		super(categoryId, categoryName, remark, sort);
		// TODO Auto-generated constructor stub
	}
	
	

}

package org.panzhi.blog.web.dto;

import org.panzhi.blog.dao.entity.Tag;

public class TagDto extends Tag{

	public TagDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public TagDto(String tagName, String sort, String remark) {
		super(tagName, sort, remark);
		// TODO Auto-generated constructor stub
	}

	public TagDto(String tagId, String tagName, String sort, String remark) {
		super(tagId, tagName, sort, remark);
		// TODO Auto-generated constructor stub
	}
}

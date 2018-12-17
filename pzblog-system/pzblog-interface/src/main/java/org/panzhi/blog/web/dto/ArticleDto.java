package org.panzhi.blog.web.dto;

import org.panzhi.blog.dao.entity.Article;

public class ArticleDto extends Article{

	public ArticleDto() {
		super();
	}
	

	public ArticleDto(String articleId, String articleContent) {
		super(articleId, articleContent);
	}

	public ArticleDto(String articleTitle, String articleType, String reprintHref,
			String articleIntroduce, String categoryId, String tagId) {
		super(articleTitle, articleType, reprintHref, articleIntroduce, categoryId, tagId);
	}
	
	public ArticleDto(String articleId, String articleTitle, String articleType, String reprintHref,
			String articleIntroduce, String categoryId, String tagId) {
		super(articleId, articleTitle, articleType, reprintHref, articleIntroduce, categoryId, tagId);
	}

}

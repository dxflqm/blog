package org.panzhi.blog.dao.entity;

import org.panzhi.blog.common.util.IdGen;

public class Article extends BaseDao{
	
	private String articleId;
	
	private String articleTitle;
	
	private Integer articleType;
	
	private String reprintHref;
	
	private String articleIntroduce;
	
	private String articleContent;
	
	private String categoryId;
	
	private String tagId;
	
	private Integer status;

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public Integer getArticleType() {
		return articleType;
	}

	public void setArticleType(Integer articleType) {
		this.articleType = articleType;
	}

	public String getReprintHref() {
		return reprintHref;
	}

	public void setReprintHref(String reprintHref) {
		this.reprintHref = reprintHref;
	}

	public String getArticleIntroduce() {
		return articleIntroduce;
	}

	public void setArticleIntroduce(String articleIntroduce) {
		this.articleIntroduce = articleIntroduce;
	}

	public String getArticleContent() {
		return articleContent;
	}

	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Article() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Article(String articleId, String articleContent) {
		super();
		this.articleId = articleId;
		this.articleContent = articleContent;
	}
	
	public Article(String articleTitle, String articleType, String reprintHref,
			String articleIntroduce, String categoryId, String tagId) {
		super();
		this.articleId = IdGen.uuid();
		this.articleTitle = articleTitle;
		this.articleType = Integer.valueOf(articleType);
		this.reprintHref = reprintHref;
		this.articleIntroduce = articleIntroduce;
		this.categoryId = categoryId;
		this.tagId = tagId;
	}
	
	public Article(String articleId, String articleTitle, String articleType, String reprintHref,
			String articleIntroduce, String categoryId, String tagId) {
		super();
		this.articleId = articleId;
		this.articleTitle = articleTitle;
		this.articleType = Integer.valueOf(articleType);
		this.reprintHref = reprintHref;
		this.articleIntroduce = articleIntroduce;
		this.categoryId = categoryId;
		this.tagId = tagId;
	}
	
}

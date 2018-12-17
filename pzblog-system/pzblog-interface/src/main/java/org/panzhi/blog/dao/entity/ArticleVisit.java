package org.panzhi.blog.dao.entity;

public class ArticleVisit {
	
	private String articleId;
	
	private Integer visitCount;

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public Integer getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(Integer visitCount) {
		this.visitCount = visitCount;
	}
	
}

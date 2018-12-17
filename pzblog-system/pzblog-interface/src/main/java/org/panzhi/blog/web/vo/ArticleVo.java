package org.panzhi.blog.web.vo;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.lang3.StringUtils;
import org.panzhi.blog.common.util.DateFormatUtil;
import org.panzhi.blog.dao.entity.Article;

public class ArticleVo{
	
	private String articleId;
	
	private String articleTitle;
	
	private Integer articleType;
	
	private String articleTypeName;
	
	private String reprintHref;
	
	private String articleIntroduce;
	
	private String articleContent;
	
	private String categoryId;
	
	private String categoryName;
	
	private String tagId;
	
	private String tagName;
	
	private Integer status;
	
	private String statusName;
	
	private Integer visitCount;
	
	private String updateTime;
	
	private String updateDate;

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

	public String getArticleTypeName() {
		return articleTypeName;
	}

	public void setArticleTypeName(String articleTypeName) {
		this.articleTypeName = articleTypeName;
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

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
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Integer getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(Integer visitCount) {
		this.visitCount = visitCount;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public ArticleVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ArticleVo(Article article) throws UnsupportedEncodingException {
		super();
		this.articleId = article.getArticleId();
		this.articleTitle = article.getArticleTitle();
		this.articleType = article.getArticleType();
		if(article.getArticleType() == 0) {
			this.articleTypeName = "原创";
		}else {
			this.articleTypeName = "转载";
		}
		if(StringUtils.isNotEmpty(article.getReprintHref())) {
			this.reprintHref = URLDecoder.decode(article.getReprintHref(), "UTF-8");
		}
		this.articleIntroduce = article.getArticleIntroduce();
		this.articleContent = article.getArticleContent();
		this.categoryId = article.getCategoryId();
		this.tagId = article.getTagId();
		this.status = article.getStatus();
		if(article.getStatus() == 0) {
			this.statusName = "草稿";
		}else if(article.getStatus() == 1) {
			this.statusName = "已发布";
		}else if(article.getStatus() == 2) {
			this.statusName = "已下线";
		}else {
			this.statusName = "异常";
		}
		this.updateTime = DateFormatUtil.dateFormatStr(article.getUpdateTime(),DateFormatUtil.DATE_FORMAT);
		this.updateDate = DateFormatUtil.dateFormatStr(article.getUpdateTime(),DateFormatUtil.DATE_BIRTHDAY_FORMAT);
	}
}

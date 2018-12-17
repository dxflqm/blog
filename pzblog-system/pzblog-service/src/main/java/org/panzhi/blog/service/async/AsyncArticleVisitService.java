package org.panzhi.blog.service.async;

import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.dao.impl.ArticleVisitDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsyncArticleVisitService implements Runnable{
	
	private static final Logger logger = LoggerFactory.getLogger(AsyncArticleVisitService.class);
	
	private String articleId;

	@Override
	public void run() {
		try {
			if(this.articleId != null) {
				new ArticleVisitDaoImpl().update(this.articleId);
			}
		} catch (CommonException e) {
			logger.error("新增文章访问记录失败!",e);
		}
		
	}

	public AsyncArticleVisitService() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AsyncArticleVisitService(String articleId) {
		super();
		this.articleId = articleId;
	}

}

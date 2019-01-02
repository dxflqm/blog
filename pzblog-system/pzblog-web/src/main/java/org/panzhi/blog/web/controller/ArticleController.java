package org.panzhi.blog.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.panzhi.blog.common.annotation.Autowrited;
import org.panzhi.blog.common.annotation.Controller;
import org.panzhi.blog.common.annotation.RequestMapping;
import org.panzhi.blog.common.annotation.RequestParam;
import org.panzhi.blog.common.error.CommonErrorMsg;
import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.common.result.BuildResponseMsg;
import org.panzhi.blog.common.servlet.util.IpAddressUtil;
import org.panzhi.blog.common.util.Assert;
import org.panzhi.blog.service.ArticleService;
import org.panzhi.blog.service.async.AsyncArticleVisitService;
import org.panzhi.blog.service.async.AsyncUserVisitService;
import org.panzhi.blog.web.dto.LogDto;
import org.panzhi.blog.web.vo.ArticleVo;
import org.panzhi.blog.web.vo.PageVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/article")
public class ArticleController {
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
	
	@Autowrited
	private ArticleService articleService;
	
	@RequestMapping(value="/queryAll")
	public String queryAll(HttpServletRequest request,HttpServletResponse response){
		try {
			PageVo<ArticleVo> data = articleService.findList(new PageVo<ArticleVo>(request,response));
			//记录用户访问ip地址
			String ip = IpAddressUtil.getIpAddress(request);
			Executors.newSingleThreadExecutor().execute(new AsyncUserVisitService(new LogDto(ip,request.getHeader("User-Agent"))));
			return BuildResponseMsg.buildCustomeMsg(data);
		} catch (CommonException e) {
			logger.error("文章控制层:查询文章信息异常",e);
			return BuildResponseMsg.buildCommonFailMsg(e.getErrMsg());
		} catch (UnsupportedEncodingException e) {
			logger.error("文章控制层:查询文章信息异常",e);
			return BuildResponseMsg.buildFailMsgNoData(CommonErrorMsg.REQUEST_ERROR);
		}
	}
	
	@RequestMapping(value="/queryByCategoryId")
	public String queryAllById(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("categoryId") String categoryId){
		try {
			Assert.isEmpty(categoryId, "类别ID不能为空");
			PageVo<ArticleVo> data = articleService.findListByCategoryId(new PageVo<ArticleVo>(request,response),categoryId);
			return BuildResponseMsg.buildCustomeMsg(data);
		} catch (CommonException e) {
			logger.error("文章控制层:通过类别ID查询文章信息异常，类别ID：" + categoryId,e);
			return BuildResponseMsg.buildCommonFailMsg(e.getErrMsg());
		} catch (UnsupportedEncodingException e) {
			logger.error("文章控制层:通过类别ID查询文章信息异常",e);
			return BuildResponseMsg.buildFailMsgNoData(CommonErrorMsg.REQUEST_ERROR);
		}
	}
	
	@RequestMapping(value="/queryById")
	public String queryArticleById(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("articleId") String articleId){
		try {
			Assert.isEmpty(articleId, "文章ID不能为空");
			ArticleVo data = articleService.findArticleById(articleId);
			//记录文章访问量
			Executors.newSingleThreadExecutor().execute(new AsyncArticleVisitService(articleId));
			return BuildResponseMsg.buildSuccessMsgAndData(data);
		} catch (CommonException e) {
			logger.error("文章控制层:通过文章ID查询文章信息异常，文章ID：" + articleId,e);
			return BuildResponseMsg.buildCommonFailMsg(e.getErrMsg());
		} catch (UnsupportedEncodingException e) {
			logger.error("文章控制层:通过文章ID查询文章信息异常",e);
			return BuildResponseMsg.buildFailMsgNoData(CommonErrorMsg.REQUEST_ERROR);
		}
	}
}

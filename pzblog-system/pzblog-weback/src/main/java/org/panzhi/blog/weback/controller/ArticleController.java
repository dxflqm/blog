package org.panzhi.blog.weback.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.panzhi.blog.common.annotation.Autowrited;
import org.panzhi.blog.common.annotation.Controller;
import org.panzhi.blog.common.annotation.RequestMapping;
import org.panzhi.blog.common.annotation.RequestParam;
import org.panzhi.blog.common.constant.SysConstant;
import org.panzhi.blog.common.error.CommonErrorMsg;
import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.common.result.BuildResponseMsg;
import org.panzhi.blog.common.util.Assert;
import org.panzhi.blog.common.util.IdGen;
import org.panzhi.blog.service.ArticleService;
import org.panzhi.blog.service.CategoryService;
import org.panzhi.blog.web.dto.ArticleDto;
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
	
	@Autowrited
	private CategoryService categoryService;
	
	@RequestMapping(value="/queryAll")
	public String queryAll(HttpServletRequest request,HttpServletResponse response){
		try {
			PageVo<ArticleVo> data = articleService.findAllList(new PageVo<ArticleVo>(request,response));
			return BuildResponseMsg.buildCustomeMsg(data);
		} catch (CommonException e) {
			logger.error("文章控制层:查询所有文章信息异常,请求接口：queryAll",e);
			return BuildResponseMsg.buildCommonFailMsg(e.getErrMsg());
		} catch (UnsupportedEncodingException e) {
			logger.error("文章控制层:查询所有文章信息异常",e);
			return BuildResponseMsg.buildFailMsgNoData(CommonErrorMsg.REQUEST_ERROR);
		}
	}
	
	@RequestMapping(value="/queryById")
	public String queryArticleById(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("articleId") String articleId){
		try {
			//效验
			Assert.isEmpty(articleId, "文章ID不能为空");
			ArticleVo data = articleService.findArticleById(articleId);
			return BuildResponseMsg.buildSuccessMsgAndData(data);
		} catch (CommonException e) {
			logger.error("文章控制层:通过文章ID查询文章信息异常,请求接口：queryById",e);
			return BuildResponseMsg.buildCommonFailMsg(e.getErrMsg());
		} catch (UnsupportedEncodingException e) {
			logger.error("文章控制层:通过文章ID查询文章信息异常",e);
			return BuildResponseMsg.buildFailMsgNoData(CommonErrorMsg.REQUEST_ERROR);
		}
	}
	
	@RequestMapping(value="/addOrEdit")
	public String addOrEditArticle(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("articleId") String articleId,
			@RequestParam("articleTitle") String articleTitle,
			@RequestParam("articleType") String articleType,
			@RequestParam("reprintHref") String reprintHref,
			@RequestParam("articleIntroduce") String articleIntroduce,
			@RequestParam("categoryId") String categoryId,
			@RequestParam("tagId") String tagId){
		try {
			//效验
			Assert.isEmpty(articleTitle, "文章标题不能为空");
			Assert.isEmpty(articleType, "文章类型不能为空");
			if(articleType.equals("1")) {
				//转载链接效验
				Assert.isEmpty(reprintHref, "转载链接不能为空");
				reprintHref = URLEncoder.encode(reprintHref,"UTF-8");
			}
			Assert.isEmpty(articleIntroduce, "文章介绍不能为空");
			Assert.isEmpty(categoryId, "类别不能为空");
			Assert.isEmpty(tagId, "标签不能为空");
			if(StringUtils.isNotEmpty(articleId)) {
				//修改文章
				boolean result = articleService.updateBaseArticle(new ArticleDto(articleId,articleTitle,articleType,
						reprintHref,articleIntroduce,categoryId,tagId));
				if(!result) {
					logger.warn("修改文章失败，文章ID：" + articleId);
				}
			}else {
				//新增文章
				boolean result = articleService.addArticle(new ArticleDto(articleTitle,articleType,
						reprintHref,articleIntroduce,categoryId,tagId));
				if(!result) {
					logger.warn("新增文章失败");
				}
			}
			return BuildResponseMsg.buildSuccessMsgNoData();
		} catch (CommonException e) {
			logger.error("文章控制层:修改文章基本信息异常,请求接口：addOrEdit",e);
			return BuildResponseMsg.buildCommonFailMsg(e.getErrMsg());
		} catch (SQLException | UnsupportedEncodingException e) {
			logger.error("文章控制层:修改文章基本信息异常",e);
			return BuildResponseMsg.buildFailMsgNoData(CommonErrorMsg.REQUEST_ERROR);
		}
	}
	
	@RequestMapping(value="/editArticle")
	public String editArticle(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("articleId") String articleId,
			@RequestParam("articleContent") String articleContent) {
		try {
			//效验
			Assert.isEmpty(articleId, "文章ID不能为空");
			Assert.isEmpty(articleContent, "文章内容不能为空");
			articleContent = URLEncoder.encode(articleContent, "UTF-8");
			//修改文章
			boolean result = articleService.updateArticleContent(new ArticleDto(articleId,articleContent));
			if(!result) {
				logger.warn("修改文章失败，文章ID：" + articleId);
			}
			return BuildResponseMsg.buildSuccessMsgNoData();
		} catch (CommonException e) {
			logger.error("文章控制层:修改文章内容信息异常,请求接口：editArticle",e);
			return BuildResponseMsg.buildCommonFailMsg(e.getErrMsg());
		} catch (UnsupportedEncodingException e) {
			logger.error("文章控制层:修改文章内容信息异常",e);
			return BuildResponseMsg.buildFailMsgNoData(CommonErrorMsg.REQUEST_ERROR);
		}
	}
	
	@RequestMapping(value="/delete")
	public String delete(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("articleId") String articleId){
		try {
			//效验
			Assert.isEmpty(articleId, "文章ID不能为空");
			boolean result = articleService.delete(articleId);
			if(!result) {
				logger.warn("删除文章失败，文章ID：" + articleId);
			}
			return BuildResponseMsg.buildSuccessMsgNoData();
		} catch (CommonException e) {
			logger.error("文章控制层:删除文章内容信息异常,请求接口：delete",e);
			return BuildResponseMsg.buildCommonFailMsg(e.getErrMsg());
		}
	}
	
	@RequestMapping(value="/start")
	public String startArticle(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("articleId") String articleId){
		try {
			//效验
			Assert.isEmpty(articleId, "文章ID不能为空");
			boolean result = articleService.startArticle(articleId);
			if(!result) {
				logger.warn("文章发布失败，文章ID：" + articleId);
			}
			return BuildResponseMsg.buildSuccessMsgNoData();
		} catch (CommonException e) {
			logger.error("文章控制层:发布文章内容信息异常,请求接口：start",e);
			return BuildResponseMsg.buildCommonFailMsg(e.getErrMsg());
		} catch (SQLException e) {
			logger.error("文章控制层:发布文章内容信息异常",e);
			return BuildResponseMsg.buildFailMsgNoData(CommonErrorMsg.REQUEST_ERROR);
		}
	}
	
	@RequestMapping(value="/stop")
	public String stopArticel(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("articleId") String articleId){
		try {
			//效验
			Assert.isEmpty(articleId, "文章ID不能为空");
			boolean result = articleService.stopArticel(articleId);
			if(!result) {
				logger.warn("文章停用失败，文章ID：" + articleId);
			}
			return BuildResponseMsg.buildSuccessMsgNoData();
		} catch (CommonException e) {
			logger.error("文章控制层:停用文章内容信息异常,请求接口：stop",e);
			return BuildResponseMsg.buildCommonFailMsg(e.getErrMsg());
		} catch (SQLException e) {
			logger.error("文章控制层:停用文章内容信息异常",e);
			return BuildResponseMsg.buildFailMsgNoData(CommonErrorMsg.REQUEST_ERROR);
		}
	}
	
	@RequestMapping(value="/upload")
	public String upload(HttpServletRequest request,HttpServletResponse response) {
		try {
			ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
			List<FileItem> fileItems = new ArrayList<FileItem>();
			//断点续传，只有单个文件
			for (FileItem item : upload.parseRequest(request)) {
				if(item.isFormField()){
//					String filedName = item.getFieldName();
//					//解决普通输入项的数据的中文乱码问题
//					String filedValue = item.getString("UTF-8");
				}else{
					String filename = item.getName();
					if (filename == null || filename.trim().equals("")) {
						continue;
					}
					fileItems.add(item);
				}
			}
			File file = new File(SysConstant.SERVER_UPLOAD_ADS);
			if(!file.exists()){
				file.mkdirs();
			}
			Map<String,Object> data = new LinkedHashMap<>();
			data.put("success", 1);
			data.put("message", "上传成功!");
			for (FileItem fileItem : fileItems) {
				String fileName = IdGen.uuid() + fileItem.getName().substring(fileItem.getName().lastIndexOf("."));
				String filePath = SysConstant.SERVER_UPLOAD_ADS + File.separatorChar + fileName;
				InputStream in = fileItem.getInputStream(); //获取item中的上传文件的输入流 
                OutputStream out = new FileOutputStream(filePath); //创建一个文件输出流
                byte b[] = new byte[1024];
                int len = -1;
                while((len=in.read(b))!=-1){
                    out.write(b, 0, len);
                }
                out.close();
                in.close();
                String downloadUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/service/common/download?file="+fileName;
                data.put("url", downloadUrl);
			}
			return BuildResponseMsg.buildCustomeMsg(data);
		} catch (Exception e) {
			logger.error("文章控制层:上传内容信息异常",e);
			return BuildResponseMsg.buildFailMsgNoData(CommonErrorMsg.REQUEST_ERROR);
		}
	}
}

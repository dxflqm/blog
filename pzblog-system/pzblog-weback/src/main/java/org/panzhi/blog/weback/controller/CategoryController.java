package org.panzhi.blog.weback.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.panzhi.blog.common.annotation.Autowrited;
import org.panzhi.blog.common.annotation.Controller;
import org.panzhi.blog.common.annotation.RequestMapping;
import org.panzhi.blog.common.annotation.RequestParam;
import org.panzhi.blog.common.error.CommonErrorMsg;
import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.common.result.BuildResponseMsg;
import org.panzhi.blog.common.util.Assert;
import org.panzhi.blog.dao.entity.Category;
import org.panzhi.blog.service.CategoryService;
import org.panzhi.blog.web.vo.CategoryVo;
import org.panzhi.blog.web.vo.PageVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/category")
public class CategoryController {
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
	
	@Autowrited
	private CategoryService categoryService;
	
	@RequestMapping(value="/queryAll")
	public String queryAll(HttpServletRequest request,HttpServletResponse response){
		try {
			//效验
			PageVo<CategoryVo> data = categoryService.findAllList(new PageVo<CategoryVo>(request,response));
			return BuildResponseMsg.buildCustomeMsg(data);
		} catch (CommonException e) {
			logger.error("类别控制层:查询所有类别信息异常,请求接口：queryAll",e);
			return BuildResponseMsg.buildCommonFailMsg(e.getErrMsg());
		}
	}
	
	@RequestMapping(value="/queryCategory")
	public String queryCategory(HttpServletRequest request,HttpServletResponse response){
		try {
			//效验
			List<CategoryVo> data = categoryService.findAllCategory();
			return BuildResponseMsg.buildSuccessMsgAndData(data);
		} catch (CommonException e) {
			logger.error("类别控制层:查询类别集合信息异常,请求接口：queryCategory",e);
			return BuildResponseMsg.buildCommonFailMsg(e.getErrMsg());
		}
	}
	
	@RequestMapping(value="/queryById")
	public String queryCategory(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("categoryId") String categoryId){
		try {
			//效验
			CategoryVo data = categoryService.findById(categoryId);
			return BuildResponseMsg.buildSuccessMsgAndData(data);
		} catch (CommonException e) {
			logger.error("类别控制层:通过ID查询类别信息异常,请求接口：queryById",e);
			return BuildResponseMsg.buildCommonFailMsg(e.getErrMsg());
		}
	}
	
	@RequestMapping(value="/addOrEdit")
	public String addOrEdit(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("categoryId") String categoryId,
			@RequestParam("categoryName") String categoryName,
			@RequestParam("remark") String remark,
			@RequestParam("sort") String sort){
		try {
			//效验
			Assert.isEmpty(categoryName, "类别名称不能为空");
			Assert.isEmpty(sort, "排序不能为空");
			if(StringUtils.isNotEmpty(categoryId)) {
				boolean result = categoryService.update(new Category(categoryId,categoryName,remark,sort));
				if(!result) {
					logger.warn("修改类别失败，类别ID：" + categoryId);
				}
			}else {
				boolean result = categoryService.add(new Category(categoryName,remark,sort));
				if(!result) {
					logger.warn("新增类别失败");
				}
			}
			return BuildResponseMsg.buildSuccessMsgNoData();
		} catch (CommonException e) {
			logger.error("类别控制层:新增、修改类别信息异常,请求接口：addOrEdit",e);
			return BuildResponseMsg.buildCommonFailMsg(e.getErrMsg());
		}
	}
	
	@RequestMapping(value="/delete")
	public String delete(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("categoryId") String categoryId){
		try {
			//效验
			Assert.isEmpty(categoryId, "类别ID不能为空");
			boolean result = categoryService.delete(categoryId);
			if(!result) {
				logger.warn("删除类别失败，类别ID：" + categoryId);
			}
			return BuildResponseMsg.buildSuccessMsgNoData();
		} catch (CommonException e) {
			logger.error("类别控制层:删除类别信息异常,请求接口：delete",e);
			return BuildResponseMsg.buildCommonFailMsg(e.getErrMsg());
		} catch (SQLException e) {
			logger.error("类别控制层:删除类别信息异常,请求接口：delete",e);
			return BuildResponseMsg.buildFailMsgNoData(CommonErrorMsg.REQUEST_ERROR);
		}
	}
}

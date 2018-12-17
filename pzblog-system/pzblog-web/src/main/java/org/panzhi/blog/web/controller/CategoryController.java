package org.panzhi.blog.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.panzhi.blog.common.annotation.Autowrited;
import org.panzhi.blog.common.annotation.Controller;
import org.panzhi.blog.common.annotation.RequestMapping;
import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.common.result.BuildResponseMsg;
import org.panzhi.blog.service.CategoryService;
import org.panzhi.blog.web.vo.CategoryVo;
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
			List<CategoryVo> data = categoryService.findList();
			return BuildResponseMsg.buildSuccessMsgAndData(data);
		} catch (CommonException e) {
			logger.error("类别控制层:查询类别信息异常",e);
			return BuildResponseMsg.buildCommonFailMsg(e.getErrMsg());
		}
	}
}

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
import org.panzhi.blog.service.TagService;
import org.panzhi.blog.web.dto.TagDto;
import org.panzhi.blog.web.vo.PageVo;
import org.panzhi.blog.web.vo.TagVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/tag")
public class TagController {
	
	private static final Logger logger = LoggerFactory.getLogger(TagController.class);
	
	@Autowrited
	private TagService tagService;
	
	@RequestMapping(value="/queryAll")
	public String queryAll(HttpServletRequest request,HttpServletResponse response){
		try {
			//效验
			PageVo<TagVo> data = tagService.findAllList(new PageVo<TagVo>(request,response));
			return BuildResponseMsg.buildCustomeMsg(data);
		} catch (CommonException e) {
			logger.error("标签控制层:查询所有标签信息异常,请求接口：queryAll",e);
			return BuildResponseMsg.buildCommonFailMsg(e.getErrMsg());
		}
	}
	
	@RequestMapping(value="/queryTag")
	public String findAllTag(HttpServletRequest request,HttpServletResponse response){
		try {
			//效验
			List<TagVo> data = tagService.findAllTag();
			return BuildResponseMsg.buildSuccessMsgAndData(data);
		} catch (CommonException e) {
			logger.error("标签控制层:查询标签集合信息异常,请求接口：queryTag",e);
			return BuildResponseMsg.buildCommonFailMsg(e.getErrMsg());
		}
	}
	
	@RequestMapping(value="/queryById")
	public String findAllTag(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("tagId") String tagId){
		try {
			//效验
			TagVo data = tagService.findById(tagId);
			return BuildResponseMsg.buildSuccessMsgAndData(data);
		} catch (CommonException e) {
			logger.error("标签控制层:通过ID查询标签信息异常,请求接口：queryById",e);
			return BuildResponseMsg.buildCommonFailMsg(e.getErrMsg());
		}
	}
	
	@RequestMapping(value="/addOrEdit")
	public String add(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("tagId") String tagId,
			@RequestParam("tagName") String tagName,
			@RequestParam("sort") String sort,
			@RequestParam("remark") String remark){
		try {
			//效验
			Assert.isEmpty(tagName, "标签名称不能为空");
			Assert.isEmpty(sort, "排序不能为空");
			if(StringUtils.isNotEmpty(tagId)) {
				boolean result = tagService.update(new TagDto(tagId,tagName,sort,remark));
				if(!result) {
					logger.warn("修改标签失败，标签ID：" + tagId);
				}
			}else {
				boolean result = tagService.add(new TagDto(tagName,sort,remark));
				if(!result) {
					logger.warn("新增标签失败，");
				}
			}
			return BuildResponseMsg.buildSuccessMsgNoData();
		} catch (CommonException e) {
			logger.error("标签控制层:新增、修改标签信息异常,请求接口：addOrEdit",e);
			return BuildResponseMsg.buildCommonFailMsg(e.getErrMsg());
		}
	}
	
	@RequestMapping(value="/delete")
	public String delete(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("tagId") String tagId){
		try {
			//效验
			Assert.isEmpty(tagId, "标签ID不能为空");
			boolean result = tagService.delete(tagId);
			if(!result) {
				logger.warn("删除标签失败，标签ID：" + tagId);
			}
			return BuildResponseMsg.buildSuccessMsgNoData();
		} catch (CommonException e) {
			logger.error("标签控制层:删除标签信息异常,请求接口：delete",e);
			return BuildResponseMsg.buildCommonFailMsg(e.getErrMsg());
		} catch (SQLException e) {
			logger.error("标签控制层:删除标签信息异常,请求接口：delete",e);
			return BuildResponseMsg.buildFailMsgNoData(CommonErrorMsg.REQUEST_ERROR);
		}
	}

}

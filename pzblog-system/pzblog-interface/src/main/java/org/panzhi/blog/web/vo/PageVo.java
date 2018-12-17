package org.panzhi.blog.web.vo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.panzhi.blog.dao.entity.Page;

public class PageVo<T> extends Page{
	
	private int code;
	
	private String msg;
	
	private List<T> data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public PageVo() {
		super();
	}
	
	public PageVo(HttpServletRequest request,HttpServletResponse response) {
		super();
		String pageNo = request.getParameter("pageNo");
		if (StringUtils.isNumeric(pageNo)){
			this.setPageNo(Integer.parseInt(pageNo));
		}else{
			this.setPageNo(1);
		}
		String pageSize = request.getParameter("pageSize");
		if (StringUtils.isNumeric(pageSize)){
			this.setPageSize(Integer.parseInt(pageSize));
		}else {
			this.setPageSize(10);
		}
		this.setCurrentRow((this.getPageNo() - 1) * this.getPageSize());
		this.code = 200;
		String input = request.getParameter("input");
		if(StringUtils.isNotEmpty(input)) {
			this.setInput(input);
		}else {
			this.setInput(null);
		}
	}

	public PageVo(int code, String msg, List<T> data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
}

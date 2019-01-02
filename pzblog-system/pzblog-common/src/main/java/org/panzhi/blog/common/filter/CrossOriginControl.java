package org.panzhi.blog.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "CrossOriginControl",urlPatterns = "/*")
public class CrossOriginControl implements Filter{
	
	private boolean isCross = false;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if(isCross){
			HttpServletRequest httpServletRequest = (HttpServletRequest)request;
			HttpServletResponse httpServletResponse = (HttpServletResponse)response;
            System.out.println("Filter拦截请求: "+httpServletRequest.getServletPath());
//            httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");  
            httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));  
            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");  
            httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");  
            httpServletResponse.setHeader("Access-Control-Max-Age", "3600");  
            httpServletResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with");  
            //禁止缓存
//            httpServletResponse.setHeader("Pragma", "no-cache");
//            httpServletResponse.setHeader("Cache-Control", "no-cache");
//            httpServletResponse.setDateHeader("Expires", -1);
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}

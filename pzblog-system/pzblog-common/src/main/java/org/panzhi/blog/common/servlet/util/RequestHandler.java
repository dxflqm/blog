package org.panzhi.blog.common.servlet.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.panzhi.blog.common.annotation.RequestParam;

/**
 * 请求映射实体类
 * @author panzhi
 * @version v1.0, 2018.8.7
 */
public class RequestHandler {
	
	private Object controller;
	
	private Method method;
	
	private Pattern pattern;
	
	private Map<String,Integer> paramIndexMapping;

	public Object getController() {
		return controller;
	}

	public void setController(Object controller) {
		this.controller = controller;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Pattern getPattern() {
		return pattern;
	}

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}

	public Map<String, Integer> getParamIndexMapping() {
		return paramIndexMapping;
	}

	public void setParamIndexMapping(Map<String, Integer> paramIndexMapping) {
		this.paramIndexMapping = paramIndexMapping;
	}
	
	public RequestHandler(Pattern pattern,Object controller, Method method) {
		super();
		this.pattern = pattern;
		this.controller = controller;
		this.method = method;
		this.paramIndexMapping = new HashMap<String,Integer>();
		putParamIndexMapping(method);
	}
	
	private void putParamIndexMapping(Method method){
		Annotation[][] param = method.getParameterAnnotations();
		//获取多个注解参数
		for (int i = 0; i < param.length; i++) {
			//每个注解的参数
			for (Annotation annotations : param[i]) {
				if(annotations instanceof RequestParam){
					String paramName = ((RequestParam) annotations).value();
					if(!"".equals(paramName.trim())){
						paramIndexMapping.put(paramName, i);
					}
				}
			}
		}
		Class<?>[] parameterTypes = method.getParameterTypes();
		for (int j = 0; j < parameterTypes.length; j++) {
			Class<?> type = parameterTypes[j];
			if(type == HttpServletRequest.class || type == HttpServletResponse.class){
				paramIndexMapping.put(type.getName(), j);
			}
		}
	}
}

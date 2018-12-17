package org.panzhi.blog.common.servlet.util;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 权限效验
 * @author panzhi
 * @version v1.1, 2017.10.23
 */
public class SessionUtil {
	
	/**
	 * session创建
	 * @param request
	 * @return boolean
	 */
	public static void createSessionKV(HttpServletRequest request,String key,Object object){
		//存入会话session
        HttpSession session = request.getSession(true);
        if(session.getAttribute(key) != null){
        	session.removeAttribute(key);
		}
		session.setAttribute(key, object);
	}
	
	/**
	 * session取值
	 * @param request
	 * @param key
	 * @return
	 */
	public static Object getSessionKV(HttpServletRequest request,String key){
		HttpSession session = request.getSession(true); 
		if(session.getAttribute(key) != null){
			return session.getAttribute(key);
		}else{
			return null;
		}
	}
	
	/**
	 * session移除
	 * @param request
	 * @param key
	 * @return
	 */
	public static void removeSessionKV(HttpServletRequest request,String key){
		HttpSession session = request.getSession(true); 
		if(session.getAttribute(key) != null){
			session.removeAttribute(key);
		}
	}
	
	/**
	 * 移除所有session
	 * @param request
	 * @param key
	 * @return
	 */
	public static void removeAllSessionKV(HttpServletRequest request){
		HttpSession session = request.getSession(true);
		Enumeration<String> enumNames = session.getAttributeNames();
		while(enumNames.hasMoreElements()){
	        session.removeAttribute(enumNames.nextElement());
		}
	}
	
	/**
	 * session检查
	 * @param request
	 * @return boolean
	 */
	public static boolean checkSessionKV(HttpServletRequest request,String key){
		HttpSession session = request.getSession(true);
		if(session.getAttribute(key) != null){
			return true;
        }
		return false;
	}
}

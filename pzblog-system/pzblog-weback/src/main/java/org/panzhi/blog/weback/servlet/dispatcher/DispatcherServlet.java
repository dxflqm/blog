package org.panzhi.blog.weback.servlet.dispatcher;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.panzhi.blog.common.annotation.Autowrited;
import org.panzhi.blog.common.annotation.Controller;
import org.panzhi.blog.common.annotation.RequestMapping;
import org.panzhi.blog.common.annotation.Service;
import org.panzhi.blog.common.error.CommonErrorMsg;
import org.panzhi.blog.common.result.BuildResponseMsg;
import org.panzhi.blog.common.servlet.util.PackageUtil;
import org.panzhi.blog.common.servlet.util.RequestHandler;
import org.panzhi.blog.common.servlet.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * servlet跳转层
 * @author panzhi
 * @version v1.0, 2018.8.7
 */
@WebServlet(name ="webackDispatcherServlet",urlPatterns = "/service/*",loadOnStartup = 1,initParams = {@WebInitParam(name="contextConfigLocation", value="sso-mvc.xml"),@WebInitParam(name="serviceLocation", value="service"),@WebInitParam(name="isSafeCheck", value="true")})
public class DispatcherServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
	
	/**xml文件*/
	private static Map<String, Object> properties = new HashMap<String, Object>();
	
	/**服务路径名，拦截所有指定路径下的服务请求*/
	private static String serviceLocation = "";
	
	/**是否效验session*/
	private static boolean isSafeCheck = true;
	
	/**存放所有类名*/
	private List<String> classNames = new ArrayList<String>();
	
	/**ioc中容器的实例*/
	private Map<String, Object> ioc = new HashMap<String, Object>();
	
	/**请求方法映射*/
	private List<RequestHandler> handlerMapping = new ArrayList<RequestHandler>();
	
	
       
    
	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			//1、获取servlet中的contextConfigLocation、serviceLocation参数值
			loadConfigFile(config.getInitParameter("contextConfigLocation"));
			serviceLocation = config.getInitParameter("serviceLocation");
			String isCheck = config.getInitParameter("isSafeCheck");
			if(StringUtils.isNotEmpty(isCheck) && isCheck.equals("false")) {
				isSafeCheck = false;
			}
			//2、扫描指定包下所有的类
			doScaner(String.valueOf(properties.get("scan-package")));
			//3、初始化所有类实例，放入ioc容器，也就是map对象中
			doIntance();
			//4、实现自动依赖注入
			doAutowried();
			//5、初始化方法mapping
			initHandleMapping();
		} catch (Exception e) {
			logger.error("dispatcher-serlvet类初始化失败!",e);
		}
	}
	
	/**
	 * 载入配置文件
	 * @param configUrl
	 * @throws Exception
	 */
	private void loadConfigFile(String configUrl) throws Exception {
		try {
			Document document = new SAXBuilder().build(this.getClass().getClassLoader().getResource(configUrl));
			Element root = document.getRootElement();// 获取根元素
	        Element element = root.getChild("scan-package");//获取指定标签
	        properties.put("scan-package", element.getAttributeValue("value"));
		} catch (JDOMException | IOException  e) {
			logger.error("读取mvc配置文件失败!",e);
			throw e;
		}
        
	}
	
	/**
	 * 扫描指定包文件下所有的类
	 * @param packageName
	 * @throws IOException 
	 */
	private void doScaner(String packageName) throws IOException{
		if(StringUtils.isEmpty(packageName)){
			logger.warn("mvc配置文件中指定扫描包名为空!");
			return;
		}
		classNames = PackageUtil.getClassName(packageName);
	}
	
	/**
	 * 初始化所有类实例，放入ioc容器
	 * @throws ReflectiveOperationException
	 */
	private void doIntance() throws ReflectiveOperationException{
		if(classNames == null || classNames.isEmpty()){
			logger.warn("初始化类名为空!");
			return;
		}
		for (String className : classNames) {
			try {
				//通过反射机制构造对象
				Class<?> clazz = Class.forName(className);
				if(clazz.isAnnotationPresent(Controller.class)){
					String baneName = firstLowerCase(clazz.getSimpleName());//将类名第一个字母小写
					ioc.put(baneName, clazz.newInstance());
				}else if(clazz.isAnnotationPresent(Service.class)){
					//服务层注解判断
					Service service = clazz.getAnnotation(Service.class);
					String beanName = service.value();
					//如果该注解上没有自定义类名，则默认首字母小写
					if("".equals(beanName.trim())){
						beanName = firstLowerCase(clazz.getSimpleName());
					}
					Object instance = clazz.newInstance();
					ioc.put(beanName, instance);
					//如果自定义了类名，则优先使用（@service("AAA")）
					Class<?>[] interfaces = clazz.getInterfaces();
					for (Class<?> iface : interfaces) {
						//如果注入的是接口，可以巧妙的用接口的类型作为key
						ioc.put(iface.getName(), instance);
					}
				}else{
					continue;
				}
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				logger.error("初始化mvc-ioc容器失败!",e);
				throw e;
			}
		}
	}
	
	/**
	 * 实现自动依赖注入
	 * @throws Exception 
	 */
	private void doAutowried() throws Exception{
		if(ioc.isEmpty()){
			logger.warn("初始化实现自动依赖失败，ioc为空!");
			return ;
		}
		for(Map.Entry<String, Object> entry:ioc.entrySet()){
			//获取对象下所有的属性
			Field[] fields = entry.getValue().getClass().getDeclaredFields();
			for (Field field : fields) {
				//判断字段上有没有@Autowried注解，有的话才注入
				if(!field.isAnnotationPresent(Autowrited.class)){
					continue;
				}
				try {
					Autowrited autowrited = field.getAnnotation(Autowrited.class);
					//获取注解上有没有自定义值
					String beanName = autowrited.value().trim();
					if("".equals(beanName)){
						beanName = field.getType().getName();
					}
					//如果想要访问到私有的属性，我们要强制授权
					field.setAccessible(true);
					field.set(entry.getValue(), ioc.get(beanName));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					logger.error("初始化实现自动依赖注入失败!",e);
					throw e;
				}
			}
		}
	}
	
	/**
	 * 初始化方法mapping
	 */
	private void initHandleMapping(){
		if(ioc.isEmpty()){
			logger.warn("初始化实现自动依赖失败，ioc为空!");
			return ;
		}
		for(Map.Entry<String, Object> entry:ioc.entrySet()){
			Class<?> clazz = entry.getValue().getClass();
			//判断是否是controller层
			if(!clazz.isAnnotationPresent(Controller.class)){
				continue;
			}
			String baseUrl = null;
			//判断类有没有requestMapping注解
			if(clazz.isAnnotationPresent(RequestMapping.class)){
				RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
				baseUrl= requestMapping.value();
			}
			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				//判断方法上有没有requestMapping
				if(!method.isAnnotationPresent(RequestMapping.class)){
					continue;
				}
				RequestMapping requestMethodMapping = method.getAnnotation(RequestMapping.class);
				//"/+",表示将多个"/"转换成"/"
				String regex = ("/" + serviceLocation + baseUrl + requestMethodMapping.value()).replaceAll("/+", "/");
				Pattern pattern = Pattern.compile(regex);
				handlerMapping.add(new RequestHandler(pattern, entry.getValue(), method));
			}
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doDispatch(request, response);
	}
	
	/**
	 * servlet请求跳转
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void doDispatch(HttpServletRequest request, HttpServletResponse response) throws IOException{
		try {
			request.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", -1);
			response.setContentType("text/html");
			response.setHeader("content-type", "text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			RequestHandler handle = getHandleMapping(request);
			if(handle == null){
				//异常请求地址
				logger.warn("异常请求地址!地址:" + request.getRequestURI());
				PrintWriter out = response.getWriter();
				out.println(BuildResponseMsg.buildFailMsgNoData(CommonErrorMsg.SRC_ERROR));
			    out.flush();
			    out.close();
			    return;
			}
			//过滤loginMgr路径下的请求
			if(isSafeCheck && !filterMapCheck(request,"common")){
				//非法请求效验
				if(!SessionUtil.checkSessionKV(request, "login")){
					PrintWriter out = response.getWriter();
					out.println(BuildResponseMsg.buildFailMsgNoData(CommonErrorMsg.ILLEGE_ERROR));
				    out.flush();
				    out.close();
				    return;
				}
			}
			//获取参数列表
			Class<?>[] parameters = handle.getMethod().getParameterTypes();
			//保存所有自动赋值的参数值
			Object[] paramValues = new Object[parameters.length];
			Map<String,String[]> paramMap = request.getParameterMap();
			for (Entry<String,String[]> param : paramMap.entrySet()) {
				String value = Arrays.toString(param.getValue()).replaceAll("\\[|\\]", "").replaceAll(",\\s", ",");
				if(!handle.getParamIndexMapping().containsKey(param.getKey())){
					continue;
				}
				int index = handle.getParamIndexMapping().get(param.getKey());
				paramValues[index] = convert(parameters[index],value);
			}
			int requestIndex= handle.getParamIndexMapping().get(HttpServletRequest.class.getName());
			paramValues[requestIndex] = request;
			int responseIndex= handle.getParamIndexMapping().get(HttpServletResponse.class.getName());
			paramValues[responseIndex] = response;
			Object result = handle.getMethod().invoke(handle.getController(), paramValues);
			if(result != null){
				PrintWriter out = response.getWriter();
				out.println(result);
			    out.flush();
			    out.close();
			}
		} catch (Exception e) {
			logger.error("接口请求失败!",e);
			PrintWriter out = response.getWriter();
			out.println(BuildResponseMsg.buildFailMsgNoData(CommonErrorMsg.REQUEST_ERROR));
		    out.flush();
		    out.close();
		}
	}
	
	/**
	 * 获取用户请求方法名
	 * 与handlerMapping中的路径名进行匹配
	 * @param request
	 * @return
	 */
	private RequestHandler getHandleMapping(HttpServletRequest request){
		if(handlerMapping.isEmpty()){
			return null;
		}
		//获取用户请求路径
		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		url = url.replaceAll(contextPath, "").replaceAll("/+", "/");
		for (RequestHandler handle : handlerMapping) {
			//正则匹配请求方法名
			Matcher matcher = handle.getPattern().matcher(url);
			if(matcher.matches()){
				//匹配成功
				return handle;
			}
			continue;
		}
		return null;
	}
	
	/**
	 * 数据类型转换
	 * @param type
	 * @param value
	 * @return
	 */
	private Object convert(Class<?> type,String value){
		if(Integer.class == type){
			return Integer.valueOf(value);
		}
		if(StringUtils.isNotEmpty(value)) {
			value = value.trim();
//			value = transactSQL(value);
		}
		return value;
	}
	
	/**
	 * 将类名第一个字母小写
	 * @param clazzName
	 * @return
	 */
	private String firstLowerCase(String clazzName){
		char[] chars = clazzName.toCharArray();
		chars[0] += 32;
		return String.valueOf(chars);
	}
	
	/**
	 * 过滤指定map效验
	 * @param request
	 * @param requestMap
	 * @return
	 */
	private boolean filterMapCheck(HttpServletRequest request,String requestMap){
		String url = request.getRequestURI().replaceAll(request.getContextPath(), "").replaceAll("/+", "/");
		if(url.contains(requestMap)){
			return true;
		}
		return false;
	}
	
	/**
	 * 防止sql注入
	 * @param str
	 * @return
	 */
	public static String transactSQL(String str){
		return str.replaceAll(".*([';]+|(--)+).*", " "); 
	}
}

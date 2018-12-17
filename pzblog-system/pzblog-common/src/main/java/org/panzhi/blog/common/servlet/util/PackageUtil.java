package org.panzhi.blog.common.servlet.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 包扫描工具类
 * @author panzhi
 *
 */
public class PackageUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(PackageUtil.class);
	
	/**
	 * 获取所有指定包下的类
	 * @param packageName
	 * @return
	 */
	public static List<String> getClassName(String packageName){
		List<String> fileNames = null;
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		fileNames = getClassNameByUrls(((URLClassLoader) loader).getURLs(), packageName.replace(".", "/"));
		return fileNames;
	}
	
	private static List<String> getClassNameByUrls(URL[] urls, String packagePath){
		List<String> myClassName = new ArrayList<String>();
		if(urls != null){
			for (int i = 0; i < urls.length; i++) {
				URL url = urls[i];
				String urlPath = url.getPath();
				if(urlPath.contains("classes/")){
					myClassName.addAll(getClassNameByFile(url.getPath(),null,packagePath));
				}else{
					String jarPath = urlPath +"!/" + packagePath;
					myClassName.addAll(getClassNameByJar(jarPath));
				}
			}
		}
		return myClassName;
	}
	
	/**
	 * 扫描本地工程中的类
	 * @param filePath
	 * @param className
	 * @param packagePath
	 * @return
	 */
	private static List<String> getClassNameByFile(String filePath, List<String> className, String packagePath){
		List<String> myClassName = new ArrayList<String>();
		File file = new File(filePath);
		File[] childFiles = file.listFiles();
		for (File childFile : childFiles) {
			if(childFile.isDirectory()){
				myClassName.addAll(getClassNameByFile(filePath + childFile.getName() + "/",myClassName,packagePath));
			}else{
				String childFilePath = filePath + childFile.getName();
				if(childFilePath.endsWith(".class")){
					if(!childFilePath.contains(packagePath)){
						continue;
					}
					childFilePath = childFilePath.substring(childFilePath.indexOf("classes/") + 8,  childFilePath.lastIndexOf("."));
					childFilePath = childFilePath.replace("/", ".");
					myClassName.add(childFilePath);
				}
			}
		}
		return myClassName;
	}
	
	
	/**
	 * 扫描jar包中的类
	 * @param jarPath
	 * @return
	 */
	@SuppressWarnings("resource")
	private static List<String> getClassNameByJar(String jarPath){
		List<String> myClassName = new ArrayList<String>();
		String[] jarInfo = jarPath.split("!");
		String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"));
		String packagePath = jarInfo[1].substring(1);
		try {
			JarFile jarFile = new JarFile(jarFilePath);
			Enumeration<JarEntry> entrys = jarFile.entries();
			while (entrys.hasMoreElements()) {
				JarEntry jarEntry = entrys.nextElement();
				String entryName = jarEntry.getName();
				if (entryName.endsWith(".class")) {
					if(entryName.startsWith(packagePath)){
						entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
						myClassName.add(entryName);
					}
				}
			}
		} catch (IOException e) {
			logger.error("jar扫描失败!",e);
		}
		return myClassName;
	}
}

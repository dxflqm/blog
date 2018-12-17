package org.panzhi.blog.weback.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;

import org.panzhi.blog.common.constant.SysConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SysResouceReadUtil {
	
private static final Logger logger = LoggerFactory.getLogger(SysResouceReadUtil.class);
	
    private static Map<String, String> source = new HashMap<String, String>();
    
    static{
		//读取指定位置的配置文档(读取class目录文件)
    	classPathSourceRead();
    	//读取磁盘位置的文件	
    	fileSourceRead();
    }

    private static void classPathSourceRead(){
    	//读取指定位置的配置文档(读取class目录文件)
    	try {
			ResourceBundle rb = ResourceBundle.getBundle("system");
			put(rb);
			logger.info("初始化默认system配置文件成功!");
		} catch (Exception e) {
			logger.error("初始化默认system文件失败!",e);
		}
    }
    
    private static void fileSourceRead(){
    	ResourceBundle rb;
    	BufferedInputStream inputStream = null; 
    	String proFilePath = SysConstant.SERVER_CONFIG_ADS + "/system.properties";
        try {
            inputStream = new BufferedInputStream(new FileInputStream(proFilePath));  
            rb = new PropertyResourceBundle(inputStream);  
            put(rb);
            logger.info("初始化服务器配置文件:system.properties,成功!");
        } catch (Exception e) {  
        	logger.error("初始化服务器配置文件失败,文件地址: "+ proFilePath);  
        } finally{
        	try {
        		if (inputStream != null) {
        			inputStream.close();
				}
			} catch (IOException e) {
				logger.error("初始化服务器配置文件失败,文件地址: "+ proFilePath);  
			}  
        }
    }
    
    private synchronized static void put(ResourceBundle rb){
    	if (rb != null) {
    		Set<String> keySet = rb.keySet();
        	for (String key : keySet) {
        		source.put(key, rb.getString(key));
    		}
		}
    }
    
    /**
     * 根据配置文件中的key查询值
     * @param key
     * @return
     */
    public static String get(String key){
    	String msg = null;
    	if (source.containsKey(key)) {
    		msg = source.get(key);
		}
    	return msg;
    }
    
    public static void main(String[] args) {
		System.out.println(SysResouceReadUtil.get("username"));
	}
}

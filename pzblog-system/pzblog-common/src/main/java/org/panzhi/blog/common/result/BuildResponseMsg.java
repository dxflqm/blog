package org.panzhi.blog.common.result;

import org.apache.commons.lang3.StringUtils;
import org.panzhi.blog.common.error.CommonErrorMsg;
import org.panzhi.blog.common.error.ErrorMsg;

import com.alibaba.fastjson.JSON;


public class BuildResponseMsg {
	
	
	public static String buildCustomeMsg(Object data){
		return JSON.toJSONString(data);
	}
	
	/** 
     * 操作成功,无返回数据
     * @return 
     */  
    public static String buildSuccessMsgNoData(){
    	ResultMsg<Object> responseMsg  = new ResultMsg<Object>();  
    	responseMsg.setCode(200); 
    	responseMsg.setMsg("");
        responseMsg.setData(null);
        return JSON.toJSONString(responseMsg);
    }
    
    /**
     * 操作成功,有返回数据 
     * @param data
     * @return
     */
    public static String buildSuccessMsgAndData(Object data){  
    	ResultMsg<Object> responseMsg  = new ResultMsg<Object>();  
    	responseMsg.setCode(200); 
    	responseMsg.setMsg("");
    	responseMsg.setData(data);
    	return JSON.toJSONString(responseMsg);
    }
    
    /**
     * 操作失败,无返回数据
     * @param errorMsg
     * @return
     */
    public static String buildFailMsgNoData(ErrorMsg errorMsg){  
    	ResultMsg<Object> responseMsg  = new ResultMsg<Object>();  
    	responseMsg.setCode(errorMsg.getCode()); 
    	responseMsg.setMsg(errorMsg.getMessage());
    	responseMsg.setData(null);
    	return JSON.toJSONString(responseMsg);
    }
    
    public static String buildCommonFailMsg(String msg){
    	if(StringUtils.isNotEmpty(msg)) {
    		CommonErrorMsg.CUSTOM_ERROR.setMessage(msg);
    	}
    	ResultMsg<Object> responseMsg  = new ResultMsg<Object>();  
    	responseMsg.setCode(CommonErrorMsg.CUSTOM_ERROR.getCode()); 
    	responseMsg.setMsg(CommonErrorMsg.CUSTOM_ERROR.getMessage());
    	responseMsg.setData(null);
    	return JSON.toJSONString(responseMsg);
    }
    
    /**
     * 操作失败,有返回数据
     * @param errorMsg
     * @param object
     * @return
     */
    public static String buildFailMsgAndData(ErrorMsg errorMsg, Object object){  
    	ResultMsg<Object> responseMsg  = new ResultMsg<Object>();  
    	responseMsg.setCode(errorMsg.getCode()); 
    	responseMsg.setMsg(errorMsg.getMessage());
    	responseMsg.setData(object);
    	return JSON.toJSONString(responseMsg);
    }

}

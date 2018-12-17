package org.panzhi.blog.common.servlet.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataFilterUtil {
	
	private static final String JUNBAO_EMAIL = "junbaob2b.com";
	
	public static boolean isInteger(String str) {  
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
        return pattern.matcher(str).matches();  
	}
	
	public static String transactSQL(String str){
		if(str !=null){
			return str.replaceAll(".*([';]+|(--)+).*", " ");  
		}else{
			return null;
		}
	}
	
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			//限制为钧保注册邮箱
			if(email.contains(JUNBAO_EMAIL)){
				String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
				Pattern regex = Pattern.compile(check);
				Matcher matcher = regex.matcher(email);
				flag = matcher.matches();
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	public static boolean checkMobileNumber(String mobileNumber) {
		boolean flag = false;
		try {
			if(mobileNumber.length() == 11){
				flag = true;
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	public static void main(String[] args) {
		System.out.println(isInteger(""));
	}

}

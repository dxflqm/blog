package org.panzhi.blog.common.util;

import java.util.List;

public class SQLValiateUtil {
	
	public static String listConvertString(List<String> list) {
		if(list == null || list.isEmpty()) {
			return "";
		}
		StringBuffer data = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i) == null) {
				continue;
			}
			if(i == list.size() - 1) {
				data.append("'" + list.get(i) + "'");
			}else {
				data.append("'" + list.get(i) + "',");
			}
		}
		return data.toString();
	}

}

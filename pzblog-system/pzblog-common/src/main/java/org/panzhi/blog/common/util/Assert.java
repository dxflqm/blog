package org.panzhi.blog.common.util;

import org.apache.commons.lang3.StringUtils;
import org.panzhi.blog.common.exception.CommonException;

public abstract class Assert {

	public static void isTrue(boolean expression, String message) throws CommonException {
		if (!expression) {
			throw new CommonException(message);
		}
	}
	public static void isEmpty(String param, String message) throws CommonException{
		if(StringUtils.isEmpty(param)) {
			throw new CommonException(message);
		}
	}

	public static void isNull(Object object, String message) throws CommonException {
		if (object == null) {
			throw new CommonException(message);
		}
	}

	public static void noNullElements(Object[] array, String message) throws CommonException {
		if (array != null) {
			for (Object element : array) {
				if (element == null) {
					throw new CommonException(message);
				}
			}
		}
	}

	public static void noNullElements(Object[] array) throws CommonException {
		noNullElements(array, "[Assertion failed] - this array must not contain any null elements");
	}

	public static void state(boolean expression, String message) throws CommonException {
		if (expression) {
			throw new CommonException(message);
		}
	}

}

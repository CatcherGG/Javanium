package com.catcher.javanium.utilities;

import java.lang.reflect.Method;

public class TraceHelper {

	public static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(TraceHelper.class.getName());

	private static Method m;

	static {
		try {
			m = Throwable.class.getDeclaredMethod("getStackTraceElement", int.class);
			m.setAccessible(true);
		} catch (Exception e) {
			log.warning("Failed initializing TraceHelper.");
		}
	}

	public static String getMethodName(final int depth) {
		try {
			StackTraceElement element = (StackTraceElement) m.invoke(new Throwable(), depth + 1);
			return element.getMethodName();
		} catch (Exception e) {
			log.warning("Failed getting failed name.");
			return null;
		}
	}

	public static String getCallingMethodName() {
		return getMethodName(2);
	}

	public static String getClassName(final int depth) {
		try {
			StackTraceElement element = (StackTraceElement) m.invoke(new Throwable(), depth + 1);
			return element.getClassName();
		} catch (Exception e) {
			log.warning("Failed getting failed name.");
			return null;
		}
	}

	public static String getCallingClassName() {
		return getClassName(2);
	}

}
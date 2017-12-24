package com.catcher.javanium.utilities;

public class Logger {

	public static void info(String message){
		java.util.logging.Logger.getLogger(TraceHelper.getCallingClassName()).info(message);
	}

	public static void entering(){
		String sourceClass = TraceHelper.getCallingClassName();
		String sourceMethod = TraceHelper.getCallingClassName();
		java.util.logging.Logger.getLogger(TraceHelper.getCallingClassName()).entering(sourceClass, sourceMethod);
	}

	public static void exiting(){
		String sourceClass = TraceHelper.getCallingClassName();
		String sourceMethod = TraceHelper.getCallingClassName();
		java.util.logging.Logger.getLogger(TraceHelper.getCallingClassName()).exiting(sourceClass, sourceMethod);
	}

	public static void error(Exception thrown){
		String sourceClass = TraceHelper.getCallingClassName();
		String sourceMethod = TraceHelper.getCallingClassName();
		java.util.logging.Logger.getLogger(TraceHelper.getCallingClassName()).throwing(sourceClass, sourceMethod, thrown);
	}



}

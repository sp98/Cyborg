package com.Santosh.Cyborg.Utils;

import org.apache.log4j.Logger;

public class Log {
	
	public static Logger appLogs= null;
	
	public Log(){
		org.apache.log4j.PropertyConfigurator.configure(System.getProperty("user.dir")+ "/log4j.properties");
		appLogs = Logger.getRootLogger();
	}

	 public static void info(Object message){
		appLogs.info(message); 
	 }
	
	 public static void error(Object message){
		 appLogs.error(message); 
	 }
	 
	 public static void exception (Object message){
		appLogs.error(message);
	 }
	 
	 public static void debug(Object message){
		 appLogs.debug(message);
	 }
}

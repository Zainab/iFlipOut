package com.out.iflipout;

import android.util.Log;

public class Logger {
	private static final String TAG = "iFlipOut";
	
	public static void i(String message) {
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		String methodName = stackTraceElements[4].getMethodName();
		int lineNumber = stackTraceElements[4].getLineNumber();
		String fileName = stackTraceElements[4].getFileName().split("\\.")[0];
		
		Log.i(TAG, "(" + fileName + ":" + methodName + ":" + lineNumber + "): " + message);
	}

}

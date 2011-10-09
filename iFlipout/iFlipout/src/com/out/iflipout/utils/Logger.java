package com.out.iflipout.utils;


import android.util.Log;

public class Logger {

  public static void i(String message) {
    StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
    String methodName = stackTraceElements[4].getMethodName();
    int lineNumber = stackTraceElements[4].getLineNumber();
    String fileName = stackTraceElements[4].getFileName().split("\\.")[0];

    Log.i(Constants.TAG, "(" + fileName + ":" + methodName + ":" + lineNumber + "): " + message);
  }
}

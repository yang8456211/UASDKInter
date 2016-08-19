package com.uainter.util;

import android.util.Log;

public class UALog {
	
	// 日志的类型
	enum LogType {
		V,D,I,W,E
	}
	// 工程的调试模式，0为非调试模式，1为调试模式
	public static int debugMode = 0;
	
	// 日志Tag
	private static final String Tag = "uainter";
	
	private static void Log(String msg, LogType type){
		if (debugMode == 1){
			switch (type) {
			case V:
				Log.v(Tag, msg);
				break;
			case D:
				Log.d(Tag, msg);
				break;
			case I:
				Log.i(Tag, msg);
				break;
			case W:
				Log.w(Tag, msg);
				break;
			case E:
				Log.e(Tag, msg);
				break;
			default:
				break;
			}
		}
		
	}
	
	public static void D(String msg){
		Log(msg,LogType.D);
	}
	
	public static void W(String msg){
		Log(msg,LogType.W);
	}
	
	public static void I(String msg){
		Log(msg,LogType.I);
	}
	
	public static void E(String msg){
		Log(msg,LogType.E);
	}
	
	public static void V(String msg){
		Log(msg,LogType.V);
	}
}

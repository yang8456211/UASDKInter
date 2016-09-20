package com.uainter.util;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

public class UAUtil {

	// 上次调用接口时间
	private static long lastClickTime = 0;
	
	// 两次点击时间间隔（时间间隔内多次调用无效）
	private final static long TIME_INTERVAL = 500;

	public static void showHint(Activity activity) {

		Log.e("Unity", "[注意]当前的debugMode为：" + UALog.debugMode);

		if (UALog.debugMode == 1) {
			Toast.makeText(activity.getApplicationContext(),
					"当前debug模式为开启，请注意发布时关闭", Toast.LENGTH_SHORT).show();
		}
	}

	public static boolean isClickAvaliable() {
		long time = System.currentTimeMillis();
		if (time - lastClickTime > TIME_INTERVAL) {
			lastClickTime = time;
			return true;
		}
		lastClickTime = time;
		return false;
	}
}

package com.uainter.util;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

public class UAUtil {
	public static void showHint(Activity activity) {

		Log.e("Unity", "[注意]当前的debugMode为：" + UALog.debugMode);

		if (UALog.debugMode == 1) {
			Toast.makeText(activity.getApplicationContext(), "当前debug模式为开启，请注意发布时关闭",
					Toast.LENGTH_SHORT).show();
		}
	}
}

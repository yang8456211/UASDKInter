package com.uainter.sdks.xiaomi;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

import com.uainter.interf.UAGameInterf;
import com.uainter.main.UAMain;
import com.uainter.util.UALog;
import com.xiaomi.gamecenter.sdk.MiCommplatform;
import com.xiaomi.gamecenter.sdk.MiErrorCode;
import com.xiaomi.gamecenter.sdk.OnLoginProcessListener;
import com.xiaomi.gamecenter.sdk.entry.MiAccountInfo;
import com.xiaomi.gamecenter.sdk.entry.MiAppInfo;
import com.xiaomi.gamecenter.sdk.entry.ScreenOrientation;

public class XiaoMiManager implements UAGameInterf {

	private static XiaoMiManager xiaomimanager = null;

	public static XiaoMiManager shareManager() {
		if (xiaomimanager == null) {
			xiaomimanager = new XiaoMiManager();
		}
		return xiaomimanager;
	}

	private String appid;
	private String appkey;
	private boolean isLandScape;
	private Activity activity;

	// ======================== get/set ===========================

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public boolean isLandScape() {
		return isLandScape;
	}

	public void setLandScape(boolean isLandScape) {
		this.isLandScape = isLandScape;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	// ======================== get/set ===========================

	@Override
	public void init(JSONObject sJson) {
		MiAppInfo appInfo = new MiAppInfo();
		appInfo.setAppId(getAppid());
		appInfo.setAppKey(getAppkey());
		if (isLandScape()) {
			appInfo.setOrientation(ScreenOrientation.horizontal); // 横屏
		} else {
			appInfo.setOrientation(ScreenOrientation.vertical); // 横竖屏
		}

		try {
			MiCommplatform.Init(getActivity(), appInfo);
			JSONObject jsonObj = new JSONObject();
			String code = "1";
			jsonObj.put("callbackType", "Init");
			jsonObj.put("code", code);
			UAMain.dybCallback(jsonObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void login(JSONObject sJson) {

		MiCommplatform.getInstance().miLogin(getActivity(),
				new OnLoginProcessListener() {
					@Override
					public void finishLoginProcess(int status,
							MiAccountInfo arg1) {
						String code = "0";
						long uid = 0;
						String session = "";
						switch (status) {
						case MiErrorCode.MI_XIAOMI_GAMECENTER_SUCCESS:
							// 获取用户的登陆后的UID（即用户唯一标识）
							code = "1";
							uid = arg1.getUid();
							session = arg1.getSessionId();
							break;
						case MiErrorCode.MI_XIAOMI_GAMECENTER_ERROR_LOGIN_FAIL:
							// 登陆失败
							break;
						case MiErrorCode.MI_XIAOMI_GAMECENTER_ERROR_CANCEL:
							// 取消登录
							break;
						case MiErrorCode.MI_XIAOMI_GAMECENTER_ERROR_ACTION_EXECUTED:
							// 登录操作正在进行中
							break;
						default:
							// 登录失败
							break;
						}
						JSONObject jsonObj = new JSONObject();
						try {
							jsonObj.put("code", code);
							jsonObj.put("status", status);
							jsonObj.put("uid", Long.toString(uid));
							jsonObj.put("session", session);
							jsonObj.put("callbackType", "Login");
							UAMain.dybCallback(jsonObj);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
	}

	@Override
	public void logout() {

	}

	@Override
	public boolean initParams(JSONObject sJson) {
		this.setActivity(UAMain.activity);
		try {
			this.setAppid(sJson.getString("appid"));
			this.setAppkey(sJson.getString("appkey"));
			this.setLandScape(sJson.getBoolean("islandscape"));
			UALog.I("调用到这里");
			return true;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void exit() {
		
	}

	@Override
	public void lifeCycle(int status) {
		
	}

	@Override
	public void upUserInfo(JSONObject sJson) {

	}

}

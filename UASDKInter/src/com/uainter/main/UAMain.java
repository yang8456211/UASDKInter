package com.uainter.main;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

import com.uainter.interf.UAGameInterf;
import com.uainter.interf.UAPayInterf;
import com.uainter.sdks.xiaomi.XiaoMiManager;
import com.uainter.sdks.xiaomi.XiaoMiPay;
import com.uainter.util.UAConstants;
import com.uainter.util.UALog;
import com.uainter.util.UAUtil;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

public class UAMain extends UnityPlayerActivity{

	// 当前的渠道号
	private static int sChannel = -1;

	private static UAMain uaMainSingleTon = null;

	// Android 发送返回消息，默认接收对象的名称
	public static String callBackobj = "Game-SDK";

	// Android 发送返回消息，默认接收的方法
	public static String callBackFun = "OnGameSdkCallback";

	// Unity默认的Activity
	public static final Activity activity = UnityPlayer.currentActivity;

	public static UAMain getInvokeClass() {
		if (uaMainSingleTon == null) {
			uaMainSingleTon = new UAMain();
		}
		return uaMainSingleTon;
	}

	// 初始化
	public static void uaInit(String jsonString) {
		try {
			final JSONObject sJson = new JSONObject(jsonString);

			sChannel = sJson.getInt("channel");
			UALog.debugMode = sJson.getInt("debugmode");

			if (sJson.has("callbackobj")){
				callBackobj = sJson.getString("callbackobj");
			}
			
			if (sJson.has("callbackfun")){
				callBackFun = sJson.getString("callbackfun");
			}
			
			UALog.I("调用uaInit，当前的channel为 " + sChannel);

			final UAGameInterf uaManager = getSdkObj(sChannel);

			if (uaManager.initParams(sJson)) {
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						UAUtil.showHint(activity);
						uaManager.init(sJson);
					}
				});
			} else {
				UALog.E("初始化传入的Json解析发生错误！（列如类型错误）");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 登录
	public static void uaLogin(String jsonString) {
		try {
			final JSONObject sJson = new JSONObject(jsonString);

			final UAGameInterf uaManager = getSdkObj(sChannel);

			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					uaManager.login(sJson);
				}
			});

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	// 登出
	public static void uaLogout() {
		final UAGameInterf uaManager = getSdkObj(sChannel);
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				uaManager.logout();
			}
		});
	}

	// 退出
	public static void uaExit() {
		final UAGameInterf uaManager = getSdkObj(sChannel);
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				uaManager.exit();
			}
		});
	}

	// 上报SDK所需要的用户信息
	public static void uaUpUserInfo(String jsonString) {
		try {
			JSONObject sJson = new JSONObject(jsonString);
			UAGameInterf uaManager = getSdkObj(sChannel);
			uaManager.upUserInfo(sJson);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 生命周期函数
	public static void uaLifeCycle(String jsonString) {
		try {
			JSONObject sJson = new JSONObject(jsonString);
			final int status = sJson.getInt("status");

			if (sChannel == -1) {
				UALog.I("调用uaLifeCycle方法！SDK还未初始化，不调用生命周期方法 ");
				return;
			}
			final UAGameInterf uaManager = getSdkObj(sChannel);

			if (uaManager != null) {
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						uaManager.lifeCycle(status);
					}
				});
			} else {
				UALog.E("初始化UAManager失败，不执行初始化生命周期操作");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 支付
	public static void uaPay(String jsonString) {
		try {
			JSONObject sJson = new JSONObject(jsonString);
			// manager接口
			final UAPayInterf dybPay = getDybSdkPayObj(sChannel);

			if (dybPay.initParams(sJson)) {
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						dybPay.pay();
					}
				});
			} else {
				UALog.E("初始化传入的Json解析发生错误！（列如类型错误）");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 发送消息回Unity3d
	public static void dybCallback(JSONObject rjson) {
		UnityPlayer.UnitySendMessage(UAMain.callBackobj, UAMain.callBackFun,
				rjson.toString());
	}

	// ======================== 分割线 以下为私有方法========================
	// 根据渠道获得对象
	private static UAGameInterf getSdkObj(int sChannel) {
		UAGameInterf uaManager = null;
		switch (sChannel) {
		case UAConstants.HUAWEI_SDK:
			break;
		case UAConstants.OPPO_SDK:
			break;
		case UAConstants.VIVO_SDK:
			break;
		case UAConstants.JINLI_SDK:
			break;
		case UAConstants.KUPAI_SDK:
			break;
		case UAConstants.LENOVO_SDK:
			break;
		case UAConstants.YYB_SDK:
			break;
		case UAConstants.MEIZU_SDK:
			break;
		case UAConstants.DANGLE_SDK:
			break;
		case UAConstants.S360_SDK:
			break;
		case UAConstants.XIAOMI_SDK:
			uaManager = XiaoMiManager.shareManager();
			break;
		case UAConstants.DIYIBO_SDK:
			break;
		case UAConstants.BAIDU_SDK:
			break;
		case UAConstants.UC_SDK:
			break;
		default:
			UALog.E("初始化SDK渠道错误，manager找不到对象!");
			break;
		}
		return uaManager;
	}

	// 根据渠道获得支付对象
	private static UAPayInterf getDybSdkPayObj(int sChannel) {
		UAPayInterf uaPay = null;
		switch (sChannel) {
		case UAConstants.HUAWEI_SDK:
			break;
		case UAConstants.OPPO_SDK:
			break;
		case UAConstants.VIVO_SDK:
			break;
		case UAConstants.JINLI_SDK:
			break;
		case UAConstants.KUPAI_SDK:
			break;
		case UAConstants.LENOVO_SDK:
			break;
		case UAConstants.YYB_SDK:
			break;
		case UAConstants.MEIZU_SDK:
			break;
		case UAConstants.DANGLE_SDK:
			break;
		case UAConstants.S360_SDK:
			break;
		case UAConstants.XIAOMI_SDK:
			uaPay = XiaoMiPay.sharePayInstance();
			break;
		case UAConstants.DIYIBO_SDK:
			break;
		case UAConstants.BAIDU_SDK:
			break;
		case UAConstants.UC_SDK:
			break;
		default:
			UALog.E("支付SDK渠道错误，manager找不到对象!");
			break;
		}
		return uaPay;
	}
}

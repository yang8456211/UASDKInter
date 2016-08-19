package com.uainter.sdks.xiaomi;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;

import com.uainter.interf.UAPayInterf;
import com.uainter.main.UAMain;
import com.xiaomi.gamecenter.sdk.GameInfoField;
import com.xiaomi.gamecenter.sdk.MiCommplatform;
import com.xiaomi.gamecenter.sdk.MiErrorCode;
import com.xiaomi.gamecenter.sdk.OnPayProcessListener;
import com.xiaomi.gamecenter.sdk.entry.MiBuyInfo;

public class XiaoMiPay implements UAPayInterf {
	private static XiaoMiPay xiaomipay = null;

	public static XiaoMiPay sharePayInstance() {
		if (xiaomipay == null) {
			xiaomipay = new XiaoMiPay();
		}
		return xiaomipay;
	}

	private Activity activity;
	private String cpOrderId;
	private String cpUserinfo;
	private int amount;
	private String gameUserBalance;
	private String gameUserGamerVip;
	private String gameUserLv;
	private String gameUserPartyName;
	private String gameUserRoleName;
	private String gameUserRoleid;
	private String gameUserServerName;

	// ======================== get/set ===========================

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public String getCpOrderId() {
		return cpOrderId;
	}

	public void setCpOrderId(String cpOrderId) {
		this.cpOrderId = cpOrderId;
	}

	public String getCpUserinfo() {
		return cpUserinfo;
	}

	public void setCpUserinfo(String cpUserinfo) {
		this.cpUserinfo = cpUserinfo;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getGameUserBalance() {
		return gameUserBalance;
	}

	public void setGameUserBalance(String gameUserBalance) {
		this.gameUserBalance = gameUserBalance;
	}

	public String getGameUserGamerVip() {
		return gameUserGamerVip;
	}

	public void setGameUserGamerVip(String gameUserGamerVip) {
		this.gameUserGamerVip = gameUserGamerVip;
	}

	public String getGameUserLv() {
		return gameUserLv;
	}

	public void setGameUserLv(String gameUserLv) {
		this.gameUserLv = gameUserLv;
	}

	public String getGameUserPartyName() {
		return gameUserPartyName;
	}

	public void setGameUserPartyName(String gameUserPartyName) {
		this.gameUserPartyName = gameUserPartyName;
	}

	public String getGameUserRoleName() {
		return gameUserRoleName;
	}

	public void setGameUserRoleName(String gameUserRoleName) {
		this.gameUserRoleName = gameUserRoleName;
	}

	public String getGameUserRoleid() {
		return gameUserRoleid;
	}

	public void setGameUserRoleid(String gameUserRoleid) {
		this.gameUserRoleid = gameUserRoleid;
	}

	public String getGameUserServerName() {
		return gameUserServerName;
	}

	public void setGameUserServerName(String gameUserServerName) {
		this.gameUserServerName = gameUserServerName;
	}

	// ======================== get/set ===========================

	@Override
	public void pay() {
		// TODO Auto-generated method stub
		final MiBuyInfo miBuyInfo = new MiBuyInfo();
		miBuyInfo.setCpOrderId(getCpOrderId());// 订单号唯一（不为空）
		miBuyInfo.setCpUserInfo(getCpUserinfo()); // 此参数在用户支付成功后会透传给CP的服务器
		miBuyInfo.setAmount(getAmount()); // 必须是大于1的整数，10代表10米币，即10元人民币（不为空）

		// 用户信息，网游必须设置、单机游戏或应用可选
		Bundle mBundle = new Bundle();
		mBundle.putString(GameInfoField.GAME_USER_BALANCE, getGameUserBalance()); // 用户余额
		mBundle.putString(GameInfoField.GAME_USER_GAMER_VIP,
				getGameUserGamerVip()); // vip等级
		mBundle.putString(GameInfoField.GAME_USER_LV, getGameUserLv()); // 角色等级
		mBundle.putString(GameInfoField.GAME_USER_PARTY_NAME,
				getGameUserPartyName()); // 工会，帮派
		mBundle.putString(GameInfoField.GAME_USER_ROLE_NAME,
				getGameUserRoleName()); // 角色名称
		mBundle.putString(GameInfoField.GAME_USER_ROLEID, getGameUserRoleid()); // 角色id
		mBundle.putString(GameInfoField.GAME_USER_SERVER_NAME,
				getGameUserServerName()); // 所在服务器
		miBuyInfo.setExtraInfo(mBundle); // 设置用户信息

		// TODO Auto-generated method stub
		MiCommplatform.getInstance().miUniPay(getActivity(), miBuyInfo,
				new OnPayProcessListener() {
					@Override
					public void finishPayProcess(int status) {
						String code = "0";
						switch (status) {
						case MiErrorCode.MI_XIAOMI_PAYMENT_SUCCESS:
							code = "1";
							// 购买成功
							break;
						case MiErrorCode.MI_XIAOMI_PAYMENT_ERROR_PAY_CANCEL:
							// 取消购买
							break;
						case MiErrorCode.MI_XIAOMI_PAYMENT_ERROR_PAY_FAILURE:
							// 购买失败
							break;
						case MiErrorCode.MI_XIAOMI_PAYMENT_ERROR_ACTION_EXECUTED:
							// 操作正在进行中
							break;
						default:
							// 购买失败
							break;
						}
						JSONObject jsonObj = new JSONObject();
						try {
							jsonObj.put("code", code);
							jsonObj.put("status", status);
							jsonObj.put("callbackType", "Pay");
							UAMain.dybCallback(jsonObj);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	@Override
	public boolean initParams(JSONObject sJson) {
		this.setActivity(UAMain.activity);
		try {
			this.setCpOrderId(sJson.getString("cporderid"));
			this.setCpUserinfo(sJson.getString("cpuserinfo"));
			this.setAmount(sJson.getInt("amount"));
			this.setGameUserBalance(sJson.getString("gameuserbalance"));
			this.setGameUserGamerVip(sJson.getString("gameusergamervip"));
			this.setGameUserLv(sJson.getString("gameuserlv"));
			this.setGameUserPartyName(sJson.getString("gameuserpartyname"));
			this.setGameUserRoleName(sJson.getString("gameuserrolename"));
			this.setGameUserRoleid(sJson.getString("gameuserroleid"));
			this.setGameUserServerName(sJson.getString("gameuserservername"));
			return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}

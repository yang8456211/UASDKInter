package com.uainter.interf;

import org.json.JSONObject;

public interface UAPayInterf {
	// 支付
	public void pay();
	
	// 初始化参数
	public boolean initParams(JSONObject sJson);
}

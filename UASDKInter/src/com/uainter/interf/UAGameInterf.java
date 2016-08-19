package com.uainter.interf;

import org.json.JSONObject;

public interface UAGameInterf {
	
	// 初始化
	public void init(JSONObject sJson);
	
	// 登录
	public void login(JSONObject sJson);
	
	// 登出
	public void logout();
	
	// 退出游戏
	public void exit();
	
	// 初始化参数检查
	public boolean initParams(JSONObject sJson);
	
	// 设置生命周期的函数
	public void lifeCycle(int status);
	
	// 存储用户信息
	public void upUserInfo(JSONObject sJson);
}

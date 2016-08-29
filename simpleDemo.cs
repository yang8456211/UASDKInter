using UnityEngine;
using System.Collections;
using System;

public class simpleDemo: MonoBehaviour {

	private string stringToEdit = "初始化 -> 登录 -> 支付";
	AndroidJavaClass ajc_UnityPlayer = null;
	AndroidJavaObject currentActivity = null;
	AndroidJavaClass ajc_SDKCall = null;

	void Start () {
		//获取context
		ajc_UnityPlayer = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
		currentActivity = ajc_UnityPlayer.GetStatic<AndroidJavaObject>("currentActivity");
		ajc_SDKCall = new AndroidJavaClass("com.uainter.main.UAMain");

        print ("C# init");
		string json = "{'channel':'11','debugmode':1,'appid':'2882303761517490332','appkey':'5811749054332','islandscape':false}";
		ajc_SDKCall.CallStatic("uaInit",json);
	}
	
	void Update () {
		if (Input.GetKeyDown(KeyCode.Escape))
		{
			print ("点击了返回键！");
			ajc_SDKCall.CallStatic("uaExit");
		}
	}

	void OnGUI()  
	{  
		GUI.skin.textArea.fontSize = 50;
		GUI.skin.button.fontSize = 50;

		//绘制一个输入框用于显示Android的返回值
		stringToEdit = GUILayout.TextArea(stringToEdit,GUILayout.Width(1000),GUILayout.Height(200));

		// Login
		if(GUI.Button(new Rect(100,300,450,300),"登 录"))  
		{  
			string json = "{}";
			ajc_SDKCall.CallStatic("uaLogin",json);
		} 
			
		
		// Pay
		if(GUI.Button(new Rect(600,300,450,300),"生命周期测试1"))
		{
			string json = "{'status':'2'}";
			ajc_SDKCall.CallStatic("uaLifeCycle",json);
		}
		
		// Logout
		if(GUI.Button(new Rect(100,700,450,300),"生命周期测试2"))
		{
			string json = "{'status':'3'}";
			ajc_SDKCall.CallStatic("uaLifeCycle",json);
		}

		// Pay
		if(GUI.Button(new Rect(600,700,450,300),"支付"))
		{
			string json = "{'cporderid':'2111112121212121212121','cpuserinfo':'','amount':'21','gameuserbalance':'','gameusergamervip':''," +
				"'gameuserlv':'','gameuserpartyname':'','gameuserrolename':'','gameuserroleid':'','gameuserservername':''}";
			ajc_SDKCall.CallStatic("uaPay",json);
		}

	} 
	
	
	
	// -- SDK CallBack --
	void OnGameSdkCallback(string str)
	{
		print ("OnGameSdkCallback str is " + str);
		stringToEdit = str;
	}

	void OnApplicationPause(bool isPause)
	{
		if (isPause) {
			string json = "{'status':'3'}";
			ajc_SDKCall.CallStatic("uaLifeCycle",json);
		}
	}
	
	void OnApplicationFocus(bool isFocus)
	{
		if (isFocus)
		{
            if (ajc_SDKCall != null){
                string json = "{'status':'1'}";
                ajc_SDKCall.CallStatic("uaLifeCycle",json);
                json = "{'status':'2'}";
                ajc_SDKCall.CallStatic("uaLifeCycle",json);
            }
		}
	}
	
	void OnApplicationQuit()
	{
		string json = "{'status':'5'}";
		ajc_SDKCall.CallStatic("uaLifeCycle",json);
	}
}

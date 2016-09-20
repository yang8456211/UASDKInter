# 一、整体思想

- 通用，形式简单

- 只关注SDK业务逻辑

- 调用简单


## 通用，形式简单

	Jar包的形式，只包含纯代码，没有资源和任何配置文件。调用方只用把jar包放在 Plugins/Android/libs 里面即可使用，对于不同游戏来说，没有差别。
	
	
## 只关注SDK业务逻辑

	已经封装好了(Unity --> Android)与(Android --> Unity)的发送与回调消息，需要添加新的SDK只需要在接口中实现对应的方法即可。
	
## 调用简单

	使用C#初始化“包名+类名”的AndroidJavaClass对象，使用该对象去调用静态方法，调用简单。
	
#二、代码编写说明

以小米为例：

Android：

## 新建XiaomiManager
1. 继承UAGameInterf接口，并实现接口的方法。此处实现了initParams、Init、login。（无需管是否主线程，直接写对应SDK里面的方法即可）
2. initParams中传入对应init需要的信息，请注意`this.setActivity(UAMain.activity);`**是必须要设置的**。
3. 返回Unity的消息通过，`UAMain.dybCallback(jsonObj);`返回，组成的json内容请自定。
4. 生命周期的处理：
不需要修改启动的Activity，生命周期的处理放在了C#去控制，Android提供接口`public void lifeCycle(int status);` 在这个接口里面处理渠道SDK需要做的生命周期操作。

因为小米这里没有需要显示的操作生命周期的行为。
所以这里以华为为例子：

```

    public void lifeCycle(int status) {
        if (getActivity() == null) {
            DybGSdkUtil.E("还未Init初始化，不执行生命周期操作 ");
            return;
        }

        switch (status) {
        case DybGSdkConstants.onStart:
            break;
        case DybGSdkConstants.onResume:
            BuoyOpenSDK.getIntance().showSmallWindow(getActivity());
            break;
        case DybGSdkConstants.onPause:          
            BuoyOpenSDK.getIntance().hideSmallWindow(getActivity());
            BuoyOpenSDK.getIntance().hideBigWindow(getActivity());
            break;
        case DybGSdkConstants.onStop:
            break;
        case DybGSdkConstants.onDestroy:
            OpenHwID.releaseResouce();
            BuoyOpenSDK.getIntance().destroy(getActivity());
            break;
        default:
            break;
        }
    }
```


## 新建XiaomiPay
1. 继承UAPayInterf接口，并实现接口方法。
2. initParams中传入对应init需要的信息，请注意`this.setActivity(UAMain.activity);`**是必须要设置的**。
3. 返回Unity的消息通过，`UAMain.dybCallback(jsonObj);`返回，组成的json内容请自定。

Android的代码编写完成。


============================================

C#：

## 调用接口说明

**包名**： com.uainter.main 

**接口类名**：UAMain

在C#创建AndroidJavaClass对象：

`AndroidJavaClass ajc_SDKCall ajc_SDKCall = new AndroidJavaClass("com.uainter.main.UAMain");`

小米渠道的Init方法： 
小米渠道需要3个参数：appid、appkey、islandscape（登录与支付横屏还是竖屏显示）

```
string json = "{'channel':'11','debugmode':1,'appid':'xxx','appkey':'xxx','islandscape':false}";
ajc_SDKCall.CallStatic("uaInit",json);

```
Login方法：

```
string json = "{}";
ajc_SDKCall.CallStatic("uaLogin",json);

```

对于每个可能会有参数传入的方法，都设置了一个json对象作为参数，例如login方法，在接入应用宝的时候，需要在json数据中传入一个platform来判断是登录微信还是QQ。



## 调用Android生命周期的处理

```
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
```

具体调用请参考：simpleDemo.cs


# 三、打包

在Src右键导出jar即可，使用时请放到Plugin/Android/libs下面

# 四、效果
**初始化界面**

回调在框内显示

![](./IMG/1.jpg =300x)

**小米登录**

![](./IMG/2.jpg =300x)

**小米支付**

![](./IMG/3.jpg =300x)









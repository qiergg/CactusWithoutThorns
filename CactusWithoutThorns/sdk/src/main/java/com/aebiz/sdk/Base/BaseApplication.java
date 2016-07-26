package com.aebiz.sdk.Base;

import android.app.Application;
import android.text.TextUtils;

import com.aebiz.sdk.EventBus.ErrorCodeEvent;
import com.aebiz.sdk.EventBus.EventBus;
import com.aebiz.sdk.Utils.DataUtil;
import com.aebiz.sdk.Utils.UIUtil;

/**
 * Created by duanyytop on 15/4/10.
 */
public class BaseApplication extends Application {

    public static final String APPKEY = "92929d9ccc989d9ccfc99e9dcb9c929e9cc8c9999dc9939b93c9cb939993989f";
    public final static String APP_PWD = "9a93cccb999d9dce9c9f999ace9f92989b9898cc9b9ecf9cce9ace9ecf9b92cf";
    private static final String APP_CHANNEL = "sdk";
    private static final String TD_AD_KEY = "BDD3F6DD031DFF555EF7835C2A3BCD91";

    /**
     * **************************************************************
     * mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
     * ***************************************************************
     */
    public static final String mMode = "00";

    @Override
    public void onCreate() {
        super.onCreate();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        init();
    }


    /**
     * 初始化各个功能模块
     */
    private void init() {

//        PushService.init(this);
//        ShareSDK.initSDK(this, "aa97b9487cc0");
        MockuaiLib.getInstance().init(this, DataUtil.hexToStr(APPKEY), DataUtil.hexToStr(APP_PWD));
        MockuaiLib.getInstance().turnDebug(true);
//        TalkingDataAppCpa.init(this, TD_AD_KEY, APP_CHANNEL);
//
//        SDKInitializer.initialize(getApplicationContext());
    }


    /**
     * 处理error code
     *
     * @param event
     */
    public void onEventMainThread(ErrorCodeEvent event) {
        if (event != null && event.getResponse() != null && !TextUtils.isEmpty(event.getResponse().getCode())) {
            if (event.getResponse().getCode().startsWith("2") || event.getResponse().getCode().startsWith("3")) {
                UIUtil.toast(this, event.getResponse().getMsg());
            } else if (event.getResponse().getCode().startsWith("4")) {
                UIUtil.toast(this, "服务器出错了");
            }
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        MockuaiLib.getInstance().uninit();

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}

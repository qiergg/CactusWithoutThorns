package com.aebiz.sdk.Base;

import android.content.Context;


import com.aebiz.sdk.Business.MKNetworkWrap;
import com.aebiz.sdk.Business.MKResponseCode;
import com.aebiz.sdk.Network.MKImage;
import com.aebiz.sdk.Network.MKNetwork;
import com.aebiz.sdk.Utils.L;

import org.json.JSONObject;

/**
 * Created by liutao on 15/4/15.
 */
public class MockuaiLib {

    private static MockuaiLib mInstance;
    public static String APP_KEY;
    public static String APP_PWD;
    public static Context CONTEXT;

    private boolean isDebug = false;

    public static MockuaiLib getInstance() {
        if (mInstance == null) {
            mInstance = new MockuaiLib();
        }
        return mInstance;
    }

    public void init(Context context, String app_key, String app_pwd) {
        CONTEXT = context;
        APP_KEY = app_key;
        APP_PWD = app_pwd;
        MKNetwork.getInstance().init(context);
        MKImage.getInstance().init(context);

        //session刷新
        MKNetworkWrap.getInstance().refreshSessionToken(new MKNetwork.NetworkListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                MKNetworkWrap.MKSessionResponse mkSessionResponse = MKNetworkWrap.MKSessionResponse.parseModel(jsonObject.toString(), MKNetworkWrap.MKSessionResponse.class);
                if (MKResponseCode.SUCCESS.equals(mkSessionResponse.getCode())) {
                    //保存session
                    MKNetworkWrap.getInstance().saveSessionToken(mkSessionResponse.getData().getSession_token());
                }
            }

            @Override
            public void onError() {

            }
        });

    }

    public void turnDebug(boolean isDebug) {
        this.isDebug = isDebug;
        L.isDebug = isDebug;                   // 开启Log日志打印
    }

    public void uninit() {
        APP_KEY = null;
        CONTEXT = null;
        mInstance = null;
    }

}

package com.aebiz.sdk.Business;


import com.aebiz.sdk.EventBus.ErrorCodeEvent;
import com.aebiz.sdk.EventBus.EventBus;
import com.aebiz.sdk.Network.MKNetwork;
import com.aebiz.sdk.Utils.ApiUtils;
import com.aebiz.sdk.Utils.L;
import com.aebiz.sdk.Utils.MKStorage;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liutao on 15/5/14.
 */
public class MKNetworkWrap {

    public static MKNetworkWrap mInstance;

    public static MKNetworkWrap getInstance() {
        if (mInstance == null) {
            mInstance = new MKNetworkWrap();
        }
        return mInstance;
    }

    public void get(final String url, final Map<String, String> params, final MKNetwork.NetworkListener listener) {
        if (url != null && listener != null) {
            L.d("url = " + url);
            MKNetwork.getInstance().get(url, params, new MKNetwork.NetworkListener() {
                @Override
                public void onSuccess(final JSONObject jsonObject) {
                    MKBaseResponse response = MKBaseResponse.parseModel(jsonObject.toString(), MKBaseResponse.class);
                    EventBus.getDefault().post(new ErrorCodeEvent(response));
                    //session失效
                    if (MKResponseCode.SESSION_TOKEN_INVALID.equals(response.getCode())) {
                        refreshSessionToken(new MKNetwork.NetworkListener() {
                            @Override
                            public void onSuccess(JSONObject json) {
                                MKSessionResponse mkSessionResponse = MKSessionResponse.parseModel(json.toString(), MKSessionResponse.class);
                                //session刷新成功
                                if (MKResponseCode.SUCCESS.equals(mkSessionResponse.getCode())) {
                                    //保存session
                                    saveSessionToken(mkSessionResponse.getData().getSession_token());
                                    //更新请求session
                                    params.put("session_token", mkSessionResponse.getData().getSession_token());
                                    //重新请求接口
                                    MKNetwork.getInstance().get(url, params, listener);
                                } else {
                                    //session刷新失败，则将原结果返回
                                    listener.onSuccess(jsonObject);
                                }
                            }

                            @Override
                            public void onError() {
                                //session刷新失败，则将原结果返回
                                listener.onSuccess(jsonObject);
                            }
                        });
                    }
                    //accessToken失效
                    else if (MKResponseCode.ACCESS_TOKEN_INVALID.equals(response.getCode())) {
                        refreshAccessToken(new MKNetwork.NetworkListener() {
                            @Override
                            public void onSuccess(JSONObject json) {
                                MKUserResponse mkUserResponse = MKUserResponse.parseModel(json.toString(), MKUserResponse.class);
                                //accessToken刷新成功
                                if (MKResponseCode.SUCCESS.equals(mkUserResponse.getCode())) {
                                    //保存accessToken和refreshToken
                                    saveAccessToken(mkUserResponse.getData().getAccess_token());
//                                    saveRefreshToken(mkUserResponse.getData().getRefresh_token());
                                    //更新请求accessToken
                                    params.put("access_token", mkUserResponse.getData().getAccess_token());
                                    MKNetwork.getInstance().get(url, params, listener);
                                } else {

                                    //如果是refreshToken失效，则清楚AccessToken和RefreshToken
                                    if (MKResponseCode.REFRESH_TOKEN_INVALID.equals(mkUserResponse.getCode())) {
                                        saveAccessToken("");
                                        saveRefreshToken("");
//                                        UIUtil.toast(MockuaiLib.CONTEXT, "登陆已失效，请重新登陆");
                                    }

                                    //accessToken刷新失败，则将原结果返回
                                    listener.onSuccess(jsonObject);
                                }
                            }

                            @Override
                            public void onError() {
                                //accessToken刷新失败，则将原结果返回
                                listener.onSuccess(jsonObject);
                            }
                        });

                    }
                    //不做处理，原结果返回
                    else {
                        listener.onSuccess(jsonObject);
                    }
                }

                @Override
                public void onError() {
                    listener.onError();
                }
            });
        }
    }

    public void post(final String url, final Map<String, String> params, final MKNetwork.NetworkListener listener) {

        if (url != null && listener != null) {
            MKNetwork.getInstance().post(url, params, new MKNetwork.NetworkListener() {
                @Override
                public void onSuccess(final JSONObject jsonObject) {
                    MKBaseResponse response = MKBaseResponse.parseModel(jsonObject.toString(), MKBaseResponse.class);
                    EventBus.getDefault().post(new ErrorCodeEvent(response));
                    //session失效
                    if (MKResponseCode.SESSION_TOKEN_INVALID.equals(response.getCode())) {
                        refreshSessionToken(new MKNetwork.NetworkListener() {
                            @Override
                            public void onSuccess(JSONObject json) {
                                MKSessionResponse mkSessionResponse = MKSessionResponse.parseModel(json.toString(), MKSessionResponse.class);
                                //session刷新成功
                                if (MKResponseCode.SUCCESS.equals(mkSessionResponse.getCode())) {
                                    //保存session
                                    saveSessionToken(mkSessionResponse.getData().getSession_token());
                                    //更新请求session
                                    params.put("session_token", mkSessionResponse.getData().getSession_token());
                                    //重新请求接口
                                    MKNetwork.getInstance().post(url, params, listener);
                                } else {
                                    //session刷新失败，则将原结果返回
                                    listener.onSuccess(jsonObject);
                                }
                            }

                            @Override
                            public void onError() {
                                //session刷新失败，则将原结果返回
                                listener.onSuccess(jsonObject);
                            }
                        });
                    }
                    //accessToken失效
                    else if (MKResponseCode.ACCESS_TOKEN_INVALID.equals(response.getCode())) {
                        refreshAccessToken(new MKNetwork.NetworkListener() {
                            @Override
                            public void onSuccess(JSONObject json) {
                                MKUserResponse mkUserResponse = MKUserResponse.parseModel(json.toString(), MKUserResponse.class);
                                //accessToken刷新成功
                                if (MKResponseCode.SUCCESS.equals(mkUserResponse.getCode())) {
                                    //保存accessToken和refreshToken
                                    saveAccessToken(mkUserResponse.getData().getAccess_token());
//                                    saveRefreshToken(mkUserResponse.getData().getRefresh_token());
                                    //更新请求accessToken
                                    params.put("access_token", mkUserResponse.getData().getAccess_token());
                                    MKNetwork.getInstance().post(url, params, listener);
                                } else {

                                    //如果是refreshToken失效，则清楚AccessToken和RefreshToken
                                    if (MKResponseCode.REFRESH_TOKEN_INVALID.equals(mkUserResponse.getCode())) {
                                        saveAccessToken("");
                                        saveRefreshToken("");
//                                        UIUtil.toast(MockuaiLib.CONTEXT, "登陆已失效，请重新登陆");
                                    }

                                    //accessToken刷新失败，则将原结果返回
                                    listener.onSuccess(jsonObject);
                                }
                            }

                            @Override
                            public void onError() {
                                //accessToken刷新失败，则将原结果返回
                                listener.onSuccess(jsonObject);
                            }
                        });

                    }
                    //不做处理，原结果返回
                    else {
                        listener.onSuccess(jsonObject);
                    }
                }

                @Override
                public void onError() {
                    listener.onError();
                }
            });
        }

    }

    public void saveSessionToken(String sessionToken) {
        MKStorage.setStringValue("session_token", sessionToken);
    }

    private void saveAccessToken(String accessToken) {
        MKStorage.setStringValue("access_token", accessToken);
    }

    private void saveRefreshToken(String refreshToken) {
        MKStorage.setStringValue("refresh_token", refreshToken);
    }

    private void refreshAccessToken(final MKNetwork.NetworkListener listener) {
        HashMap<String, String> param = new HashMap<String, String>();
        param.putAll(ApiUtils.getCommonParams());
        param.put("refresh_token", MKStorage.getStringValue("refresh_token", ""));
        param.put("access_token", MKStorage.getStringValue("access_token", ""));
        param.put("api_sign", ApiUtils.getApiSign(param));

        MKNetwork.getInstance().post(MKUrl.REFRESH_ACCESS_TOKEN, param, new MKNetwork.NetworkListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                listener.onSuccess(jsonObject);
            }

            @Override
            public void onError() {
                listener.onError();
            }
        });
    }

    public void refreshSessionToken(final MKNetwork.NetworkListener listener) {
        HashMap<String, String> param = new HashMap<String, String>();
        param.putAll(ApiUtils.getCommonParams());
        param.put("api_sign", ApiUtils.getApiSign(param));

        MKNetwork.getInstance().get(MKUrl.GET_SESSION_TOKEN, param, new MKNetwork.NetworkListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                listener.onSuccess(jsonObject);
            }

            @Override
            public void onError() {
                listener.onError();
            }
        });
    }

    public class MKSession {
        private String session_token;

        public String getSession_token() {
            return session_token;
        }

        public void setSession_token(String session_token) {
            this.session_token = session_token;
        }
    }

    public class MKSessionResponse extends MKBaseResponse {
        private MKSession data;

        @Override
        public MKSession getData() {
            return data;
        }

        public void setData(MKSession data) {
            this.data = data;
        }
    }

}

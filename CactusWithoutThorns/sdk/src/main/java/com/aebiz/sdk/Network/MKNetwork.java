package com.aebiz.sdk.Network;

import android.content.Context;
import android.text.TextUtils;


import com.aebiz.sdk.Network.volley.AuthFailureError;
import com.aebiz.sdk.Network.volley.Request;
import com.aebiz.sdk.Network.volley.RequestQueue;
import com.aebiz.sdk.Network.volley.Response;
import com.aebiz.sdk.Network.volley.VolleyError;
import com.aebiz.sdk.Network.volley.toolbox.JsonObjectRequest;
import com.aebiz.sdk.Network.volley.toolbox.StringRequest;
import com.aebiz.sdk.Network.volley.toolbox.Volley;
import com.aebiz.sdk.Utils.L;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by liutao on 15/4/7.
 */
public class MKNetwork {

    public interface NetworkListener {
        /**
         * 接口请求成功
         *
         * @param jsonObject 返回的json数据
         */
        public void onSuccess(JSONObject jsonObject);

        /**
         * 接口请求失败
         */
        public void onError();
    }

    private static MKNetwork mInstance;
    private static RequestQueue mRequestQueue;
    protected Context mContext;

    public static MKNetwork getInstance() {
        if (mInstance == null) {
            mInstance = new MKNetwork();
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public void init(Context context) {
        mContext = context;
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public void get(String url, Map<String, String> params, final NetworkListener listener) {
        if (listener == null) {
            return;
        }
        if (params != null && params.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder(url + "&");
            List<String> keys = new ArrayList<String>(params.keySet());
            for (int i = 0; i < keys.size(); i++) {
                String paramKey = keys.get(i);
                String paramValue = params.get(paramKey);
                if (!TextUtils.isEmpty(paramValue)) {
                    if (i > 0) {
                        stringBuilder.append("&");
                    }

                    try {
                        stringBuilder.append(paramKey + "=" + URLEncoder.encode(paramValue, "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        stringBuilder.append(paramKey + "=" + paramValue);
                    } catch (Exception e2) {
                    }
                }
            }
            url = stringBuilder.toString();
            L.d("http", url);
        }
        mRequestQueue.add(new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError();
            }
        }));

    }


    public void post(final String url, final Map<String, String> params, final NetworkListener listener) {
        if (listener == null) {
            return;
        }
        mRequestQueue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    L.d("response: " + response);
                    L.d("http", url);
                    JSONObject jsonObject = new JSONObject(response);
                    listener.onSuccess(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onError();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                L.e("http volley error: " + error.getMessage());
                listener.onError();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        });
    }


    /**
     * 上传文件
     *
     * @param url    url地址
     * @param params post参数
     * @param files  需要上传的文件
     * @return
     * @throws Exception
     */
    public void postFile(String url, Map<String, String> params, Map<String, File> files, final NetworkListener listener) throws Exception {

        String BOUNDARY = java.util.UUID.randomUUID().toString();
        String PREFIX = "--";
        String LINE_END = "\r\n";
        String MULTIPART_FROM_DATA = "multipart/form-data";
        String CHARSET = "UTF-8";
        HttpURLConnection conn = null;
        String fileName = "images";

        try {
            URL uri = new URL(url);
            conn = (HttpURLConnection) uri.openConnection();
            conn.setReadTimeout(5 * 1000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);

            StringBuilder sbHeader = new StringBuilder();
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    sbHeader.append(PREFIX);
                    sbHeader.append(BOUNDARY);
                    sbHeader.append(LINE_END);
                    sbHeader.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINE_END);
                    sbHeader.append("Content-Type: text/plain; charset=" + CHARSET + LINE_END);
                    sbHeader.append("Content-Transfer-Encoding: 8bit" + LINE_END);
                    sbHeader.append(LINE_END);
                    sbHeader.append(entry.getValue());
                    sbHeader.append(LINE_END);
                }
            }

            DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
            outStream.write(sbHeader.toString().getBytes());
            if (files != null) {
                for (Map.Entry<String, File> file : files.entrySet()) {
                    StringBuilder sbContentHeader = new StringBuilder();
                    sbContentHeader.append(PREFIX);
                    sbContentHeader.append(BOUNDARY);
                    sbContentHeader.append(LINE_END);
                    sbContentHeader.append("Content-Disposition: form-data; name=\"userupfile\"; filename=\"" + fileName + "\"" + LINE_END);
                    sbContentHeader.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINE_END);
                    sbContentHeader.append(LINE_END);
                    outStream.write(sbContentHeader.toString().getBytes());
                    InputStream is = new FileInputStream(file.getValue());
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = is.read(buffer)) != -1) {
                        outStream.write(buffer, 0, len);
                    }
                    is.close();
                    outStream.write(LINE_END.getBytes());
                }
            }
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
            outStream.write(end_data);
            outStream.flush();
            outStream.close();

            StringBuilder sbResponse = new StringBuilder();
            int res = conn.getResponseCode();
            if (res == 200) {
                InputStream in = conn.getInputStream();
                int ch;
                while ((ch = in.read()) != -1) {
                    sbResponse.append((char) ch);
                }
                listener.onSuccess(new JSONObject(sbResponse.toString()));
                in.close();
            } else {
                listener.onError();
            }

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }


}

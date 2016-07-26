package com.aebiz.sdk.Utils;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by duanyytop on 15/4/17.
 */
public class JSONModel {

    public static <T> T parseModel(String jsonStr, Class<T> cl) {
        JSONObject jsonObject = null;
        String data = null;
        try {
            jsonObject = new JSONObject(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            data = jsonObject.optString("data");
            JSONObject dataJson = new JSONObject(data);
        } catch (Exception e) {
            try {
                jsonObject.put("data", null);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        if (!TextUtils.isEmpty(data)) {
            data = data.trim().replace(" ", "");
            if (TextUtils.equals("[]", data) || TextUtils.equals("{}", data)) {
                try {
                    jsonObject.put("data", null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return JSONUtil.parseModel(jsonObject.toString(), cl);
    }

    public static <T> List<T> parseList(String jsonStr, TypeToken<List<T>> type) {
        JSONObject jsonObject = null;
        String data = null;
        try {
            jsonObject = new JSONObject(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            data = jsonObject.optString("data");
            JSONObject dataJson = new JSONObject(data);
        } catch (Exception e) {
            try {
                jsonObject.put("data", null);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        if (!TextUtils.isEmpty(data)) {
            data = data.trim().replace(" ", "");
            if (TextUtils.equals("[]", data) || TextUtils.equals("{}", data)) {
                try {
                    jsonObject.put("data", null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return JSONUtil.parseModelList(jsonStr, type);
    }

    public String toJsonString() {
        return JSONUtil.objectToJson(this);
    }


}

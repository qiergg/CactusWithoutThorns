package com.aebiz.sdk.Utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by liutao on 14/11/25.
 */
public class ApiUtils {
    /**
     * 获取公有的参数
     *
     * @return
     */
    public static HashMap<String, String> getCommonParams() {
        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("app_key", MockuaiLib.APP_KEY);
//
//        if (!TextUtils.isEmpty(MKStorage.getStringValue("api_token", ""))) {
//            params.put("api_token", MKStorage.getStringValue("api_token", ""));
//        }
//        if (!TextUtils.isEmpty(MKStorage.getStringValue("access_token", ""))) {
//            params.put("access_token", MKStorage.getStringValue("access_token", ""));
//        }
//        params.put("session_token", MKStorage.getStringValue("session_token", " "));
        params.put("format", "json");
        params.put("platform", "android");
//        params.put("app_ver", getApplicationVersion(MockuaiLib.CONTEXT));
//        params.put("device_id", MKDevice.getDeviceId());
        return params;
    }

    public static String getApiSign(HashMap<String, String> params) {

        return md5Encrypt(params, kSort(params));
    }

    public static String getApiSign(List<NameValuePair> params) {
        return getApiSign(listToHashMap(params));
    }

    public static String getApplicationVersion(Context context) {
        try {
            return context.getApplicationContext().getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return "";
    }

    public static String getPostApiSign(List<BasicNameValuePair> params) {
        List<String> keys = kPostSort(params);
        HashMap<String, String> mapParams = listPostToHashMap(params);
        return md5Encrypt(mapParams, keys);
    }

    private static List<String> kSort(List<NameValuePair> params) {
        List<String> keys = new ArrayList<String>();
        List<String> values = new ArrayList<String>();
        for (int i = 0; i < params.size(); i++) {
            keys.add(params.get(i).getName());
        }
        for (int i = 0; i < params.size(); i++) {
            values.add(params.get(i).getValue());
        }
        String[] strs = new String[keys.size()];
        Arrays.sort(keys.toArray(strs), String.CASE_INSENSITIVE_ORDER);
        return Arrays.asList(strs);
    }

    private static List<String> kSort(Map<String, String> params) {
        Set<String> keys = params.keySet();
        String[] strs = new String[keys.size()];
        Arrays.sort(keys.toArray(strs), String.CASE_INSENSITIVE_ORDER);
        return Arrays.asList(strs);
    }

    private static List<String> kPostSort(List<BasicNameValuePair> params) {
        List<String> keys = new ArrayList<String>();
        List<String> values = new ArrayList<String>();
        for (int i = 0; i < params.size(); i++) {
            keys.add(params.get(i).getName());
        }
        for (int i = 0; i < params.size(); i++) {
            values.add(params.get(i).getValue());
        }
        String[] strs = new String[keys.size()];
        Arrays.sort(keys.toArray(strs), String.CASE_INSENSITIVE_ORDER);
        return Arrays.asList(strs);
    }

    private static HashMap<String, String> listToHashMap(List<NameValuePair> params) {
        HashMap<String, String> mapParams = new HashMap<String, String>();
        for (int i = 0; i < params.size(); i++) {
            mapParams.put(params.get(i).getName(), params.get(i).getValue());
        }
        return mapParams;
    }

    private static HashMap<String, String> listPostToHashMap(List<BasicNameValuePair> params) {
        HashMap<String, String> mapParams = new HashMap<String, String>();
        for (int i = 0; i < params.size(); i++) {
            mapParams.put(params.get(i).getName(), params.get(i).getValue());
        }
        return mapParams;
    }

    private static String md5Encrypt(HashMap<String, String> mapParams, List<String> keys) {

        Map<String, String> signMap = new TreeMap<String, String>();
        for (Map.Entry<String, String> entry : mapParams.entrySet()) {
            signMap.put(entry.getKey(), entry.getValue());
        }

        StringBuilder paramSb = new StringBuilder();
        for (Map.Entry<String, String> entry : signMap.entrySet()) {
            paramSb.append(entry.getKey());
            paramSb.append("=");
            paramSb.append(entry.getValue());
            paramSb.append("&");
        }
        paramSb.deleteCharAt(paramSb.length() - 1);

        String params = "";

//        StringBuffer paramSb = new StringBuffer();
//
//        for (int i = 0; i < keys.size(); i++) {
//            paramSb.append(keys.get(i));
//            paramSb.append("=");
//            paramSb.append(mapParams.get(keys.get(i)));
//            paramSb.append("&");
//        }
//        paramSb.deleteCharAt(paramSb.length() - 1);


//        params = MockuaiLib.APP_PWD + paramSb + MockuaiLib.APP_PWD;
        return MD5(params);
    }

    public static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

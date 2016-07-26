package com.aebiz.sdk.Utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

public class ConnectionUtil {


    /**
     * 判断是否有网络
     *
     * @param context
     * @return boolean 是否存在网络
     */
    public static boolean haveConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return false;
        } else if (!networkInfo.isAvailable()) {
            return false;
        }
        return true;
    }


    /**
     * 判断网络类型
     *
     * @param context
     * @return String wifi or mobile
     */
    public static String connectionType(Activity context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        String result = "";
        int type = networkInfo.getType();
        if (type == ConnectivityManager.TYPE_WIFI) {
            result = "wifi";
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            result = "mobile";
        }
        return result;
    }

    /**
     * 通过HTTP协议post文件,参数
     *
     * @param actionUrl 上传URL地址
     * @param params    Map<String,String>参数, 没有参数的话传null
     * @param files     Map<String,File>参数,没有参数的话传null
     * @return String 返回页面的内容
     * @throws java.io.IOException
     */
    public static String post(String actionUrl, Map<String, String> params,
                              Map<String, File> files) throws IOException {

        String BOUNDARY = java.util.UUID.randomUUID().toString();
        String PREFIX = "--", LINEND = "\r\n";
        String MULTIPART_FROM_DATA = "multipart/form-data";
        String CHARSET = "UTF-8";

        URL uri = new URL(actionUrl);
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setDoInput(true);// 允许输入
        conn.setDoOutput(true);// 允许输出
        conn.setUseCaches(false); // 不允许使用缓存
        conn.setRequestMethod("POST");
        conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("Charsert", "UTF-8");
        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
                + ";boundary=" + BOUNDARY);
        // 首先组拼文本类型的参数
        StringBuilder sb = new StringBuilder();
        if (params != null) {
            for (Entry<String, String> entry : params.entrySet()) {
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINEND);
                sb.append("Content-Disposition: form-data; name=\""
                        + entry.getKey() + "\"" + LINEND);
                sb.append("Content-Type: text/plain; charset=" + CHARSET
                        + LINEND);
                sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
                sb.append(LINEND);
                sb.append(entry.getValue());
                sb.append(LINEND);
            }
        }
        DataOutputStream outStream = new DataOutputStream(
                conn.getOutputStream());
        outStream.write(sb.toString().getBytes("UTF-8"));

        // 发送文件数据
        if (files != null) {
            for (Entry<String, File> file : files.entrySet()) {
                StringBuilder sb1 = new StringBuilder();
                sb1.append(PREFIX);
                sb1.append(BOUNDARY);
                sb1.append(LINEND);
                sb1.append("Content-Disposition: form-data; name=\""
                        + file.getKey() + "\"; filename=\""
                        + file.getValue().getPath() + "\"" + LINEND);
                sb1.append("Content-Type: application/octet-stream; charset="
                        + CHARSET + LINEND);
                sb1.append(LINEND);
                outStream.write(sb1.toString().getBytes("UTF-8"));

                InputStream is = new FileInputStream(file.getValue());
                byte[] buffer = new byte[1024 * 50];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }

                is.close();
                outStream.write(LINEND.getBytes());
            }
        }

        // 请求结束标志
        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
        outStream.write(end_data);
        outStream.flush();

        // 得到响应码
        int res = conn.getResponseCode();
        StringBuilder sb2 = new StringBuilder();
        InputStream in = null;
        if (res == 200) {
            in = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in,
                    "UTF-8"));
            String ch = null;
            while ((ch = br.readLine()) != null) {
                sb2.append(ch);
            }
        }
        return in == null ? "" : sb2.toString();
    }


}

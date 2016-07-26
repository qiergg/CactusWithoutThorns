package com.aebiz.sdk.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;


import com.aebiz.sdk.Base.MockuaiLib;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by duanyangyang on 15/3/30.
 */
public class MKDevice {

    private final static String DEVICE_ID_KEY = "mockuai_device_id";
    private final static int TIME_CHAR_LENGTH = 11;
    private static String deviceId = null;
    private static String APP_Version = "";

    /**
     * 获取手机终端的唯一设备号
     *
     * @return
     */
    public static String getDeviceId() {

        deviceId = getIdFromSystemSetting();
        if (!isNumberOrLetterOrNotnull(deviceId)) {
            deviceId = getIdFromRom();                        // Setting获取失败，从sd卡读取
            if (!isNumberOrLetterOrNotnull(deviceId)) {                  // 都读取失败，重新生成id
                deviceId = generateDeviceId();
                MKStorage.setStringValue(DEVICE_ID_KEY, deviceId);
                putIdToRom(deviceId);
            } else {                                          //sd卡读取成功，并把id写入Setting
                putIdToSystemSetting(deviceId);
            }
        }
        return deviceId;
    }

    /**
     * 获取手机系统版本号
     *
     * @return
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机机型
     *
     * @return
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }


    public static String getAppVersion() {

        if (TextUtils.isEmpty(APP_Version) && MockuaiLib.CONTEXT != null) {
            String packageName = MockuaiLib.CONTEXT.getPackageName();
            PackageManager packageManager = MockuaiLib.CONTEXT.getPackageManager();
            try {
                PackageInfo info = packageManager
                        .getPackageInfo(packageName, 0);
                APP_Version = info.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

        }
        return APP_Version;
    }

    /**
     * 获取手机串号
     *
     * @return
     * @author zcf
     * @see [类、类#方法、类#成员]
     */
    private static String getIMEI() {

        TelephonyManager telMg = (TelephonyManager) MockuaiLib.CONTEXT
                .getSystemService(Context.TELEPHONY_SERVICE);

        return telMg.getDeviceId();
    }

    /**
     * 获取手机机型
     */
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机IP地址
     *
     * @return
     */

    public static String getIpAddress() {
        if (isInWifi()) {
            return "0";
        } else {
            return getGPRSLocalIpAddress();
        }
    }

    /**
     * 当前是否是WiFi环境
     *
     * @return boolean
     */
    public static boolean isInWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MockuaiLib.CONTEXT
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }
        return false;
    }

    /**
     * 使用WIFI时，获取本机IP地址
     *
     * @return
     */
    private static String getWIFILocalIpAdress() {

        // 获取wifi服务
        WifiManager wifiManager = (WifiManager) MockuaiLib.CONTEXT
                .getSystemService(Context.WIFI_SERVICE);
        // 判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = formatIpAddress(ipAddress);
        return ip;
    }

    private static String formatIpAddress(int ipAdress) {

        return (ipAdress & 0xFF) + "." + ((ipAdress >> 8) & 0xFF) + "."
                + ((ipAdress >> 16) & 0xFF) + "." + (ipAdress >> 24 & 0xFF);
    }

    /**
     * 使用GPRS时，获取本机IP地址
     *
     * @return
     */
    private static String getGPRSLocalIpAddress() {
        String ipaddress = "0";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        if (inetAddress.getHostAddress().toString().contains(".")) {
                            ipaddress = inetAddress.getHostAddress().toString();
                            break;
                        }
                    }
                }
            }
        } catch (SocketException ex) {
        }
        return ipaddress;
    }

    /**
     * 获取运营商名称
     *
     * @return
     */

    public static String getProvidersName() {
        TelephonyManager tm = (TelephonyManager) MockuaiLib.CONTEXT
                .getSystemService(Context.TELEPHONY_SERVICE);

        String providersName = "";
        // 返回唯一的用户ID;就是这张卡的编号神马的
        String IMSI = tm.getSubscriberId();
        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
        if (IMSI != null) {
            if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
                providersName = "中国移动";
            } else if (IMSI.startsWith("46001")) {
                providersName = "中国联通";
            } else if (IMSI.startsWith("46003")) {
                providersName = "中国电信";
            } else {
                providersName = IMSI;
            }
        }
        return providersName;

    }

    /**
     * 判断输入参数是否为空，并且数据格式是否只包含数字和字母
     *
     * @param value
     * @return
     */
    private static boolean isNumberOrLetterOrNotnull(String value) {
        if (value == null) {
            return false;
        } else {
            value = value.substring(0, 32);
            Pattern p = Pattern.compile("^[0-9a-zA-Z]{0,}$");
            Matcher m = p.matcher(value);
            return m.matches();
        }
    }

    /**
     * 从手机ROM的文件中获取设备号
     *
     * @return
     */
    private static String getIdFromRom() {
        String path = MockuaiLib.CONTEXT.getFilesDir() + "/mockuai.txt";
        String result = null;
        File file = new File(path);
        byte[] buffer = new byte[32];
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(file));
            in.read(buffer);
            result = new String(buffer);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 将设备号存入手机ROM文件中
     *
     * @param value
     */
    private static void putIdToRom(String value) {
        String path = MockuaiLib.CONTEXT.getFilesDir() + "/mockuai.txt";
        File file = null;
        try {
            file = new File(path);
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    throw new Exception("文件不存在，创建失败！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            out.write(value.getBytes());
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 从系统Setting中获取设备号
     *
     * @return
     */
    private static String getIdFromSystemSetting() {
        return Settings.System.getString(MockuaiLib.CONTEXT.getContentResolver(), DEVICE_ID_KEY);
    }

    /**
     * 将设备号存入Setting中
     *
     * @param value
     * @return
     */
    private static boolean putIdToSystemSetting(String value) {
        boolean isSuccess = Settings.System.putString(MockuaiLib.CONTEXT.getContentResolver(), DEVICE_ID_KEY, value);
        return isSuccess;
    }

    /**
     * 生成唯一设备号
     *
     * @return
     */
    private static String generateDeviceId() {
        return getCurrentTimeHex() + getRandomHex();
    }

    /**
     * 生成84bit的随机数
     *
     * @return
     */
    private static String getRandomHex() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int i = 0;
        while (i < 21) {
            sb.append(Integer.toHexString(random.nextInt(15)));
            i++;
        }
        return sb.toString();
    }

    /**
     * 将当前时间转换成16进制字符串
     *
     * @return
     */
    private static String getCurrentTimeHex() {
        long time = System.currentTimeMillis();
        String result = Long.toHexString(time);

        if (result.length() < TIME_CHAR_LENGTH) { //在末尾补0直至16
            StringBuilder sb = new StringBuilder(result);
            int delta = TIME_CHAR_LENGTH - result.length();
            for (int i = 0; i < delta; i++) {
                sb.insert(0, "0");
            }
            result = sb.toString();
        } else if (result.length() > TIME_CHAR_LENGTH) {   //删除末尾的字符直至为16
            StringBuilder sb = new StringBuilder(result);
            int delta = result.length() - TIME_CHAR_LENGTH;
            for (int i = 0; i < delta; i++) {
                sb.deleteCharAt(0);
            }
            result = sb.toString();
        }

        return result;
    }
}

package com.aebiz.sdk.Utils;

import android.content.Context;
import android.content.SharedPreferences;


import com.aebiz.sdk.Base.MockuaiLib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by duanyangyang on 15/3/30.
 */
public class MKStorage {
    private final static String SETTING_PREFERENCES = "setting_preferences";

    // 获取保存字符串类型信息
    public static String getStringValue(String key, String defaultValue) {
        SharedPreferences pref = MockuaiLib.CONTEXT.getSharedPreferences(SETTING_PREFERENCES, Context.MODE_PRIVATE);
        String value = pref.getString(key, defaultValue);
        return value;
    }

    public static void setStringValue(String key, String value) {
        SharedPreferences pref = MockuaiLib.CONTEXT.getSharedPreferences(SETTING_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }


    // 获取保存布尔类型信息
    public static Boolean getBooleanValue(String key, boolean defaultValue) {
        SharedPreferences pref = MockuaiLib.CONTEXT.getSharedPreferences(SETTING_PREFERENCES, Context.MODE_PRIVATE);
        Boolean value = pref.getBoolean(key, defaultValue);
        return value;
    }

    public static void setBooleanValue(String key, Boolean value) {
        SharedPreferences pref = MockuaiLib.CONTEXT.getSharedPreferences(SETTING_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }


    // 获取保存整型类型信息
    public static int getIntValue(String key, int defaultValue) {
        SharedPreferences pref = MockuaiLib.CONTEXT.getSharedPreferences(SETTING_PREFERENCES, Context.MODE_PRIVATE);
        int value = pref.getInt(key, defaultValue);
        return value;
    }

    public static void setIntValue(String key, int value) {
        SharedPreferences pref = MockuaiLib.CONTEXT.getSharedPreferences(SETTING_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.commit();
    }


    // 获取保存长整形类型信息
    public static long getLongValue(String key, long defaultValue) {
        SharedPreferences pref = MockuaiLib.CONTEXT.getSharedPreferences(SETTING_PREFERENCES, Context.MODE_PRIVATE);
        long value = pref.getLong(key, defaultValue);
        return value;
    }

    public static void setLongValue(String key, long value) {
        SharedPreferences pref = MockuaiLib.CONTEXT.getSharedPreferences(SETTING_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(key, value);
        editor.commit();
    }


    // 获取保存浮点类型信息
    public static float getFloatValue(String key, float defaultValue) {
        SharedPreferences pref = MockuaiLib.CONTEXT.getSharedPreferences(SETTING_PREFERENCES, Context.MODE_PRIVATE);
        float value = pref.getFloat(key, defaultValue);
        return value;
    }

    public static void setFloatValue(String key, float value) {
        SharedPreferences pref = MockuaiLib.CONTEXT.getSharedPreferences(SETTING_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(key, value);
        editor.commit();
    }


    /**
     * 从文件中获取对象
     *
     * @param context
     * @param key
     * @return
     */
    public static Object getObjectValue(Context context, String key) {
        String path = context.getFilesDir() + "/object/" + key + ".dat";
        Object result = null;
        File file = new File(path);
        FileInputStream in;
        try {
            in = new FileInputStream(file);
            ObjectInputStream objIn = new ObjectInputStream(in);
            result = objIn.readObject();
            objIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 保存对象至文件中
     *
     * @param context
     * @param key
     * @param value
     */
    public static void setObjectValue(Context context, String key, Object value) {
        String path = context.getFilesDir() + "/object/";
        String fileName = key + ".dat";
        File file = null;

        try {
            file = new File(path);
            if (!file.exists()) {
                if (!file.mkdir()) {
                    throw new Exception("目录不存在，创建失败！");
                }
            }
            path += fileName;
            file = new File(path);
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    throw new Exception("文件不存在，创建失败！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(value);
            objOut.flush();
            objOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

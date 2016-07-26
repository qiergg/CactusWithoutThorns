package com.aebiz.sdk.Utils;

import android.content.Context;


/**
 * Created by duanyytop on 15/4/28.
 */
public class DataUtil {

    // 获取购物车商品总数量
    public static int getCartNum(Context context) {
        return MKStorage.getIntValue("cart_num", 0);
    }

    public static void setCartNum(Context context, int cartNum) {
        MKStorage.setIntValue("cart_num", cartNum);
    }


    // 转化十六进制编码为字符串
    public static String hexToStr(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16) ^ 0xAA);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "utf-8");//UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }


}

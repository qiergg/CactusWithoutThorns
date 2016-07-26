package com.aebiz.sdk.Utils;

import java.util.List;

/**
 * Created by duanyytop on 15/4/23.
 */
public class NumberUtil {

    public static String getFormatPrice(String price) {
        try {
            String temp = String.format("%.2f", Float.valueOf(price) / 100.0);
            if ("0".equals(temp.substring(temp.length() - 1))) {
                temp = temp.substring(0, temp.length() - 1);
                if ("0".equals(temp.substring(temp.length() - 1))) {
                    temp = temp.substring(0, temp.indexOf("."));
                }
            }
            return temp;
        } catch (Exception e) {
        }
        return price;
    }


    public static String getFormatPrice(long price) {
        try {
            String temp = String.format("%.2f", (double) price / 100.0);
            if ("0".equals(temp.substring(temp.length() - 1))) {
                temp = temp.substring(0, temp.length() - 1);
                if ("0".equals(temp.substring(temp.length() - 1))) {
                    temp = temp.substring(0, temp.indexOf("."));
                }
            }
            return temp;
        } catch (Exception e) {

        }
        return String.valueOf(price);
    }

    // 将字符串转换成long型
    public static long strToLong(String str) {
        if (str.contains(".")) {
            str = str.substring(0, str.indexOf("."));
        }
        try {
            return Long.parseLong(str);
        } catch (Exception e) {

        }
        return 0l;
    }

    // 将字符串转换成int型
    public static int strToInt(String str) {
        if (str.contains(".")) {
            str = str.substring(0, str.indexOf("."));
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {

        }
        return 0;
    }

    public static String getFormatDiscount(Double discount) {
        String num = String.valueOf(discount);
        int index = num.indexOf(".");
        num = num.substring(0, index) + num.substring(index, index + 2);
        return num;
    }


    // 获取List中的最小值
    public static Long getLongMin(List<Long> list) {
        if (list != null && list.size() > 0) {
            long result = list.get(0);
            for (Long l : list) {
                result = Math.min(result, l);
            }
            return result;
        }
        return 0L;
    }

    // 获取List中的最大值
    public static Long getLongMax(List<Long> list) {
        if (list != null && list.size() > 0) {
            long result = list.get(0);
            for (Long l : list) {
                result = Math.max(result, l);
            }
            return result;
        }
        return 0L;
    }

    // 获取Double List中的最小值
    public static Double getDoubleMin(List<Double> list) {
        if (list != null && list.size() > 0) {
            Double result = list.get(0);
            for (Double l : list) {
                result = Math.min(result, l);
            }
            return result;
        }
        return 0d;
    }

    // 获取Double List中的最大值
    public static Double getDoubleMax(List<Double> list) {
        if (list != null && list.size() > 0) {
            Double result = list.get(0);
            for (Double l : list) {
                result = Math.max(result, l);
            }
            return result;
        }
        return 0d;
    }
}

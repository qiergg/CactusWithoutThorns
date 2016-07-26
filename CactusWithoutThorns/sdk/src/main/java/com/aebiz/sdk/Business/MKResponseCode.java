package com.aebiz.sdk.Business;

/**
 * Created by liutao on 15/5/14.
 */
public class MKResponseCode {

    public static String SUCCESS = "10000";

    public static String SESSION_TOKEN_MISS = "50001"; //缺少session_token参数
    public static String SESSION_TOKEN_INVALID = "50002"; //session_token无效或已过期 , 重新获取session_token

    public static String ACCESS_TOKEN_MISS = "50003"; //缺少access_token参数
    public static String ACCESS_TOKEN_INVALID = "50004";//access_token无效或已过期，重新换取最新的access_token

    public static String REFRESH_TOKEN_MISS = "50005"; //缺少refresh_token参数
    public static String REFRESH_TOKEN_INVALID = "50006";//refresh_token无效或已过期 , 需要重新登陆

}

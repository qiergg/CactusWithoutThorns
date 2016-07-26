package com.aebiz.sdk.Business;

/**
 * Created by liutao on 15/4/15.
 */
public class MKUrl {

    //    public final static String Base_Url = "http://192.168.31.180:8082";               // 内网服务端ip
    public final static String Base_Url = "http://eujing.com/jk/index.php?";                     // 线上ip
    public final static String Base_Https_Url = "";              // 线上ip

    // 商品分类列表
    public static final String CLASSIFY_GET = Base_Url + "app=goods&act=getGoodsCateList";
    // 登陆
    public static final String USER_LOGIN = Base_Url + "app=user&act=login";
    // 获取用户详情
    public static final String GET_USER_INFO = Base_Url + "/user/current_user/get";
    // 注册/重置密码发送验证码
    public static final String USER_SEND_SMS = Base_Url + "app=user&act=sendVcode";
    // 注册
    public static final String USER_REGISTER = Base_Url + "app=user&act=mobRegister";
    // 地址管理
    public static final String GET_CONSIGNEE_LIST = Base_Url + "app=user&act=getAddress";
    // 新增地址
    public static final String ADD_CONSIGNEE = Base_Url + "app=user&act=addAddress";
    // 修改地址
    public static final String UPDATE_CONSIGNEE = Base_Url + "app=user&act=editAddress";
    // 删除地址
    public static final String DELETE_CONSIGNEE = Base_Url + "app=user&act=delAddress";
    // 首页
    public static final String HOME_URL = Base_Url + "app=goods&act=getIndexData";
    // 商品列表
    public static final String ITEM_LIST_GET = Base_Url + "app=goods&act=getGoodsList";
    // 店铺列表
    public static final String STORE_LIST_GET = Base_Url + "app=goods&act=getStoreList";
    // 有镜券列表
    public static final String GET_COUPON_LIST = Base_Url + "app=user&act=getEujCouponOrders";
    // 订单相关
    public static final String ORDER_DELETE = Base_Url + "/trade/order/delete";
    public static final String ORDER_COMMENT = Base_Url + "app=user&act=evaluate";

    // 取消订单
    public static final String ORDER_CANCEL = Base_Url + "app=user&act=cancel_order";
    // 我的订单
    public static final String ORDER_LIST = Base_Url + "app=user&act=getOrderList";
    public static final String ORDER_GET = Base_Url + "app=user&act=getOrderDetail";
    public static final String ORDER_APPLY_REFUND = Base_Url + "app=user&act=order_refund";
    public static final String ORDER_RECEIPT = Base_Url + "app=user&act=confirm_order";
    public static final String ORDER_GET_STATISTIC = Base_Url + "/trade/order/statistic/get";
    // 购物车
    public static final String GET_CART_LIST = Base_Url + "app=user&act=getCartList";
    // 商品详情
    public static final String ITEM_GET = Base_Url + "app=goods&act=getGoodsDetail";
    // 加入购物车
    public static final String ITEM_ADD_CART = Base_Url + "app=user&act=addToCart";
    // 更新购物车
    public static final String UPDATE_CART = Base_Url + "app=user&act=updateCart";
    // 移除购物车数据
    public static final String REMOVE_CART = Base_Url + "app=user&act=dropCartGoods";

    // 提交订单
    public static final String ORDER_ADD = Base_Url + "app=user&act=addOrderRecord";
    // 获取支付数据
    public static final String GET_PAYMENT = Base_Url + "app=user&act=getPayUrl";
    // 充值支付数据
    public static final String UPDATE_PAYMENT = Base_Url + "app=user&act=updatePayType";
    public static final String USER_FORGET_PASSWORD = Base_Url + "app=user&act=findPwd";
    // 注销登录
    public static final String USER_LOGOUT = Base_Url + "/user/logout";


    public static final String ITEM_ADD_COLLECT = Base_Url + "";
    public static final String ITEM_DELETE_COLLECT = Base_Url + "";
    public static final String ITEM_ADD_COMMENT = Base_Url + "";
    public static final String ITEM_COMMENT_LIST = Base_Url + "";
    public static final String DELIVERY_GET = Base_Url + "";
    public static final String OPENID_LOGIN = Base_Https_Url + "";
    public static final String USER_BIND = Base_Https_Url + "";
    public static final String USER_CHANGE_PASSWORD = "";
    public static final String USER_GET_COLLECTION = Base_Url + "";
    public static final String REFRESH_ACCESS_TOKEN = Base_Https_Url + "";
    public static final String GET_SESSION_TOKEN = Base_Url + "";
    public static final String DEFAULT_CONSIGNEE = Base_Url + "";
    public static final String USER_ADD_AUTH_INFO = Base_Url + "";
    public static final String USER_GET_AUTH_INFO = Base_Url + "";

    public static final String GET_SETTLEMENT = Base_Url + "";


    public static final String GET_ORDER_NUMBER = Base_Url + "";
    public static final String GET_WEALTH_ACCOUNT = Base_Url + "";
}

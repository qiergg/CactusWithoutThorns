package com.aebiz.sdk.Business;

/**
 * Created by liutao on 15/4/15.
 */
public interface MKBusinessListener {
    /**
     * 接口请求成功，code = 10000
     *
     * @param baseObject 数据类的统一接口，根据需要强转成需要的类
     */
    public void onSuccess(MKBaseObject baseObject);

    /**
     * 接口请求成功，code != 10000
     */
    public void onFail(MKBaseObject baseObject);

    /**
     * 接口调用失败，可能错误是网络不可用，timeout等
     */
    public void onError();

}

package com.aebiz.sdk.Business;

/**
 * Created by duanyytop on 15/4/15.
 */
public class MKUserResponse extends MKBaseResponse {

    private MKUser data;

    @Override
    public MKUser getData() {
        return data;
    }

    public void setData(MKUser data) {
        this.data = data;
    }
}

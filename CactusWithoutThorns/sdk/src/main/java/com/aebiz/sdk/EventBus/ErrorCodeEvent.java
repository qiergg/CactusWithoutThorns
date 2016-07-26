package com.aebiz.sdk.EventBus;


import com.aebiz.sdk.Business.MKBaseResponse;

/**
 * Created by duanyytop on 15/6/10.
 */
public class ErrorCodeEvent {
    private MKBaseResponse response;

    public ErrorCodeEvent(MKBaseResponse response) {
        this.response = response;
    }


    public MKBaseResponse getResponse() {
        return response;
    }

    public void setResponse(MKBaseResponse response) {
        this.response = response;
    }
}

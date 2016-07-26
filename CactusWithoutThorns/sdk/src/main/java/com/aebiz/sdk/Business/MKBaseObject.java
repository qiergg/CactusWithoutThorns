package com.aebiz.sdk.Business;


import com.aebiz.sdk.Utils.JSONModel;

import java.io.Serializable;


public abstract class MKBaseObject extends JSONModel implements Serializable {

    private String code;

    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public abstract Object getData();

}

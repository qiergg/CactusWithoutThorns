package com.aebiz.sdk.Model;

/**
 * Created by duanyytop on 15/4/9.
 */
public class Unit {

    private String pid;
    private String name;
    private String nletter;

    public Unit(String pid, String name) {
        this.pid = pid;
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNletter() {
        return nletter;
    }

    public void setNletter(String nletter) {
        this.nletter = nletter;
    }
}

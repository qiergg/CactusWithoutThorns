package com.weifeng_cactus.cactuswithoutthorns.bean;

import java.util.Date;
import java.util.UUID;

/**
 * Created by maiya on 16/8/4.
 */
public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Date getDate() {

        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public Crime() {
        mId = UUID.randomUUID();
        mDate = new Date();


    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {

        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    @Override
    public String toString() {
        return mTitle;
    }
}

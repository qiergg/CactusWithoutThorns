package com.jingchen.pulltorefresh;

import java.util.ArrayList;

/**
 * Created by maiya on 16/7/28.
 */
public class ListViewData {

    private ListViewData(){

    }
    private static ListViewData ls = new ListViewData();
    public static ListViewData getInstance(){
        if(ls==null){
            return ls;
        }
        return ls;
    }
    public  ArrayList<String> getData(){
        ArrayList<String> items =  new ArrayList<String>();
        for (int i = 0; i < 30; i++)
        {
            items.add("这里是item " + i);
        }
        return items ;
    }
}

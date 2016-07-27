package com.weifeng_cactus.cactuswithoutthorns.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maiya on 16/7/27.
 */
public class ListViewItemData {

    ArrayList<String> mData ;
    public  List<String> getData(){
        if(mData==null){
            mData = new ArrayList<>();
        }
        for(int i=0;i<50;i++){
            mData.add("张伟峰======"+i);
        }
        return mData;
    }
}

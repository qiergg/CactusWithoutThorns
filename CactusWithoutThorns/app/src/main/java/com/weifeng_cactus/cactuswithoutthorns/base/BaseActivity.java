package com.weifeng_cactus.cactuswithoutthorns.base;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maiya on 16/7/26.
 */
public class BaseActivity extends Activity {
    List<Activity> mActivity ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(mActivity==null){
            mActivity = new ArrayList<>();
        }
        mActivity.add(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivity.remove(this);
    }

    public void finishAll(List<Activity> list){
        for(Activity activity:list){
            activity.finish();
        }
    }
}

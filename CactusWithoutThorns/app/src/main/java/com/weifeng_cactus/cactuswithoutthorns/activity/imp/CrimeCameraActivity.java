package com.weifeng_cactus.cactuswithoutthorns.activity.imp;

import android.support.v4.app.Fragment;

import com.weifeng_cactus.cactuswithoutthorns.activity.SingleFragmentActivity;
import com.weifeng_cactus.cactuswithoutthorns.fragment.CrimeCameraFragment;

public class CrimeCameraActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new CrimeCameraFragment();
    }
}

package com.weifeng_cactus.cactuswithoutthorns.activity.imp;

import android.support.v4.app.Fragment;

import com.weifeng_cactus.cactuswithoutthorns.activity.SingleFragmentActivity;
import com.weifeng_cactus.cactuswithoutthorns.fragment.CrimeListFragment;

public class CrimeListActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {

        return new CrimeListFragment();
    }
}

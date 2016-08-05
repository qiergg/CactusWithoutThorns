package com.weifeng_cactus.cactuswithoutthorns.activity.imp;

import android.support.v4.app.Fragment;

import com.weifeng_cactus.cactuswithoutthorns.activity.SingleFragmentActivity;
import com.weifeng_cactus.cactuswithoutthorns.fragment.CrimeFragment;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        UUID crimeID= (UUID) getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);

        return CrimeFragment.newInstance(crimeID);
    }
}

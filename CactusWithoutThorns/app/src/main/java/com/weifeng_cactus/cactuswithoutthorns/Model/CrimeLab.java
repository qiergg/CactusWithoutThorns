package com.weifeng_cactus.cactuswithoutthorns.model;

import android.content.Context;

import com.weifeng_cactus.cactuswithoutthorns.bean.Crime;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by maiya on 16/8/4.
 */
public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private final Context mAppContext;
    private ArrayList<Crime> mCrimes;


    public static CrimeLab get(Context c) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    }
    public  Crime getCrime(UUID id) {
        for (Crime c : mCrimes) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }
    public ArrayList<Crime> getCrimes() {
        return mCrimes;
    }
    private CrimeLab(Context appContext) {
        mAppContext = appContext;
        mCrimes = new ArrayList<Crime>();
//        for (int i=0;i<100;i++){
//            Crime c = new Crime();
//            c.setTitle("Crime #"+i);
//            c.setSolved(i%2==0);
//            mCrimes.add(c);
//        }
    }

    public void adddCrime(Crime c){
        mCrimes.add(c);
    }

}

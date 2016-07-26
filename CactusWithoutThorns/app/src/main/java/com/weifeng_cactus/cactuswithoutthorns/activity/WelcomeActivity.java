package com.weifeng_cactus.cactuswithoutthorns.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.weifeng_cactus.cactuswithoutthorns.R;
import com.weifeng_cactus.cactuswithoutthorns.utils.CacheUtils;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Intent intent = null;

        if(!CacheUtils.getBoolean(this,"isOpen",false)){
            intent= new Intent(this,SplishActivity.class);
            startActivity(intent);
            finish();
        }else {
            intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}

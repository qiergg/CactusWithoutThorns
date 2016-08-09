package com.weifeng_cactus.cactuswithoutthorns;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Start extends AppCompatActivity implements View.OnClickListener {

    private Button mP2;
    private Button mP1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initView();
        initListener();
    }

    private void initListener() {
        mP1.setOnClickListener(this);
        mP2.setOnClickListener(this);
    }

    private void initView() {
        mP1 = (Button) findViewById(R.id.p1);
        mP2 = (Button) findViewById(R.id.p2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.p1:

                break;
            case R.id.p2:

                break;

        }
    }
}

package com.weifeng_cactus.cactuswithoutthorns.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.aebiz.sdk.Base.BaseFragmentActivity;
import com.aebiz.sdk.UIKit.component.refresh.PullToRefreshBase;
import com.aebiz.sdk.UIKit.component.refresh.PullToRefreshListView;
import com.aebiz.sdk.UIKit.component.viewpagerindicator.PageIndicator;
import com.weifeng_cactus.cactuswithoutthorns.R;
import com.weifeng_cactus.cactuswithoutthorns.adapter.MessageAdapter;

public class MainActivity extends BaseFragmentActivity {

    private PullToRefreshListView messageListView;
    private MessageAdapter messageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


        messageListView.setMode(PullToRefreshBase.Mode.BOTH);
        messageAdapter = new MessageAdapter(this);
        messageListView.setAdapter(messageAdapter);

        messageListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                messageAdapter.new FinishRefresh(messageListView).execute();
                messageAdapter.notifyDataSetChanged();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                messageAdapter.new FinishRefresh(messageListView).execute();

                messageAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initView() {
        messageListView = (PullToRefreshListView) findViewById(R.id.coupon_list);

        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
startActivity(new Intent(MainActivity.this,PopWindow.class));
            }
        });
        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PagerIndictorDemo.class));
            }
        });
    }


}

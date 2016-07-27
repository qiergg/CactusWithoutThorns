package com.weifeng_cactus.cactuswithoutthorns.activity;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.aebiz.sdk.Base.BaseFragmentActivity;
import com.aebiz.sdk.UIKit.component.refresh.PullToRefreshBase;
import com.aebiz.sdk.UIKit.component.refresh.PullToRefreshListView;
import com.weifeng_cactus.cactuswithoutthorns.Fragment.ImageBanner;
import com.weifeng_cactus.cactuswithoutthorns.Model.IndexCarouselItem;
import com.weifeng_cactus.cactuswithoutthorns.R;
import com.weifeng_cactus.cactuswithoutthorns.adapter.MessageAdapter;

public class MainActivity extends BaseFragmentActivity {

    private PullToRefreshListView messageListView;
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageListView = (PullToRefreshListView) findViewById(R.id.coupon_list);
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

    private void generateComponentView(IndexCarouselItem[] indexCarouselItems, ViewGroup viewGroup) {
        // 广告banner
        if (indexCarouselItems.length > 0) {
            ImageBanner imageBanner = new ImageBanner(getApplicationContext());
            imageBanner.setData(indexCarouselItems);
            viewGroup.addView(imageBanner.getView(), ViewGroup.LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 167, getApplicationContext().getResources().getDisplayMetrics()));
            View view = new View(getApplicationContext());
            view.setBackgroundColor(getResources().getColor(R.color.default_separate_gray));
            viewGroup.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 1, getApplicationContext().getResources().getDisplayMetrics()));
        }

        View menuView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.view_home_header, null);
        viewGroup.addView(menuView, ViewGroup.LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, ViewGroup.LayoutParams.WRAP_CONTENT, getApplicationContext().getResources().getDisplayMetrics()));
    }

    private View perpareHeadView(IndexCarouselItem[] indexCarouselItems) {
        LinearLayout headView = (LinearLayout) this.getLayoutInflater().inflate(R.layout.headview_home, null);
        generateComponentView(indexCarouselItems, headView);
        return headView;
    }


}

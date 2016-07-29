package com.example.pull_to_refresh_view;

import com.example.pull_to_refresh_view.PullToRefreshView.OnFooterRefreshListener;
import com.example.pull_to_refresh_view.PullToRefreshView.OnHeaderRefreshListener;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;


public class TestGridView extends Activity implements OnHeaderRefreshListener,OnFooterRefreshListener{
	PullToRefreshView mPullToRefreshView;
	GridView mGridView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_gridview);
		
		mPullToRefreshView = (PullToRefreshView)findViewById(R.id.main_pull_refresh_view);
		mGridView = (GridView)findViewById(R.id.gridview);
		mGridView.setAdapter(new DataAdapter(this));
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        
	}
	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				mPullToRefreshView.onFooterRefreshComplete();
			}
		}, 1000);
	}
	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// 璁剧疆鏇存柊鏃堕棿
				//mPullToRefreshView.onHeaderRefreshComplete("鏈�繎鏇存柊:01-23 12:01");
				mPullToRefreshView.onHeaderRefreshComplete();
			}
		},1000);
		
	}
}

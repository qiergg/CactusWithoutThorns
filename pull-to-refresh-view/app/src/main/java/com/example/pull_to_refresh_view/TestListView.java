package com.example.pull_to_refresh_view;

import com.example.pull_to_refresh_view.PullToRefreshView.OnFooterRefreshListener;
import com.example.pull_to_refresh_view.PullToRefreshView.OnHeaderRefreshListener;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 瀹炵幇OnHeaderRefreshListener,OnFooterRefreshListener鎺ュ彛
 * @author Administrator
 *
 */
public class TestListView extends ListActivity implements OnHeaderRefreshListener,OnFooterRefreshListener{
	PullToRefreshView mPullToRefreshView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_listview);
		mPullToRefreshView = (PullToRefreshView)findViewById(R.id.main_pull_refresh_view);
        
		setListAdapter(new DataAdapter(this));
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Toast.makeText(this, "positon = "+position, 1000).show();
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
				//璁剧疆鏇存柊鏃堕棿
				//mPullToRefreshView.onHeaderRefreshComplete("鏈�繎鏇存柊:01-23 12:01");
				mPullToRefreshView.onHeaderRefreshComplete();
			}
		},1000);
		
	}
}

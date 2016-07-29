package com.jingchen.pulltorefresh;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.jingchen.pulltorefresh.PullToRefreshLayout.OnRefreshListener;

import java.util.ArrayList;

public class MyListener implements OnRefreshListener
{



	private  MyAdapter myAdapter;
	private Context cont1;
	private ArrayList<String> data;

	public 	MyListener(Context context){
		cont1 = context;
}
	public 	MyListener(ListViewData data1, MyAdapter adapter){

		data = data1.getData();

		myAdapter = adapter;
	}
	@Override
	public void onRefresh(final PullToRefreshLayout pullToRefreshLayout)
	{
		// 下拉刷新操作
		new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{

				// 千万别忘了告诉控件刷新完毕了哦！
				pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
			}
		}.sendEmptyMessageDelayed(0, 2000);
		Log.d("maiya",data.size()+"uuuuuuu");
				data.add(0,"zwf");
		Log.d("maiya",data.size()+"====uuuuuuu");
				myAdapter.notifyDataSetChanged();

	}

	@Override
	public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout)
	{
		// 加载操作
		new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{


				// 千万别忘了告诉控件加载完毕了哦！
				pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
			}
		}.sendEmptyMessageDelayed(0, 2000);
	}

}

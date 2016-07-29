package com.jingchen.pulltorefresh.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.jingchen.pulltorefresh.ListViewData;
import com.jingchen.pulltorefresh.MyAdapter;
import com.jingchen.pulltorefresh.MyListener;
import com.jingchen.pulltorefresh.PullToRefreshLayout;
import com.jingchen.pulltorefresh.R;

import java.util.ArrayList;

public class PullableListViewActivity extends Activity
{
	private ListView listView;
	private PullToRefreshLayout ptrl;
	private boolean isFirstIn = true;
    private ArrayList<String> items;
    private MyAdapter dapter;
    private ListViewData data;

    @Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);
		ptrl = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
		listView = (ListView) findViewById(R.id.content_view);
        initData();
		initListView();



	}

    public ArrayList<String> initData() {
        data =  ListViewData.getInstance();
        items = data.getData();

        return items;
    }

    @Override
	public void onWindowFocusChanged(boolean hasFocus)
	{

		super.onWindowFocusChanged(hasFocus);
		// 第一次进入自动刷新
		if (isFirstIn)
		{
//			ptrl.autoRefresh();
			isFirstIn = false;
		}
	}

	/**
	 * ListView初始化方法
	 */
	private void initListView()
	{

        dapter = new MyAdapter(this, initData() );

		listView.setAdapter(dapter);

        ptrl.setOnRefreshListener(new MyListener(data,dapter));
		listView.setOnItemLongClickListener(new OnItemLongClickListener()
		{

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				Toast.makeText(
						PullableListViewActivity.this,
						"LongClick on "
								+ parent.getAdapter().getItemId(position),
						Toast.LENGTH_SHORT).show();
				return true;
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
                initData();
                Log.d("maiya",initData().size()+"ppppp");

				Toast.makeText(PullableListViewActivity.this,
						" Click on " + parent.getAdapter().getItemId(position),
						Toast.LENGTH_SHORT).show();
			}
		});
	}

}

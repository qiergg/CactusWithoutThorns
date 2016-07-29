package com.xwj.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GridMenusAdapter extends BaseAdapter {

	Context mcon;

	int[] tagimg = { R.drawable.tag1, R.drawable.tag2, R.drawable.tag3,
			R.drawable.tag4,R.drawable.tag5,R.drawable.tag6};

	public GridMenusAdapter(Context mcon) {
		this.mcon = mcon;
	}

	static class ViewHolder {
		ImageView showtag;
	}

	public int getCount() {
		return tagimg.length;
	}

	public Object getItem(int arg0) {

		return null;
	}

	public long getItemId(int pos) {
		return 0;
	}

	public View getView(int pos, View view, ViewGroup arg2) {
		ViewHolder viewhold;
		if (view == null) {
			viewhold = new ViewHolder();
			view = LayoutInflater.from(mcon).inflate(R.layout.showgrid,
					null);
			viewhold.showtag= (ImageView) view.findViewById(R.id.showtag);
			view.setTag(viewhold);
		}else{
			viewhold=(ViewHolder) view.getTag();
		}
		
		viewhold.showtag.setBackgroundResource(tagimg[pos]);
		return view;
	}

}

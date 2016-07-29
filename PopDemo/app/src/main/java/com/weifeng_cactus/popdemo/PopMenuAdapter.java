package com.weifeng_cactus.popdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PopMenuAdapter extends BaseAdapter {

	ArrayList<String> itemList;
	Context mcon;
	
	public PopMenuAdapter(Context mcon, ArrayList<String> itemList){
		this.mcon=mcon;
		this.itemList=itemList;
	}
	
	@Override
	public int getCount() {
		return itemList.size();
	}

	@Override
	public Object getItem(int position) {
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mcon).inflate(
					R.layout.showpoplist, null);
			holder = new ViewHolder();
			convertView.setTag(holder);

			holder.groupItem = (TextView) convertView
					.findViewById(R.id.textview);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.groupItem.setText(itemList.get(position));

		return convertView;
	}

	private final class ViewHolder {
		TextView groupItem;
	}
}
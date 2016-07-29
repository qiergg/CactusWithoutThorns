package com.weifeng_cactus.cactuswithoutthorns.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aebiz.sdk.UIKit.component.refresh.PullToRefreshListView;
import com.weifeng_cactus.cactuswithoutthorns.Model.ListViewItemData;
import com.weifeng_cactus.cactuswithoutthorns.R;

import java.util.List;


public class MessageAdapter extends BaseAdapter {

    private Context mcontext;
    private final List<String> data;


    public MessageAdapter(Context context) {
        mcontext = context;
        ListViewItemData ls = new ListViewItemData();
        data = ls.getData();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MessageViewHolder messageViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.item_message, null);
            messageViewHolder = new MessageViewHolder();
            messageViewHolder.textView = (TextView) convertView.findViewById(R.id.item_tv);

            convertView.setTag(messageViewHolder);
        } else {
            messageViewHolder = (MessageViewHolder) convertView.getTag();
        }
        messageViewHolder.textView.setText(data.get(position));

        return convertView;
    }

    public class MessageViewHolder {

        TextView textView;
    }

  public   class FinishRefresh extends AsyncTask<Void, Void, Void> {

        private final PullToRefreshListView mView;

      public    FinishRefresh(PullToRefreshListView view) {
            mView = view;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(2000);
                    data.add(0,"YN");
                data.add("yn");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mView.onRefreshComplete();
            notifyDataSetChanged();
        }
    }



}

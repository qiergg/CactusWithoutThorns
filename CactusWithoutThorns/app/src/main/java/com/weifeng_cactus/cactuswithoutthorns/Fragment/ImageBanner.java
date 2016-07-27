package com.weifeng_cactus.cactuswithoutthorns.Fragment;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aebiz.sdk.Network.MKImage;
import com.aebiz.sdk.UIKit.component.Banner;
import com.weifeng_cactus.cactuswithoutthorns.Model.IndexCarouselItem;
import com.weifeng_cactus.cactuswithoutthorns.R;

import java.util.ArrayList;

/**
 * Created by liutao on 15/5/5.
 */
public class ImageBanner {

    private Banner mBanner;
    private LayoutInflater mInflater;
    private Context mContext;

    public ImageBanner setData(final IndexCarouselItem[] items) {
        if (items != null && items.length > 0) {
            ArrayList<View> viewList = new ArrayList<View>();
            for (final IndexCarouselItem item : items) {
                final ImageView imageView = (ImageView) mInflater.inflate(R.layout.banneritem, null);
                imageView.setImageResource(R.mipmap.placeholder_banner);
                MKImage.getInstance().getImage(item.getImage(), imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String banner_id = item.getType();
                        if (!TextUtils.isEmpty(banner_id)){
//                            Intent intent = new Intent(mContext, GoodsListActivity.class);
//                            intent.putExtra("brand_id", banner_id);
//                            intent.putExtra("title",item.getImage_name());
//                            mContext.startActivity(intent);
                        }
                    }
                });
                viewList.add(imageView);
            }

            mBanner.setAdapter(new ImageBannerAdapter(viewList));
            if (mBanner.getIndicatorView() != null) {
                mBanner.getIndicatorView().setFocusColor(mContext.getResources().getColor(R.color.banner_focus_color));
                mBanner.getIndicatorView().setUnfocusColor(mContext.getResources().getColor(R.color.banner_unfocus_color));
            }
        }
        return this;
    }

    public ImageBanner setHeight(float height, int unit) {
        mBanner.getLayoutParams().height = (int) TypedValue.applyDimension(unit, height, mContext.getResources().getDisplayMetrics());
        mBanner.invalidate();
        return this;
    }

    public ImageBanner enableAutoScroll(boolean autoScroll) {
        mBanner.setAutoScroll(autoScroll);
        return this;
    }

    public View getView() {
        return mBanner;
    }


    public ImageBanner(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mBanner = (Banner) mInflater.inflate(R.layout.component_imagebanner, null);
    }

    class ImageBannerAdapter extends PagerAdapter {

        private ArrayList<View> mViewList = new ArrayList<View>();

        public ImageBannerAdapter(ArrayList<View> list) {
            mViewList = list;
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = mViewList.get(position);
            container.removeView(view);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}

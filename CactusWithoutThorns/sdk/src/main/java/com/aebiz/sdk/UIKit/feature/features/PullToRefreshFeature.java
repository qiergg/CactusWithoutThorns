package com.aebiz.sdk.UIKit.feature.features;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Scroller;

import com.aebiz.sdk.R;

import com.aebiz.sdk.UIKit.feature.callback.ScrollCallback;
import com.aebiz.sdk.UIKit.feature.callback.TouchEventCallback;
import com.aebiz.sdk.UIKit.feature.features.internal.pullrefresh.IViewEdgeJudge;
import com.aebiz.sdk.UIKit.feature.features.internal.pullrefresh.RefreshController;

/**
 * Created with IntelliJ IDEA.
 * User: guanjie.yjf
 * Date: 14-4-18
 * Time: 下午4:02
 * To change this template use File | Settings | File Templates.
 */
public class PullToRefreshFeature extends AbsFeature<ListView> implements TouchEventCallback, IViewEdgeJudge, ScrollCallback {

    public interface OnPullToRefreshListener {

        public void onPullDownToRefresh();

        public void onPullUpToRefresh();

    }

    private RefreshController mRefreshController;
    private Scroller mScroller;
    private Context mContext;
    private boolean isAuto = false;

    public PullToRefreshFeature(Context context) {
        mScroller = new Scroller(context,
                new DecelerateInterpolator());
        mRefreshController = new RefreshController(this,
                context, mScroller);
        mContext = context;
    }

    /**
     * 设置正在下拉刷新中
     */
    public void setIsDownRefreshing() {
        if (mRefreshController != null) {
            mRefreshController.setIsDownRefreshing();
        }
    }

    /**
     * 设置正在上拉刷新中
     */
    public void setIsUpRefreshing() {
        if (mRefreshController != null) {
            mRefreshController.setIsUpRefreshing();
        }
    }

    @Override
    public void constructor(Context context, AttributeSet attrs, int defStyle) {
    }

    @Override
    public void setHost(final ListView host) {
        // TODO Auto-generated method stub
        super.setHost(host);
        mRefreshController.addFooterView();
        mRefreshController.addHeaderView();
        if (isAuto) {
            host.setOnScrollListener(new OnScrollListener() {

                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem,
                                     int visibleItemCount, int totalItemCount) {
//    				int lastItem = firstVisibleItem + visibleItemCount;
//    				if(lastItem == totalItemCount) {
//    					View lastItemView = host.getChildAt(host.getChildCount()-1);
//    					if(lastItemView == null) {
//    						return ;
//    					}
//    					//滑动到底部，并且在mRefreshController控制中的mScroller停止滑动
//    					if(host.getBottom() == lastItemView.getBottom() && mRefreshController.isScrollStop()) {
//    						mRefreshController.autoLoadingData();
//    					}
//    				}
                    if (hasArrivedBottomEdge() && mRefreshController.isScrollStop()) {
                        mRefreshController.autoLoadingData();
                    }
                }
            });
        }
    }

    @Override
    public void beforeOnTouchEvent(MotionEvent event) {
        if (mRefreshController != null)
            mRefreshController.onTouchEvent(event);
    }

    @Override
    public void afterOnTouchEvent(MotionEvent event) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void beforeDispatchTouchEvent(MotionEvent event) {

    }

    @Override
    public void afterDispatchTouchEvent(MotionEvent event) {

    }

    @Override
    public boolean hasArrivedTopEdge() {
        return mHost.getFirstVisiblePosition() == 0;
    }

    @Override
    public boolean hasArrivedBottomEdge() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
            return mHost.getLastVisiblePosition() == mHost.getCount() - 1 && mHost.getFirstVisiblePosition() != 0;
        } else {
            return mHost.getLastVisiblePosition() >= mHost.getCount() - 2 && mHost.getFirstVisiblePosition() != 0;
        }
    }

    @Override
    public void setHeadView(View view) {
        mHost.addHeaderView(view);
    }

    @Override
    public void setFooterView(View view) {
        mHost.addFooterView(view);
    }

    @Override
    public void keepTop() {
        mHost.setSelection(0);
    }

    @Override
    public void keepBottom() {
        mHost.setSelection(mHost.getCount());
    }


    public void enablePullDownToRefresh(boolean enable) {
        ImageView view = new ImageView(mContext);
        view.setImageResource(R.drawable.uik_list_logo);
        view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        enablePullDownToRefresh(enable, R.drawable.uik_arrow, 0, view);
    }

    /**
     * 设置下拉动画的颜色值
     *
     * @param color
     */
    public void setProgressBarColor(int color) {
        if (mRefreshController != null)
            mRefreshController.setProgressBarColor(color);
    }

    public void enablePullUpToRefresh(boolean enable) {
        enablePullUpToRefresh(enable, R.drawable.uik_arrow, 0, null);
    }

    public void enablePullDownToRefresh(boolean enable, int arrowDrawableId, int progressViewID, View custom) {
        if (enable) {
            mRefreshController.enablePullDownRefresh(enable, arrowDrawableId,
                    progressViewID, custom);
        } else {
            mRefreshController.enablePullDownRefresh(enable, 0, 0, null);
        }

    }

    public void enablePullUpToRefresh(boolean enable, int arrowDrawableId, int progressViewID, View custom) {

        if (mRefreshController == null) {
            return;
        }

        if (enable) {
            mRefreshController.enablePullUpRefresh(enable, arrowDrawableId,
                    progressViewID, custom);
//    		setPullUpToRefreshType(false);
        } else {
            mRefreshController.enablePullUpRefresh(enable, 0, 0, null);
        }
    }

    /**
     * 用于区别是否需要向上拖动才能刷新，如果设置为true，则代表当listview滑动到底部自动刷新，
     * 设置为false，需要在listview到达底部的时候手动向上拖动到一定位置才能刷新，默认为false
     *
     * @param isAuto
     */
    public void setPullUpToRefreshAuto(boolean isAuto) {

        mRefreshController.setPullUpRefreshAuto(isAuto);
        this.isAuto = isAuto;
        if (getHost() != null && isAuto) {
            getHost().setOnScrollListener(new OnScrollListener() {

                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem,
                                     int visibleItemCount, int totalItemCount) {
//    				int lastItem = firstVisibleItem + visibleItemCount;
//    				if(lastItem == totalItemCount) {
//    					View lastItemView = getHost().getChildAt(getHost().getChildCount()-1);
//    					if(lastItemView == null) {
//    						return ;
//    					}
//    					//滑动到底部，并且在mRefreshController控制中的mScroller停止滑动
//    					if(getHost().getBottom() == lastItemView.getBottom() && mRefreshController.isScrollStop()) {
//    						mRefreshController.autoLoadingData();
//    					}
//    				}
                    if (hasArrivedBottomEdge() && mRefreshController.isScrollStop()) {
                        mRefreshController.autoLoadingData();
                    }
                }
            });
        }
    }

    public void setPullDownRefreshTips(String[] array) {
        if (mRefreshController == null) {
            return;
        }

        mRefreshController.setDownRefreshTips(array);
    }

    public void setPullUpRefreshTips(String[] array) {
        if (mRefreshController == null) {
            return;
        }
        mRefreshController.setUpRefreshTips(array);
    }

    public void setOnPullToRefreshListener(OnPullToRefreshListener refreshListener) {
        if (mRefreshController != null)
            mRefreshController.setOnRefreshListener(refreshListener);
    }

    public void onPullRefreshComplete() {
        if (mRefreshController != null)
            mRefreshController.onRefreshComplete();
    }

    @Override
    public void beforeComputeScroll() {
        if (mScroller != null && mScroller.computeScrollOffset()) {
            if (mRefreshController != null)
                mRefreshController.onScrollerStateChanged(
                        mScroller.getCurrY(), true);
            this.getHost().postInvalidate();
        } else {
            if (mRefreshController != null)
                mRefreshController.onScrollerStateChanged(
                        mScroller.getCurrY(), false);
        }
    }


    @Override
    public void afterComputeScroll() {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeOnScrollChanged(int l, int t, int oldl, int oldt) {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterOnScrollChanged(int l, int t, int oldl, int oldt) {
        // TODO Auto-generated method stub

    }

    public void setDownRefreshFinish(boolean downRefreshLoadFinish) {
        mRefreshController.setDownRefreshFinish(downRefreshLoadFinish);
    }

    public void setUpRefreshFinish(boolean downRefreshLoadFinish) {
        mRefreshController.setUpRefreshFinish(downRefreshLoadFinish);
    }
}

package com.aebiz.sdk.Base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.aebiz.sdk.EventBus.EventBus;
import com.aebiz.sdk.R;
import com.aebiz.sdk.Utils.L;


/**
 * Created by duanyytop on 15/4/9.
 */
public class BaseFragmentActivity extends FragmentActivity {

    protected FrameLayout root;
//    private ShareDialog shareDialog;
    protected View loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        TCAgent.init(this);
//        TCAgent.setReportUncaughtExceptions(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        TCAgent.onResume(this);
        L.d("onPageStart activity: " + this.getClass().getSimpleName());
    }

    @Override
    protected void onPause() {
        super.onPause();
//        TCAgent.onPause(this);
        L.d("onPageEnd activity: " + this.getClass().getSimpleName());
    }

    /**
     * 弹出更多选择框，包括分享的部分
     *
     * @param title
     * @param content
     * @param url
     * @param imageUrl
     * @param app
     */
    public void showMoreDialogWithShare(String title, String content, String url, String imageUrl, String app) {
//        if (null == shareDialog) {
//            shareDialog = new ShareDialog(this);
//        }
//        shareDialog.showWithShare(title, content, url, imageUrl, app);
    }

    /**
     * 弹出更多选择框，没有分享的部分
     */
    public void showMoreDialogWithoutShare() {
//        if (null == shareDialog) {
//            shareDialog = new ShareDialog(this);
//        }
//        shareDialog.showWithoutShare();
    }


    /**
     * 显示加载
     *
     * @param isCover 是否覆盖界面
     */
    public void showLoading(boolean isCover) {
        if (null == root)
            root = (FrameLayout) getWindow().getDecorView().findViewById(android.R.id.content);
        if (null == loadingView)
            loadingView = LayoutInflater.from(this).inflate(R.layout.loading, null);
        loadingView.findViewById(R.id.cover).setVisibility(isCover ? View.VISIBLE : View.GONE);
        root.removeView(loadingView);
        root.addView(loadingView);
    }

    public void hideLoading() {
        if (null != root) {
            root.removeView(loadingView);
        }
    }

    public boolean isLoading() {
        if (null == root)
            return false;
        return -1 != root.indexOfChild(loadingView);
    }


    public void initEventBus(Activity activity) {
        if (!EventBus.getDefault().isRegistered(activity)) {
            EventBus.getDefault().register(activity);
        }
    }

    public void destroyEventBus(Activity activity) {
        if (EventBus.getDefault().isRegistered(activity)) {
            EventBus.getDefault().unregister(activity);
        }
    }
}

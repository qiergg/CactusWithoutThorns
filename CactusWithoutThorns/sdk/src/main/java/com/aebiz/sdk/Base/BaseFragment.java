package com.aebiz.sdk.Base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.aebiz.sdk.R;
import com.aebiz.sdk.Utils.L;


/**
 * Created by duanyytop on 15/4/20.
 */
public class BaseFragment extends Fragment {

    protected FrameLayout root;
//    private ShareDialog shareDialog;
    protected View loadingView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        TCAgent.init(getActivity());
//        TCAgent.setReportUncaughtExceptions(true);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
//            TCAgent.onPageEnd(getActivity(), this.getClass().getSimpleName());
            L.d("onPageEnd Fragment: " + this.getClass().getSimpleName());
        } else {
//            TCAgent.onPageStart(getActivity(), this.getClass().getSimpleName());
            L.d("onPageStart Fragment: " + this.getClass().getSimpleName());
        }
    }


    public void showShare(String title, String content, String url, String imageUrl, String app) {
//        if (null == shareDialog) {
//            shareDialog = new ShareDialog(getActivity());
//        }
//        shareDialog.showWithShare(title, content, url, imageUrl, app);
    }


    /**
     * 显示加载
     *
     * @param isCover 是否覆盖界面
     */
    public void showLoading(boolean isCover) {

        if (getActivity() != null && getActivity().getWindow() != null) {
            if (null == root)
                root = (FrameLayout) getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
            if (null == loadingView)
                loadingView = LayoutInflater.from(getActivity()).inflate(R.layout.loading, null);
            loadingView.findViewById(R.id.cover).setVisibility(isCover ? View.VISIBLE : View.GONE);
            root.removeView(loadingView);
            root.addView(loadingView);
        }
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
}

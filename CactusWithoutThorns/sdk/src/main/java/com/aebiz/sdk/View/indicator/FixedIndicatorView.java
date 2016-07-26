/*
Copyright 2014 shizhefei（LuckyJayce）

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.aebiz.sdk.View.indicator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;


import com.aebiz.sdk.R;
import com.aebiz.sdk.View.indicator.slidebar.ScrollBar;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author duanyytop
 * @version 1.0 主要用于固定大小来平均分配tab的情况的指示器
 * @date 2014年11月1日
 */
public class FixedIndicatorView extends LinearLayout implements Indicator {

    private IndicatorAdapter mAdapter;

    private OnItemSelectedListener onItemSelectedListener;

    private int mSelectedTabIndex = 0;

    /**
     * 平均分配tab
     */
    public static final int SPLITMETHOD_EQUALS = 0;
    /**
     * 如果内容等的话会平均分配tab,如果内容不相等的话，内容比较多的那个tab占的位置比较大
     */
    public static final int SPLITMETHOD_WEIGHT = 1;
    /**
     * 根据tab自己分配所占的空间
     */
    public static final int SPLITMETHOD_WRAP = 2;

    /**
     * 默认为平均分配
     */
    private int splitMethod = SPLITMETHOD_EQUALS;

    public FixedIndicatorView(Context context) {
        super(context);
        init();
    }

    @SuppressLint("NewApi")
    public FixedIndicatorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public FixedIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inRun = new InRun();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        inRun.stop();
    }

    @Override
    public void setAdapter(IndicatorAdapter adapter) {
        if (this.mAdapter != null) {
            this.mAdapter.unRegistDataSetObserver(dataSetObserver);
        }
        this.mAdapter = adapter;
        adapter.registDataSetObserver(dataSetObserver);
        adapter.notifyDataSetChanged();
        initNotifyOnPageScrollListener();
    }

    @Override
    public void setOnItemSelectListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @Override
    public IndicatorAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item, true);
    }

    private int mPreSelectedTabIndex = 0;

    @Override
    public void setCurrentItem(int item, boolean anim) {
        if (item < 0) {
            item = 0;
        } else if (item > mAdapter.getCount() - 1) {
            item = mAdapter.getCount() - 1;
        }
        if (mSelectedTabIndex != item) {
            mPreSelectedTabIndex = mSelectedTabIndex;
            mSelectedTabIndex = item;
            final int tabCount = mAdapter.getCount();
            /*
             * 设置当前选中的tab
			 */
            for (int i = 0; i < tabCount; i++) {
                final ViewGroup group = (ViewGroup) getChildAt(i);
                View child = group.getChildAt(0);
                final boolean isSelected = (i == item);
                //  Log.e(i + "==" + item, String.valueOf(isSelected) + ((TextView)child).getText().toString());
                child.setSelected(isSelected);
                if (isSelected) {
                    ((TextView) child).setTextColor(getResources().getColor(R.color.tab_top_select));
                } else {
                    ((TextView) child).setTextColor(getResources().getColor(R.color.tab_top_unselect));

                }
            }

            // 判断切换动画是否完成,没有完成，就停止
            if (!inRun.isFinished()) {
                inRun.stop();
            }
            // 判断scrollBar不为空，且用户没有使用viewpager切换，且之前有选择过
            if (scrollBar != null && mPositionOffset < 0.01f && mPreSelectedTabIndex >= 0 && mPreSelectedTabIndex < getChildCount()) {
                // 获取scrollBar开始移动的起始位置
                int sx = getChildAt(mPreSelectedTabIndex).getLeft();
                // 获取scrollBar结束的位置
                int ex = getChildAt(item).getLeft();
                final float pageDelta = (float) Math.abs(ex - sx) / (getChildAt(item).getWidth());
                int duration = (int) ((pageDelta + 1) * 100);
                duration = Math.min(duration, 600);
                // 开始执行scrollBar的滑动动画
                inRun.startScroll(sx, ex, duration);
            }
            // measureScrollBar(true);
        }
    }

    private void initNotifyOnPageScrollListener() {
        int tabCount;
        if (mAdapter != null && (tabCount = mAdapter.getCount()) > 1) {
            if (onPageScrollListener != null && tabCount > 1 && mSelectedTabIndex >= 0) {
                int position2 = mSelectedTabIndex + 1;
                if (position2 > tabCount - 1) {
                    position2 = mSelectedTabIndex - 1;
                }
                View view1 = getItemView(mSelectedTabIndex);
                View view2 = getItemView(position2);
                onPageScrollListener.onTransition(view1, mSelectedTabIndex, 1);
                onPageScrollListener.onTransition(view2, position2, 0);
            }
        }
    }

    @Override
    public int getCurrentItem() {
        return mSelectedTabIndex;
    }

    private List<ViewGroup> views = new LinkedList<ViewGroup>();

    private DataSetObserver dataSetObserver = new DataSetObserver() {
        @Override
        public void onChange() {
            int count = getChildCount();
            int newCount = mAdapter.getCount();
            views.clear();
            for (int i = 0; i < count && i < newCount; i++) {
                views.add((ViewGroup) getChildAt(i));
            }
            removeAllViews();
            int size = views.size();
            for (int i = 0; i < newCount; i++) {
                LinearLayout result = new LinearLayout(getContext());
                View view;
                if (i < size) {
                    View temp = views.get(i).getChildAt(0);
                    views.get(i).removeView(temp);
                    view = mAdapter.getView(i, temp, result);
                } else {
                    view = mAdapter.getView(i, null, result);
                }
                result.addView(view);
                result.setOnClickListener(onClickListener);
                result.setTag(i);
                addView(result, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
            }
            mPreSelectedTabIndex = -1;
            setCurrentItem(mSelectedTabIndex, false);
            measureTabs();
        }
    };

    public void setSplitMethod(int splitMethod) {
        this.splitMethod = splitMethod;
        measureTabs();
    }

    public int getSplitMethod() {
        return splitMethod;
    }

    private OnClickListener onClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            int i = (Integer) v.getTag();
            ViewGroup parent = (ViewGroup) v;
            setCurrentItem(i);
            if (onItemSelectedListener != null) {
                onItemSelectedListener.onItemSelected(parent.getChildAt(0), i, mPreSelectedTabIndex);
            }
        }
    };

    private int mWidthMode;

    private ScrollBar scrollBar;

    @Override
    public void setScrollBar(ScrollBar scrollBar) {
        int paddingBottom = getPaddingBottom();
        int paddingTop = getPaddingTop();
        if (this.scrollBar != null) {
            switch (this.scrollBar.getGravity()) {
                case BOTTOM_FLOAT:
                    paddingBottom = paddingBottom - scrollBar.getHeight(getHeight());
                    break;

                case TOP_FLOAT:
                    paddingTop = paddingTop - scrollBar.getHeight(getHeight());
                    break;
                default:
                    break;
            }
        }
        this.scrollBar = scrollBar;
        switch (this.scrollBar.getGravity()) {
            case BOTTOM_FLOAT:
                paddingBottom = paddingBottom + scrollBar.getHeight(getHeight());
                break;

            case TOP_FLOAT:
                paddingTop = paddingTop + scrollBar.getHeight(getHeight());
                break;
            default:
                break;
        }
        setPadding(getPaddingLeft(), paddingTop, getPaddingRight(), paddingBottom);
        // measureScrollBar(true);
    }

    private InRun inRun;

    /**
     * 用于做tab的scrollBar的滑动动画的刷新
     */
    private class InRun implements Runnable {
        private int updateTime = 20;

        private Scroller scroller;
        private final Interpolator sInterpolator = new Interpolator() {
            public float getInterpolation(float t) {
                t -= 1.0f;
                return t * t * t * t * t + 1.0f;
            }
        };

        public InRun() {
            super();
            scroller = new Scroller(getContext(), sInterpolator);
        }

        public void startScroll(int startX, int endX, int dration) {
            scroller.startScroll(startX, 0, endX - startX, 0, dration);
            ViewCompat.postInvalidateOnAnimation(FixedIndicatorView.this);
            post(this);
        }

        public boolean isFinished() {
            return scroller.isFinished();
        }

        public boolean computeScrollOffset() {
            return scroller.computeScrollOffset();
        }

        public int getCurrentX() {
            return scroller.getCurrX();
        }

        public void stop() {
            if (scroller.isFinished()) {
                scroller.abortAnimation();
            }
            removeCallbacks(this);
        }

        @Override
        public void run() {
            if (!scroller.isFinished()) {
                ViewCompat.postInvalidateOnAnimation(FixedIndicatorView.this);
                postDelayed(this, updateTime);
            }
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (scrollBar != null && scrollBar.getGravity() == ScrollBar.Gravity.CENTENT_BACKGROUND) {
            drawSlideBar(canvas);
        }
        super.dispatchDraw(canvas);
        if (scrollBar != null && scrollBar.getGravity() != ScrollBar.Gravity.CENTENT_BACKGROUND) {
            drawSlideBar(canvas);
        }
    }

    private void drawSlideBar(Canvas canvas) {
        if (mAdapter == null || scrollBar == null) {
            return;
        }
        final int count = mAdapter.getCount();
        if (count == 0) {
            return;
        }
        if (getCurrentItem() >= count) {
            setCurrentItem(count - 1);
            return;
        }
        float offsetX = 0;
        int offsetY = 0;
        switch (this.scrollBar.getGravity()) {
            case CENTENT_BACKGROUND:
                offsetY = (getHeight() - scrollBar.getHeight(getHeight())) / 2;
                break;
            case TOP:
            case TOP_FLOAT:
                offsetY = 0;
                break;
            case BOTTOM:
            case BOTTOM_FLOAT:
            default:
                offsetY = getHeight() - scrollBar.getHeight(getHeight());
                break;
        }
        View currentView = null;
        if (!inRun.isFinished() && inRun.computeScrollOffset()) {
            offsetX = inRun.getCurrentX();
            int position = 0;
            for (int i = 0; i < count; i++) {
                currentView = getChildAt(i);
                if (currentView.getLeft() <= offsetX && offsetX < currentView.getRight()) {
                    position = i;
                    break;
                }
            }
            int width = currentView.getWidth();
            int positionOffsetPixels = (int) (offsetX - currentView.getLeft());
            float positionOffset = (offsetX - currentView.getLeft()) / width;
            notifyPageScrolled(position, positionOffset, positionOffsetPixels);
        } else if (mPositionOffset - 0.0f > 0.01) {
            currentView = getChildAt(mPosition);
            int width = currentView.getWidth();
            offsetX = currentView.getLeft() + width * mPositionOffset;
            notifyPageScrolled(mPosition, mPositionOffset, mPositionOffsetPixels);
        } else {
            currentView = getChildAt(mSelectedTabIndex);
            if (currentView == null) {
                return;
            }
            offsetX = currentView.getLeft();
        }
        int tabWidth = currentView.getWidth();
        int width = scrollBar.getSlideView().getWidth();
        width = Math.min(tabWidth, width);
        offsetX += (tabWidth - width) / 2;
        int saveCount = canvas.save();
        canvas.translate(offsetX, offsetY);
        canvas.clipRect(0, 0, width, scrollBar.getHeight(getHeight())); // needed

        int preHeight = scrollBar.getSlideView().getHeight();
        int preWidth = scrollBar.getSlideView().getHeight();
        if (preHeight != scrollBar.getHeight(getHeight()) || preWidth != scrollBar.getWidth(tabWidth)) {
            measureScrollBar(true);
        }
        scrollBar.getSlideView().draw(canvas);
        canvas.restoreToCount(saveCount);
    }

    private float firstPositionOffset = 0;
    private float secondPositionOffset = 0;
    private int preSelect = -1;
    private Set<Integer> hasSelectPosition = new HashSet<Integer>(4);

    private void notifyPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset <= 0.0001f) {
            firstPositionOffset = 0;
            secondPositionOffset = 0;
        } else if (firstPositionOffset <= 0.01f) {
            firstPositionOffset = positionOffset;
        } else if (secondPositionOffset <= 0.01f) {
            secondPositionOffset = positionOffset;
        }
        if (secondPositionOffset < 0.0001f) {
            return;
        }
        if (scrollBar != null) {
            scrollBar.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
        if (onPageScrollListener != null && position + 1 <= getChildCount() - 1) {
            int unSelect = 0;
            int select = 0;
            float selectPercent;
            if (firstPositionOffset < secondPositionOffset) {
                select = position;
                unSelect = position + 1;
                selectPercent = 1 - positionOffset;
            } else {
                unSelect = position;
                select = position + 1;
                selectPercent = positionOffset;
            }
            if (preSelect != select) {
                hasSelectPosition.remove(select);
                hasSelectPosition.remove(unSelect);
                for (int i : hasSelectPosition) {
                    View view = getItemView(i);
                    onPageScrollListener.onTransition(view, i, 0);
                }
            }
            View selectView = getItemView(select);
            View unSelectView = getItemView(unSelect);
            onPageScrollListener.onTransition(selectView, select, selectPercent);
            onPageScrollListener.onTransition(unSelectView, unSelect, 1 - selectPercent);
            hasSelectPosition.add(select);
            hasSelectPosition.add(unSelect);
            preSelect = select;
        }
    }

    private void measureScrollBar(boolean needChange) {
        if (scrollBar == null)
            return;
        View view = scrollBar.getSlideView();
        if (view.isLayoutRequested() || needChange) {
            if (mAdapter != null && mAdapter.getCount() > 0 && mSelectedTabIndex >= 0 && mSelectedTabIndex < mAdapter.getCount()) {
                int widthSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), mWidthMode);
                int heightSpec;
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams != null && layoutParams.height > 0) {
                    heightSpec = MeasureSpec.makeMeasureSpec(layoutParams.height, MeasureSpec.EXACTLY);
                } else {
                    heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                }
                view.measure(widthSpec, heightSpec);
                View curr = getChildAt(mSelectedTabIndex);
                view.layout(0, 0, scrollBar.getWidth(curr.getMeasuredWidth()), scrollBar.getHeight(getHeight()));
            }
        }
    }

    /**
     * 设置tab的分配方式
     */
    private void measureTabs() {
        int count = getChildCount();
        switch (splitMethod) {
            case SPLITMETHOD_EQUALS:
                for (int i = 0; i < count; i++) {
                    View view = getChildAt(i);
                    LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                    layoutParams.width = 0;
                    layoutParams.weight = 1;
                    view.setLayoutParams(layoutParams);
                }
                break;
            case SPLITMETHOD_WRAP:
                for (int i = 0; i < count; i++) {
                    View view = getChildAt(i);
                    LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                    layoutParams.width = LayoutParams.WRAP_CONTENT;
                    layoutParams.weight = 0;
                    view.setLayoutParams(layoutParams);
                }
                break;
            case SPLITMETHOD_WEIGHT:
                for (int i = 0; i < count; i++) {
                    View view = getChildAt(i);
                    LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                    layoutParams.width = LayoutParams.WRAP_CONTENT;
                    layoutParams.weight = 1;
                    view.setLayoutParams(layoutParams);
                }
                break;
        }
    }

    @Override
    protected void measureChildren(int widthMeasureSpec, int heightMeasureSpec) {
        super.measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    // 布局过程中， 先调onMeasure计算每个child的大小， 然后调用onLayout对child进行布局，
    // onSizeChanged（）实在布局发生变化时的回调函数，间接回去调用onMeasure, onLayout函数重新布局
    // 当屏幕旋转的时候导致了 布局的size改变，故而会调用此方法。
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 重新计算浮动的view的大小
        measureScrollBar(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidthMode = MeasureSpec.getMode(widthMeasureSpec);
    }

    private int mPosition;
    private int mPositionOffsetPixels;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        this.mPosition = position;
        this.mPositionOffset = positionOffset;
        this.mPositionOffsetPixels = positionOffsetPixels;
        if (scrollBar != null) {
            ViewCompat.postInvalidateOnAnimation(this);
        } else {
            notifyPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    private float mPositionOffset;

    @Override
    public void setOnTransitionListener(OnTransitionListener onPageScrollListener) {
        this.onPageScrollListener = onPageScrollListener;
        initNotifyOnPageScrollListener();
    }

    private OnTransitionListener onPageScrollListener;

    @Override
    public View getItemView(int position) {
        if (position < 0 || position > mAdapter.getCount() - 1) {
            return null;
        }
        final ViewGroup group = (ViewGroup) getChildAt(position);
        return group.getChildAt(0);
    }

    @Override
    public OnItemSelectedListener getOnItemSelectListener() {
        return onItemSelectedListener;
    }

    @Override
    public OnTransitionListener getOnTransitionListener() {
        return onPageScrollListener;
    }

    @Override
    public int getPreSelectItem() {
        return mPreSelectedTabIndex;
    }

}

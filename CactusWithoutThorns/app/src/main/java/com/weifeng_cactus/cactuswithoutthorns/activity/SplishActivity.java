package com.weifeng_cactus.cactuswithoutthorns.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.Button;

import com.aebiz.sdk.Base.BaseFragmentActivity;
import com.weifeng_cactus.cactuswithoutthorns.R;
import com.weifeng_cactus.cactuswithoutthorns.utils.CacheUtils;

import java.util.ArrayList;
import java.util.List;

public class SplishActivity extends BaseFragmentActivity implements View.OnClickListener {

    private ViewPager splish_viewpager;
    private List<View> splashPic = new ArrayList<>();
    private Button splash_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splish);
//        SplashData spData = new SplashData(SplishActivity.this);
//        splashPic = spData.getSplashPic();
        LayoutInflater from = getLayoutInflater().from(this);
        View view1 = from.inflate(R.layout.activity_splish_item1, null);
        View view2 = from.inflate(R.layout.activity_splish_item2, null);
        View view5 = from.inflate(R.layout.activity_splish_item3, null);
        View view3 = from.inflate(R.layout.activity_splish_item4, null);
        View view4 = from.inflate(R.layout.activity_splish_item5, null);
        splashPic.add(view1);
        splashPic.add(view2);
        splashPic.add(view3);
        splashPic.add(view4);
        splashPic.add(view5);
        initView();
        initEvents();
    }

    private void initEvents() {


        splish_viewpager.setAdapter(new MyPagerAdapter());
        splash_btn.setOnClickListener(this);
        splish_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == splashPic.size() - 1) {
                    splash_btn.setVisibility(View.VISIBLE);
                } else {
                    splash_btn.setVisibility(View.GONE);
                }

                if (position == 3) {
                    setAnimalShow(splashPic.get(3));
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    private void initView() {
        splish_viewpager = (ViewPager) findViewById(R.id.splish_viewpager);
        splash_btn = (Button) findViewById(R.id.splash_btn);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.splash_btn:
                startActivity(new Intent(this, MainActivity.class));
                CacheUtils.putBoolean(this, "isOpen", true);
                finish();
                break;
            default:
                break;
        }
    }

    class MyPagerAdapter extends PagerAdapter {
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return splashPic.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View imageView = splashPic.get(position);
            container.addView(imageView);
            return imageView;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(splashPic.get(position));
        }
    }


    public void setAnimalShow(View view) {
//        AnimationUtils.
        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(2000);
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1000);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(rotateAnimation);
        view.startAnimation(animationSet);
    }


}

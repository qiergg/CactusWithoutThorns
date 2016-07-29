package com.weifeng_cactus.cactuswithoutthorns.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.weifeng_cactus.cactuswithoutthorns.R;


public class PopWindow extends AppCompatActivity implements View.OnClickListener {

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_window);
        initView();
        initEvents();
    }

    private void initEvents() {
        btn.setOnClickListener(this);
    }

    private void initView() {
        btn = (Button) findViewById(R.id.pop);
    }

    @Override
    public void onClick(View view) {
        View pop_view = LayoutInflater.from(this).inflate(R.layout.pop_view, null);



        PopupWindow popupWindow = new PopupWindow(pop_view, WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);


        ColorDrawable colorDrawable = new ColorDrawable(0xb0000000);
        popupWindow.setBackgroundDrawable(colorDrawable);

        popupWindow.setAnimationStyle(R.style.myanim);
//底部显示popupWindow

//        popupWindow.showAtLocation(btn, Gravity.BOTTOM,0,0);
       //指定位置展示popl
        popupWindow.showAsDropDown(btn,400,50);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });

        pop_view.findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PopWindow.this,"9999",Toast.LENGTH_SHORT).show();
            }
        });

    }
}

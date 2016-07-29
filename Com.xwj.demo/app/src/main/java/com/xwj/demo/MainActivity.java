package com.xwj.demo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

public class MainActivity extends Activity {
private GridView gridview; 
	
	private PopMenu popMenu;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		context = MainActivity.this;
		popMenu = new PopMenu(context);
		popMenu.addItems(new String[] { "更新", "关于", "设置", "退出"});
		// 菜单项点击监听器
		popMenu.setOnItemClickListener(popmenuItemClickListener);

		gridview=(GridView)findViewById(R.id.gridview);
		GridMenusAdapter  adapter=new GridMenusAdapter(this);
		gridview.setAdapter(adapter);
		
		Button button1 = (Button) findViewById(R.id.btnmenu);
		button1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				popMenu.showAsDropDown(v);
			}
		});

	}

	// 弹出菜单监听器
	OnItemClickListener popmenuItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			System.out.println("下拉菜单点击" + position);
			popMenu.dismiss();
		}
	};
}
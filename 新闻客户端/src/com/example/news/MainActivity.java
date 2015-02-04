package com.example.news;

import com.example.news.fragment.Home;
import com.example.news.fragment.MenuFragment;
import com.example.news.fragment.News;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class MainActivity extends SlidingFragmentActivity {

	private SlidingMenu sm;

	/**
	 * 滑动菜单的睡步骤
	 * 1.得到菜单。
	 * 2.设置菜单的出现是左边还是右边。
	 * 3.设置菜单出来的宽度。
	 * 4.设置菜单的阴影。
	 * 5.设置菜单的滑出的范围。
	 */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setBehindContentView(R.layout.menu_left);//设置滑动菜单的布局文件。
		
		setContentView(R.layout.activity_main);
		
		//一开始时显示为新闻页面。
		Fragment fragment = new Home();
		getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment,"Home").commit();
		
		sm = getSlidingMenu();
		//2.设置菜单的出现是左边还是右边。
		sm.setMode(SlidingMenu.LEFT);
		//3.设置菜单出来的宽度。
		sm.setBehindWidthRes(R.dimen.slidingmenu_offset);
		//4.设置菜单的阴影。
		sm.setShadowDrawable(R.drawable.shadow);
		//5.设置菜单的滑出的范围。TOUCHMODE_FULLSCREEN全屏,TOUCHMODE_MARGIN为边缘，TOUCHMODE_NONE不滑出,
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		
		
		MenuFragment mf = new MenuFragment();
		//获取Fragment管理器，并开启事物，并用Fragment中的内容替换BehindContentView中的Framlayout.记得最后要 commit
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_left, mf).commit();
		
	}
	
	/**
	 * 根据滑出菜来改变页面内容。
	 * @param f
	 */
	public void SwitchFragment(Fragment f){
		
		getSupportFragmentManager().beginTransaction().replace(R.id.content, f).commit();
		
		sm.toggle();//自动关闭滑出菜单。
		
	}
}









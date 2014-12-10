package com.blueice.mobilelottery.view.manager;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blueice.mobilelottery.R;

/**
 * 控制底部导航容器
 * 
 */
public class BottomManager {
	
	private static final String TAG = "MyLog";
	
	/******************* 第一步：管理对象的创建(单例模式) ***************************************************/
	// 创建一个静态实例
	private static BottomManager instrance;

	// 构造私有
	private BottomManager() {
	}

	// 提供统一的对外获取实例的入口
	public static BottomManager getInstrance() {
		if (instrance == null) {
			instrance = new BottomManager();
		}
		return instrance;
	}

	/******************* 第二步：初始化各个导航容器及相关控件设置监听 *********************************/

	/********** 底部菜单容器 **********/
	private RelativeLayout bottomMenuContainer;
	/************ 底部导航 ************/
	private LinearLayout commonBottom;// 购彩通用导航
	private LinearLayout playBottom;// 购彩

	/***************** 导航按钮 ******************/

	/************ 购彩导航底部按钮及提示信息 ************/
	private ImageButton cleanButton;
	private ImageButton addButton;

	private TextView notice;

	/************ 通用导航底部按钮 ************/
	private ImageButton homeButton;
	private ImageButton hallButton;
	private ImageButton rechargeButton;
	private ImageButton myselfButton;

	public void init(Activity activity) {
		
		commonBottom = (LinearLayout) activity.findViewById(R.id.ii_bottom_common);
		
		playBottom = (LinearLayout) activity.findViewById(R.id.ii_bottom_game);

		notice = (TextView) activity.findViewById(R.id.ii_bottom_game_choose_notice);
		
		homeButton = (ImageButton) activity.findViewById(R.id.ii_bottom_home);
		
		hallButton = (ImageButton) activity.findViewById(R.id.ii_bottom_lottery_hall);
		
		rechargeButton = (ImageButton) activity.findViewById(R.id.ii_bottom_recharge);
		
		myselfButton = (ImageButton) activity.findViewById(R.id.ii_bottom_lottery_myself);
		
		cleanButton = (ImageButton) activity.findViewById(R.id.ii_bottom_game_choose_clean);
		
		addButton = (ImageButton) activity.findViewById(R.id.ii_bottom_game_choose_ok);

		// 设置监听
		setListener();
	}
	
	
	private void initBottom(){
		commonBottom.setVisibility(View.GONE);
		playBottom.setVisibility(View.GONE);
	}

	/**
	 * 设置监听
	 */
	private void setListener() {
		
		homeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(TAG, "Home");
			}
		});
		
		hallButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(TAG, "Hall");
			}
		});
		
		rechargeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(TAG, "recharge");
			}
		});
		
		myselfButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(TAG, "myself");
			}
		});
		
		// 清空按钮
		cleanButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Log.i(TAG, "点击清空按钮");

			}
		});
		// 选好按钮
		addButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Log.i(TAG, "点击选好按钮");

			}
		});
	}
	
	
	/****************** 第三步：控制各个导航容器的显示和隐藏 *****************************************/
	/**
	 * 转换到通用导航
	 */
	public void showCommonBottom() {
		initBottom();
		commonBottom.setVisibility(View.VISIBLE);
		playBottom.setVisibility(View.INVISIBLE);
	}

	/**
	 * 转换到购彩
	 */
	public void showGameBottom() {
		initBottom();
		commonBottom.setVisibility(View.INVISIBLE);
		playBottom.setVisibility(View.VISIBLE);
	}
	
	
	public void setNotice(String str){
		notice.setText(str);
	}

}











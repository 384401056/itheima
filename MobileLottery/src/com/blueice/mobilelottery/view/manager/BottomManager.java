package com.blueice.mobilelottery.view.manager;

import java.util.Observable;
import java.util.Observer;

import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.content.Context;
import android.opengl.Visibility;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blueice.mobilelottery.ConstValue;
import com.blueice.mobilelottery.R;
import com.blueice.mobilelottery.view.BaseUI;

/**
 * 控制底部导航容器
 * 
 */
public class BottomManager implements Observer {
	
	private static final String TAG = "MyLog";
	private Context context;
	
	/******************* 第一步：管理对象的创建(单例模式) ***************************************************/
	// 创建一个静态实例
	private static BottomManager instrance;

	// 构造私有
	private BottomManager() {
	}

	// 提供统一的对外获取实例的入口
	public static BottomManager getInstance() {
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
		
		this.context = activity;
		
		bottomMenuContainer = (RelativeLayout) activity.findViewById(R.id.ii_bottom);
		
		commonBottom = (LinearLayout) activity.findViewById(R.id.ii_bottom_common);
		
		playBottom = (LinearLayout) activity.findViewById(R.id.ii_bottom_game);

		notice = (TextView) activity.findViewById(R.id.ii_bottom_game_choose_notice);
		
		homeButton = (ImageButton) activity.findViewById(R.id.ii_bottom_home);
		
		hallButton = (ImageButton) activity.findViewById(R.id.ii_bottom_lottery_hall);
		
		rechargeButton = (ImageButton) activity.findViewById(R.id.ii_bottom_recharge);
		
		myselfButton = (ImageButton) activity.findViewById(R.id.ii_bottom_lottery_myself);
		
		cleanButton = (ImageButton) activity.findViewById(R.id.ii_bottom_game_choose_clean);
		
		addButton = (ImageButton) activity.findViewById(R.id.ii_bottom_game_choose_ok);
		
		/*
		 * 购彩bottom的文字控件。
		 */
		notice = (TextView) activity.findViewById(R.id.ii_bottom_game_choose_notice);

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
		
		/**
		 *  清空按钮
		 */
		cleanButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				Log.i(TAG, "点击清空按钮");
				
				//调用CurrentUI中的clearBall()方法。
				BaseUI currentUI = MiddleManager.getInstance().getCurrentUI();
				
				if(currentUI!=null && currentUI instanceof IGameUICommonMethod){
					
					((IGameUICommonMethod)currentUI).clearBall();
					
				}
				

			}
		});
		
		/**
		 * 点击选好按钮
		 */
		addButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Log.i(TAG, "点击选好按钮");
				
				//调用CurrentUI中的Clear()方法。
				BaseUI currentUI = MiddleManager.getInstance().getCurrentUI();
				
				if(currentUI!=null && currentUI instanceof IGameUICommonMethod){
					
					((IGameUICommonMethod)currentUI).selectDone();
					
				}
				

			}
		});
	}
	
	
	/****************** 第三步：控制各个导航容器的显示和隐藏 *****************************************/
	/**
	 * 转换到通用导航
	 */
	public void showCommonBottom() {
		initBottom();
		if (bottomMenuContainer.getVisibility() == View.GONE || bottomMenuContainer.getVisibility() == View.INVISIBLE) {
			bottomMenuContainer.setVisibility(View.VISIBLE);
		}
		commonBottom.setVisibility(View.VISIBLE);
		playBottom.setVisibility(View.INVISIBLE);
	}

	/**
	 * 转换到购彩
	 */
	public void showGameBottom() {
		initBottom();
		if (bottomMenuContainer.getVisibility() == View.GONE || bottomMenuContainer.getVisibility() == View.INVISIBLE) {
			bottomMenuContainer.setVisibility(View.VISIBLE);
		}
		commonBottom.setVisibility(View.INVISIBLE);
		playBottom.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 设置提示信息。
	 * @param str
	 */
	public void setNotice(String str){
		notice.setText(str);
	}

	
	/**
	 * 改变底部导航容器显示情况
	 */
	public void changeBottomVisiblity(int type) {
		if (bottomMenuContainer.getVisibility() != type)
			bottomMenuContainer.setVisibility(type);
	}
	
	
	
	/**
	 * 根据Midlle容器的切换来变动Bottom容器。
	 */
	@Override
	public void update(Observable observable, Object data) {
		// 如果参数不为空，并且为数字。
		if (data != null && StringUtils.isNumeric(data.toString())) {
			switch (Integer.parseInt(data.toString())) {
			case ConstValue.VIEW_HELL:
			case ConstValue.VIEW_PREBET:
			case ConstValue.VIEW_LOGIN:
				showCommonBottom();
				break;
			case ConstValue.VIEW_SSQ:
				showGameBottom();
				break;
			case ConstValue.VIEW_SHOPPING:
				changeBottomVisiblity(View.GONE);
				break;
			}
		}
		
	}



}











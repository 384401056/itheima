package com.blueice.mobilelottery.view.manager;

import java.util.Observable;
import java.util.Observer;

import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blueice.mobilelottery.ConstValue;
import com.blueice.mobilelottery.R;


/**
 * 管理标题容器的工具
 * 
 */
public class TitleManager implements Observer {
	
	private static final String TAG = "MyLog";
	private static TitleManager instance = new TitleManager();
	private Context context;

	private TitleManager() {
	}

	public static TitleManager getInstance() {
		if (instance == null) {
			instance = new TitleManager();
		}
		return instance;
	}

	private RelativeLayout commonContainer;
	private RelativeLayout loginContainer;
	private RelativeLayout unLoginContainer;

	private ImageView goback;// 返回
	private ImageView help;// 帮助
	private ImageView login;// 登录
	private ImageView regist; //注册

	private TextView titleContent;// 标题内容
	private TextView userInfo;// 用户信息

	public void init(Activity activity) {
		
		this.context = activity;
		
		commonContainer = (RelativeLayout) activity
				.findViewById(R.id.ii_common_container);
		unLoginContainer = (RelativeLayout) activity
				.findViewById(R.id.ii_unlogin_title);
		loginContainer = (RelativeLayout) activity
				.findViewById(R.id.ii_login_title);

		goback = (ImageView) activity.findViewById(R.id.ii_title_goback);
		help = (ImageView) activity.findViewById(R.id.ii_title_help);
		regist = (ImageView) activity.findViewById(R.id.ii_title_regist);
		login = (ImageView) activity.findViewById(R.id.ii_title_login);

		titleContent = (TextView) activity.findViewById(R.id.ii_title_content);
		userInfo = (TextView) activity.findViewById(R.id.ii_top_user_info);

		setListener();
	}

	private void setListener() {
		goback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(TAG, "返回键");

			}
		});
		help.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i(TAG, "help");
			}
		});
		
		regist.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(TAG, "regist");
			}
		});;
		
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i(TAG, "login");
				
//				MiddleManager.getInstance().changeUI(SecondUI.class);
				
			}
		});

	}

	private void initTitle() {
		commonContainer.setVisibility(View.GONE);
		loginContainer.setVisibility(View.GONE);
		unLoginContainer.setVisibility(View.GONE);
	}

	/**
	 * 显示通用标题
	 */
	public void showCommonTitle() {
		initTitle();
		commonContainer.setVisibility(View.VISIBLE);
	}

	/**
	 * 显示未登录的标题
	 */
	public void showUnLoginTitle() {
		initTitle();
		unLoginContainer.setVisibility(View.VISIBLE);
	}

	/**
	 * 显示登陆的标题
	 */
	public void showLoginTitle() {
		initTitle();
		loginContainer.setVisibility(View.VISIBLE);

	}

	/**
	 * 设置标题文字
	 * @param title
	 */
	public void changeTitle(String title) {
		titleContent.setText(title);
	}
	
	/**
	 * 设置用户信息。
	 * @param str
	 */
	public void changeUserInfo(String str){
		userInfo.setText(str);
	}

	/**
	 * 根据Midlle容器的切换来变动Bottom容器。
	 */
	@Override
	public void update(Observable observable, Object data) {
		
		//如果参数不为空，并且为数字。
		if(data!=null && StringUtils.isNumeric(data.toString())){
			
			switch (Integer.parseInt(data.toString())) {
			case ConstValue.VIEW_HELL:
				showUnLoginTitle();
				break;
			case ConstValue.VIEW_SSQ:
				showUnLoginTitle();
				break;
			case ConstValue.VIEW_SHOPPING:
				showUnLoginTitle();
				break;
			case ConstValue.VIEW_PREBET:
				showUnLoginTitle();
				break;
			default:
				break;
			}
		}
	}
}





























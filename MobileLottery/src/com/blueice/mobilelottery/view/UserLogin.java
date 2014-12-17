package com.blueice.mobilelottery.view;

import java.util.Properties;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewDebug.FlagToString;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.blueice.mobilelottery.ConstValue;
import com.blueice.mobilelottery.GlobalParams;
import com.blueice.mobilelottery.R;
import com.blueice.mobilelottery.bean.User;
import com.blueice.mobilelottery.engine.UserEngine;
import com.blueice.mobilelottery.net.protocal.Message;
import com.blueice.mobilelottery.net.protocal.Oelement;
import com.blueice.mobilelottery.net.protocal.element.BalanceElement;
import com.blueice.mobilelottery.utils.EngineFactory;
import com.blueice.mobilelottery.utils.PromptManager;
import com.blueice.mobilelottery.view.BaseUI.MyAsynTask;
import com.blueice.mobilelottery.view.manager.MiddleManager;

public class UserLogin extends BaseUI {

	private EditText username;
	private ImageView clear;// 清空用户名
	private EditText password;
	private Button login;

	public UserLogin(Context context) {
		super(context);
	}

	@Override
	public void init() {
		showInMiddle = (ViewGroup) View.inflate(context,
				R.layout.il_user_login, null);

		username = (EditText) findViewById(R.id.ii_user_login_username);
		clear = (ImageView) findViewById(R.id.ii_clear);
		password = (EditText) findViewById(R.id.ii_user_login_password);
		login = (Button) findViewById(R.id.ii_user_login);
	}

	@Override
	public void OnResume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnPause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOnClickListener() {
		// 用户名输入框事件。
		username.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				//如果输入一个字符，显示删除图片。
				if (username.getText().toString().length() > 0) {
					clear.setVisibility(View.VISIBLE);
				}
			}
		});

		clear.setOnClickListener(this);
		login.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ii_clear:
			// 清除用户名,并隐藏删除图片。
			username.setText("");
			clear.setVisibility(View.INVISIBLE);
			break;
		case R.id.ii_user_login:
			//用户输入信息
			if(checkUserInfo()){
				
				// 登录。
				User user = new User();
				user.setUserName(username.getText().toString());
				user.setPassWord(password.getText().toString());

				// 访问网络
				new MyAsynTask<User>(){
					@Override
					protected Message doInBackground(User... params) {
						UserEngine engine = EngineFactory.Instance(UserEngine.class);
						Message loginResult = engine.login(params[0]);
						
						if(loginResult!=null){
							
							Oelement element = loginResult.getBody().getOelement();
							//如果登陆成功了。
							if(ConstValue.SUCCESS.equals(element.getErrorcode())){
								
								GlobalParams.isLogin = true;
								GlobalParams.USERNAME = params[0].getUserName();
								
								//再根据user来拿余额。
								Message balance = engine.getBalance(params[0]);
								if(balance!=null){
									element = balance.getBody().getOelement();
									//如果返回成功。
									if(ConstValue.SUCCESS.equals(element.getErrorcode())){
										
										//对全局参数中的金额赋值。
										BalanceElement balanceElement = (BalanceElement) balance.getBody().getElements().get(0);
										GlobalParams.MONEY = Float.parseFloat(balanceElement.getInvestvalues());
										
										return balance;
									}
								}
								
							}
						}
						return null;
					}

					@Override
					protected void onPreExecute() {
						PromptManager.showProgressDialog(context);
					}

					@Override
					protected void onPostExecute(Message result) {
						PromptManager.closeProgressDialog();
						
						//如果收到结果，跳转界面(前一个界面。)。
						if(result!=null){
							PromptManager.showToast(context, "登陆成功");
							MiddleManager.getInstance().goback();
							
						}else{
							PromptManager.showToast(context, "服务器忙...稍后请重试。");
						}
						
					}

				}.executeProxy(user);
				
			}

			break;
		}

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ConstValue.VIEW_LOGIN;
	}

	/**
	 * 用户输入信息格式判断
	 * 
	 * @return
	 */
	private boolean checkUserInfo() {

		return true;
	}

}

package com.blueice.wlwsd;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity implements OnClickListener {

    private Button btnLogin;
    private EditText etUserName,etPwd;
    private String mUserName,mPwd,mAuthentication;
    private ProgressDialog mProgressDialog;
    private SharedPreferences mSharedPreferences;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		
	}
	
	/**
	 * 初始化视图。
	 */
	private void initView(){
		etUserName = (EditText)findViewById(R.id.etUserName);
        etPwd = (EditText)findViewById(R.id.etPwd);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        
        btnLogin.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		
	}

}

package com.blueice.mobilesafe;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.blueice.mobilesafe.utils.PromptManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class Home extends Activity implements OnItemClickListener {

	private Context context;
	private TextView tv01;
	private GridView home_list;
	
	private MyListAdapter adapter;
	
	private static int[] names = {
		R.string.safe,R.string.callmsgsafe,R.string.app,
		R.string.taskmanager,R.string.netmanager,R.string.trojan,
		R.string.clear,R.string.atools,R.string.settings

	};
	
	private static int[] icons = {
		R.drawable.safe,R.drawable.callmsgsafe,R.drawable.app,
		R.drawable.taskmanager,R.drawable.netmanager,R.drawable.trojan,
		R.drawable.clear,R.drawable.atools,R.drawable.settings
	};

  	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		this.context = Home.this;
		GlobalParams.sp = getSharedPreferences("setting", MODE_PRIVATE);
		init();
	}



	private void init() {
		
		tv01 = (TextView) findViewById(R.id.textView1);
		home_list = (GridView) findViewById(R.id.list_home);
		
		adapter = new MyListAdapter();
		home_list.setAdapter(adapter);
		
		
		home_list.setOnItemClickListener(this);
	}
	
	/**
	 * 功能点击事件。
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		switch (position) {
		case 0:  //进入手机防盗
			showSetPwdDialog();
			break;
		case 1:  
			break;
		case 2:  
			break;
		case 3:  
			break;
		case 4:  
			break;
		case 5:  
			break;
		case 6:  
			break;
		case 7:  
			break;
		case 8:  //进入设置中心
			startSetting();
			break;
		}
	}
	

	private Button btnConfirm;
	private Button btnCancel;
	private EditText etPwd;
	private EditText etConfPwd;
	private AlertDialog dialog = null;
	
	/**
	 * 进入手机防盗管理前，打开密码输入对话框。（判断有没有设置过密码）
	 */
	private void showSetPwdDialog() {
		
		AlertDialog.Builder builder = new Builder(context);
		View view;
		
		//如果设置过密码，打开输入对话框.
		if(isSetPassword()){
			view = View.inflate(context, R.layout.dialog_enter_password, null);
			
			btnConfirm = (Button) view.findViewById(R.id.ok);
			btnCancel = (Button) view.findViewById(R.id.cancel);
			etPwd = (EditText) view.findViewById(R.id.et_setup_pwd);
			
			/**
			 * 确定按钮的事件处理。
			 */
			btnConfirm.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					String savePwd = GlobalParams.sp.getString("password", null);
					String pwd = DigestUtils.md5Hex(etPwd.getText().toString().trim());//对输入的密码进行md5加密。
					
					//检查输入的密码与已经保存的密码是否一致
					if(savePwd.equals(pwd)){
						dialog.dismiss();
						
						//进入手机安全界面。
						startSafe();
						Log.i("MyLog","进入手机安全界面");
						
					}else{
						etPwd.setText("");
						PromptManager.showToast(context, "密码错误。");
					}
				}

			});
			
			/**
			 * 取消按钮的事件处理。
			 */
			btnCancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			
//			builder.setView(view);
//			dialog = builder.show();
			dialog = builder.create();
			dialog.setView(view, 0, 0, 0, 0);//为了让低版本的显示没有黑边。
			dialog.show();
			
		}else{//如果没有设置过，打开设置密码对话框。
			
			view = View.inflate(context, R.layout.dialog_setup_password, null);
			
			btnConfirm = (Button) view.findViewById(R.id.ok);
			btnCancel = (Button) view.findViewById(R.id.cancel);
			etPwd = (EditText) view.findViewById(R.id.et_setup_pwd);
			etConfPwd = (EditText) view.findViewById(R.id.et_setup_confirm);

			/**
			 * 确定按钮的事件处理。
			 */
			btnConfirm.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String pwd = etPwd.getText().toString().trim();
					String confPwd = etConfPwd.getText().toString().trim();
					
					if(TextUtils.isEmpty(pwd) || TextUtils.isEmpty(confPwd)){
						PromptManager.showToast(context, "密码不能为空。");
					}else{
						if(pwd.equals(confPwd)){
							Editor edit = GlobalParams.sp.edit();
							edit.putString("password", DigestUtils.md5Hex(pwd.trim()));//md5加密后的密码存入SharedPreferences文件。
							edit.commit();
							dialog.dismiss();
							
							//进入手机安全界面。
							startSafe();
							Log.i("MyLog","进入手机安全界面");
							
						}else {
							PromptManager.showToast(context, "两次输入密码不一致。");
						}
					}
				}
			});
			
			/**
			 * 取消按钮的事件处理。
			 */
			btnCancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			
			
//			builder.setView(view);
//			dialog = builder.show();
			dialog = builder.create();
			dialog.setView(view, 0, 0, 0, 0);//为了让低版本的显示没有黑边。
			dialog.show();
		}

		
		
		
		/**
		 * 用户点击对话框外，或者点击返回键后执行的事件。
		 */
		builder.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				dialog.dismiss();
			}
		});
		
	}
	
	
	/**
	 * 进入手机安全页面。
	 */
	private void startSafe() {
		Intent intent = new Intent(context,SafeActivity.class);
		startActivity(intent);
	}
	
	
	/**
	 * 判断用户之前是否设置过密码。
	 * @return
	 */
	private boolean isSetPassword() {

		String pwd = GlobalParams.sp.getString("password", null);
		
		if(StringUtils.isBlank(pwd)){
			return false;
		}else{
			return true;
		}
		
	}



	/**
	 * 进入设置页面。
	 */
	private void startSetting() {
		Intent intent = new Intent(context,SettingActivity.class);
		startActivity(intent);
		
	}


	/**
	 * GridView的Adapter.
	 */
	class MyListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return names.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ViewHolder holder = null;
			
			if(convertView==null){
				
				holder = new ViewHolder();
				convertView = View.inflate(context, R.layout.list_item_home, null);
				
				holder.name = (TextView) convertView.findViewById(R.id.tv_item);
				holder.icon = (ImageView) convertView.findViewById(R.id.iv_item);
			
				convertView.setTag(holder);
				
			}else{
				
				holder = (ViewHolder) convertView.getTag();
			}
			
			
			//设置界面
			
//			convertView.setBackgroundColor(getResources().getColor(bg_color[position]));
			holder.name.setText(getResources().getString(names[position]));
			holder.icon.setImageResource(icons[position]);
			
			return convertView;
		}
		
		class ViewHolder {

			TextView name;
			ImageView icon;

		}
	
		
	}



}







































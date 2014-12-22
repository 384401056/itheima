package com.blueice.mobilesafe;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blueice.mobilesafe.db.dao.NumberAddrQueryUtils;
import com.blueice.mobilesafe.utils.NetUtils;
import com.blueice.mobilesafe.utils.PromptManager;

public class NumberQueryActivity extends Activity {

	private Context context;
	private EditText et_phone;
	private TextView tv_result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_number_query);
		this.context = NumberQueryActivity.this;
		et_phone = (EditText) findViewById(R.id.et_phone);
		tv_result = (TextView) findViewById(R.id.tv_result);
		
		/**
		 * 设置输入框的监听。
		 */
		et_phone.addTextChangedListener(new TextWatcher() {
			
			/**
			 * 当文本改变中执行。
			 */
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				if(s!=null&&s.length()>=3){
					String address = NumberAddrQueryUtils.queryNumber(s.toString());
					
					if (TextUtils.isEmpty(address)) {
						tv_result.setText("对不起，没有找到....");
					} else {
						tv_result.setText("查询结果：" + address);
					}

				}
				
			}
			
			/**
			 * 在文本改变之前执行。
			 */
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			/**
			 * 在文本改变之后执行。
			 */
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});

	}

	/**
	 * 查询按钮事件。
	 * 
	 * @param view
	 */
	public void addrNumberQuery(View view) {
		String phoneNum = et_phone.getText().toString().trim();

		if (!TextUtils.isEmpty(phoneNum)) {

			// if(NetUtils.checkNet(context)){
			// 1.网络查询
			// }

			// 2.本地数据库查询。
			String address = NumberAddrQueryUtils.queryNumber(phoneNum);

			if (TextUtils.isEmpty(address)) {
				tv_result.setText("对不起，没有找到....");
			} else {
				tv_result.setText("查询结果：" + address);
			}

		} else {
			PromptManager.showToast(context, "号码不能为空.");
		}

	}
}

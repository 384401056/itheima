package com.example.nfctestdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InputTextActivity extends Activity implements OnClickListener{

	private Button btn_ok;
	private EditText et_input;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input_text);
		
		btn_ok = (Button) findViewById(R.id.btn_ok);
		et_input = (EditText) findViewById(R.id.et_input);
		
		btn_ok.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
		if("".equals(et_input.getText().toString())){
			Toast.makeText(this, "输入不能为空", Toast.LENGTH_LONG).show();
		}else{
			
			Intent intent = new Intent();
			intent.putExtra("text", et_input.getText().toString());
			setResult(1, intent);//返回上一个Activity.
			finish();
		}
		
	}
	
	
	
}

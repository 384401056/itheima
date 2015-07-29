package com.example.nfctestdemo;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

/**
 * 读取和写入NFDE格式的数据。
 *
 */

public class MainActivity extends Activity {

	private Button btn_write;
	private TextView tv_write;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btn_write = (Button) findViewById(R.id.btn_write);
		tv_write = (TextView) findViewById(R.id.tv_write);
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		Intent myIntent = new Intent(this,ShowNFCTextActivity.class);
		myIntent.putExtras(intent);//将NFC扫描后得到的数据传给下一个Acitvity
		myIntent.setAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
		startActivity(myIntent);
	}
}

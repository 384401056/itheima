package com.example.nfctestdemo;

import com.example.nfctestdemo.utils.TextRecord;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 读取和写入NFDE格式的数据。
 *
 */

public class MainActivity extends Activity implements OnClickListener{

	private Button btn_write;
	private TextView tv_write;
	String mText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btn_write = (Button) findViewById(R.id.btn_write);
		tv_write = (TextView) findViewById(R.id.tv_write);
		btn_write.setOnClickListener(this);
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		
		//读取
		if (mText == null) {
			Intent myIntent = new Intent(this, ShowNFCTextActivity.class);
			myIntent.putExtras(intent);// 将NFC扫描后得到的数据传给下一个Acitvity
			myIntent.setAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
			startActivity(myIntent);
		//写入
		}else{
			Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			NdefMessage message = new NdefMessage(new NdefRecord[]{TextRecord.createTextRecord(mText)});//创建一个Record数组。生成message
			writeTag(message, tag);//写入
			Toast.makeText(this, "写入成功", Toast.LENGTH_LONG).show();
			mText = null;
			tv_write.setText("");
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == 1 && resultCode==1){
			
			mText = data.getStringExtra("text");
			tv_write.setText("将要写入的数据: "+mText);
			
		}
	}
	
	/**
	 * 将数据写入标签中
	 * @param msg
	 * @param tag
	 * @return
	 */
	private boolean writeTag(NdefMessage msg,Tag tag){
		
		try {
			
			Ndef ndef = Ndef.get(tag);
			ndef.connect();
			ndef.writeNdefMessage(msg);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return false;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, InputTextActivity.class);
		startActivityForResult(intent, 1);
	}
}



























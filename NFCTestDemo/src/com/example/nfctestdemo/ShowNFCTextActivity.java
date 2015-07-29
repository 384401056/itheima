package com.example.nfctestdemo;

import com.example.nfctestdemo.utils.TextRecord;

import android.R.integer;
import android.app.Activity;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;

public class ShowNFCTextActivity extends Activity {

	private TextView tv_context;
	private TextView tv_text;
	private Tag myTag;
	private String mTagText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_nfctext);
		
		tv_context = (TextView) findViewById(R.id.tv_context);
		tv_text = (TextView) findViewById(R.id.tv_text);
		
		myTag = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
		
		Ndef ndef = Ndef.get(myTag);
		
		mTagText = "标签类型:"+ndef.getType()+"\n最大容量:"+ndef.getMaxSize()+"bytes\n";
		
		readNFCTag();
		
		tv_context.setText(mTagText);
	}

	private void readNFCTag() {
		//获取NDEF中的数据。
		Parcelable[] arrayMsg = getIntent().getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
		
		NdefMessage message[] = null;
		int contentSize = 0;
		if (arrayMsg != null) {
			message = new NdefMessage[arrayMsg.length];
			for (int i = 0; i < arrayMsg.length; i++) {
				message[i] = (NdefMessage) arrayMsg[i];
				contentSize += message[i].toByteArray().length;
			}
		}

		try {
			if (message != null) {
				NdefRecord record = message[0].getRecords()[0];
				// 通过TextRecord类来解析record
				TextRecord textRecord = TextRecord.parsef(record);
				mTagText += "已经使用："+contentSize + " bytes\n";
				tv_text.setText(textRecord.getmText());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}



















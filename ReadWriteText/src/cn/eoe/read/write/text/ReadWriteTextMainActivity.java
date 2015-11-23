package cn.eoe.read.write.text;

import java.nio.charset.Charset;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ReadWriteTextMainActivity extends Activity {
	private TextView mInputText;
	private String mText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read_write_text_main);

		mInputText = (TextView) findViewById(R.id.textview_input_text);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1 && resultCode == 1) {
			mText = data.getStringExtra("text");
			mInputText.setText(mText);
		}
	}

	public void onNewIntent(Intent intent) {
		// read nfc text
		if (mText == null) {
			Intent myIntent = new Intent(this, ShowNFCTagContentActivity.class);
			myIntent.putExtras(intent);
			myIntent.setAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
			startActivity(myIntent);

		} else // write nfc text
		{
			Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			NdefMessage ndefMessage = new NdefMessage(
					new NdefRecord[] { createTextRecord(mText) });
			writeTag(ndefMessage, tag);
		}
	}

	public NdefRecord createTextRecord(String text) {
		byte[] langBytes = Locale.CHINA.getLanguage().getBytes(
				Charset.forName("US-ASCII"));
		Charset utfEncoding = Charset.forName("UTF-8");
		byte[] textBytes = text.getBytes(utfEncoding);
		int utfBit = 0;
		char status = (char) (utfBit + langBytes.length);

		byte[] data = new byte[1 + langBytes.length + textBytes.length];
		data[0] = (byte) status;
		System.arraycopy(langBytes, 0, data, 1, langBytes.length);
		System.arraycopy(textBytes, 0, data, 1 + langBytes.length,
				textBytes.length);

		NdefRecord ndefRecord = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,
				NdefRecord.RTD_TEXT, new byte[0], data);
		return ndefRecord;
	}

	boolean writeTag(NdefMessage ndefMessage, Tag tag) {
		try {
			Ndef ndef = Ndef.get(tag);
			ndef.connect();
			ndef.writeNdefMessage(ndefMessage);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return false;

	}
	
	public void onClick_InputText(View view)
	{
		Intent intent = new Intent(this, InputTextActivity.class);
		startActivityForResult(intent, 1);
	}

}

package cn.eoe.mifareultralight;

import java.nio.charset.Charset;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Toast;

public class MifareultralightMainActivity extends Activity {

	private CheckBox mWriteData;
	private NfcAdapter mNfcAdapter;
	private PendingIntent mPendingIntent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_mifareultralight);
		mWriteData = (CheckBox) findViewById(R.id.checkbox_write);

		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
				getClass()), 0);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mNfcAdapter != null) {
			mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, null,null);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mNfcAdapter != null) {
			mNfcAdapter.disableForegroundDispatch(this);
		}
	}
    @Override
    public void onNewIntent(Intent intent)
    {
    	Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
    	
    	String[] techList =tag.getTechList();
    	
    	boolean haveMifareUltralight = false;
    	for(String tech: techList)
    	{
    		if(tech.indexOf("MifareUltralight") >= 0)
    		{
    			haveMifareUltralight = true;
    			break;
    					
    		}
    	}
    	if(!haveMifareUltralight)
    	{
    		Toast.makeText(this, "��֧��MifareUltralight���ݸ�ʽ", Toast.LENGTH_LONG).show();
    		return;
    	}
    	if(mWriteData.isChecked())
    	{
    		writeTag(tag);
    	}
    	else {
			String data = readTag(tag);
			if(data != null)
				Toast.makeText(this, data, Toast.LENGTH_LONG).show();
		}
    	
    	
    }
	public void writeTag(Tag tag) {
		MifareUltralight ultralight = MifareUltralight.get(tag);
		try {
			ultralight.connect();
			ultralight.writePage(4, "�й�".getBytes(Charset.forName("GB2312")));
			ultralight.writePage(5, "����".getBytes(Charset.forName("GB2312")));
			ultralight.writePage(6, "Ӣ��".getBytes(Charset.forName("GB2312")));
			ultralight.writePage(7, "����".getBytes(Charset.forName("GB2312")));

			Toast.makeText(this, "�ɹ�д��MifareUltralight��ʽ����!", Toast.LENGTH_LONG)
					.show();
		} catch (Exception e) {
			// TODO: handle exception
		}
		finally
		{
			try
			{
				ultralight.close();
			}
			catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	public String readTag(Tag tag)
	{
		MifareUltralight ultralight = MifareUltralight.get(tag);
		
		try
		{
			ultralight.connect();
			byte[] data = ultralight.readPages(4);
			return new String(data, Charset.forName("GB2312"));
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		finally
		{
			try
			{
				ultralight.close();
			}
			catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}

}

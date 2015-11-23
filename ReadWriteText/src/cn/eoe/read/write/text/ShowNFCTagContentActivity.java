package cn.eoe.read.write.text;

import cn.eoe.read.write.text.library.TextRecord;
import android.app.Activity;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;

public class ShowNFCTagContentActivity extends Activity {

	private TextView mTagContent;
	private Tag mDetectedTag;
	private String mTagText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_nfctag_content);
		mTagContent = (TextView) findViewById(R.id.textview_tag_content);

		mDetectedTag = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);

		Ndef ndef = Ndef.get(mDetectedTag);

		mTagText = ndef.getType() + "\nmax size:" + ndef.getMaxSize()
				+ "bytes\n\n";

		readNFCTag();

		mTagContent.setText(mTagText);
	}

	private void readNFCTag() {
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
			Parcelable[] rawMsgs = getIntent().getParcelableArrayExtra(
					NfcAdapter.EXTRA_NDEF_MESSAGES);
			NdefMessage msgs[] = null;
			int contentSize = 0;
			if (rawMsgs != null) {
				msgs = new NdefMessage[rawMsgs.length];
				for (int i = 0; i < rawMsgs.length; i++) {
					msgs[i] = (NdefMessage) rawMsgs[i];
					contentSize += msgs[i].toByteArray().length;
				}
			}
			try {
				if (msgs != null) {
					NdefRecord record = msgs[0].getRecords()[0];
					TextRecord textRecord = TextRecord.parse(record);
					mTagText += textRecord.getText() + "\n\ntext\n"
							+ contentSize + " bytes";
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}

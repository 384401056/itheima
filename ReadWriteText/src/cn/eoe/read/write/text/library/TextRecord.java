package cn.eoe.read.write.text.library;

import java.util.Arrays;

import android.nfc.NdefRecord;

public class TextRecord {
	private final String mText;

	private TextRecord(String text) {
		mText = text;
	}

	public String getText() {
		return mText;
	}

	public static TextRecord parse(NdefRecord ndefRecord) {
		// verify tnf
		if (ndefRecord.getTnf() != NdefRecord.TNF_WELL_KNOWN) {
			return null;
		}
		if (!Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
			return null;
		}

		try {
			byte[] payload = ndefRecord.getPayload();

			String textEncoding = ((payload[0] & 0x80) == 0) ? "UTF-8"
					: "UTF-16";

			int languageCodeLength = payload[0] & 0x3f;

			String languageCode = new String(payload, 1, languageCodeLength,
					"US-ASCII");

			String text = new String(payload, languageCodeLength + 1,
					payload.length - languageCodeLength - 1, textEncoding);

			return new TextRecord(text);

		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}

}

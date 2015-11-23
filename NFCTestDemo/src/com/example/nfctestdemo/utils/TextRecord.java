package com.example.nfctestdemo.utils;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Locale;

import android.nfc.NdefRecord;

public class TextRecord {
	
	private final String mText;
	
	private TextRecord(String text) {
		mText = text;
	}

	
	public static TextRecord parsef(NdefRecord ndefRecord){
		
		//判断NdefRecord对象中存储的是否是NDEF格式数据。
		if(ndefRecord.getTnf()!=NdefRecord.TNF_WELL_KNOWN){
			return null;
		}
		//可变长度必须是NdefRecord.RTD_TEXT.
		if(!Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)){
			return null;
		}
		
		try {
			
			byte[] data = ndefRecord.getPayload();//获取NFC数据。
			
			//取出数据data[0]中的第7位，判断出文本数据的编码格式。
			String textEcoding = ((data[0] & 0x80)==0)?"UTF-8":"UTF-16";
			
			//data[0]中的0-5位.得到语言编码字节长度。如:en-US,fr-CA.编码格式是US-ASCII.
			int languageLenght = (data[0] & 0x3F);
			
			//取出语言编码.data[1]后直到第n个字节。（可以不用解析）.
			String languageCode = new String(data, 1, languageLenght, "US-ASCII");
			
			//取出文本数据。
			String text = new String(data,languageLenght+1,data.length-languageLenght-1,textEcoding);
			
			return new TextRecord(text);//返回一个TextRecord对象。
			
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}
	
	
	public static NdefRecord createTextRecord(String text){
		
		//1.生成语言码
		byte[] languageByte = Locale.CHINA.getLanguage().getBytes(Charset.forName("US-ASCII"));
		//2.生成状态码
		int utfBit = 0;
		char status = (char) (utfBit+languageByte.length);//0+语言编码长度
		//3.生成文本数据
		byte[] textByte = text.getBytes(Charset.forName("UTF-8"));
		
		byte[] data = new byte[languageByte.length+textByte.length+1];

		data[0] = (byte) status;//设置状态码
		System.arraycopy(languageByte, 0, data, 1, languageByte.length);//设置语言编码
		System.arraycopy(textByte, 0, data, 1+languageByte.length, textByte.length);//设置文本数据
		
		//将data生成一个NdefRecord对象.第三个参数就是一个Record的序号。
		NdefRecord ndefRecord = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], data);
		
		return ndefRecord;
	}
	
	
	
	public String getmText() {
		return mText;
	}
	
	
	
	

}

package com.blueice.mobilelottery.net.protocal;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.xmlpull.v1.XmlSerializer;

import com.blueice.mobilelottery.ConstValue;
import com.blueice.mobilelottery.utils.DES;

import android.util.Xml;

public class Body {
	
	private List<Element> elements = new ArrayList<Element>();

	public void serializerBody(XmlSerializer serializer){

		try{
			
		serializer.startTag(null, "body");
			serializer.startTag(null, "elements");
		
				for(Element item:elements){
					item.serializerElement(serializer);
				}
			
			serializer.endTag(null, "elements");
		serializer.endTag(null, "body");
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public String getWholeBody(){
		
		XmlSerializer bodyserializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			
			bodyserializer.setOutput(writer);
			serializerBody(bodyserializer);
			
			/*
			 * 如果没有调用 serializer.endDocument()的话 serializerBody就不会返回数据;所以要加上bodyserializer.flush()
			 * 因为在原码中，serializer.endDocument()最后也是调用了bodyserializer.flush()才会有输出。
			 */
			bodyserializer.flush(); 
			
			return writer.toString();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 反回body中除<body>标签之外的加密码数据。
	 * @return
	 */
	public String getBodyInsideDESInfo(){
		
		//截取body标签内的数据。
		String wholeBody = getWholeBody();
		String insideInfo = StringUtils.substringBetween(wholeBody, "<body>", "</body>");
		
		//加密.
		DES des = new DES();
		String authcode = des.authcode(insideInfo, "DECODE", ConstValue.DES_PASSWORD);
		return authcode;


	}
	
	
	public List<Element> getElements() {
		return elements;
	}
}

package com.blueice.mobilelottery.net.protocal;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import com.blueice.mobilelottery.ConstValue;

import android.util.Xml;


public class Message {

	private Header header = new Header();
	private Body body = new Body();
	
	
	public void serializerMessage(XmlSerializer serializer){
		try{
			
		serializer.startTag(null, "message");
		serializer.attribute(null, "version", "1.0");
		header.serializerHeader(serializer, "1111");
		body.serializerBody(serializer);
		serializer.endTag(null, "message");
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	
	public String getXml(){
		
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		
		try {
			
			serializer.setOutput(writer);
			
			serializer.startDocument(ConstValue.ENCODING, null);
			
			this.serializerMessage(serializer);
			
			serializer.endDocument();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		
		return null;
	}
	
	
}

















package com.blueice.mobilelottery.net.protocal;

import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import com.blueice.mobilelottery.ConstValue;

import android.util.Xml;


public class Message {

	private Header header = new Header();
	private Body body = new Body();
	
	/**
	 * 序化Message.
	 * @param serializer XmlSerializer对象。
	 */
	public void serializerMessage(XmlSerializer serializer){
		try{
			
		serializer.startTag(null, "message");
		serializer.attribute(null, "version", "1.0");
		
		//明文的Body还没设置，此处设置为 1111
		header.serializerHeader(serializer, body.getWholeBody());

		//加密后的body数据.加密码数据是不包含body标签的。
		serializer.startTag(null, "body");
		serializer.text(body.getBodyInsideDESInfo());
		serializer.endTag(null, "body");
		
		
		serializer.endTag(null, "message");
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * 生成请求的XML
	 * @param element body中的Elemnet。
	 * @return
	 */
	public String getXml(Element element){
		
		//如果参数为null，抛出异常。
		if(element == null){
			throw new IllegalArgumentException("element is null");
		}
		
		//从Element的方法中获取操作码。
		header.getTransactiontype().setTagValue(element.getTransactionType());
		//获取用户名。
//		header.getUsername().setTagValue("");
		
		//在body中添加操作元素。
		body.getElements().add(element);
		
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		
		try {
			
			serializer.setOutput(writer);
			serializer.startDocument(ConstValue.ENCODING, null);
			serializerMessage(serializer);
			serializer.endDocument();
			
			return writer.toString();
			
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}
		
		return null;
	}


	public Header getHeader() {
		return header;
	}


	public Body getBody() {
		return body;
	}
	
	
	
	
}


/**
 * 要处理的问题。
 * 
 * 1.明文的Body还没设置，此处设置为 1111 (已经处理)
 * 2.请求的标识和内容需要设置。 (已经处理)
 * 3.加密body中的数据。 (已经处理)
 * 
 * 4.请求Element要变为通用。(已经处理)
 */














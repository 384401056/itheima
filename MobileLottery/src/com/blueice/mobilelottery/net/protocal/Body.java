package com.blueice.mobilelottery.net.protocal;

import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

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
}

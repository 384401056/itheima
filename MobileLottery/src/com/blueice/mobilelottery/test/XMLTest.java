package com.blueice.mobilelottery.test;

import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import com.blueice.mobilelottery.ConstValue;
import com.blueice.mobilelottery.net.protocal.Element;
import com.blueice.mobilelottery.net.protocal.Header;

import android.test.AndroidTestCase;
import android.util.Log;
import android.util.Xml;

/**
 * 生成XML文件的测试类。
 * 
 * @author ServerAdmin
 *
 */
public class XMLTest extends AndroidTestCase {

	public void createXML() {

		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		
		Header header = new Header();
		header.getTransactiontype().setTagValue("10040");
		header.getUsername().setTagValue("gaoyanbin");
		
		Element element = new Element();
		element.getLotteryid().setTagValue("20140312");
		
		try {

			serializer.setOutput(writer);
			serializer.startDocument(ConstValue.ENCODING, null);

				serializer.startTag(null, "message");
				
					header.serializerHeader(serializer, "1111111");
	
					serializer.startTag(null, "body");
					serializer.startTag(null, "elements");
					element.serializerElement(serializer);
					serializer.endTag(null, "elements");
					serializer.endTag(null, "body");
				
				serializer.endTag(null, "message");
				
			serializer.endDocument();

		} catch (Exception e) {
			e.printStackTrace();
		}

		
		Log.i("MyLog", writer.toString());
		
	}

}

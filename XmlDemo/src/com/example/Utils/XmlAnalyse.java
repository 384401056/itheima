package com.example.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.os.Environment;
import android.util.Xml;

import com.example.domain.Person;

public class XmlAnalyse {


	/**
	 * 将List<Person>中的信息写入本地XML文件中。
	 */
	public static void WriteXMLToLocal() {

		List<Person> list = getPersons();
		// 获取XML文件序列化对象。
		XmlSerializer serializer = Xml.newSerializer();

		try {

			// 创建输出文件流。
			File file = new File(Environment.getExternalStorageDirectory(),
					"persons.xml");
			FileOutputStream fos = new FileOutputStream(file);

			// 指定序列化对象的输出位置。
			serializer.setOutput(fos, "utf-8");

			/*
			 * 开始写XML文档，相当于开始写。<?xml version="1.0" encoding="UTF-8"
			 * standalone="true"> 每个startDocument要对应一个endDocument.
			 */
			serializer.startDocument("UTF-8", true);

			// 开始定XML中的元素。starTag是开始标签，每个startTag要对应一个endTag.
			serializer.startTag(null, "persons");

			for (Person persion : list) {

				serializer.startTag(null, "person");
				serializer.attribute(null, "id",String.valueOf(persion.getId())); // 属性。

				serializer.startTag(null, "name");
				serializer.text(persion.getName()); // name值。
				serializer.endTag(null, "name");

				serializer.startTag(null, "age");
				serializer.text(String.valueOf(persion.getAge())); // age值。
				serializer.endTag(null, "age");

				serializer.endTag(null, "person");
			}

			serializer.endTag(null, "persons");

			serializer.endDocument();

		} catch (Exception e) {
			e.printStackTrace();
		}
 
	}

	/**
	 * 将SD卡中的XML文件解析为List<Person>返回。
	 * 
	 * @return List<Person>
	 */
	public static List<Person> PullParesXMLFromLocal() {

		List<Person> resList = null;
		
		Person person = null;

		try {
			
			// 创建输入文件流。
			File file = new File(Environment.getExternalStorageDirectory(),"persons.xml");
			FileInputStream fis = new FileInputStream(file);

			// 获取Pull解析器对象。
			XmlPullParser parser = Xml.newPullParser();
			//指定解析的文件和编码集,并开始解析。
			parser.setInput(fis, "UTF-8");

			int eventType = parser.getEventType(); //获取当前的解析事件。
			String tagName = null;
			
			
			//如果当前事件不是解析到文件尾，则开始循环生成List<Person>.
			while(eventType != XmlPullParser.START_DOCUMENT){
				
				//获取当前解析到的标签名,用来判断解析到了哪个节点。
				tagName = parser.getName();
				
				switch (eventType) {
			
				//如果解析到元素开始。
				case XmlPullParser.START_TAG:
					
					//当解析到 persons 元素开始符时，创建List<Person>对象。
					if("persons".equals(tagName)){
						resList = new ArrayList<Person>();
						
					//当解析到 person 元素开始符时，创建person对象并获取属性id的值，赋值给person对象。	
					}else if("person".equals(tagName)){
						
						person = new Person();
						String id = parser.getAttributeValue(null, "id");
						
						person.setId(Integer.parseInt(id));
						
					//当解析到 name 元素开始符时，获取name元素的值，赋值给person对象。	
					}else if("name".equals(tagName)){
						
						person.setName(parser.nextText());
						
					//当解析到 age 元素开始符时，获取age元素的值，赋值给person对象。		
					}else if("age".equals(tagName)){
						
						String age = parser.nextText();
						person.setAge(Integer.valueOf(age));
						
					}
					
					break;

				//如果解析到元素结束。
				case XmlPullParser.END_TAG:

					//当解析到 person 的结束符时。将person对象加入List.
					if("person".equals(tagName)){
						
						resList.add(person);
						
					}
					break;
					
				default:
					break;
				}
				
				eventType = parser.next();
			}
			
			//返回生成的List.
			return resList;
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private static List<Person> getPersons() {

		List<Person> persions = new ArrayList<Person>();
		for (int i = 1; i < 11; i++) {

			Person p = new Person(i, "zhang" + i, 20 + i);

			persions.add(p);
		}

		return persions;
	}

}

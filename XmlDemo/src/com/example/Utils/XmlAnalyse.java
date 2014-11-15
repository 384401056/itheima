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
	 * ��List<Person>�е���Ϣд�뱾��XML�ļ��С�
	 */
	public static void WriteXMLToLocal() {

		List<Person> list = getPersons();
		// ��ȡXML�ļ����л�����
		XmlSerializer serializer = Xml.newSerializer();

		try {

			// ��������ļ�����
			File file = new File(Environment.getExternalStorageDirectory(),
					"persons.xml");
			FileOutputStream fos = new FileOutputStream(file);

			// ָ�����л���������λ�á�
			serializer.setOutput(fos, "utf-8");

			/*
			 * ��ʼ��XML�ĵ����൱�ڿ�ʼд��<?xml version="1.0" encoding="UTF-8"
			 * standalone="true"> ÿ��startDocumentҪ��Ӧһ��endDocument.
			 */
			serializer.startDocument("UTF-8", true);

			// ��ʼ��XML�е�Ԫ�ء�starTag�ǿ�ʼ��ǩ��ÿ��startTagҪ��Ӧһ��endTag.
			serializer.startTag(null, "persons");

			for (Person persion : list) {

				serializer.startTag(null, "person");
				serializer.attribute(null, "id",
						String.valueOf(persion.getId())); // ���ԡ�

				serializer.startTag(null, "name");
				serializer.text(persion.getName()); // nameֵ��
				serializer.endTag(null, "name");

				serializer.startTag(null, "age");
				serializer.text(String.valueOf(persion.getAge())); // ageֵ��
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
	 * ��SD���е�XML�ļ�����ΪList<Person>���ء�
	 * 
	 * @return List<Person>
	 */
	public static List<Person> PullParesXMLFromLocal() {

		List<Person> resList = null;
		
		Person person = null;

		try {
			
			// ���������ļ�����
			File file = new File(Environment.getExternalStorageDirectory(),"persons.xml");
			FileInputStream fis = new FileInputStream(file);

			// ��ȡPull����������
			XmlPullParser parser = Xml.newPullParser();
			//ָ���������ļ��ͱ��뼯,����ʼ������
			parser.setInput(fis, "UTF-8");

			int eventType = parser.getEventType(); //��ȡ��ǰ�Ľ����¼���
			String tagName = null;
			
			
			//�����ǰ�¼����ǽ������ļ�β����ʼѭ������List<Person>.
			while(eventType != XmlPullParser.END_DOCUMENT){
				
				//��ȡ��ǰ�������ı�ǩ��,�����жϽ��������ĸ��ڵ㡣
				tagName = parser.getName();
				
				switch (eventType) {
				
				//���������Ԫ�ؿ�ʼ��
				case XmlPullParser.START_TAG:
					
					//�������� persons Ԫ�ؿ�ʼ��ʱ������List<Person>����
					if("persons".equals(tagName)){
						resList = new ArrayList<Person>();
						
					//�������� person Ԫ�ؿ�ʼ��ʱ������person���󲢻�ȡ����id��ֵ����ֵ��person����	
					}else if("person".equals(tagName)){
						
						person = new Person();
						String id = parser.getAttributeValue(null, "id");
						
						person.setId(Integer.parseInt(id));
						
					//�������� name Ԫ�ؿ�ʼ��ʱ����ȡnameԪ�ص�ֵ����ֵ��person����	
					}else if("name".equals(tagName)){
						
						person.setName(parser.nextText());
						
					//�������� age Ԫ�ؿ�ʼ��ʱ����ȡageԪ�ص�ֵ����ֵ��person����		
					}else if("age".equals(tagName)){
						
						String age = parser.nextText();
						person.setAge(Integer.valueOf(age));
						
					}
					
					break;

				//���������Ԫ�ؽ�����
				case XmlPullParser.END_TAG:

					//�������� person �Ľ�����ʱ����person�������List.
					if("person".equals(tagName)){
						
						resList.add(person);
						
					}
					break;
					
				default:
					break;
				}
				
				eventType = parser.next();
			}
			
			//�������ɵ�List.
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

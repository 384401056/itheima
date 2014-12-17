package com.blueice.mobilelottery.engine.impl;

import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.blueice.mobilelottery.bean.ShoppingCar;
import com.blueice.mobilelottery.bean.Ticket;
import com.blueice.mobilelottery.bean.User;
import com.blueice.mobilelottery.engine.BaseEngine;
import com.blueice.mobilelottery.engine.UserEngine;
import com.blueice.mobilelottery.net.protocal.Message;
import com.blueice.mobilelottery.net.protocal.element.BalanceElement;
import com.blueice.mobilelottery.net.protocal.element.BetElement;
import com.blueice.mobilelottery.net.protocal.element.CurrentIssues;
import com.blueice.mobilelottery.net.protocal.element.UserLoginElement;

/**
 * 用户业务的实现类。
 *
 */
public class UserEngineImpl extends BaseEngine implements UserEngine {

	public Message login(User user) {

		/**
		 * 1.生成用户登陆请求XML。
		 */
		Message message = new Message();
		UserLoginElement element = new UserLoginElement();
		message.getHeader().getUsername().setTagValue(user.getUserName());
		String xml = message.getXml(element);

		/*
		 * 2.调用父类的方法，发送生成的请求XML文件，以及解析并校验服务器返回的XML数据 .成功返回一个Message对象。则否返回null
		 */
		Message respons = getResponse(xml);

		/**
		 * 3.//解析明文body，并取得所需的返回值。
		 */
		if (respons != null) {
			try {
				XmlPullParser parser = Xml.newPullParser();
				// 将body的明文转为StringReader，进行解析。
				parser.setInput(new StringReader(respons.getBody()
						.getServiceBodyInsideInfo()));
				int eventType = 0;
				String tagName = "";

				eventType = parser.getEventType();

				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
					case XmlPullParser.START_TAG:
						tagName = parser.getName();// 获取标签名。

						if ("errorcode".equals(tagName)) {
							respons.getBody().getOelement()
									.setErrorcode(parser.nextText()); // 获取返回值。

						}

						if ("errormsg".equals(tagName)) {
							respons.getBody().getOelement()
									.setErrormsg(parser.nextText()); // 获取返回值。

						}
						break;

					default:
						break;
					}
					eventType = parser.next();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return respons;

		}

		return null;

	}

	@Override
	public Message getBalance(User user) {
		/**
		 * 1.生成获取余额请求XML。
		 */
		Message message = new Message();
		BalanceElement element = new BalanceElement();
		message.getHeader().getUsername().setTagValue(user.getUserName());
		String xml = message.getXml(element);

		/*
		 * 2.调用父类的方法，发送生成的请求XML文件，以及解析并校验服务器返回的XML数据 .成功返回一个Message对象。则否返回null
		 */
		Message respons = getResponse(xml);

		// 服务器返回的element.
		BalanceElement responsElement = null;

		/**
		 * 3.//解析明文body，并取得所需的返回值。
		 */
		if (respons != null) {
			try {
				XmlPullParser parser = Xml.newPullParser();
				// 将body的明文转为StringReader，进行解析。
				parser.setInput(new StringReader(respons.getBody()
						.getServiceBodyInsideInfo()));
				int eventType = 0;
				String tagName = "";

				eventType = parser.getEventType();

				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
					case XmlPullParser.START_TAG:
						tagName = parser.getName();// 获取标签名。

						if ("errorcode".equals(tagName)) {
							respons.getBody().getOelement()
									.setErrorcode(parser.nextText()); // 获取返回值。
						}

						if ("errormsg".equals(tagName)) {
							respons.getBody().getOelement()
									.setErrormsg(parser.nextText()); // 获取返回值。
						}

						// 判断后面是否含有element数据。如果有创建element对象，并加入Message类中。
						if ("element".equals(tagName)) {
							responsElement = new BalanceElement();
							respons.getBody().getElements().add(responsElement);
						}

						if ("investvalues".equals(tagName)) {
							if (responsElement != null) {
								responsElement.setInvestvalues(parser
										.nextText());
							}

						}

						if ("accounvalues".equals(tagName)) {
							if (responsElement != null) {
								responsElement.setAccounvalues(parser
										.nextText());
							}
						}
						break;

					default:
						break;
					}
					eventType = parser.next();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return respons;

		}

		return null;
	}

	@Override
	public Message bet(User user) {

		/**
		 * 1.生成投注请求XML。
		 */
		Message message = new Message();
		BetElement element = new BetElement();
		element.getLotteryid().setTagValue(ShoppingCar.getInstance().getLotteryid()+"");
		
		StringBuffer code = new StringBuffer();
		for(Ticket item:ShoppingCar.getInstance().getTickets()){
			
			code.append("^")
			.append(item.getRedNum().replace(" ", ""))
			.append("|")
			.append(item.getBlueNum());
		}
		
		element.getLotterycode().setTagValue(code.substring(1));
		
		message.getHeader().getUsername().setTagValue(user.getUserName());
		String xml = message.getXml(element);

		/*
		 * 2.调用父类的方法，发送生成的请求XML文件，以及解析并校验服务器返回的XML数据 .成功返回一个Message对象。则否返回null
		 */
		Message respons = getResponse(xml);

		BetElement responsElement = null;

		/**
		 * 3.//解析明文body，并取得所需的返回值。
		 */
		if (respons != null) {
			try {
				XmlPullParser parser = Xml.newPullParser();
				// 将body的明文转为StringReader，进行解析。
				parser.setInput(new StringReader(respons.getBody()
						.getServiceBodyInsideInfo()));
				int eventType = 0;
				String tagName = "";

				eventType = parser.getEventType();

				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
					case XmlPullParser.START_TAG:
						tagName = parser.getName();// 获取标签名。

						if ("errorcode".equals(tagName)) {
							respons.getBody().getOelement()
									.setErrorcode(parser.nextText()); // 获取返回值。
						}

						if ("errormsg".equals(tagName)) {
							respons.getBody().getOelement()
									.setErrormsg(parser.nextText()); // 获取返回值。
						}

						// 判断后面是否含有element数据。如果有创建element对象，并加入Message类中。
						if ("element".equals(tagName)) {
							responsElement = new BetElement();
							respons.getBody().getElements().add(responsElement);
						}

						if ("actvalue".equals(tagName)) {
							if (responsElement != null) {
								responsElement.setActvalue(parser
										.nextText());
							}

						}
						break;
					}
					eventType = parser.next();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		return respons;
	}
}

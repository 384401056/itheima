package com.blueice.mobilelottery.net.protocal;


import org.xmlpull.v1.XmlSerializer;
public class Leaf {

	private String tagName;
	private String tagValue = "";

	/**
	 * 为了保证每一个节点必须有名字，构造时就要传入值。
	 * 
	 * @param 节点名字
	 */
	public Leaf(String tagName) {
		this.tagName = tagName;
	}
	
	public Leaf(String tagName, String tagValue) {
		this.tagName = tagName;
		this.tagValue = tagValue;
	}

	/**
	 * 序列化XML文件中最小的节点。
	 * 
	 * @param serializer
	 */
	public void serializerLeaf(XmlSerializer serializer) {

		try {

			serializer.startTag(null, tagName);
			serializer.text(tagValue);
			serializer.endTag(null, tagName);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTagValue() {
		return tagValue;
	}

	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}

}

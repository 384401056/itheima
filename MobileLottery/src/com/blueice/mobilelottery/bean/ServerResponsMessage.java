package com.blueice.mobilelottery.bean;

/**
 * 服务器端回复公共数据信息封装类。
 *
 */
public class ServerResponsMessage {

	/**
	 * 时间戳
	 */
	private String timestamp;
	
	/**
	 * md5验证码.时间+代理商密钥(常量应该放在jni中去调用)+Body明文的加密数据
	 */
	private String digest;
	/**
	 * 加密的body.
	 */
	private String bodyDES; 
	
	/**
	 * 返回码
	 */
	private String errorcode;
	
	/**
	 * 返回信息。
	 */
	private String errormsg;
	
	/**
	 * 明文的body.
	 */
	private String body; 
	
	
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
	public String getBodyDES() {
		return bodyDES;
	}
	public void setBodyDES(String body) {
		this.bodyDES = body;
	}
	public String getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}
	public String getErrormsg() {
		return errormsg;
	}
	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}
	
	
	
	
}

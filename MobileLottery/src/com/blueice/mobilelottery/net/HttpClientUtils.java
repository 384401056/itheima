package com.blueice.mobilelottery.net;

import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import com.blueice.mobilelottery.ConstValue;
import com.blueice.mobilelottery.GlobalParams;

public class HttpClientUtils {

	private HttpClient client;
	private HttpPost post;
	private HttpGet get;
	
	/**
	 * 构造方法，如果有代理就设置代理。
	 */
	public HttpClientUtils(){
		client = new DefaultHttpClient();
		
		//如果有代理地址，先设置代理。
		if(StringUtils.isNotBlank(GlobalParams.PROXY)){
			
			//设置代理主机的地址和端口。
			HttpHost host = new HttpHost(GlobalParams.PROXY, GlobalParams.PORT);
			//设置client的代理主机。
			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, host);
			
		}
	}
	
	/**
	 * 发送XML文件请求。
	 * @param uri 服务器地址。
	 * @param xml XML文件。
	 * @return 请求结果InputStream。
	 */
	public InputStream sendXML(String uri,String xml){
		
		post = new HttpPost(uri);
		
		try {
			
			StringEntity entity = new StringEntity(xml, ConstValue.ENCODING);
			
			post.setEntity(entity);
			
			HttpResponse Response = client.execute(post);
			
			if(Response.getStatusLine().getStatusCode()==200){
				
				//返回结果的一个输入流。
				return Response.getEntity().getContent();
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
}

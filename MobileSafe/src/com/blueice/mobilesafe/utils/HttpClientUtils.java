package com.blueice.mobilesafe.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.blueice.mobilesafe.ConstValue;
import com.blueice.mobilesafe.GlobalParams;

public class HttpClientUtils {

	private HttpClient client;
	private HttpPost post;
	private HttpGet get;
	
	/**
	 * 构造方法，如果有代理就设置代理。
	 */
	public HttpClientUtils(){
		
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 4000); //设置连接超时
        HttpConnectionParams.setSoTimeout(params, 5000); //设置请求超时
//        post.setParams(params);
		
		client = new DefaultHttpClient(params);//默认的Http客户端。
		
		//如果有代理地址，先设置代理。
		if(StringUtils.isNotBlank(GlobalParams.PROXY)){
			
			//设置代理主机的地址和端口。
			HttpHost host = new HttpHost(GlobalParams.PROXY, GlobalParams.PORT);
			//设置client的代理主机。
			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, host);
			
		}
	}
	
	/**
	 * 发送http请求。
	 * @param uri 服务器地址。
	 * @param 请求。可以为String,也可是XML,看服务器端的需要。
	 * @return 请求结果InputStream。
	 * @throws ClientProtocolException 
	 * @throws IOException 
	 */
	public InputStream sendRequest(String uri,String xml) throws ClientProtocolException, IOException{
		
		post = new HttpPost(uri);
		
		StringEntity entity = new StringEntity(xml, ConstValue.ENCODING);
		
		post.setEntity(entity);
		
		HttpResponse Response = client.execute(post);
		
		if(Response.getStatusLine().getStatusCode()==200){
			
			//返回结果的一个输入流。
			return Response.getEntity().getContent();
			
		}

		return null;
		
	}
}

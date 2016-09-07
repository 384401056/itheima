package cn.com.gstar.smartcamera.utils;

import cn.com.gstar.smartcamera.global.Params;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import org.apache.http.entity.InputStreamEntity;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class HttpTools {
	private static final int TIME_OUT = 10000;
	private static HttpUtils httpUtils = new HttpUtils(TIME_OUT);
	static{
		// 设置当前请求的缓存时间
		httpUtils.configCurrentHttpCacheExpiry(0 * 1000);
		// 设置默认请求的缓存时间
		httpUtils.configDefaultHttpCacheExpiry(0);
		// 设置线程数
		httpUtils.configRequestThreadPoolSize(10);
		httpUtils.configResponseTextCharset("utf-8");
	}
	
	
	/**
	 * GET请求
	 * @param str 请求地址
	 * @return json数据
	 */
	public static String getJsonByUrl(String str){
		String url = "http://"+ Params.ipAddress + str;
		try {
			ResponseStream responseStream = httpUtils.sendSync(HttpMethod.GET,url);
			LogUtils.i("StatusCode :"+responseStream.getStatusCode());
			if(responseStream.getStatusCode()==200){
				return responseStream.readString(); 
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.i(e.getMessage());
			return null;
		}
		return null;
	}


	/**
	 * POST请求，有参数。
	 * @param str 请求地址
	 * @param jsonStr json数据。
	 * @return tag msg data。
	 */
	public static String postJson(String str,String jsonStr){
		String url = "http://"+ Params.ipAddress + str;
		InputStream instream = null;
		try {
			RequestParams params = new RequestParams("UTF-8");
			params.setContentType("application/json");
			byte[] data = jsonStr.getBytes("UTF-8");
			instream = new  ByteArrayInputStream(data);
			long length = data.length;
			//添加请求参数
			params.setBodyEntity(new InputStreamEntity(instream , length));

			ResponseStream responseStream = httpUtils.sendSync(HttpMethod.POST, url,params);

			if(responseStream.getStatusCode()==200){
				return responseStream.readString();
			}

		} catch (Exception e) {
//			e.printStackTrace();
			LogUtils.i(e.getMessage());
		} finally{
			try {
				instream.close();
			} catch (IOException e) {
				e.printStackTrace();
				LogUtils.i(e.getMessage());
			}
		}

		return null;
	}


	/**
	 * 上传图片
	 * @param str 请求地址
	 * @param list 图片文件对象列表
	 * @return
	 */
	public static int postImgFile(String str,List<File> list){
		for(File event: list){
			String url = "http://"+ Params.ipAddress + str;
			try {
				RequestParams params = new RequestParams("UTF-8");
				params.addBodyParameter("file", new File(event.getPath()));
				ResponseStream responseStream = httpUtils.sendSync(HttpMethod.POST, url, params);
				if(responseStream.getStatusCode()==200){
					LogUtils.i(responseStream.readString());
				}
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
		return 1;
	}
}














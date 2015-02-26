package com.example.news.utils;
import com.google.gson.Gson;

public class GsonUtils {

	/**
	 * Gson可以直接将Json解析到Bean中。
	 * @param jsonResult 要解析的json数据。
	 * @param clz 要反回的Bean.class类
	 * @return
	 */
	public static <T> T jsonToBean(String jsonResult,Class<T> clz){
		
		//Gson可以直接将Json解析到Bean中。
		Gson gson = new Gson();
		T res = gson.fromJson(jsonResult, clz);
		return res;
	}

}

package cn.com.gstar.smartcamera.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {
	private static Gson gson = new Gson();
	public static <T> T jsonToObject(String jsonStr,Class<T> clazz){
		return gson.fromJson(jsonStr, clazz);
	}
	public static <T> ArrayList<T> jsonToObjectList(String jsonStr,TypeToken token){
		return gson.fromJson(jsonStr, token.getType());
	}
	public static <T> ArrayList<T> jsonToObjectList(String jsonStr,Type type){
		return gson.fromJson(jsonStr, type);
	}
	public static String objectToJson(Object src){
		return gson.toJson(src);
	}
	public static <T> String objectListToJson(Object src,Class<T> clazz){
		return gson.toJson(src, new TypeToken<List<T>>(){}.getType());
	}
}

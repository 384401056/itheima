package com.example.news.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.news.R;
import com.example.news.bean.NewsCenterCategory;
import com.example.news.utils.GsonUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

public class News extends BasicFragment {

	@Override
	public View initView(LayoutInflater inflater) {
		View view = LayoutInflater.from(context).inflate(R.layout.news_fragment, null);
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		
		HttpUtils http= new HttpUtils();
		http.send(HttpRequest.HttpMethod.GET, "http://127.0.0.1", new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				LogUtils.i(responseInfo.result);
				
				//Gson可以直接将Json解析到Bean中。
				GsonUtils.jsonToBean(responseInfo.result, NewsCenterCategory.class);
			}
			
			
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}

			
			
		});
	}


}

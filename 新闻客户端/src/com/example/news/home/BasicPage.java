package com.example.news.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public abstract class BasicPage {

	private View view;
	public Context context;

	public BasicPage(Context context) {

		this.context = context;
		
		//通过系统服务从XML中得到View
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = initView(inflater);
	
	}

	/*
	 * 通过系统服务来得到一个view.
	 */
	public abstract View initView(LayoutInflater inflater);
	
	/**
	 * 初始化数据。
	 */
	public abstract void initData();

	
	public View getView() {
		return view;
	}

}

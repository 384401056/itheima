package com.example.news.home;

import com.example.news.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class NewsCenterPage extends BasicPage {

	public NewsCenterPage(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.newscenter_page, null);
		return view;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}

}

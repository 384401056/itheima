package com.example.news.functionpage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.news.R;
import com.example.news.home.BasicPage;

public class FirstPage extends BasicPage {

	public FirstPage(Context context) {
		super(context);
	}

	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.first_page,null);
		return view;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

}

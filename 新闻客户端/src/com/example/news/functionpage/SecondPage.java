package com.example.news.functionpage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.news.R;
import com.example.news.home.BasicPage;

public class SecondPage extends BasicPage {

	public SecondPage(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.second_page,null);
		return view;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

}

package com.example.news.home;

import com.example.news.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class GovAffairsPage extends BasicPage {

	public GovAffairsPage(Context context) {
		super(context);
	}

	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.govaffairs_page, null);
		return view;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}


}

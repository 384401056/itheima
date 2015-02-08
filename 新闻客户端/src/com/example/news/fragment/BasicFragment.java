package com.example.news.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BasicFragment extends Fragment {

	private View view;
	public Context context;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData(savedInstanceState);
	}

	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = initView(inflater);
		return view;
	}

	/**
	 * 初始化视图。
	 * @param inflater LayoutInflater
	 * @return View
	 */
	public abstract View initView(LayoutInflater inflater);

	/**
	 * 初始化数据。
	 * @param savedInstanceState Bundle
	 */
	public abstract void initData(Bundle savedInstanceState);


	public View getView() {
		return view;
	}
	
	
	
}

package com.example.news.fragment;

import com.example.news.MainActivity;
import com.example.news.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class MenuFragment extends Fragment implements OnClickListener {

	private View view;
	private Context context;
	private LinearLayout ll_news;
	private LinearLayout ll_topic;
	private LinearLayout ll_picture;
	private LinearLayout ll_vote;
	



	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i("MyLog", "onCreate");
		this.context = getActivity();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		Log.i("MyLog", "onCreateView");
		view = LayoutInflater.from(getActivity()).inflate(R.layout.menu_fragment, null);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i("MyLog", "onActivityCreated");
		super.onActivityCreated(savedInstanceState);
		
		ll_news = (LinearLayout) view.findViewById(R.id.ll_news);
		ll_topic = (LinearLayout) view.findViewById(R.id.ll_topic);
		ll_picture = (LinearLayout) view.findViewById(R.id.ll_picture);
		ll_vote = (LinearLayout) view.findViewById(R.id.ll_vote);
		
		ll_news.setOnClickListener(this);
		ll_topic.setOnClickListener(this);
		ll_picture.setOnClickListener(this);
		ll_vote.setOnClickListener(this);
	}
	

	@Override
	public void onClick(View v) {

		Fragment f = null;

		switch (v.getId()) {
		case R.id.ll_news:
			f = new News();
			break;
		case R.id.ll_topic:
			f = new Topic();
			break;
		case R.id.ll_picture:
			f = new Picture();
			break;
		case R.id.ll_vote:
			f = new Vote();
			break;
		}
		
		/**
		 * 回调MainActivity中的SwitchFragment()方法。
		 */
		if(f!=null){
			if(context instanceof MainActivity){
				((MainActivity)context).SwitchFragment(f);
			}
		}
	}

	 
	
}





















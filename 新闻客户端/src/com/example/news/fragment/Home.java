package com.example.news.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.news.R;
import com.example.news.home.BasicPage;
import com.example.news.home.FunctionPage;
import com.example.news.home.GovAffairsPage;
import com.example.news.home.NewsCenterPage;
import com.example.news.home.SettingPage;
import com.example.news.home.SmartServicePage;
import com.example.news.view.LazyViewPager.OnPageChangeListener;
import com.example.news.view.NoTouchViewPager;

public class Home extends BasicFragment {

	private NoTouchViewPager viewPager;
	private RadioGroup main_radio;
	private int checkedId = R.id.rb_function;
	
	private List<BasicPage> list;
	
	@Override
	public View initView(LayoutInflater inflater) {
		
		View view = inflater.inflate(R.layout.home, null);
		
		viewPager = (NoTouchViewPager) view.findViewById(R.id.viewpager);
		main_radio = (RadioGroup) view.findViewById(R.id.main_radio);

		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		//加入5个Page。
		list = new ArrayList<BasicPage>();
		list.add(new FunctionPage(context));//context是BasicFragment父类中。
		list.add(new NewsCenterPage(context));
		list.add(new SmartServicePage(context));
		list.add(new GovAffairsPage(context));
		list.add(new SettingPage(context));
		
		MyViewPageAdapter adapter = new MyViewPageAdapter(context,list);
		viewPager.setAdapter(adapter);
		
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
				
			}
		});

		main_radio.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_function:
					viewPager.setCurrentItem(0,false);//第二个参数设置为false可以跳过载中间页。
					break;
				case R.id.rb_news_center:
					viewPager.setCurrentItem(1,false);
					break;
				case R.id.rb_smart_service:
					viewPager.setCurrentItem(2,false);
					break;
				case R.id.rb_gov_affairs:
					viewPager.setCurrentItem(3,false);
					break;
				case R.id.rb_setting:
					viewPager.setCurrentItem(4,false);
					break;

				default:
					break;
				}
				
			}
		});
	}


	/**
	 * 自定义的PageAdapter.
	 *
	 */
	class MyViewPageAdapter extends PagerAdapter{

		private Context context;
		private List<BasicPage> list;
		
		public MyViewPageAdapter(Context context, List<BasicPage> list) {
			this.context = context;
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view==object;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((NoTouchViewPager)container).removeView(list.get(position).getView());
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			
			((NoTouchViewPager)container).addView(list.get(position).getView(), 0);
			
			BasicPage page = list.get(position);
			page.initData();
			
			return list.get(position).getView();
		}
		
		
		
	}
}

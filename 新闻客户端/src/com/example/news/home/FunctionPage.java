package com.example.news.home;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.news.R;
import com.example.news.functionpage.FirstPage;
import com.example.news.functionpage.SecondPage;
import com.example.news.view.LazyViewPager;
import com.example.news.view.LazyViewPager.OnPageChangeListener;

public class FunctionPage extends BasicPage {

	private RadioGroup main_radio;
	private LazyViewPager viewPager;
	private List<BasicPage> list;

	public FunctionPage(Context context) {
		super(context);
	}

	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.function_page, null);

		viewPager = (LazyViewPager) view.findViewById(R.id.viewpager);
		main_radio = (RadioGroup) view.findViewById(R.id.main_radio);

		return view;
	}

	@Override
	public void initData() {
		
		list = new ArrayList<BasicPage>();
		list.add(new FirstPage(context));
		list.add(new SecondPage(context));
		
		/**
		 * 当点击上方的RadioButton按钮时进行viewPage的切换。
		 */
		main_radio.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_first:
					viewPager.setCurrentItem(0,false);
					break;
				case R.id.rb_second:
					viewPager.setCurrentItem(1,false);
					break;
				}
				
			}
		});
		
		
		MyViewPageAdapter adapter = new MyViewPageAdapter(context, list);
		viewPager.setAdapter(adapter);

		
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			/**
			 * 当viewPage滑动切换时，改变RadioButton.
			 */
			@Override
			public void onPageSelected(int position) {
				switch (position) {
				case 0:
					
					break;
				case 1:
					
					break;
				}
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
		
	}

	/**
	 * 自定义的PageAdapter.
	 *
	 */
	class MyViewPageAdapter extends PagerAdapter {

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
			return view == object;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((LazyViewPager) container)
					.removeView(list.get(position).getView());
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {

			((LazyViewPager) container)
					.addView(list.get(position).getView(), 0);

			return list.get(position).getView();
		}

	}

}

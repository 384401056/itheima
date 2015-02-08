package com.example.viewpagerdemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ViewPager viewPager;
	private TextView imageDisc;
	private LinearLayout pointGroup;
	
	private final int[] images = {
			R.drawable.img_01,
			R.drawable.img_02,
			R.drawable.img_03,
			R.drawable.img_04,
			R.drawable.img_05
			
	};
	
	private final String[] discription = {
			"巩俐不低俗，我就不能低俗",
			"扑树又回来啦！再唱经典老歌引万人大合唱",
			"揭秘北京电影如何升级",
			"乐视网TV版大派送",
			"热血屌丝的反杀"	
	};
	
	
	private List<ImageView> imageList = new ArrayList<ImageView>();
	
	//上一个页面的位置
	protected int lastPosition = 0;
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		imageDisc = (TextView) findViewById(R.id.tv01);
		imageDisc.setText(discription[0]);
		pointGroup = (LinearLayout) findViewById(R.id.pointGroup);
		

		for (int i = 0; i < images.length; i++) {
			
			//初始化图片资源
			ImageView iv = new ImageView(this);
			iv.setBackgroundResource(images[i]);
			imageList.add(iv);
			
			//添加指示点
			ImageView point = new ImageView(this);
			
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20,20); 
			params.rightMargin = 15; 
			point.setLayoutParams(params);
			
			point.setBackgroundResource(R.drawable.point_selector);
			if(i==0){
				point.setEnabled(true);
			}else{
				point.setEnabled(false);
			}
			pointGroup.addView(point);
			
			point.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.i("MyLog", v.getX()+"");
				}
			});
			
		}
		
		
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewPager.setAdapter(new MyPageAdapter());
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			/**
			 * 页面切换后调用 
			 * position  新的页面位置
			 */
			@Override
			public void onPageSelected(int position) {
				
				//设置文字描述内容
				imageDisc.setText(discription[position]);
				
				//改变指示点的状态
				//把当前点enbale 为true 
				pointGroup.getChildAt(position).setEnabled(true);
				//把上一个点设为false
				pointGroup.getChildAt(lastPosition).setEnabled(false);

				lastPosition = position;
				
			}
			
			/**
			 * 页面正在滑动的时候，回调
			 */
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				
			}
			
			/**
			 * 当页面状态发生变化的时候，回调
			 */
			@Override
			public void onPageScrollStateChanged(int state) {
				
			}
		});
		
		/*
		  * 自动循环：
		  * 1、定时器：Timer
		  * 2、开子线程 while  true 循环
		  * 3、ColckManager 
		  * 4、 用handler 发送延时信息，实现循环
		  */
//		 isRunning = true;
//		 handler.sendEmptyMessageDelayed(0, 2000);

	}
	
	/**
	 * 判断是否自动滚动
	 */
//	private boolean isRunning = false;
	
	class MyPageAdapter extends PagerAdapter{

		/**
		 * 获得页面的总数
		 */
		@Override
		public int getCount() {
			return imageList.size();

		}

		
		/**
		 * 获得相应位置上的view
		 * container  view的容器，其实就是viewpager自身
		 * position 	相应的位置
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			
			//��ViewPager���������View.�����ش�view
			container.addView(imageList.get(position));
			return imageList.get(position);
			
		}



		/**
		 * 销毁对应位置上的object
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
			object = null;
		}




		/**
		 * 判断 view和object的对应关系 
		 */
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
		
		
	}

}























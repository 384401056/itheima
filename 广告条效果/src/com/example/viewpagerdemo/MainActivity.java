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
			"泰凯斯",
			"穆拉丁",
			"雷加尔",
			"凯瑞甘",
			"泰瑞尔"	
	};
	
	
	private List<ImageView> imageList = new ArrayList<ImageView>();
	
	//上一个的点的位置。
	protected int lastPosition = 0;
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		imageDisc = (TextView) findViewById(R.id.tv01);
		imageDisc.setText(discription[0]);
		pointGroup = (LinearLayout) findViewById(R.id.pointGroup);
		

		for (int i = 0; i < images.length; i++) {
			
			//添加图片到imageList.
			ImageView iv = new ImageView(this);
			iv.setBackgroundResource(images[i]);
			imageList.add(iv);
			
			//添加点到Linearlayout.
			ImageView point = new ImageView(this);
			
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20,20); 
			params.rightMargin = 15;     //设置ImageView的margin_rigth属性。
			point.setLayoutParams(params);
			
			point.setBackgroundResource(R.drawable.point_selector);
			if(i==0){
				point.setEnabled(true);
			}else{
				point.setEnabled(false);
			}
			pointGroup.addView(point);
			
			//圆点的点击事件。
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
			 * 页面切换时调用此方法。
			 */
			@Override
			public void onPageSelected(int position) {
				
				//设置文本显示
				imageDisc.setText(discription[position]);
				
				//设置point的显示,这里要注意，设置已后要把上一个点恢复为false
				pointGroup.getChildAt(position).setEnabled(true);
				
				pointGroup.getChildAt(lastPosition).setEnabled(false);

				lastPosition = position;
				
			}
			
			/**
			 * 页面正在切换，不断地回调。
			 */
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				
			}
			
			/**
			 * 页面状态改变时。
			 */
			@Override
			public void onPageScrollStateChanged(int state) {
				
			}
		});

	}
	

	/**
	 * 自定义PagerAdapter类。
	 *
	 */
	class MyPageAdapter extends PagerAdapter{

		/**
		 * 如果要现实左右滑动无限循环的功能，要把getCount的返回值设为一个Integer.MAX_VALUE.
		 * 用这个很大的数值对imageList.size()取余这样就还是会得到 0-imageList.size()之间的数，这样右边的数很难滑动到最后，并且超过5就会变为0.
		 * 如果把一开始viewPager.setCurrentItem()设置为Integer.MAX_VALUE的一半，那些向左边滑动也可以循环了，并且超过0就变为了5.
		 * 
		 * 自动循环就用Handler的信息队列就可以实现。记得程序onDestroy时，停止信息发送。
		 */
		@Override
		public int getCount() {
			return imageList.size();

		}

		
		/**
		 * 获取相应位置上的View
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			
			//给ViewPager的容器添加View.并返回此view
			container.addView(imageList.get(position));
			return imageList.get(position);
			
		}



		/**
		 * 销毁相应位置上的View.
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
			object = null;
		}




		/**
		 * 判断view 和 object之间的关系。
		 */
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
		
		
	}

}























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
			"̩��˹",
			"������",
			"�׼Ӷ�",
			"�����",
			"̩���"	
	};
	
	
	private List<ImageView> imageList = new ArrayList<ImageView>();
	
	//��һ���ĵ��λ�á�
	protected int lastPosition = 0;
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		imageDisc = (TextView) findViewById(R.id.tv01);
		imageDisc.setText(discription[0]);
		pointGroup = (LinearLayout) findViewById(R.id.pointGroup);
		

		for (int i = 0; i < images.length; i++) {
			
			//���ͼƬ��imageList.
			ImageView iv = new ImageView(this);
			iv.setBackgroundResource(images[i]);
			imageList.add(iv);
			
			//��ӵ㵽Linearlayout.
			ImageView point = new ImageView(this);
			
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20,20); 
			params.rightMargin = 15;     //����ImageView��margin_rigth���ԡ�
			point.setLayoutParams(params);
			
			point.setBackgroundResource(R.drawable.point_selector);
			if(i==0){
				point.setEnabled(true);
			}else{
				point.setEnabled(false);
			}
			pointGroup.addView(point);
			
			//Բ��ĵ���¼���
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
			 * ҳ���л�ʱ���ô˷�����
			 */
			@Override
			public void onPageSelected(int position) {
				
				//�����ı���ʾ
				imageDisc.setText(discription[position]);
				
				//����point����ʾ,����Ҫע�⣬�����Ѻ�Ҫ����һ����ָ�Ϊfalse
				pointGroup.getChildAt(position).setEnabled(true);
				
				pointGroup.getChildAt(lastPosition).setEnabled(false);

				lastPosition = position;
				
			}
			
			/**
			 * ҳ�������л������ϵػص���
			 */
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				
			}
			
			/**
			 * ҳ��״̬�ı�ʱ��
			 */
			@Override
			public void onPageScrollStateChanged(int state) {
				
			}
		});

	}
	

	/**
	 * �Զ���PagerAdapter�ࡣ
	 *
	 */
	class MyPageAdapter extends PagerAdapter{

		/**
		 * ���Ҫ��ʵ���һ�������ѭ���Ĺ��ܣ�Ҫ��getCount�ķ���ֵ��Ϊһ��Integer.MAX_VALUE.
		 * ������ܴ����ֵ��imageList.size()ȡ�������ͻ��ǻ�õ� 0-imageList.size()֮������������ұߵ������ѻ�������󣬲��ҳ���5�ͻ��Ϊ0.
		 * �����һ��ʼviewPager.setCurrentItem()����ΪInteger.MAX_VALUE��һ�룬��Щ����߻���Ҳ����ѭ���ˣ����ҳ���0�ͱ�Ϊ��5.
		 * 
		 * �Զ�ѭ������Handler����Ϣ���оͿ���ʵ�֡��ǵó���onDestroyʱ��ֹͣ��Ϣ���͡�
		 */
		@Override
		public int getCount() {
			return imageList.size();

		}

		
		/**
		 * ��ȡ��Ӧλ���ϵ�View
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			
			//��ViewPager���������View.�����ش�view
			container.addView(imageList.get(position));
			return imageList.get(position);
			
		}



		/**
		 * ������Ӧλ���ϵ�View.
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
			object = null;
		}




		/**
		 * �ж�view �� object֮��Ĺ�ϵ��
		 */
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
		
		
	}

}























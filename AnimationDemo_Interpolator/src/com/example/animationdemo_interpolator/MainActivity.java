package com.example.animationdemo_interpolator;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	enum InterpolatorType {
		Accelerate, Decelerate, AccelerateDecelerate, LinearInterpolator, BounceInterpolator, 
		AnticipateInterpolator, AnticipateOvershootInterpolator, CycleInterpolator, OvershootInterpolator  
	};
	
	private Button btn01;
	private Button btn02;
	private Button btn03;
	private Button btn04;
	private Button btn05;
	private Button btn06;
	private Button btn07;
	private Button btn08;
	private Button btn09;
	private ImageView imageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		imageView = (ImageView) findViewById(R.id.imageView1);
	
		btn01 = (Button) findViewById(R.id.button1);
		btn01.setOnClickListener(new MyOnClickListener(InterpolatorType.Accelerate));
		
		btn02 = (Button) findViewById(R.id.button2);
		btn02.setOnClickListener(new MyOnClickListener(InterpolatorType.Decelerate));
		
		btn03 = (Button) findViewById(R.id.button3);
		btn03.setOnClickListener(new MyOnClickListener(InterpolatorType.AccelerateDecelerate));
		
		btn04 = (Button) findViewById(R.id.button4);
		btn04.setOnClickListener(new MyOnClickListener(InterpolatorType.LinearInterpolator));
		
		btn05 = (Button) findViewById(R.id.button5);
		btn05.setOnClickListener(new MyOnClickListener(InterpolatorType.BounceInterpolator));
		
		btn06 = (Button) findViewById(R.id.button6);
		btn06.setOnClickListener(new MyOnClickListener(InterpolatorType.AnticipateInterpolator));
		
		btn07 = (Button) findViewById(R.id.button7);
		btn07.setOnClickListener(new MyOnClickListener(InterpolatorType.AnticipateOvershootInterpolator));
		
		btn08 = (Button) findViewById(R.id.button8);
		btn08.setOnClickListener(new MyOnClickListener(InterpolatorType.CycleInterpolator));
		
		btn09 = (Button) findViewById(R.id.button9);
		btn09.setOnClickListener(new MyOnClickListener(InterpolatorType.OvershootInterpolator));
	}
	
	
	
	
	private class MyOnClickListener implements OnClickListener{

		private InterpolatorType type;
		
		public MyOnClickListener(InterpolatorType type) {
			this.type = type;
		}
		
		@Override
		public void onClick(View v) {
			switch (type) {

			
			//设置 加速度插入器。
			case Accelerate:
				
				TranslateAnimation animation1 = new TranslateAnimation(
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, 10f,
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, 0
						);
				
				
				animation1.setInterpolator(new AccelerateInterpolator(4f));
				
				animation1.setDuration(5000);
				animation1.setRepeatCount(1);
				animation1.setRepeatMode(Animation.RESTART);
				
				imageView.startAnimation(animation1);
				
				break;
				
			//设置 减速度插入器。		
			case Decelerate:
				
				TranslateAnimation animation2 = new TranslateAnimation(
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, 10f,
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, 0
						);
				
				
				animation2.setInterpolator(new DecelerateInterpolator(4f));
				
				animation2.setDuration(5000);
				animation2.setRepeatCount(1);
				animation2.setRepeatMode(Animation.RESTART);
				
				imageView.startAnimation(animation2);
				
				
				break;
			
				
			//设置 加速减速度插入器。	
			case AccelerateDecelerate:
				
				TranslateAnimation animation3 = new TranslateAnimation(
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, 10f,
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, 0
						);
				
				animation3.setInterpolator(new AccelerateDecelerateInterpolator());
				
				animation3.setDuration(5000);
				animation3.setRepeatCount(1);
				animation3.setRepeatMode(Animation.RESTART);
				
				imageView.startAnimation(animation3);
				
				
				break;
				
			//设置 线性插入器。	
			case LinearInterpolator:
				
				TranslateAnimation animation4 = new TranslateAnimation(
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, 10f,
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, 0
						);
				
				animation4.setInterpolator(new LinearInterpolator());
				
				animation4.setDuration(5000);
				animation4.setRepeatCount(1);
				animation4.setRepeatMode(Animation.RESTART);
				
				imageView.startAnimation(animation4);
				
				
				break;
				
			//设置 弹跳插入器。
			case BounceInterpolator:
				
				TranslateAnimation animation5 = new TranslateAnimation(
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, 10f,
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, 0
						);
				
				animation5.setInterpolator(new BounceInterpolator());
				animation5.setDuration(5000);
				animation5.setFillAfter(true);
				imageView.startAnimation(animation5);
				
				break;
				
			//设置 回荡秋千插值器
			case AnticipateInterpolator:
				
				
				
				TranslateAnimation animation6 = new TranslateAnimation(
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, 10f,
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, 0
						);
				
				animation6.setInterpolator(new AnticipateInterpolator(2f));
				animation6.setDuration(5000);
				animation6.setFillAfter(true);
				imageView.startAnimation(animation6);
				
				break;	
			
			case AnticipateOvershootInterpolator:
				
				TranslateAnimation animation7 = new TranslateAnimation(
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, 10f,
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, 0
						);
				
				animation7.setInterpolator(new AnticipateOvershootInterpolator(2f));
				animation7.setDuration(5000);
				animation7.setFillAfter(true);
				imageView.startAnimation(animation7);
				
				break;

			//设置 正弦周期变化插值器
			case CycleInterpolator:
					
				TranslateAnimation animation8 = new TranslateAnimation(
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, 5f,
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, 0
						);
				
				animation8.setInterpolator(new CycleInterpolator(2f));
				animation8.setDuration(5000);
				animation8.setFillAfter(true);
				imageView.startAnimation(animation8);
				
				break;
				
				
			
			case OvershootInterpolator:
				
				TranslateAnimation animation9 = new TranslateAnimation(
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, 5f,
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, 0
						);
				
				animation9.setInterpolator(new OvershootInterpolator(4f));
				animation9.setDuration(5000);
				animation9.setFillAfter(true);
				imageView.startAnimation(animation9);
				
				break;

			default:
				break;
			}
		}
		
	}
	

}

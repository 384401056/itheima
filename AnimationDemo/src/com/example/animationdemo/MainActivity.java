package com.example.animationdemo;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

/**
 * TweenAination动画和FrameAnimation动画的示例。
 *
 */
public class MainActivity extends Activity {

	enum AnimationType {
		Alpha, Rotate, Scale, Translate, Set, Drawable
	};

	private ImageView imageView;
	private Button btn1;
	private Button btn2;
	private Button btn3;
	private Button btn4;
	private Button btn5;
	private Button btn6;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		imageView = (ImageView)findViewById(R.id.imageView1);

		btn1 = (Button) findViewById(R.id.button1);
		btn1.setOnClickListener(new MyOnclickListener(AnimationType.Alpha));

		btn2 = (Button) findViewById(R.id.button2);
		btn2.setOnClickListener(new MyOnclickListener(AnimationType.Rotate));

		btn3 = (Button) findViewById(R.id.button3);
		btn3.setOnClickListener(new MyOnclickListener(AnimationType.Scale));

		btn4 = (Button) findViewById(R.id.button4);
		btn4.setOnClickListener(new MyOnclickListener(AnimationType.Translate));

		btn5 = (Button) findViewById(R.id.button5);
		btn5.setOnClickListener(new MyOnclickListener(AnimationType.Set));
		
		btn6 = (Button) findViewById(R.id.button6);
		btn6.setOnClickListener(new MyOnclickListener(AnimationType.Drawable));

	}

	class MyOnclickListener implements OnClickListener {

		private AnimationType animotionType;

		public MyOnclickListener(AnimationType type) {
			animotionType = type;
		}

		@Override
		public void onClick(View v) {
			switch (animotionType) {

			case Alpha:
				// 建立动画 。
				AlphaAnimation animation1 = new AlphaAnimation(1, 0.2f);
				animation1.setDuration(3000); // 动画时间。
				animation1.setRepeatCount(3); // 重复次数。
				animation1.setRepeatMode(Animation.REVERSE); // 从动画结束的时间反向循环。

				// 开始动画 。
				imageView.startAnimation(animation1);

				break;

			case Rotate:
				// 建立动画 。
				RotateAnimation animation2 = new RotateAnimation(0, 360,
						Animation.RELATIVE_TO_SELF, 0.5f,
						Animation.RELATIVE_TO_SELF, 0.5f);
				animation2.setDuration(3000); // 动画时间。
				animation2.setRepeatCount(3); // 重复次数。
				animation2.setRepeatMode(Animation.REVERSE); // 从动画结束的时间反向循环。

				// 开始动画 。
				imageView.startAnimation(animation2);

				break;

			case Scale:

				ScaleAnimation animation3 = new ScaleAnimation(1, 2f, 1, 2f,
						Animation.RELATIVE_TO_SELF, 0.5f,
						Animation.RELATIVE_TO_SELF, 0.5f);

				animation3.setDuration(3000); // 动画时间。
				animation3.setRepeatCount(3); // 重复次数。
				animation3.setRepeatMode(Animation.REVERSE); // 从动画结束的时间反向循环。

				// 开始动画 。
				imageView.startAnimation(animation3);
				
				break;

			case Translate:

				TranslateAnimation animation4 = new TranslateAnimation(
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, 1f,
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, 4f
						);
				animation4.setZAdjustment(Animation.ZORDER_TOP);;
				animation4.setDuration(3000); // 动画时间。
				animation4.setRepeatCount(3); // 重复次数。
				animation4.setRepeatMode(Animation.REVERSE); // 从动画结束的样子反向循环到开始时的样子。

				// 开始动画 。
				imageView.startAnimation(animation4);
				
				break;

			case Set:

				AnimationSet animationSet = new AnimationSet(false);//false为不使用插入器。
				// 建立动画 。
				AlphaAnimation animation5 = new AlphaAnimation(1, 0.2f);
				animation5.setDuration(3000); // 动画时间。
				animation5.setRepeatCount(3); // 重复次数。
				animation5.setRepeatMode(Animation.REVERSE); // 从动画结束的时间反向循环。
				
				// 建立动画 。
				RotateAnimation animation6 = new RotateAnimation(0, 360,
						Animation.RELATIVE_TO_SELF, 0.5f,
						Animation.RELATIVE_TO_SELF, 0.5f);
				animation6.setDuration(3000); // 动画时间。
				animation6.setRepeatCount(3); // 重复次数。
				animation6.setRepeatMode(Animation.REVERSE); // 从动画结束的时间反向循环。
				
				
				ScaleAnimation animation7 = new ScaleAnimation(1, 2f, 1, 2f,
						Animation.RELATIVE_TO_SELF, 0.5f,
						Animation.RELATIVE_TO_SELF, 0.5f);

				animation7.setDuration(3000); // 动画时间。
				animation7.setRepeatCount(3); // 重复次数。
				animation7.setRepeatMode(Animation.REVERSE); // 从动画结束的时间反向循环。
				
				
				TranslateAnimation animation8 = new TranslateAnimation(
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, 1f,
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, 4f
						);
				animation8.setZAdjustment(Animation.ZORDER_TOP);;
				animation8.setDuration(3000); // 动画时间。
				animation8.setRepeatCount(3); // 重复次数。
				animation8.setRepeatMode(Animation.REVERSE); // 从动画结束的样子反向循环到开始时的样子。
				
				animationSet.addAnimation(animation5);
				animationSet.addAnimation(animation6);
				animationSet.addAnimation(animation7);
				animationSet.addAnimation(animation8);
			
				
				imageView.startAnimation(animationSet);
				

				break;
				
			case Drawable:
				imageView.setImageDrawable(null);
				imageView.setBackgroundResource(R.drawable.drawablelist);
				AnimationDrawable ad = (AnimationDrawable)imageView.getBackground();
				ad.setOneShot(true);
				ad.start();	
				
				break;
				
			default:
				break;
			}

		}

	}
}

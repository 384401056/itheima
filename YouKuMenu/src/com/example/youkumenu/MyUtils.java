package com.example.youkumenu;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

public class MyUtils {

	
	/**
	 * 旋转动画Out.
	 * @param layouView 要旋转的控件.
	 */
	public static void startAnimOut(RelativeLayout layouView) {

		startAnimOut(layouView, 0);

	}

	/**
	 * 旋转动画In.
	 * @param layouView 要旋转的控件.
	 */
	public static void startAnimIn(RelativeLayout layouView) {

		RotateAnimation animation = new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
		animation.setDuration(300);
		animation.setFillAfter(true);
		layouView.startAnimation(animation);
		
	}
	
	/**
	 * 延迟旋转动画Out.
	 * @param layouView 要旋转的控件
	 * @param offset 要延迟的时间。
	 */
	public static void startAnimOut(RelativeLayout layouView, long offset) {

		RotateAnimation animation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
		animation.setDuration(300);
		animation.setFillAfter(true);
		animation.setStartOffset(offset);
		layouView.startAnimation(animation);
		
	}

}

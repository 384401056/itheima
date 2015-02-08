package com.example.news.view;

import android.R.bool;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoTouchViewPager extends LazyViewPager {

	private boolean isTouchMode = false;

	public NoTouchViewPager(Context context) {
		super(context);
	}

	public NoTouchViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (isTouchMode) {
			return super.onInterceptTouchEvent(ev);
		} else {
			return false;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (isTouchMode) {
			return super.onTouchEvent(ev);
		} else {
			return false;
		}
	}

}

package com.blueice.mobilelottery.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blueice.mobilelottery.R;
import com.blueice.mobilelottery.utils.DensityUtil;

public class MyGridView extends GridView {

	private Context context;
	private PopupWindow pop;
	private TextView popTV;

	private OnActionUPListener onActionUPListener;



	public MyGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

		View view = View.inflate(context, R.layout.il_gridview_item_pop, null);
		popTV = (TextView) view.findViewById(R.id.ii_pretextView);

		pop = new PopupWindow(context);
		pop.setContentView(view);

		// 由于view是inflate到pop中的，没有设置父容器。所以要指定pop的高宽才能正常的显示。
		pop.setWidth(DensityUtil.dip2px(context, 55));
		pop.setHeight(DensityUtil.dip2px(context, 53));
		pop.setBackgroundDrawable(null);;// 去除背景。
		pop.setAnimationStyle(0);// 去除动画。
	}

	/**
	 * 点击、拖动和放开事件的处理。
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		int x = (int) ev.getX();
		int y = (int) ev.getY();

		int position = pointToPosition(x, y); // 通过屏幕上的点得到属于GridView中的哪个Item。

		// 如果这个点不属于任何一个Item，则return false;
		if (position == INVALID_POSITION) {
			return false;
		}

		// 如果属于某个Item,则返回一个Item对应位置上的View.
		TextView child = (TextView) getChildAt(position);

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			//当在GridView范围内按下手指时，让ScrollView对滑动事件放行。否则接下来的滑动会与ScrollView冲突。
			//this.getpParent()。只是获取到时GridView的父容器Linearlayout.
			this.getParent().getParent().requestDisallowInterceptTouchEvent(true);
			showPop(child);
			break;
		case MotionEvent.ACTION_MOVE:
			updatePop(child);
			break;
		case MotionEvent.ACTION_UP:
			//当在GridView范围内放开手指时，让ScrollView获取滑动事件。
			this.getParent().getParent().requestDisallowInterceptTouchEvent(false);
			hiddenPop(child);
			// 在此处执行一个接口的方法，此方法在PlaySSQ中去实现
			if (onActionUPListener != null)
				onActionUPListener.onActionUp(child,position);
			break;
		}

		return super.onTouchEvent(ev);
	}

	/**
	 * 显示PopWindows.
	 * 
	 * @param child
	 *            GridView的item.
	 */
	private void showPop(TextView child) {

		popTV.setText(child.getText());

		int xOffset = -(pop.getWidth() - child.getWidth()) / 2;
		int yOffset = -(pop.getHeight() + child.getHeight());
		pop.showAsDropDown(child, xOffset, yOffset);
		
	}

	private void updatePop(TextView child) {

		popTV.setText(child.getText());

		int xOffset = -(pop.getWidth() - child.getWidth()) / 2;
		int yOffset = -(pop.getHeight() + child.getHeight());

		pop.update(child, xOffset, yOffset, -1, -1);

	}

	private void hiddenPop(TextView child) {
		pop.dismiss();
	}

	/**
	 * 当手指抬起时的监听接口。
	 */
	public interface OnActionUPListener {

		void onActionUp(View view,int position);

	}

	public void setOnActionUPListener(OnActionUPListener onActionUPListener) {
		this.onActionUPListener = onActionUPListener;
	}
	
	
	
}

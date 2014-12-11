package com.blueice.mobilelottery.view;

import com.blueice.mobilelottery.ConstValue;
import com.blueice.mobilelottery.R;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 购彩大厅的界面。
 *
 */
public class HallUI extends BaseUI {

	private LinearLayout showInMiddle;

	public HallUI(Context context) {
		super(context);
		init();
	}

	private void init() {
		// 从布局文件中得到View.
		showInMiddle = (LinearLayout) View.inflate(context, R.layout.il_hall,null);

		//如果返回的View布局参数为null,则设置参数。如果不设置参数，在中间容器的屏幕右边会有白边。
		if (showInMiddle.getLayoutParams() == null) {

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT);
			
			showInMiddle.setLayoutParams(params);
		}

	}

	@Override
	public View getChildView() {
		return showInMiddle;
	}

	@Override
	public int getID() {
		return ConstValue.VIEW_HELL;
	}

}

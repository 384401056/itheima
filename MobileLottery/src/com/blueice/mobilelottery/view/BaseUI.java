package com.blueice.mobilelottery.view;

import android.content.Context;
import android.view.View;


/**
 * 所有中间界面的父类。
 *
 */
public abstract class BaseUI {
	
	protected Context context; //注意是protected.

	public BaseUI(Context context) {
		this.context = context;
	}

	/**
	 * 获取在中间容器中要加载的内容。
	 * @return
	 */
	public abstract  View getChildView();
}

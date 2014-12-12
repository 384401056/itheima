package com.blueice.mobilelottery.view;

import com.blueice.mobilelottery.net.NetUtils;
import com.blueice.mobilelottery.net.protocal.Message;
import com.blueice.mobilelottery.utils.PromptManager;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 所有中间界面的父类。
 *
 */
public abstract class BaseUI implements OnClickListener {

	protected Context context; // 注意是protected.子类要用。

	protected ViewGroup showInMiddle; //返回的中间容器ViewGroup,在子类中创建。

	public BaseUI(Context context) {
		this.context = context;
		init();
		setOnClickListener();
	}

	
	/**
	 * 一旦进入界面，就被执行的方法。
	 */
	public abstract void OnResume();
	
	/**
	 * 一旦界面退出，就被抛行的方法。
	 */
	public abstract void OnPause();
	
	/**
	 * 界面初始化。
	 */
	public abstract void init();
	
	/**
	 * 返回对应Id的控件。
	 * @param id 控件资源Id.
	 * @return 控件View
	 */
	public View findViewById(int id){
		return showInMiddle.findViewById(id);
	}

	/**
	 * OnClickListener事件设置
	 */
	public abstract void setOnClickListener();


	/**
	 * 设置每个UI的唯一标识符。
	 * 
	 * @return 标识符
	 */
	public abstract int getID();

	/**
	 * 获取在中间容器中要加载的内容。
	 * @return 中间容器中的View
	 */
	public View getChildView() {
		// 如果返回的View布局参数为null,则设置参数。如果不设置参数，在中间容器的屏幕右边会有白边。
		if (showInMiddle.getLayoutParams() == null) {

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT);

			showInMiddle.setLayoutParams(params);
		}

		return showInMiddle;
	}
	
	
	/**
	 * 
	 * @param <Params>
	 */
	protected abstract class MyAsynTask<Params> extends AsyncTask<Params, Void, Message>{

		/**
		 * 类似与Thread.start方法 由于final修饰，无法Override，方法重命名。网络判断后再执行。
		 * 
		 * @param params
		 * @return
		 */
		public final AsyncTask<Params, Void, Message> executeProxy(
				Params... params) {
			if (NetUtils.checkNet(context)) {
				return super.execute(params);
			} else {
				PromptManager.showNoNetWork(context);
			}
			return null;
		}
		
		
		
	}
	
	
	
	
	
}
















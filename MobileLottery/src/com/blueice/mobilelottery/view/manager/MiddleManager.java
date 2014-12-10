package com.blueice.mobilelottery.view.manager;

import com.blueice.mobilelottery.MainActivity;
import com.blueice.mobilelottery.R;
import com.blueice.mobilelottery.view.BaseUI;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

/**
 * 中间容器的管理类。
 *
 */
public class MiddleManager {

	/*************************  单例化  *******************/
	private static MiddleManager instance = new MiddleManager();
	
	private MiddleManager(){
		
	}

	public static MiddleManager getInstance() {
		
		if(instance==null){
			instance = new MiddleManager();
		}
		return instance;
	}

	/********************************************************/
	
	
	private RelativeLayout middle;
	private Context context;

	/**
	 * 初始化MiddleManager.
	 * @param viewGroup 中间容器
	 * @param activity 
	 */
	public void init(RelativeLayout viewGroup){
		
		this.middle = viewGroup;
		this.context = middle.getContext();
	}
	
	
	/**
	 * 切换界面。
	 * @param view 要切换到的界面。
	 */
	public void changeUI(BaseUI view){
		//切换动画的监听器中如果已经加了 removeView()的方法，此处就不用了。
		middle.removeAllViews();
		
		View child = view.getChildView();
		middle.addView(child);
		
//		FadeUtil.fadeIn(child, 2000, 2000);
		
		//第二个界面的切换动画。
		Animation anim = AnimationUtils.loadAnimation(this.context, R.anim.ia_view_change);
		child.setAnimation(anim);
		
	}
	
}

package com.blueice.mobilelottery;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.blueice.mobilelottery.view.BaseUI;
import com.blueice.mobilelottery.view.FirstUI;
import com.blueice.mobilelottery.view.SecondUI;
import com.blueice.mobilelottery.view.manager.BottomManager;
import com.blueice.mobilelottery.view.manager.MiddleManager;
import com.blueice.mobilelottery.view.manager.TitleManager;

/**
 * @author Administrator
 *
 */
public class MainActivity extends Activity {

	private RelativeLayout middleContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.il_main);
		
		init();
		
	}

	
	private void init() {

		TitleManager title = TitleManager.getInstance();
		BottomManager bottom = BottomManager.getInstrance();
		MiddleManager middle = MiddleManager.getInstance();
		/**
		 * 获取中间容器，并加载View
		 */
		middleContainer = (RelativeLayout)findViewById(R.id.ii_middle);
		
		title.init(this);
		bottom.init(this);
		middle.init(middleContainer);
		
		title.showUnLoginTitle();;
		bottom.showCommonBottom();;
		

	}
	

}

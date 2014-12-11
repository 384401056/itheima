package com.blueice.mobilelottery;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
		
		//加载首页。
		title.showUnLoginTitle();;
		bottom.showCommonBottom();;
		middle.changeUI(FirstUI.class);

	}
	
	/**
	 * 响应返回键。
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		//如果按了返回键，则进行退栈操作。其它键则放行。
		if(keyCode == KeyEvent.KEYCODE_BACK){

			//返回以前的界面是否成功。
			boolean result = MiddleManager.getInstance().goback();
			
			if(!result){
				Toast.makeText(MainActivity.this, "退出程序", Toast.LENGTH_LONG).show();		
			}
			
			return false;
		}

		return super.onKeyDown(keyCode, event);
	}
	
}













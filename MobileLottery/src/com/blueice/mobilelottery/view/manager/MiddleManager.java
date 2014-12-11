package com.blueice.mobilelottery.view.manager;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;

import com.blueice.mobilelottery.R;
import com.blueice.mobilelottery.view.BaseUI;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

/**
 * 中间容器的管理类。
 *
 */
public class MiddleManager extends Observable {

	private static final String TAG = "MyLog";
	
	/**
	 * 界面key堆栈。
	 */
	private LinkedList<String> History = new LinkedList<String>();
	
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
	
	
	//定义一个UI栈.
	Map<String, BaseUI> UIStack = new HashMap<String, BaseUI>();
	
	private BaseUI currentUI = null;//当前正在显示的BaseUI.
	
	/**
	 * 切换界面。
	 * 解决的问题1：三个容器的连动。
	 * 
	 * @param view 要切换到的界面。
	 */
	public void changeUI(Class<? extends BaseUI> targetClazz){
		
		//判断当前界面是不是传入的targetClass对应的界面。
		if(currentUI!=null&& currentUI.getClass()==targetClazz){
			return;
		}
		
		
		String key = targetClazz.getSimpleName();
		BaseUI targetUI = null;
		
		//思路：一旦创建过就重用，曾经创建过的界面需要存储。
		if(UIStack.containsKey(key)){
			
			targetUI = UIStack.get(key);
			
		}else{
			
			try {
				//由于构造targetUI需要参数，所以定义一个构造器。Context.class就是参数的类型。
				Constructor<? extends BaseUI> constructor = targetClazz.getConstructor(Context.class);
				targetUI = constructor.newInstance(context);
				
				UIStack.put(key, targetUI);//把构造出的BaseUI加入栈.
				
			} catch (Exception e) {
				throw new RuntimeException("constructor new instance error!");
			}
			
		}
		
		Log.i(TAG,targetUI.toString());
		
		
		//如果切换动画的监听器中如果已经加了 removeView()的方法，此处就不用了。
		middle.removeAllViews();
		View child = targetUI.getChildView();
		middle.addView(child);
//		FadeUtil.fadeIn(child, 2000, 2000);
		
		//界面的切换动画。
		Animation anim = AnimationUtils.loadAnimation(this.context, R.anim.ia_view_change);
		child.setAnimation(anim);
		
		currentUI = targetUI;
		
		//将界面key加入堆栈中。
		History.addFirst(key);
		
		//当中间容器切换成功时，处理另外的两个容器。(通过观察者模式来进行通知)
		changeTitileBottom();
	}
	
	/**
	 * 当中间容器切换成功时，处理另外的两个容器。
	 * 
	 */
	private void changeTitileBottom() {
		
		/**
		 * 方案1.比对的依据，是BaseUI的名称。来连动Title和Bottom。
		 * 存在问题：在界面初期，就必须将所有的界面名称确定。
		 */
//		if(currentUI.getClass().getSimpleName().equals("FirstUI")){
//			TitleManager.getInstance().showUnLoginTitle();
//			BottomManager.getInstance().showCommonBottom();
//		}
//		
//		if(currentUI.getClass().getSimpleName().equals("SecondUI")){
//			TitleManager.getInstance().showCommonTitle();
//			BottomManager.getInstance().showGameBottom();
//		}
		
		/**
		 * 方案二：更换比对的依据，为每一个BaseUI都设置一个int值。根据此值来比对。
		 */
//		switch (currentUI.getID()) {
//		case ConstValue.VIEW_FIRST:
//			TitleManager.getInstance().showUnLoginTitle();
//			BottomManager.getInstance().showCommonBottom();
//			break;
//		case ConstValue.VIEW_SECOND:
//			TitleManager.getInstance().showCommonTitle();
//			BottomManager.getInstance().showGameBottom();
//			break;
//		}
		
		/**
		 * 方案三：1.将中间容器变为被观察者。
		 * 		  2.将Title和Bottom变以观察者。
		 *        3.将Title和Bottom加入到观察者的列表中。
		 *        4.一旦中间容器变动，修改boolean值为true,最后通知所有观察者。
		 *        5.观察者执行updata()方法。
		 */
		setChanged();
		notifyObservers(currentUI.getID());
	}

	/**
	 * 切换界面。
	 * 解决的问题1：每次点击都会创建一个对象。思路：一旦创建过就重用，曾经创建过的界面需要存储。
	 * 解决的问题1：如果当前已经是targetClazz对应的界面，就不切换了。
	 * 
	 * @param view 要切换到的界面。
	 */
	public void changeUI1(Class<? extends BaseUI> targetClazz){
		
		//判断当前界面是不是传入的targetClass对应的界面。
		if(currentUI!=null&& currentUI.getClass()==targetClazz){
			return;
		}
		
		
		String key = targetClazz.getSimpleName();
		BaseUI targetUI = null;
		
		//思路：一旦创建过就重用，曾经创建过的界面需要存储。
		if(UIStack.containsKey(key)){
			
			targetUI = UIStack.get(key);
			
		}else{
			
			try {
				//由于构造targetUI需要参数，所以定义一个构造器。Context.class就是参数的类型。
				Constructor<? extends BaseUI> constructor = targetClazz.getConstructor(Context.class);
				targetUI = constructor.newInstance(context);
				
				UIStack.put(key, targetUI);//把构造出的BaseUI加入栈.
				
			} catch (Exception e) {
				throw new RuntimeException("constructor new instance error!");
			}
			
		}
		
		Log.i(TAG,targetUI.toString());
		
		
		//如果切换动画的监听器中如果已经加了 removeView()的方法，此处就不用了。
		middle.removeAllViews();
		View child = targetUI.getChildView();
		middle.addView(child);
//		FadeUtil.fadeIn(child, 2000, 2000);
		
		//界面的切换动画。
		Animation anim = AnimationUtils.loadAnimation(this.context, R.anim.ia_view_change);
		child.setAnimation(anim);
		
		currentUI = targetUI;
		
		//将界面key加入堆栈中。
		History.addFirst(key);
		
	}

	

	
	/**
	 * 返回上个界面
	 * @return 是否成功返回。
	 */
	public boolean goback() {
		
		//删除栈顶。
		if(History.size()>1){

			History.removeFirst();
			
			//根据key得到BaseUI对象，并加入middle容器中。
			if (History.size() > 0) {
				
				String key = History.getFirst();
				BaseUI targetUI = UIStack.get(key);
				middle.removeAllViews();
				View child = targetUI.getChildView();
				middle.addView(child);
				
				//界面的切换动画。
				Animation anim = AnimationUtils.loadAnimation(this.context, R.anim.ia_view_change);
				child.setAnimation(anim);
				
				//修改当前的BaseUI.
				currentUI = targetUI;
				
				//连动。
				changeTitileBottom();

				return true;
			}
		}
		return false;
	}
	
}


























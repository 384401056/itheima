package com.example.youkumenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements OnClickListener{

	private ImageView menu;
	private ImageView home;
	
	private RelativeLayout level1;
	private RelativeLayout level2;
	private RelativeLayout level3;
	
	//�жϵ������˵��Ƿ�����ʾ״̬��
	private boolean isLevel3Show = true;
	//�жϵڶ����˵��Ƿ�����ʾ״̬��
	private boolean isLevel2Show = true;
	//�жϵ�һ���˵��Ƿ�����ʾ״̬��
	private boolean isLevel1Show = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		menu = (ImageView) findViewById(R.id.icon_menu);
		menu.setOnClickListener(this);
		
		home = (ImageView) findViewById(R.id.ico_home);
		home.setOnClickListener(this);
		
		level1 = (RelativeLayout) findViewById(R.id.level1);
		level2 = (RelativeLayout) findViewById(R.id.level2);
		level3 = (RelativeLayout) findViewById(R.id.level3);

	}


	
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.icon_menu: //ִ��menu ͼ��ĵ���¼���
			
			if(isLevel3Show){ //����������˵�Ϊ��ʾ����������������ʾ����
				
				MyUtils.startAnimOut(level3);
				
			}else{
				
				MyUtils.startAnimIn(level3);
				
			}
			
			isLevel3Show=!isLevel3Show;
			
			break;

		case R.id.ico_home:  //ִ��home ͼ��ĵ���¼���
			
			if(isLevel2Show){
				
				if(isLevel3Show){
				
					MyUtils.startAnimOut(level3);
					isLevel3Show=!isLevel3Show;
					MyUtils.startAnimOut(level2,200);
					
				}else{
					
					MyUtils.startAnimOut(level2);
				}
				
			}else{
				
				MyUtils.startAnimIn(level2);
			}
			
			
			isLevel2Show=!isLevel2Show;
			break;
			
		default:
			break;
		}
		
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode == KeyEvent.KEYCODE_MENU){
			
			if(isLevel1Show ){
				
				if(isLevel2Show){
					
					if(isLevel3Show){
					
						MyUtils.startAnimOut(level3);
						isLevel3Show=!isLevel3Show;
					}
					
					MyUtils.startAnimOut(level2,100);
					isLevel2Show=!isLevel2Show;
				}
				
				MyUtils.startAnimOut(level1,200);
				
			}else{
				
				MyUtils.startAnimIn(level1);
			}
			
			isLevel1Show = !isLevel1Show;

//			Log.i("MyLog", "KeyEvent.KEYCODE_MENU");
			
		}
		
//		Log.i("MyLog", "onKeyDown");
		
		return super.onKeyDown(keyCode, event);
	}
	

}











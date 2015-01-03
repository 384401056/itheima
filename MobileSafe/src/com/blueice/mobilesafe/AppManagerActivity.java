package com.blueice.mobilesafe;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings.TextSize;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blueice.mobilesafe.beam.AppInfo;
import com.blueice.mobilesafe.engine.AppInfoEngine;

public class AppManagerActivity extends Activity {

	private Context context;
	private TextView tv_rom;
	private TextView tv_sd;
	private LinearLayout ll_loading;
	private ListView lv_appList;
	private TextView tv_appTitle;
	private List<AppInfo> appList;
	private List<AppInfo> userApps;
	private List<AppInfo> systemApps;
	private MyAppListAdapter adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_manager);
		this.context = AppManagerActivity.this;
		init();
	}



	private void init() {
		
		tv_rom = (TextView) findViewById(R.id.tv_rom);
		tv_sd = (TextView) findViewById(R.id.tv_sd);
		lv_appList = (ListView) findViewById(R.id.lv_appList);
		ll_loading = (LinearLayout) findViewById(R.id.ll_loading);
		tv_appTitle = (TextView) findViewById(R.id.tv_appTitle);
		
		
		/*
		 * 设置listview的滚动监听，用于设置tv_appTitle的显示和改变
		 */
		lv_appList.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
			}
			
			/**
			 * firstVisibleItem:滚动过程中ListView顶端第一个Item的position.
			 */
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
				if (userApps != null && systemApps != null) {
					
					//当一开始时tv_appTitle是隐藏的，只有拖动了一个条目后才显示
					if(firstVisibleItem!=0){
						tv_appTitle.setVisibility(View.VISIBLE);
					}
					
					//当为0时隐藏tv_appTitle。
					if(firstVisibleItem==0){
						tv_appTitle.setVisibility(View.INVISIBLE);
					}
					
					// 当用户APP显示完了，改变TextView的文本内容。
					if (firstVisibleItem >= userApps.size()+1) {
						tv_appTitle.setText("系统应用(" + systemApps.size() + "个)");
					} else {
						tv_appTitle.setText("用户应用(" + userApps.size() + "个)");
					}
				}
			}
		});
		
		
		initTextView();
		adapter = new MyAppListAdapter();
		getAppData();

	}
	
	/**
	 * 在子线程中获取appList数据.
	 */
	private void getAppData() {
		ll_loading.setVisibility(View.VISIBLE);
		
		
		new Thread(){
			public void run() {
				
				userApps = new ArrayList<AppInfo>();
				systemApps = new ArrayList<AppInfo>();
				appList = AppInfoEngine.getAppInfos(context);
				
				//将手机中的应用分为两个lsit,一个放用户应用，一个放系统应用。
				for(AppInfo info:appList){
					if(info.isUserApp()){
						userApps.add(info);
					}else{
						systemApps.add(info);
					}
				}
				
				
				runOnUiThread(new Runnable() {
					public void run() {
						lv_appList.setAdapter(adapter);
						ll_loading.setVisibility(View.INVISIBLE);
					}
				});
				
				
			};
		}.start();
		
	}



	/**
	 * 初始化手机空间文本显示。
	 */
	private void initTextView() {
		
		long romSize = getAvailSpace(Environment.getDataDirectory().getAbsolutePath());//手机内存空间大小。
		long sdSize = getAvailSpace(Environment.getExternalStorageDirectory().getAbsolutePath());//SD卡空间大小。
		
		tv_rom.setText("内存可用："+ Formatter.formatFileSize(context, romSize)); //格式化后显示
		tv_sd.setText("SD卡可用："+ Formatter.formatFileSize(context, sdSize));
	}



	/**
	 * 获取指定路径的可用空间大小.
	 * @param path 路径
	 * @return 空间大小
	 */
	private long getAvailSpace(String path){
		
		StatFs statFs = new StatFs(path);
		
//		statFs.getBlockCount();//所有分区个数

		int size = statFs.getBlockSize();//每个分区的大小。
		int count = statFs.getAvailableBlocks();//可用分区的个数
		return size*count;
	}
	
	
	
	private class MyAppListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			//总数加上了两条Title.
			return userApps.size()+systemApps.size()+2;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			View view;
			ViewHodler hodler;
			AppInfo appInfo;
			
			/*
			 * 为ListView增加了2个Title.所以出现了四种需要进行判断的情况。
			 */
			
			//当ListView一开始时，
			if(position==0){
				
				TextView tv = new TextView(context);
				tv.setBackgroundColor(Color.GRAY);
				tv.setTextSize(16);
				tv.setTextColor(Color.WHITE);
				tv.setGravity(Gravity.CENTER);
				tv.setText("用户应用(" + userApps.size() + "个)");
				return tv;
			
			//当ListView到达系统应用之前时。
			}else if(position==(userApps.size()+1)){
				
				TextView tv = new TextView(context);
				tv.setBackgroundColor(Color.GRAY);
				tv.setTextSize(16);
				tv.setTextColor(Color.WHITE);
				tv.setGravity(Gravity.CENTER);
				tv.setText("系统应用(" + systemApps.size() + "个)");
				return tv;
				
			//当显示userApps时。
			}else if(position<=userApps.size()){
				
				int newPosition = position-1;//获取新的数据下标
				appInfo = userApps.get(newPosition);//取出userApps的数据。
			//当显示systemApps时。
			}else{
				
				int newPosition = position-userApps.size()-2;//获取新的数据下标
				appInfo = systemApps.get(newPosition);
				
			}
			
			if(convertView!=null && convertView instanceof RelativeLayout){
				view = convertView;
				hodler = (ViewHodler) view.getTag();
			}else{
				
				view = View.inflate(context, R.layout.list_item_appinfo, null);
				hodler = new ViewHodler();
				hodler.iv_appicon = (ImageView) view.findViewById(R.id.iv_appicon);
				hodler.tv_name = (TextView) view.findViewById(R.id.tv_name);
				hodler.tv_location = (TextView) view.findViewById(R.id.tv_location);
				hodler.tv_type = (TextView) view.findViewById(R.id.tv_type);
				hodler.btn_unstall = (Button) view.findViewById(R.id.btn_unstall);
				view.setTag(hodler);
			}
			
			//更新数据到UI
			hodler.iv_appicon.setImageDrawable(appInfo.getIcon());
			hodler.tv_name.setText(appInfo.getName());
			
			
			if(appInfo.isInRom()){
				hodler.tv_location.setText("安装位置：手机内存");
			}else{
				hodler.tv_location.setText("安装位置：外部存储");
			}

			if(appInfo.isUserApp()){
				hodler.tv_type.setText("类型:用户应用");
			}else{
				hodler.tv_type.setText("类型:系统应用");
			}
			
			if(appInfo.isUserApp()){
				
			}
			
			
			return view;
		}
		
	}
	
	
	class ViewHodler{
		
		ImageView iv_appicon;
		TextView tv_name;
		TextView tv_location;
		TextView tv_type;
		Button btn_unstall;
	}
	
	
}































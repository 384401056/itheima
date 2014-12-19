package com.blueice.mobilesafe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class Home extends Activity implements OnItemClickListener {

	private Context context;
	private TextView tv01;
	private GridView home_list;
	
	private MyListAdapter adapter;
	
	private static int[] names = {
		R.string.safe,R.string.callmsgsafe,R.string.app,
		R.string.taskmanager,R.string.netmanager,R.string.trojan,
		R.string.clear,R.string.atools,R.string.settings

	};
	
	private static int[] icons = {
		R.drawable.safe,R.drawable.callmsgsafe,R.drawable.app,
		R.drawable.taskmanager,R.drawable.netmanager,R.drawable.trojan,
		R.drawable.clear,R.drawable.atools,R.drawable.settings
	};

  	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		this.context = Home.this;
		init();
	}



	private void init() {
		
		tv01 = (TextView) findViewById(R.id.textView1);
		home_list = (GridView) findViewById(R.id.list_home);
		
		adapter = new MyListAdapter();
		home_list.setAdapter(adapter);
		
		
		home_list.setOnItemClickListener(this);
	}
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		switch (position) {
		case 8:
			
			startSettingActivity();
			
			
			break;
		}
	}
	
	/**
	 * 进入设置页面。
	 */
	private void startSettingActivity() {
		
//		Intent intert = new Intent(context,.class);
		
		
	}


	/**
	 * GridView的Adapter.
	 */
	class MyListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return names.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ViewHolder holder = null;
			
			if(convertView==null){
				
				holder = new ViewHolder();
				convertView = View.inflate(context, R.layout.list_item_home, null);
				
				holder.name = (TextView) convertView.findViewById(R.id.tv_item);
				holder.icon = (ImageView) convertView.findViewById(R.id.iv_item);
			
				convertView.setTag(holder);
				
			}else{
				
				holder = (ViewHolder) convertView.getTag();
			}
			
			
			//设置界面
			
//			convertView.setBackgroundColor(getResources().getColor(bg_color[position]));
			holder.name.setText(getResources().getString(names[position]));
			holder.icon.setImageResource(icons[position]);
			
			return convertView;
		}
		
		class ViewHolder {

			TextView name;
			ImageView icon;

		}
	
		
	}



}







































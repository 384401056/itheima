package com.blueice.mobilesafe;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.opengl.Visibility;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.blueice.mobilesafe.beam.BlackNumber;
import com.blueice.mobilesafe.db.dao.BlackListDao;
import com.blueice.mobilesafe.utils.PromptManager;

public class CallmsgsafeActivity extends Activity {

	private Context context;
	private ListView blackList;
	private Button btn_add;
	private BlackListDao dao;
	private List<BlackNumber> numbers;
	private CallmsgsafeAdapter adapter;
	
	private EditText et_number;
	private Button btn_ok;
	private Button btn_cancel;
	private CheckBox cb_phone;
	private CheckBox cb_msg;
	
	private LinearLayout ll_loading;
	private LinearLayout ll_loading2;
	
	private TextView tv_number;
	private int max = 20;//每页显示多少条。
	private int offset = 0;//从哪一条开始加载数据。
	private int dataMax = 0;//数据的总条数。
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_callmsgsafe);
		this.context = CallmsgsafeActivity.this;
		init();
	}

	private void init() {
		
		blackList = (ListView) findViewById(R.id.lv_blacklist);
		btn_add = (Button) findViewById(R.id.btn_add);
		ll_loading = (LinearLayout) findViewById(R.id.ll_loading);
		ll_loading2 = (LinearLayout) findViewById(R.id.ll_loading2);	
		
		dao = new BlackListDao(context);
		dataMax = dao.getCount();//获取数据总条数。
		
		getData();//获取数据,并加载到ListView中。
		
		/**
		 * ListView的Item点击事件。弹出修改对话框。
		 */
		blackList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				
				/**
				 * 弹出修改对话框。
				 */
				AlertDialog.Builder builder = new Builder(context);
				final AlertDialog dialog = builder.create();
				
				View dialogView = View.inflate(context, R.layout.dialog_update_blacknumber, null);
				dialog.setView(dialogView, 0, 0, 0, 0);
				
				tv_number = (TextView) dialogView.findViewById(R.id.tv_number);
				cb_phone = (CheckBox) dialogView.findViewById(R.id.cb_phone);
				cb_msg = (CheckBox) dialogView.findViewById(R.id.cb_msg);
				btn_ok = (Button) dialogView.findViewById(R.id.ok);
				btn_cancel = (Button) dialogView.findViewById(R.id.cancel);
				
				//显示选中的电话号码。
				tv_number.setText(numbers.get(position).getNumber());
				
				//根据mode中的值来确定CheckBox的选中状态。
				switch (Integer.parseInt(numbers.get(position).getMode())) {
				case 1:
					cb_phone.setChecked(true);
					break;
				case 2:
					cb_msg.setChecked(true);
					break;
				case 3:
					cb_phone.setChecked(true);
					cb_msg.setChecked(true);
					break;
				}
				
				
				final int location = position;
				
				
				/**
				 * 设置对话框的按钮点击事件。
				 */
				btn_ok.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						
						String number = numbers.get(location).getNumber();
						String newMode = "";
						if (cb_phone.isChecked() && cb_msg.isChecked()) {
							newMode = "3";
						}else if(cb_phone.isChecked()){
							newMode = "1";
						}else if(cb_msg.isChecked()){
							newMode = "2";
						}else{
							PromptManager.showToast(context, "请选择一种拦截模式");
							return;
						}
						
						dao.update(number, newMode);//更新拦截模式到数据库。
//						BlackNumber bn = new BlackNumber();
//						bn.setNumber(number);
//						bn.setMode(mode);
//						numbers.add(0, bn); //添加号码到List<BlackNumber>.
						numbers.get(location).setMode(newMode);//修改list中的数据
						
						adapter.notifyDataSetChanged(); //更新ListView.
						
						dialog.dismiss();
					}
				});

				btn_cancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				
				dialog.show();
				
			}
		});
		
		/**
		 * ListView的滚动事件。用于数据分页时的加载。
		 */
		blackList.setOnScrollListener(new OnScrollListener() {
			/**
			 * 当滚动状态发生变化时。
			 */
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_IDLE://滚动后的空闲状态。
					
					int lastPosition = blackList.getLastVisiblePosition();
					
					//如果最后一条数据等于集合大小-1，则说明用户拉到了最下面。
					if(lastPosition==(numbers.size()-1)){
						
						offset+=max;//改变查询的开始数。
						
						//判断是否到了最后一条数据。
						if(numbers.size()==dataMax){
							PromptManager.showToast(context, "没有数据了。");
							return;
						}
						getData(); //重新获取数据。
					}
					
					break;
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL://手指触摸滚动状态。
					
					break;
				case OnScrollListener.SCROLL_STATE_FLING://惯性滑行状态。
					
					break;

				default:
					break;
				}
			}
			
			/**
			 * 当正在滚动时。
			 */
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				
			}
		});
		
		//添加黑名单号码的点击事件。
		btn_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				AlertDialog.Builder builder = new Builder(context);
				final AlertDialog dialog = builder.create();
				
				View dialogView = View.inflate(context, R.layout.dialog_add_blacknumber, null);
				dialog.setView(dialogView, 0, 0, 0, 0);
				
				et_number = (EditText) dialogView.findViewById(R.id.et_number);
				cb_phone = (CheckBox) dialogView.findViewById(R.id.cb_phone);
				cb_msg = (CheckBox) dialogView.findViewById(R.id.cb_msg);
				btn_ok = (Button) dialogView.findViewById(R.id.ok);
				btn_cancel = (Button) dialogView.findViewById(R.id.cancel);
				
				btn_ok.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String number = et_number.getText().toString().trim();
						
						if(TextUtils.isEmpty(number)){
							
							PromptManager.showToast(context, "号码不能为空！");
							
						}
						
						String mode = "";
						
						if (cb_phone.isChecked() && cb_msg.isChecked()) {
							mode = "3";
						}else if(cb_phone.isChecked()){
							mode = "1";
						}else if(cb_msg.isChecked()){
							mode = "2";
						}else{
							PromptManager.showToast(context, "请选择一种拦截模式");
							return;
						}
						
						dao.add(number, mode);//添加号码到数据库。
						
						BlackNumber bn = new BlackNumber();
						bn.setNumber(number);
						bn.setMode(mode);
						numbers.add(0, bn); //添加号码到List<BlackNumber>.
						
						adapter.notifyDataSetChanged(); //更新ListView.
						
						dialog.dismiss();
					}
				});

				btn_cancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

						dialog.dismiss();
					}
				});
				
				dialog.show();
				
			}
		});
		
	}

	
	/**
	 *在子线程中获取数据。
	 */
	private void getData() {
		if(numbers==null){
			ll_loading.setVisibility(View.VISIBLE);
		}else{
			ll_loading2.setVisibility(View.VISIBLE);
		}

		new Thread() {
			public void run() {
				//如果还没有数据
				if(numbers==null){
					numbers = dao.findPart(max, offset);// 分页查询数据。
				}else{
					numbers.addAll(dao.findPart(max, offset));
				}
				
				// 在UI线程更新lsitview.
				runOnUiThread(new Runnable() {
					public void run() {
						if(adapter == null){
							adapter = new CallmsgsafeAdapter();
							blackList.setAdapter(adapter);
							ll_loading.setVisibility(View.INVISIBLE);
						} else {
							adapter.notifyDataSetChanged();
							ll_loading2.setVisibility(View.GONE);
						}
					}
				});
			};
		}.start();
	}
	
	/**
	 * 自定义ListView适配器。
	 */
	class CallmsgsafeAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return numbers.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		/**
		 * converView是那个在背后的view.
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			View view;
			ViewHolder holder;
			
			if(convertView==null){
			
				view = View.inflate(context, R.layout.list_item_callmsgsafe, null);
				holder = new ViewHolder();
				holder.tv_number = (TextView) view.findViewById(R.id.tv_number);
				holder.tv_mode = (TextView) view.findViewById(R.id.tv_mode);
				holder.iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
				
				view.setTag(holder);
			}else{
				
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}
			
			
			//设置View上的值。
			holder.tv_number.setText(numbers.get(position).getNumber());
			
			switch (Integer.parseInt(numbers.get(position).getMode())) {
				case 1:
					holder.tv_mode.setText("电话拦截");
					break;
				case 2:
					holder.tv_mode.setText("短信拦截");
					break;
				case 3:
					holder.tv_mode.setText("全部拦截");
					break;
				}
			
			final int location = position;
			
			/*
			 * 删除号码按钮事件
			 */
			holder.iv_delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					AlertDialog.Builder builder = new Builder(context);
					
					builder.setTitle("删除号码");
					builder.setMessage("确定从黑名单中删除此号码？");
					
					builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
							dao.delete(numbers.get(location).getNumber());//先删除数据库中的数据
							numbers.remove(location);//再删除List<BlackNumber>中的数据
							adapter.notifyDataSetChanged(); //最后更新ListView.
							
						}
						
					});
					
					builder.setNegativeButton("取消", null);
					builder.show();
				}
			});

			return view;
		}
		
	}
	
	static class ViewHolder{
		TextView tv_number;
		TextView tv_mode;
		ImageView iv_delete;
	}
}





















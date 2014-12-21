package com.blueice.mobilesafe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/**
 * 选择联系人页面。
 *
 */
public class SelectContactActivity extends Activity {

	private ListView contactlist;
	private List<Map<String, String>> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_contact);
		
		init();
		
		
	}
	
	private void init() {
		
		contactlist = (ListView) findViewById(R.id.list_contacts);
		
		/*
		 * ListVeiw的点击事件。
		 */
		contactlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				String phone = list.get(position).get("phone");
				
				//将电话号码返回到前一个页面。并关闭当前页面。
				Intent data = new Intent();
				data.putExtra("phone", phone);
				setResult(0, data );
				finish();
			}
		});
		
		
		list = getContactInfo();
		
		contactlist.setAdapter(new SimpleAdapter(this, list, R.layout.contact_item_view, 
				new String[]{"name","phone"}, 
				new int[]{R.id.tv_name,R.id.tv_phone}));
		
	}

	/**
	 * 获取联系人方法。
	 * @return
	 */
	private List<Map<String, String>> getContactInfo() {
		
		List<Map<String, String>> resultList = new ArrayList<Map<String,String>>();
		
		//得到一个内容解析器。
		ContentResolver resolver = getContentResolver();
	
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		Uri uriData = Uri.parse("content://com.android.contacts/data");
		
		Cursor cursor = resolver.query(uri, new String[]{"contact_id"}, null, null, null);
		
		while (cursor.moveToNext()) {
			String id = cursor.getString(0);
			
			if(id!=null){
				
				Map<String, String> map = new HashMap<String, String>();
				
				Cursor cursorData = resolver.query(uriData, new String[]{"data1","mimetype"},"contact_id=?", new String[]{id}, null);
				
				while(cursorData.moveToNext()){
					
					String data = cursorData.getString(0);
					String mimetype = cursorData.getString(1);

					if("vnd.android.cursor.item/phone_v2".equals(mimetype)){
						map.put("phone", data);
					}
					
					if("vnd.android.cursor.item/name".equals(mimetype)){
						map.put("name", data);
					}
				}
				
				resultList.add(map);
				
				cursorData.close();
				
			}
		}
		cursor.close();
		
		return resultList;
	}
}

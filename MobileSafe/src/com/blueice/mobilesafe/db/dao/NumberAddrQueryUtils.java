package com.blueice.mobilesafe.db.dao;

import com.blueice.mobilesafe.ConstValue;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NumberAddrQueryUtils {

	/**
	 * 查询数据库，
	 * 
	 * 数据库文件虽然已经放到raw文件下，但是还是必须复制到 data/data/com.blueice.mobilesafe/...的目录下才能被
	 * SQLiteDatabase找到,所以在程序一运行时就应该查检此文件是否存在，如果不存在则复制。
	 * 
	 * @param number
	 *            电话号码。
	 * @return
	 */
	public static String queryNumber(String number) {

		String address = "";
		
		SQLiteDatabase database = SQLiteDatabase.openDatabase(
				ConstValue.DBPATH, null, SQLiteDatabase.OPEN_READONLY);

		// 手机号码的正则表达式：^1[34568]\\d{9}$
		if (number.matches("^1[34568]\\d{9}$")) {

			// 以只读方式打开数据库文件。查询的手机号码只能要前7位，否则无法找到。.substring(0, 7)
			Cursor cursor = database.rawQuery(
					"select location from data2 where id = (select outkey from data1 where id=?)",
					new String[] { number.substring(0, 7)});

			while (cursor.moveToNext()) {
				address = cursor.getString(0);
			}

			cursor.close();
		
		//其它非手机号码。
		}else{
			
			switch (number.length()) {
			case 3:
				address="公共号码";
				break;
			case 4:
				address="模拟器号码";
				break;
			case 5:
			case 6:
				address="客服号码";
				break;
			case 7:
			case 8:
				address="本地号码";
				break;

			default:
				//长途电话。大于10位，第一位为0.
				if(number.length()>=10&&number.startsWith("0")){
					
					Cursor cursor = database.rawQuery("select location from data2 where area=?",
							new String[] {number.substring(1, 3) }); 
					
					while (cursor.moveToNext()) {
						String location = cursor.getString(0);
						
						address = location.substring(0, location.length()-2);
					}
					
					cursor.close();
					
					cursor = database.rawQuery("select location from data2 where area=?",
							new String[] {number.substring(1, 4) }); 
					
					while (cursor.moveToNext()) {
						String location = cursor.getString(0);
						
						address = location.substring(0, location.length()-2);
					}
					
					cursor.close();
					
				}
				break;
			}
		}
		
		return address;
	}

}














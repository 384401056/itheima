package com.blueice.mobilesafe.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BlackListDBOpenHelper extends SQLiteOpenHelper {

	/**
	 * 数据库创建的构造方法。
	 * @param context
	 */
	public BlackListDBOpenHelper(Context context) {
		super(context, "callmsgsafe", null, 1);
	}

	/**
	 * 初始化数据库的表结构
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table blacklist (_id integer primary key autoincrement,number varchar(20),mode varchar(2))";
		db.execSQL(sql);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		
		
	}

}

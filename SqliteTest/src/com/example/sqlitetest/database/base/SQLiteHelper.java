package com.example.sqlitetest.database.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 数据帮助类，使用单例模式。
 * @author Administrator
 *
 */
public class SQLiteHelper extends SQLiteOpenHelper {

	private static SQLiteDatabaseConfig sqlConfig;
	
	private static SQLiteHelper instance;
	
	private Context context;
	
	private SQLiteHelper(Context context){
		super(context, sqlConfig.getDatabaseName(), null, sqlConfig.getVersion());
		this.context = context;
	}

	public static SQLiteHelper getInstance(Context context){
		if(instance == null){
			instance = new SQLiteHelper(context);
		}
		return instance;
	}
	
	
	/**
	 * 创建数据库
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i("MyLog","onCreate");
	}

	/**
	 * 更新数据库.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i("MyLog","onUpgrade");
	}
	
	/**
	 * 一个创建数据库表的接口
	 */
	public interface SQLiteDataTable{
		/**
		 * 创建表
		 * @param database 数据库对象
		 */
		void onCreate(SQLiteDatabase database);
		
		/**
		 * 更新表
		 * @param database 数据库对象
		 */
		void onUpgrade(SQLiteDatabase database);
	}
	

}












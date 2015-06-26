package com.example.sqlitetest.database.base;

import com.example.sqlitetest.database.base.SQLiteHelper.SQLiteDataTable;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 数据库操作类。
 *
 */
public abstract class SQLiteDALBase implements SQLiteDataTable {

	private Context context;
	private SQLiteDatabase database; //数据库.
	
	public SQLiteDALBase(Context context) {
		this.context = context;
	}

	protected Context getContext() {
		return context;
	}

	/**
	 * 获取数据库对象。
	 * @return
	 */
	public SQLiteDatabase getDatabase() {
		if(database == null){
			//getWritableDatabase()执行，会调用SQLiteHelper中的onCreate()， onUpgrade()或者onOpen()方法。
			database = SQLiteHelper.getInstance(context).getWritableDatabase();
		}

		return database;
	}

	/**
	 * 开起一个事务。
	 */
	public void beginTransaction(){
		getDatabase().beginTransaction();
	}
	
	/**
	 * 设置事务执行成功。
	 */
	public void setTransactionSuccessful(){
		getDatabase().setTransactionSuccessful();
	}
	
	/**
	 * 结束事务。
	 */
	public void endTransaction(){
		getDatabase().endTransaction();
	}
	
	/**
	 * 获取指定条件的数据条数。
	 * @param condition
	 * @return 数据条数
	 */
	protected int getCount(String condition) {

		String[] strings = GetTableNameAndPK();
		Cursor cursor = execSql("select ? form ? where 1=1 " + condition,strings);
		int count = cursor.getCount();
		cursor.close();
		return count;
	}
	
	
	protected int getCount(String tableName,String PK,String condition){
		Cursor cursor = execSql("select " + PK + " form " + tableName + " where 1=1 " + condition);
		int count = cursor.getCount();
		cursor.close();
		return count;
	}
	
	/**
	 * 删除数据
	 * @param tableName 表名
	 * @param condition 条件
	 * @return 成功为true.
	 */
	protected Boolean delete(String tableName,String condition){
		
		return getDatabase().delete(tableName, condition, null)>=0;
		
	}
	

	/**
	 * 执行sql语句。
	 * @param strSql
	 * @return 结果集
	 */
	protected Cursor execSql(String strSql){
		
		return getDatabase().rawQuery(strSql, null);
		
	}
	
	protected Cursor execSql(String strSql,String[] params){
		return getDatabase().rawQuery(strSql, params);
	}

	protected abstract String[] GetTableNameAndPK();
	
	protected abstract Object findModel(Cursor cursor);
}



















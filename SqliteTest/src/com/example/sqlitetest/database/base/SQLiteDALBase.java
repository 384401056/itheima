package com.example.sqlitetest.database.base;

import java.util.ArrayList;
import java.util.List;

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
	 * @param condition 条件
	 * @return 数据条数
	 */
	protected int getCount(String condition) {

		String[] strings = GetTableNameAndPK();
		Cursor cursor = execSql("select ? form ? where 1=1 " + condition,strings);
		int count = cursor.getCount();
		cursor.close();
		return count;
	}
	
	/**
	 * 获取指定条件的数据条数。
	 * @param tableName 表名
	 * @param PK 主键
	 * @param condition 条件
	 * @return
	 */
	protected int getCount(String tableName,String PK,String condition){
		Cursor cursor = execSql("select " + PK + " form " + tableName + " where 1=1 " + condition);
		int count = cursor.getCount();
		cursor.close();
		return count;
	}
	
	/**
	 * 获取查询列表
	 * @param sqlStr sql语句。
	 * @return list
	 */
	protected List getList(String sqlStr){
		Cursor cursor = execSql(sqlStr);
		return CursorToList(cursor);
	}
	
	/**
	 * 将游标转为List
	 * @param cursor
	 * @return list
	 */
	protected List CursorToList(Cursor cursor){
		
		List list = new ArrayList();
		while (cursor.moveToNext()) {
			
			Object obj = findModel(cursor);
			list.add(obj);
		}
		cursor.close();
		
		return list;
	}
	
	/**
	 * 删除数据
	 * @param tableName 表名
	 * @param condition 条件
	 * @return 是否成功.
	 */
	protected Boolean delete(String tableName,String condition){
		
		return getDatabase().delete(tableName, condition, null)>0;
		
	}
	

	/**
	 * 执行sql语句。
	 * @param strSql
	 * @return 结果集
	 */
	protected Cursor execSql(String sqlStr){
		
		return getDatabase().rawQuery(sqlStr, null);
		
	}
	
	protected Cursor execSql(String sqlStr,String[] params){
		return getDatabase().rawQuery(sqlStr, params);
	}

	/**
	 * 获取表名和主键名。
	 * @return
	 */
	protected abstract String[] GetTableNameAndPK();
	
	/**
	 * @param cursor
	 * @return
	 */
	protected abstract Object findModel(Cursor cursor);
}



















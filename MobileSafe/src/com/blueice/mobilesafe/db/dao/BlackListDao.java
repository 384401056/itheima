package com.blueice.mobilesafe.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.blueice.mobilesafe.beam.BlackNumber;
import com.blueice.mobilesafe.db.BlackListDBOpenHelper;

/**
 * 通讯卫士中黑名单数据库的增、删、改、查的业务类。
 *
 */
public class BlackListDao {

	private BlackListDBOpenHelper helper;

	public BlackListDao(Context context) {
		helper = new BlackListDBOpenHelper(context);
	}

	/**
	 * 查询黑名单号码是否存在。
	 * 
	 * @param number
	 *            电话号码
	 * @return 返回是否找到数据。
	 */
	public boolean find(String number) {
		boolean result = false;
		SQLiteDatabase db = helper.getReadableDatabase();// 只读.
		Cursor cursor = db.rawQuery("select * from blacklist where number=?",
				new String[] { number });
		result = cursor.moveToNext();
		cursor.close();
		db.close();

		return result;
	}

	/**
	 * 通过号码查询该号码的拦截模式。如果没有此号码返回null
	 * 
	 * @param number
	 *            电话号码
	 * @return 返回拦截模式，如果没找到返回null
	 */
	public String findMode(String number) {
		String result = null;
		SQLiteDatabase db = helper.getReadableDatabase();// 只读.
		Cursor cursor = db.rawQuery("select * from blacklist where number=?",
				new String[] { number });
		while (cursor.moveToNext()) {
			result = cursor.getString(cursor.getColumnIndex("mode"));
		}
		return result;
	}

	/**
	 * 添加黑名单号码。
	 * 
	 * @param number
	 *            号码
	 * @param mode
	 *            拦截模式(1电话拦截，2短信拦截，3全部拦截。）
	 * @return long 插入的行ID，如果为-1则是添加失败
	 */
	public long add(String number, String mode) {

		SQLiteDatabase db = helper.getWritableDatabase();// 可写。
		ContentValues values = new ContentValues();

		values.put("number", number);
		values.put("mode", mode);
		long insert = db.insert("blacklist", null, values);
		db.close();

		return insert;
	}

	/**
	 * 删除黑名单号码。
	 * 
	 * @param number
	 *            号码
	 * @return 受影响的行数
	 */
	public int delete(String number) {

		SQLiteDatabase db = helper.getWritableDatabase();// 可写。
		int delete = db
				.delete("blacklist", "number=?", new String[] { number });
		db.close();
		return delete;
	}

	/**
	 * 修改黑名单拦截模式。
	 * 
	 * @param number
	 *            号码
	 * @param mode
	 *            新的模式.(1电话拦截，2短信拦截，3全部拦截。)
	 * @return 受影响的行数
	 */
	public int update(String number, String mode) {

		SQLiteDatabase db = helper.getWritableDatabase();// 可写。

		ContentValues values = new ContentValues();
		values.put("mode", mode);
		int update = db.update("blacklist", values, "number=?",
				new String[] { number });
		db.close();
		return update;

	}

	/**
	 * 查询所有黑名单号码。
	 * 
	 * @return List<BlackNumber>
	 */
	public List<BlackNumber> findAll() {

		List<BlackNumber> result = new ArrayList<BlackNumber>();
		SQLiteDatabase db = helper.getReadableDatabase();// 只读.
		Cursor cursor = db.rawQuery(
				"select * from blacklist order by _id desc", null);

		while (cursor.moveToNext()) {
			BlackNumber blackNumber = new BlackNumber();
			String number = cursor.getString(cursor.getColumnIndex("number"));
			String mode = cursor.getString(cursor.getColumnIndex("mode"));
			blackNumber.setNumber(number);
			blackNumber.setMode(mode);
			result.add(blackNumber);
		}
		return result;
	}

	/**
	 * 分页查询数据
	 * @param max一次查询多少条记录
	 * @param offset从哪条记录开始
	 * @return List<BlackNumber>
	 */
	public List<BlackNumber> findPart(int max, int offset) {

		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<BlackNumber> result = new ArrayList<BlackNumber>();
		SQLiteDatabase db = helper.getReadableDatabase();// 只读.
		Cursor cursor = db.rawQuery(
				"select * from blacklist order by _id desc limit ? offset ?",
				new String[] { String.valueOf(max), String.valueOf(offset) });

		while (cursor.moveToNext()) {
			BlackNumber blackNumber = new BlackNumber();
			String number = cursor.getString(cursor.getColumnIndex("number"));
			String mode = cursor.getString(cursor.getColumnIndex("mode"));
			blackNumber.setNumber(number);
			blackNumber.setMode(mode);
			result.add(blackNumber);
		}
		return result;
	}
	
	/**
	 * 返回数据的总条数。
	 * @return
	 */
	public int getCount(){
		int result = 0;
		SQLiteDatabase db = helper.getReadableDatabase();// 只读.
		Cursor cursor = db.rawQuery("select count(*) from blacklist",null);
		
		while (cursor.moveToNext()) {
			result = Integer.parseInt(cursor.getString(0)); 
		}
		return result;
	}
}



















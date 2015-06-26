package com.example.sqlitetest.database.sqlitedal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sqlitetest.database.base.SQLiteDALBase;

public class SQLiteDALUser extends SQLiteDALBase {

	public SQLiteDALUser(Context context) {
		super(context);
	}

	@Override
	protected String[] GetTableNameAndPK() {
		return null;
	}

	@Override
	protected Object findModel(Cursor cursor) {
		return null;
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase database) {
		
	}

}

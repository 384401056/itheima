package com.example.sqlitetest;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.sqlitetest.database.sqlitedal.SQLiteDALUser;

public class MainActivity extends Activity {

	private Context context;
	private SQLiteDALUser user;
	private SQLiteDatabase database;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.context = MainActivity.this;
		init();

	}

	private void init() {
		user = new SQLiteDALUser(context);
		database = user.getDatabase();
	}
}

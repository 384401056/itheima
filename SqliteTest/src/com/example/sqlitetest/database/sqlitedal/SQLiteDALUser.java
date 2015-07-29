package com.example.sqlitetest.database.sqlitedal;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sqlitetest.R;
import com.example.sqlitetest.database.base.SQLiteDALBase;
import com.example.sqlitetest.model.ModelUser;
import com.example.sqlitetest.utils.DateTools;

public class SQLiteDALUser extends SQLiteDALBase {

	public SQLiteDALUser(Context context) {
		super(context);
	}
	
	/**
	 * 在User中插入数据
	 * @param user 数据模型
	 * @return 是否成功
	 */
	public Boolean InsertUser(ModelUser user){
		ContentValues cValues = createParms(user);
		//插入数据到表中,返回ID。
		long insertID = getDatabase().insert(GetTableNameAndPK()[0], null, cValues);
		//将返回的ID值赋给Mode，以备后用。
		user.setUserID((int)insertID);
		
		return insertID>0;
	}
	
	/**
	 * 删除数据
	 * @param condition 条件
	 * @return 是否成功
	 */
	public Boolean deleteUser(String condition){
		//调整用父类的方法，删除数据。
		return delete(GetTableNameAndPK()[0], condition);
	}
	
	/**
	 * 修改数据.
	 * @param user 数据模型
	 * @param condition 条件
	 * @return 是否成功
	 */
	public Boolean updateUser(ModelUser user,String condition){
		ContentValues cValues = createParms(user);
		//修改数据.
		return getDatabase().update(GetTableNameAndPK()[0], cValues, condition, null)>0;
	}
	
	/**
	 * 获取User表数据
	 * @param condition 条件。 
	 * @return
	 */
	public List<ModelUser> getUser(String condition) {
		String sqlStr = "Select * Form User Where 1=1" + condition;
		return getList(sqlStr);
	}
	

	@Override
	protected String[] GetTableNameAndPK() {
		return new String[]{"User","UserID"};
	}

	/**
	 * 将Cursor转为数据模型。
	 */
	@Override
	protected Object findModel(Cursor cursor) {
		
		ModelUser user = new ModelUser();
		user.setUserID(cursor.getInt(cursor.getColumnIndex("UserID")));
		user.setUserName(cursor.getString(cursor.getColumnIndex("UserName")));
		user.setCreateDate(DateTools.getDateFromString(cursor.getString(cursor.getColumnIndex("CreateDate")), "yyyy-MM-dd HH:mm:ss"));
		user.setState(cursor.getInt(cursor.getColumnIndex("State")));
		return null;
	}

	/**
	 * 创建表
	 */
	@Override
	public void onCreate(SQLiteDatabase database) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" Create  TABLE MAIN.[User](" );
		stringBuilder.append(" [UserID] integer PRIMARY KEY AUTOINCREMENT NOT NULL" );
		stringBuilder.append(" ,[UserName] varchar(10) NOT NULL" );
		stringBuilder.append(" ,[CreateDate] datetime NOT NULL" );
		stringBuilder.append(" ,[State] integer NOT NULL DEFAULT 1" );
		stringBuilder.append(" )" );
		database.execSQL(stringBuilder.toString());
		
		initUserTable(database);
	}

	/**
	 * 初始化User表中的数据。
	 * @param database
	 */
	private void initUserTable(SQLiteDatabase database) {
		ModelUser user = new ModelUser();
		String[] arrayUser = getContext().getResources().getStringArray(R.array.initusertable);
		
		for (int i = 0; i < arrayUser.length; i++) {
			user.setUserName(arrayUser[i]);
			ContentValues cValues = createParms(user);
			database.insert(GetTableNameAndPK()[0], null, cValues);//插入数据
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase database) {
		
	}
	
	
	private ContentValues createParms(ModelUser user){
		
		ContentValues contentValues = new ContentValues();
		contentValues.put("UserName", user.getUserName());
		//使用日期工具类将日期转换为String.
		contentValues.put("CreateDate", DateTools.getFormatDateTime(user.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
		contentValues.put("UserName", user.getUserName());
		
		return contentValues;
	}

}



















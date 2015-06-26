package com.example.sqlitetest.database.base;


/**
 * Sqlite数据库配置。使用单例模式
 *
 */
public class SQLiteDatabaseConfig {

	private static final String DATABASE_NAME = "MyDataBase";//数据库名
 
	private static final int VERSION = 1;//数据库版本.
	
	private static SQLiteDatabaseConfig instance;//数据库配置的实例。
	
	private SQLiteDatabaseConfig() {
		

	}
	
	public static SQLiteDatabaseConfig getInstance(){
		
		if(instance == null){
			instance = new SQLiteDatabaseConfig();
		}
		
		return instance;
	}

	public static String getDatabaseName() {
		return DATABASE_NAME;
	}

	public static int getVersion() {
		return VERSION;
	}
	
	

}

package com.blueice.mobilesafe.test;

import java.util.List;
import java.util.Random;

import com.blueice.mobilesafe.beam.BlackNumber;
import com.blueice.mobilesafe.db.BlackListDBOpenHelper;
import com.blueice.mobilesafe.db.dao.BlackListDao;

import android.test.AndroidTestCase;
import android.util.Log;

public class testBlackListDB extends AndroidTestCase {

	public void testCreateDB() throws Exception{
		
		BlackListDBOpenHelper helper = new BlackListDBOpenHelper(getContext());
		helper.getWritableDatabase();
		
	}
	
	public void testFindAll() throws Exception{
		
		BlackListDao dao = new BlackListDao(getContext());
		List<BlackNumber> list = dao.findAll();
		for(BlackNumber item:list){
			Log.i("MyLog", item.toString());
		}
	}
	
	
	public void testFindMode(){
		
		BlackListDao dao = new BlackListDao(getContext());
		String mode = dao.findMode("120");
		Log.i("MyLog", mode);
		
	}
	
	
	public void testAdd() throws Exception{
		
		BlackListDao dao = new BlackListDao(getContext());
		long baseNumber = 13600000000l;
		Random random = new Random();
		for(int i = 0;i<100;i++){
			dao.add(String.valueOf(baseNumber+i), String.valueOf(random.nextInt(3)+1));
		}
	}
	
	
	public void testUpdate() throws Exception{
		
		BlackListDao dao = new BlackListDao(getContext());
		dao.update("13958860150", "1");
	}
	
	
	public void testFind(){
		BlackListDao dao = new BlackListDao(getContext());
		if(dao.find("13658860150")){
			Log.i("MyLog", "testFind");
		}
	}
	
	
	public void testDelete() throws Exception{
		
		BlackListDao dao = new BlackListDao(getContext());
		dao.delete("13758860150");
	}
}

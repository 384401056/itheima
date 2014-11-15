package com.blueice.test;

import android.test.AndroidTestCase;

public class MyTest extends AndroidTestCase {

	
	public void firstTest() throws Exception{
		
		int result = 20+10;
		assertEquals(30, result); //断言，result如果不等于30就出错。
	}
	
	
}

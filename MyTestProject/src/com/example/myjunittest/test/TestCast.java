package com.example.myjunittest.test;

import com.blueict.Utils.MyUtils;
import android.test.AndroidTestCase;


/*
 * 这是一个测试工程，是用来测试另一个目标工程的。
 */
public class TestCast extends AndroidTestCase {

	
	public void firstTest() throws Exception{
		
		//这里调用的就是目标工程的类。
		int result = MyUtils.incrment(10, 30); 
		
		assertEquals(40, result);
	}
	
	
}

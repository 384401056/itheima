package com.example.myjunittest.test;

import com.blueict.Utils.MyUtils;
import android.test.AndroidTestCase;


/*
 * ����һ�����Թ��̣�������������һ��Ŀ�깤�̵ġ�
 */
public class TestCast extends AndroidTestCase {

	
	public void firstTest() throws Exception{
		
		//������õľ���Ŀ�깤�̵��ࡣ
		int result = MyUtils.incrment(10, 30); 
		
		assertEquals(40, result);
	}
	
	
}

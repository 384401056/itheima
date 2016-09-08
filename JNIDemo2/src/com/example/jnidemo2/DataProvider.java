package com.example.jnidemo2;

public class DataProvider {
	static{
		System.loadLibrary("Funcs");
	}
	public native int add(int x,int y);
	public native String returnStr(String str);
	public native int[] intMethod(int[] array);
}

package com.example.spinnerDemo;

import java.util.zip.Inflater;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends Activity {

	private View conentView;
	private TextView textView01 = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		textView01 = (TextView) findViewById(R.id.textView01);
		
		LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(MainActivity.this.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.pop, null);
		
        PopupWindow popWindow = new PopupWindow();
        popWindow.setContentView(conentView);
        popWindow.setWidth(200);
        popWindow.setHeight(400);
        popWindow.setFocusable(true);
        
        popWindow.showAsDropDown(textView01, 0, 0);
	}
}

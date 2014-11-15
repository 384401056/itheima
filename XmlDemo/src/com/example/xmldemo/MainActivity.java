package com.example.xmldemo;

import java.util.List;

import com.example.Utils.XmlAnalyse;
import com.example.domain.Person;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.view.View;

public class MainActivity extends Activity{

	private Button btnWrite;
	private Button btnRead;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnWrite = (Button) findViewById(R.id.button1);
		btnWrite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				XmlAnalyse.WriteXMLToLocal();
				
			}
		});

		btnRead = (Button) findViewById(R.id.button2);
		btnRead.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				List<Person> list = XmlAnalyse.PullParesXMLFromLocal();
				
				for (Person person : list) {

					Log.i("MyLog",person.toString());
					
				}
				
			}
		});
		
		
	}


}



















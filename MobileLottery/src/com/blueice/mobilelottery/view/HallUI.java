package com.blueice.mobilelottery.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blueice.mobilelottery.ConstValue;
import com.blueice.mobilelottery.R;

/**
 * 购彩大厅的界面。
 *
 */
public class HallUI extends BaseUI{

	private ImageView ivSSQ = null;
	private TextView tvSSQ = null;
	private ImageView ivSD = null;
	private TextView tvSD = null;
	private ImageView ivQLC = null;
	private TextView tvQLC = null;
	
	public HallUI(Context context) {
		super(context);
	}

	@Override
	public void init() {
		
		// 从布局文件中得到View.
		showInMiddle = (LinearLayout) View.inflate(context, R.layout.il_hall,null);

		ivSSQ = (ImageView) findViewById(R.id.ii_hall_ssq_bet);
		ivSD = (ImageView) findViewById(R.id.ii_hall_3d_bet);
		ivQLC = (ImageView) findViewById(R.id.ii_hall_qlc_bet);
		
		tvSSQ = (TextView) findViewById(R.id.ii_hall_ssq_summary);
		tvSD = (TextView) findViewById(R.id.ii_hall_3d_summary);
		tvQLC = (TextView) findViewById(R.id.ii_hall_qlc_summary);
		
	}
	
	@Override
	public void setOnClickListener() {
		ivSSQ.setOnClickListener(this);
		ivSD.setOnClickListener(this);
		ivQLC.setOnClickListener(this);
	}
	
	@Override
	public int getID() {
		return ConstValue.VIEW_HELL;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		
		switch (id) {
		case R.id.ii_hall_ssq_bet:
			
			break;
		case R.id.ii_hall_3d_bet:
			
			break;
		case R.id.ii_hall_qlc_bet:
			
			break;
		}
	}

}














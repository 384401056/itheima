package com.blueice.mobilelottery.view;

import com.blueice.mobilelottery.ConstValue;
import com.blueice.mobilelottery.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class PreBet extends BaseUI {
	// 通用三步

	// ①填充ListView
	// ②提示信息处理
	// ③倍投和追期的设置
	// ④立即购买

	private TextView bettingNum;// 注数
	private TextView bettingMoney;// 金额

	private Button subAppnumbers;// 减少倍投
	private TextView appnumbersInfo;// 倍数
	private Button addAppnumbers;// 增加倍投

	private Button subIssueflagNum;// 减少追期
	private TextView issueflagNumInfo;// 追期
	private Button addIssueflagNum;// 增加追期

	private ImageButton lotteryPurchase;// 投注
	private ListView shoppingList;// 购物车展示

	public PreBet(Context context) {
		super(context);
	}

	@Override
	public void init() {
		showInMiddle = (ViewGroup) View.inflate(context,R.layout.il_play_prefectbetting, null);

		bettingNum = (TextView) findViewById(R.id.ii_shopping_list_betting_num);
		bettingMoney = (TextView) findViewById(R.id.ii_shopping_list_betting_money);

		subAppnumbers = (Button) findViewById(R.id.ii_sub_appnumbers);
		appnumbersInfo = (TextView) findViewById(R.id.ii_appnumbers);
		addAppnumbers = (Button) findViewById(R.id.ii_add_appnumbers);

		subIssueflagNum = (Button) findViewById(R.id.ii_sub_issueflagNum);
		issueflagNumInfo = (TextView) findViewById(R.id.ii_issueflagNum);
		addIssueflagNum = (Button) findViewById(R.id.ii_add_issueflagNum);

		lotteryPurchase = (ImageButton) findViewById(R.id.ii_lottery_purchase);
		shoppingList = (ListView) findViewById(R.id.ii_lottery_shopping_list);

	}

	@Override
	public void setOnClickListener() {
		// 倍数
		addAppnumbers.setOnClickListener(this);
		subAppnumbers.setOnClickListener(this);
		// 追期
		addIssueflagNum.setOnClickListener(this);
		subIssueflagNum.setOnClickListener(this);
		// 投注
		lotteryPurchase.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ii_add_appnumbers:
			// 增加倍数
			
			break;
		case R.id.ii_sub_appnumbers:
			// 减少倍数
		
			break;
		case R.id.ii_add_issueflagNum:
			// 增加追期
			
			break;
		case R.id.ii_sub_issueflagNum:
			// 减少追期
			
			break;

		case R.id.ii_lottery_purchase:
			// 投注请求
			
			break;
		}

	}

	@Override
	public void OnResume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnPause() {
		// TODO Auto-generated method stub

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ConstValue.VIEW_PREBET;
	}

}

package com.blueice.mobilelottery.view;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.blueice.mobilelottery.ConstValue;
import com.blueice.mobilelottery.GlobalParams;
import com.blueice.mobilelottery.R;
import com.blueice.mobilelottery.bean.ShoppingCar;
import com.blueice.mobilelottery.bean.Ticket;
import com.blueice.mobilelottery.bean.User;
import com.blueice.mobilelottery.engine.UserEngine;
import com.blueice.mobilelottery.net.protocal.Message;
import com.blueice.mobilelottery.net.protocal.Oelement;
import com.blueice.mobilelottery.net.protocal.element.BetElement;
import com.blueice.mobilelottery.utils.EngineFactory;
import com.blueice.mobilelottery.utils.PromptManager;
import com.blueice.mobilelottery.view.manager.MiddleManager;

public class PreBet extends BaseUI {
	// 通用三步

	// ①填充ListView
	// ②提示信息处理,高亮显示。
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
	
	private MyListAdapter adapter;
	

	public PreBet(Context context) {
		super(context);
	}

	@Override
	public void init() {
		showInMiddle = (ViewGroup) View.inflate(context,
				R.layout.il_play_prefectbetting, null);

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
		
		
		
		
		
		adapter = new MyListAdapter();
		shoppingList.setAdapter(adapter);

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
	
	
	private void changeNotice(){
		
		Long lotterynumber = ShoppingCar.getInstance().getLotterynumber();
		Long lotteryvalue = ShoppingCar.getInstance().getLotteryvalue();
		float money = GlobalParams.MONEY;
		
		String notice1 = context.getResources().getString(R.string.is_shopping_list_betting_num);
		String notice2 = context.getResources().getString(R.string.is_shopping_list_betting_money);
		
		notice1 = StringUtils.replace(notice1, "NUM", String.valueOf(lotterynumber));
		notice2 = StringUtils.replaceEach(notice2, new String[]{"MONEY1","MONEY2"}, new String[]{String.valueOf(lotteryvalue),String.valueOf(money)});
		
		
		appnumbersInfo.setText(ShoppingCar.getInstance().getAppnumbers().toString());
		issueflagNumInfo.setText(ShoppingCar.getInstance().getIssuesnumbers().toString());
		
		bettingNum.setText(Html.fromHtml(notice1));
		bettingMoney.setText(Html.fromHtml(notice2));
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ii_add_appnumbers:
			// 增加倍数
			
			if(ShoppingCar.getInstance().optionAppnumbers(true)){
				changeNotice();
			}
			
			break;
		case R.id.ii_sub_appnumbers:
			// 减少倍数
			if(ShoppingCar.getInstance().optionAppnumbers(false)){
				changeNotice();
			}
			break;
		case R.id.ii_add_issueflagNum:
			// 增加追期

			if(ShoppingCar.getInstance().optionIssuesnumbers(true)){
				changeNotice();
			}
			break;
		case R.id.ii_sub_issueflagNum:
			// 减少追期
			if(ShoppingCar.getInstance().optionIssuesnumbers(false)){
				changeNotice();
			}
			break;

		case R.id.ii_lottery_purchase:
			// 投注请求
			
			if(ShoppingCar.getInstance().getLotteryvalue()<GlobalParams.MONEY){
				User user = new User();
				user.setUserName(GlobalParams.USERNAME);
				
				new MyAsynTask<User>() {
					@Override
					protected Message doInBackground(User... params) {
	
						UserEngine engine = EngineFactory.Instance(UserEngine.class);
						return engine.bet(params[0]);
					}
					
					@Override
					protected void onPostExecute(Message result) {
						if(result!=null){
							Oelement oelement = result.getBody().getOelement();
							if(ConstValue.SUCCESS.equals(oelement.getErrorcode())){
								// 修改用户的余额信息
								BetElement element = (BetElement) result.getBody().getElements().get(0);
								GlobalParams.MONEY = Float.parseFloat(element.getActvalue());
								
								// 清理返回键
								MiddleManager.getInstance().clear();
								// 跳转到购彩大厅，提示对话框
								MiddleManager.getInstance().changeUI(HallUI.class);
								PromptManager.showErrorDialog(context, "投注成功");
								// 清空购物车
								ShoppingCar.getInstance().clear();
							}else{
								PromptManager.showToast(context, "投注失败...");
							}
							
						}else{
							PromptManager.showToast(context, "服务器忙...稍后请重试。");
						}
					}
					
				}.executeProxy(user);
				
			}else{
				PromptManager.showToast(context, "余额不足。");
			}
			
			
			break;
		}

	}

	@Override
	public void OnResume() {
		changeNotice();
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

	class MyListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return ShoppingCar.getInstance().getTickets().size();
		}

		@Override
		public Object getItem(int position) {
			return ShoppingCar.getInstance().getTickets().get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;

			if (convertView == null) {

				holder = new ViewHolder();
				convertView = View.inflate(context, R.layout.il_play_prefectbetting_row,
						null);

				
				holder.redNum = (TextView) convertView.findViewById(R.id.ii_shopping_item_reds);
				holder.blueNum = (TextView) convertView.findViewById(R.id.ii_shopping_item_blues);

				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// 设置界面的显示
			Ticket ticket = ShoppingCar.getInstance().getTickets().get(position);
			
			holder.redNum.setText(ticket.getRedNum());
			holder.blueNum.setText(ticket.getBlueNum());
			
			
			return convertView;
		}

		class ViewHolder {
			TextView redNum;
			TextView blueNum;
		}

	}

}

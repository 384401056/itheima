package com.blueice.mobilelottery.view;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.blueice.mobilelottery.net.protocal.element.UserLoginElement;
import com.blueice.mobilelottery.utils.PromptManager;
import com.blueice.mobilelottery.view.manager.MiddleManager;

public class Shopping extends BaseUI {

	//通用三步
	
	
	//1.填充购手车。
	//2.添加自选+机选。
	//3.清空购物车。
	//4.高亮提示信息处理。注数：  金额：  元。 changeNotice(); 
	//5.购买。
	private Button btnOptional;
	private Button btnRandom;
	private Button btnBuy;
	private ListView shoppingList;
	private ImageButton btnClear;
	private TextView tvNotice;
	private MyListAdapter adapter;
	
	public Shopping(Context context) {
		super(context);
	}

	
	@Override
	public void init() {

		showInMiddle = (ViewGroup) View.inflate(context, R.layout.il_shopping, null);
		
		btnOptional = (Button) findViewById(R.id.ii_add_optional);
		btnRandom = (Button) findViewById(R.id.ii_add_random);
		shoppingList = (ListView) findViewById(R.id.ii_shopping_list);
		btnClear = (ImageButton) findViewById(R.id.ii_shopping_list_clear);
		btnBuy = (Button) findViewById(R.id.ii_lottery_shopping_buy);
		
		tvNotice = (TextView) findViewById(R.id.ii_shopping_lottery_notice);
		
		adapter = new MyListAdapter();
		shoppingList.setAdapter(adapter);
		
		
	}

	@Override
	public void OnResume() {
//		changeNotice();
	}

	@Override
	public void OnPause() {
		// TODO Auto-generated method stub

	}

	

	@Override
	public void setOnClickListener() {
		btnOptional.setOnClickListener(this);
		btnRandom.setOnClickListener(this);
		btnClear.setOnClickListener(this);
		btnBuy.setOnClickListener(this);
		
		
	}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.ii_add_optional:
			MiddleManager.getInstance().goback();
			break;
		case R.id.ii_add_random:
			randomOneTicket();
			break;
		case R.id.ii_shopping_list_clear:
			
			ShoppingCar.getInstance().getTickets().clear();
			
			adapter.notifyDataSetChanged();
			
			changeNotice();
			
			break;
		case R.id.ii_lottery_shopping_buy:
			//1.判断购物车中是否有投注。
			if(ShoppingCar.getInstance().getTickets().size()>=1){
				
				//2.用户是否登陆。
				if(GlobalParams.isLogin){
					
					//3.用户的余额够吗？
					if(ShoppingCar.getInstance().getLotteryvalue()>=0){
						//4.界面跳转--》追期和倍投。
						
						MiddleManager.getInstance().changeUI(PreBet.class,bundle);
						
						
					}else{
						PromptManager.showToast(context, "余额不足，请充值。");
					}
					
					
				}else{
					PromptManager.showToast(context, "请先登陆");
					MiddleManager.getInstance().changeUI(UserLogin.class,bundle);
					//登陆界面跳转。
				}
				
			}else{
				PromptManager.showToast(context, "选择一注。");
			}
			
			
			
			
			
			break;
		}
		
	}

	/**
	 * 随机生成一注。封装这一注信息，加入List<Ticket>中。
	 */
	private void randomOneTicket() {

		// 创建两个新集合.自动生成一注。
		Random random = new Random();
		
		List<Integer> redBallSelected = new ArrayList<Integer>();
		List<Integer> blueBallSelected = new ArrayList<Integer>();
		

		while (redBallSelected.size() < 6) {

			int num = random.nextInt(33) + 1;

			// 如果集合中已经有这个号码，则继续循环。
			if (redBallSelected.contains((Object) num)) {
				continue;
			} else {
				redBallSelected.add(num);
			}
		}
		
		int num = random.nextInt(16) + 1;
		blueBallSelected.add(num);

		
		//1.封装用户投注信息：红球、蓝球、注数。
		Ticket ticket = new Ticket();
		DecimalFormat decimalFormat = new DecimalFormat("00");
		
		StringBuffer redBuffer = new StringBuffer();
		for(Integer item:redBallSelected){
			
			redBuffer.append(" ").append(decimalFormat.format(item));
		}
		
		ticket.setRedNum(redBuffer.substring(1));
		
		
		StringBuffer blueBuffer = new StringBuffer();
		for(Integer item:blueBallSelected){
			
			blueBuffer.append(" ").append(decimalFormat.format(item));
		}
		
		ticket.setBlueNum(blueBuffer.substring(1));
		
		ticket.setNum(1);
		
		//2.创建购物车，将投注信息加入购物车
		ShoppingCar.getInstance().getTickets().add(ticket);
		
		//设备期次和彩种.
		ShoppingCar.getInstance().setIssue(bundle.getString("issue"));
		ShoppingCar.getInstance().setLotteryid(ConstValue.SSQ);
		
		
		//更新ListView.
		adapter.notifyDataSetChanged();
	}


	/**
	 * 修改ListView下方的文字信息。
	 */
	private void changeNotice(){
		
		Long num = ShoppingCar.getInstance().getLotterynumber();
		Long money = ShoppingCar.getInstance().getLotteryvalue();
		
		DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###");
		
		String notice = context.getResources().getString(R.string.is_shopping_list_notice);
		
		notice = StringUtils.replaceEach(notice, new String[]{"NUM","MONEY"}, new String[]{num.toString(),decimalFormat.format(money)});
		
		tvNotice.setText(Html.fromHtml(notice));
	}
	
	
	
	@Override
	public int getID() {
		return ConstValue.VIEW_SHOPPING;
	}
	
	
	public class MyListAdapter extends BaseAdapter {
		
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			
			ViewHolder holder = null;
			
			if(convertView==null){
				
				holder = new ViewHolder();
				convertView = View.inflate(context, R.layout.il_shopping_row, null);
				
				holder.delete = (ImageButton) convertView.findViewById(R.id.ii_shopping_item_delete);
				holder.redNum = (TextView) convertView.findViewById(R.id.ii_shopping_item_reds);
				holder.blueNum = (TextView) convertView.findViewById(R.id.ii_shopping_item_blues);
				holder.num = (TextView) convertView.findViewById(R.id.ii_shopping_item_money);

				convertView.setTag(holder);
				
			}else{
				holder = (ViewHolder)convertView.getTag();
			}

			//设置界面的显示
			Ticket ticket = ShoppingCar.getInstance().getTickets().get(position);
			
			holder.redNum.setText(ticket.getRedNum());
			holder.blueNum.setText(ticket.getBlueNum());
			holder.num.setText(ticket.getNum()+" 注");
			
			
			//删除按钮。事件的定义要放在if(convertView==null)之外。否则下一次则不会执行。
			holder.delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					//1.删除购物车中tickets中的数据项。
					ShoppingCar.getInstance().getTickets().remove(position);
					//2.更新Adapter.
					notifyDataSetChanged();
					
					changeNotice();
					//如果更新Adatper涉及到增删就必须要用notifyDataSetChanged()来更新了。
					//如果只是其中的内容更新，可以用findVeiwWithTag()来更新。
					Log.i("MyLog", "delete");
				}
			});
			
			changeNotice();
			
			return convertView;
		}
		
		class ViewHolder {
			ImageButton delete;
			TextView redNum;
			TextView blueNum;
			TextView num;
		}

	}
}

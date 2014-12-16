package com.blueice.mobilelottery.view;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

import com.blueice.mobilelottery.ConstValue;
import com.blueice.mobilelottery.R;
import com.blueice.mobilelottery.bean.ShoppingCar;
import com.blueice.mobilelottery.bean.Ticket;
import com.blueice.mobilelottery.engine.CommonInfoEngine;
import com.blueice.mobilelottery.net.protocal.Message;
import com.blueice.mobilelottery.net.protocal.Oelement;
import com.blueice.mobilelottery.net.protocal.element.CurrentIssues;
import com.blueice.mobilelottery.utils.EngineFactory;
import com.blueice.mobilelottery.utils.PromptManager;
import com.blueice.mobilelottery.view.adapter.PoolAdapter;
import com.blueice.mobilelottery.view.custom.MyGridView;
import com.blueice.mobilelottery.view.custom.MyGridView.OnActionUPListener;
import com.blueice.mobilelottery.view.manager.BottomManager;
import com.blueice.mobilelottery.view.manager.IGameUICommonMethod;
import com.blueice.mobilelottery.view.manager.MiddleManager;
import com.blueice.mobilelottery.view.manager.TitleManager;

public class PlaySSQ extends BaseUI implements IGameUICommonMethod {

	// 通用三步

	// ①标题
	// 判断购彩大厅是否获取到期次信息
	// 如果获取到：拼装标题
	// 否则默认的标题展示

	// ②填充选号容器

	// ③选号：单击+机选红蓝球
	// ④手机摇晃处理(略)

	// 机选按钮
	private Button btnRandomRed;
	private Button btnRandomBlue;
	// 选号容器
	private MyGridView redContainer;
	private GridView blueContainer;

	/**
	 * 存储被选中过的蓝红球序号。
	 */
	private List<Integer> redBallSelected;
	private List<Integer> blueBallSelected;

	private PoolAdapter redAdapter; // 红球GridView的适配器
	private PoolAdapter blueAdapter;// 蓝球GridView的适配器

	public PlaySSQ(Context context) {
		super(context);
	}

	@Override
	public void init() {

		showInMiddle = (ViewGroup) View.inflate(context, R.layout.il_playssq,
				null);

		redContainer = (MyGridView) findViewById(R.id.ii_ssq_red_number_container);
		blueContainer = (GridView) findViewById(R.id.ii_ssq_blue_number_container);

		btnRandomRed = (Button) findViewById(R.id.ii_ssq_random_red);
		btnRandomBlue = (Button) findViewById(R.id.ii_ssq_random_blue);

		redBallSelected = new ArrayList<Integer>();
		blueBallSelected = new ArrayList<Integer>();

		redAdapter = new PoolAdapter(context, 33, redBallSelected,
				R.drawable.id_redball);
		blueAdapter = new PoolAdapter(context, 16, blueBallSelected,
				R.drawable.id_blueball);

		redContainer.setAdapter(redAdapter);
		blueContainer.setAdapter(blueAdapter);

	}

	@Override
	public void OnResume() {
		changeTitle();
		changeBottomText();
		clearBall();
	}

	private void changeTitle() {
		// ①标题
		// 判断购彩大厅是否获取到期次信息
		// 如果获取到：拼装标题
		// 否则默认的标题展示

		String title = "";

		if (bundle != null) {
			title = "双色球第" + bundle.getString("issue") + "期";
		} else {
			title = "双色球选号";
		}

		TitleManager.getInstance().changeTitle(title);
	}

	@Override
	public void OnPause() {
	
	}

	/**
	 * 改变底部Title
	 */
	private void changeBottomText() {

		String notice = "";
		DecimalFormat decimal = new DecimalFormat("###,###,###");

		if (redBallSelected.size() < 6) {

			notice = "您还需要选 " + (6 - redBallSelected.size()) + " 个红球";

		} else if (blueBallSelected.size() == 0) {

			notice = "您还需要选 " + 1 + " 个蓝球";

		} else {

			long zhuNum = countNum();
			// 显示注数和金额。
			notice = "共 " + zhuNum + " 注   " +  decimal.format(zhuNum * 2) + " 元";
		}

		BottomManager.getInstance().setNotice(notice);

	}

	/**
	 * 计算注数.即所有红球的排列组合X蓝球的排列组合。
	 * 
	 * @return
	 */
	private long countNum() {

		BigInteger n = Factorial(redBallSelected.size());
		BigInteger r = Factorial(6);
		BigInteger n_r =  Factorial(redBallSelected.size()-6);
		
		//红球的组合数。
		long redC = n.divide(r.multiply(n_r)).longValue();
		
		//红球的组合数。

		// 蓝球的组合数。
		long blueC = blueBallSelected.size();
		
		return redC * blueC; // 所有的组合数。
	}

	/**
	 * 计算一个数的阶乘
	 * 
	 * @param num 要计算的数。
	 * @return 返回值为BigInteger型。
	 */
	private BigInteger Factorial(int count){
		
		BigInteger result = new BigInteger("1");//为result赋初始值，为1
		
		if(count>1){
			
			for (int i = 1; i <= count; i++) {
				   BigInteger numB = new BigInteger(String.valueOf(i));
				   result = result.multiply(numB);//调用自乘方法
				}
			
			return result;
			
		}else if(count==1||count==0){
			
			return result;
			
		}else{
			throw new IllegalArgumentException("num must >= 0");
		}
		
	}
	
	


	@Override
	public void setOnClickListener() {
		btnRandomRed.setOnClickListener(this);
		btnRandomBlue.setOnClickListener(this);

		/**
		 * 红球容器中的当手指抬起时的监听。
		 */
		redContainer.setOnActionUPListener(new OnActionUPListener() {
			@Override
			public void onActionUp(View view, int position) {

				if (!redBallSelected.contains((Object) (position + 1))) {
					// 如果没有被选中过
					// 改变背景图，设置球的摇摆动画。
					// 完成后将球的序号加入redBallSelected.
					view.setBackgroundResource(R.drawable.id_redball);
					view.setAnimation(AnimationUtils.loadAnimation(context,
							R.anim.ia_ball_shake));

					redBallSelected.add(position + 1);
				} else {
					// 如果被选中过，则恢复背景。并从序号表中删除。
					view.setBackgroundColor(Color.WHITE);
					view.setBackgroundResource(R.drawable.id_defalut_ball);

					redBallSelected.remove((Object) (position + 1));// 注意：这个remove的参数要是Object,所以要转型。

				}
				changeBottomText();
			}

		});

		// //红球容器的Click事件监听。
		// redContainer.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,int
		// position, long id) {
		//
		// if(!redBallSelected.contains((Object)(position+1))){
		// //如果没有被选中过
		// //改变背景图，设置球的摇摆动画。
		// //完成后将球的序号加入redBallSelected.
		// view.setBackgroundResource(R.drawable.id_redball);
		// view.setAnimation(AnimationUtils.loadAnimation(context,
		// R.anim.ia_ball_shake));
		//
		// redBallSelected.add(position+1);
		// }else{
		// //如果被选中过，则恢复背景。并从序号表中删除。
		// view.setBackgroundResource(R.drawable.id_defalut_ball);
		//
		// redBallSelected.remove((Object)(position+1));//注意：这个remove的参数要是Object,所以要转型。
		// }
		// }
		// });

		// 蓝球容器的Click事件监听。
		blueContainer.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (!blueBallSelected.contains((Object) (position + 1))) {
					// 如果没有被选中过
					// 改变背景图，设置球的摇摆动画。
					// 完成后将球的序号加入redBallSelected.
					view.setBackgroundResource(R.drawable.id_blueball);
					view.setAnimation(AnimationUtils.loadAnimation(context,
							R.anim.ia_ball_shake));

					blueBallSelected.add(position + 1);
				} else {
					// 如果被选中过，则恢复背景。并从序号表中删除。
					view.setBackgroundResource(R.drawable.id_defalut_ball);

					blueBallSelected.remove((Object) (position + 1));

				}

				changeBottomText();
			}
		});
	}

	/**
	 * 机选按键事件
	 */
	@Override
	public void onClick(View v) {

		Random random = new Random();

		switch (v.getId()) {
		case R.id.ii_ssq_random_red:

			// 首先清空集合，否则无法二次生成。
			redBallSelected.clear();

			while (redBallSelected.size() < 6) {

				int num = random.nextInt(33) + 1;

				// 如果集合中已经有这个号码，则继续循环。
				if (redBallSelected.contains((Object) num)) {
					continue;
				} else {
					redBallSelected.add(num);
				}
			}

			// 更新GridView.
			redAdapter.notifyDataSetChanged();
			changeBottomText();

			break;
		case R.id.ii_ssq_random_blue:

			blueBallSelected.clear();
			int num = random.nextInt(16) + 1;
			blueBallSelected.add(num);

			blueAdapter.notifyDataSetChanged();
			changeBottomText();
			break;
		}
	}

	@Override
	public int getID() {
		return ConstValue.VIEW_SSQ;
	}

	@Override
	public void clearBall() {

		//清空红蓝球的选号lsit
		redBallSelected.clear();
		blueBallSelected.clear();
		
		//更新GridView.
		redAdapter.notifyDataSetChanged();
		blueAdapter.notifyDataSetChanged();
		
		//更新底部TextView.
		changeBottomText();
		
	}

	@Override
	public void selectDone() {

		//判断用户是否选择了一注。
		if(redBallSelected.size()>=6 && blueBallSelected.size()>=1){
			
			//判断是否已经获取了当前信息。
			if(bundle!=null){
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
				
				ticket.setNum(countNum());
				
				//2.创建购物车，将投注信息加入购物车
				ShoppingCar.getInstance().getTickets().add(ticket);
				
				//设备期次和彩种.
				ShoppingCar.getInstance().setIssue(bundle.getString("issue"));
				ShoppingCar.getInstance().setLotteryid(ConstValue.SSQ);
				
				
				MiddleManager.getInstance().changeUI(Shopping.class,bundle);
				
				
			}else{
				//从服务器重新获取当前期信息。
				PromptManager.showProgressDialog(context);
				getCurrentIssueInfo();
				
				if(bundle!=null){
					PromptManager.closeProgressDialog();
					selectDone();
				}
			}
			
		}else{
			
			PromptManager.showToast(context, "最少必须选择一注");
			
		}
		
		
		
	}

	/**
	 * 从服务器重新获取当前期信息。可以参数照HallUI中的getCurrentIssueInfo
	 */
	private void getCurrentIssueInfo() {
		// 创建一个异步任务类对象,并执行。
		new MyAsynTask<Integer>() {

			@Override
			protected Message doInBackground(Integer... params) {
				// 获取数据---业务调用。
				CommonInfoEngine engine = EngineFactory.Instance(CommonInfoEngine.class);
				return engine.getCurrentIssueInfo(params[0]);
			}

			/**
			 * 获取信息前弹出提示窗口。
			 */
			@Override
			protected void onPreExecute() {
				PromptManager.showProgressDialog(context);
			}

			@Override
			protected void onPostExecute(Message result) {
				
				//关闭弹出提示窗口.
				PromptManager.closeProgressDialog();
				
				// 更新界面
				if (result != null) {

					Oelement oelement = result.getBody().getOelement();

					//获取数据成功。
					if (ConstValue.SUCCESS.equals(oelement.getErrorcode())) {
						
						//获取issue数据。
						CurrentIssues element = (CurrentIssues) result.getBody().getElements().get(0);
						String issue = element.getISSUE();
						
						//创建一个bundle.
						bundle = new Bundle();
						bundle.putString("issue", issue);
						
					
						
					} else {
						PromptManager.showToast(context, oelement.getErrormsg());
					}
				} else {
					PromptManager.showToast(context, "服务器忙，请稍后重试。");
				}

				super.onPostExecute(result);
			}

		}.executeProxy(ConstValue.SSQ); // 彩种的标识
	}

}




















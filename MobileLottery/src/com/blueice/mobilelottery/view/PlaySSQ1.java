package com.blueice.mobilelottery.view;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

import com.blueice.mobilelottery.ConstValue;
import com.blueice.mobilelottery.R;
import com.blueice.mobilelottery.view.adapter.PoolAdapter;
import com.blueice.mobilelottery.view.manager.TitleManager;

public class PlaySSQ1 extends BaseUI {

	// 通用三步

	// ①标题
	// 判断购彩大厅是否获取到期次信息
	// 如果获取到：拼装标题
	// 否则默认的标题展示

	// ②填充选号容器
	
	
	// ③选号：单击+机选红蓝球
	// ④手机摇晃处理
	
	// 机选
	private Button randomRed;
	private Button randomBlue;
	// 选号容器
	private GridView redContainer;
	private GridView blueContainer;
	
	/**
	 * 存储被选中过的蓝红球序号，用来判定球的点击。
	 */
	private List<Integer> redBallSelected;
	private List<Integer> blueBallSelected;
	
	public PlaySSQ1(Context context) {
		super(context);
	}

	@Override
	public void init() {

		showInMiddle = (ViewGroup) View.inflate(context, R.layout.il_playssq_1, null);
		
		redContainer = (GridView) findViewById(R.id.ii_ssq_red_number_container);
		blueContainer = (GridView) findViewById(R.id.ii_ssq_blue_number_container);
		
		randomRed = (Button) findViewById(R.id.ii_ssq_random_red);
		randomBlue = (Button) findViewById(R.id.ii_ssq_random_blue);
		
		redBallSelected = new ArrayList<Integer>();
		blueBallSelected = new ArrayList<Integer>();
		
		PoolAdapter redPoll = new PoolAdapter(context, 33,redBallSelected,R.drawable.id_redball);
		PoolAdapter bluePoll = new PoolAdapter(context,16,blueBallSelected,R.drawable.id_blueball);
		
		redContainer.setAdapter(redPoll);
		blueContainer.setAdapter(bluePoll);
	}
	
	
	@Override
	public void OnResume() {
		chnageTitle();
	}

	
	private void chnageTitle() {
		// ①标题
		// 判断购彩大厅是否获取到期次信息
		// 如果获取到：拼装标题
		// 否则默认的标题展示
		
		String title = "";
		
		if(bundle!=null){
			title = "双色球第"+bundle.getString("issue")+"期";
		}else {
			title="双色球选号";
		}
		
		TitleManager.getInstance().changeTitle(title);
	}

	@Override
	public void OnPause() {
		
	}
	
	@Override
	public void setOnClickListener() {
		randomRed.setOnClickListener(this);
		randomBlue.setOnClickListener(this);
		
		//红球容器的Click事件监听。
		redContainer.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				
				if(!redBallSelected.contains((Object)(position+1))){
					//如果没有被选中过
					//改变背景图，设置球的摇摆动画。
					//完成后将球的序号加入redBallSelected.
					view.setBackgroundResource(R.drawable.id_redball);
					view.setAnimation(AnimationUtils.loadAnimation(context, R.anim.ia_ball_shake));
					
					redBallSelected.add(position+1);
				}else{
					//如果被选中过，则恢复背景。并从序号表中删除。
					view.setBackgroundResource(R.drawable.id_defalut_ball);
					
					redBallSelected.remove((Object)(position+1));//注意：这个remove的参数要是Object,所以要转型。
				}
			}
		});
		
		//蓝球容器的Click事件监听。
		blueContainer.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if(!blueBallSelected.contains((Object)(position+1))){
					//如果没有被选中过
					//改变背景图，设置球的摇摆动画。
					//完成后将球的序号加入redBallSelected.
					view.setBackgroundResource(R.drawable.id_blueball);
					view.setAnimation(AnimationUtils.loadAnimation(context, R.anim.ia_ball_shake));

					blueBallSelected.add(position+1);
				}else{
					//如果被选中过，则恢复背景。并从序号表中删除。
					view.setBackgroundResource(R.drawable.id_defalut_ball);
					
					blueBallSelected.remove((Object)(position+1));
				}
			}
		});
		
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ii_ssq_random_red:

			break;
		case R.id.ii_ssq_random_blue:

			break;
		}
	}

	@Override
	public int getID() {
		return ConstValue.VIEW_SSQ;
	}

}

























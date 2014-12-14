package com.blueice.mobilelottery.view.adapter;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import com.blueice.mobilelottery.R;
import com.blueice.mobilelottery.utils.DensityUtil;

import android.R.color;
import android.R.integer;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PoolAdapter extends BaseAdapter {

	private Context context;
	private int ballNum;
	private List<Integer> selectedNums;
	private int ballImg;
	
	/**
	 * 选球池的Adapter.
	 * @param context 上下文。
	 * @param ballNum 球的数量。
	 * @param selectedNums 机选的集合
	 * @param ballImg 选中球的图片。
	 */
	public PoolAdapter(Context context, int ballNum,List<Integer> selectedNums, int ballImg) {
		super();
		this.context = context;
		this.ballNum = ballNum;
		this.selectedNums = selectedNums;
		this.ballImg = ballImg;
	}

	@Override
	public int getCount() {
		return ballNum;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		TextView textView = new TextView(context);
		//设置球体文字格式。
		DecimalFormat decimal = new DecimalFormat("00");
		textView.setText(decimal.format(position+1));
		textView.setTextColor(Color.WHITE);
		textView.setTextSize(18);
		//设置文字齐。
		textView.setGravity(Gravity.CENTER);
		textView.setBackgroundColor(color.white);
		textView.setHeight(DensityUtil.dip2px(context, 40));
		
		//设置球体的背景。
		if(selectedNums.contains(position+1)){
			textView.setBackgroundResource(ballImg);
//			Animation animation = AnimationUtils.loadAnimation(context, R.anim.ia_ball_shake);
//			animation.setStartOffset(position*5);
//			textView.setAnimation(animation);
 		}else{
			textView.setBackgroundResource(R.drawable.id_defalut_ball);
		}
		
		
		return textView;
	}

}

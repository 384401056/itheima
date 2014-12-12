package com.blueice.mobilelottery.view;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blueice.mobilelottery.ConstValue;
import com.blueice.mobilelottery.R;
import com.blueice.mobilelottery.engine.BaseEngine;
import com.blueice.mobilelottery.engine.CommonInfoEngine;
import com.blueice.mobilelottery.net.protocal.Element;
import com.blueice.mobilelottery.net.protocal.Message;
import com.blueice.mobilelottery.net.protocal.Oelement;
import com.blueice.mobilelottery.net.protocal.element.CurrentIssues;
import com.blueice.mobilelottery.utils.EngineFactory;
import com.blueice.mobilelottery.utils.PromptManager;

/**
 * 购彩大厅的界面。
 *
 */
public class HallUI extends BaseUI {

	// 第一步：加载layout（布局参数设置）
	// 第二步：初始化layout中控件
	// 第三步：设置监听
	
	private ImageView ivSSQ;
	private TextView tvSSQ;
	private ImageView ivSD ;
	private TextView tvSD;
	private ImageView ivQLC;
	private TextView tvQLC;

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
		
		getCurrentIssueInfo();
		
	}

	/**
	 * 获取当前销售期信息。
	 */
	public void getCurrentIssueInfo() {

		// 创建一个父类的异步任务类对象,并执行。
		new MyAsynTask<Integer>() {

			@Override
			protected Message doInBackground(Integer... params) {
				// 获取数据---业务调用。
				CommonInfoEngine engine = EngineFactory.Instance(CommonInfoEngine.class);
				return engine.getCurrentIssueInfo(params[0]);
			}

			@Override
			protected void onPostExecute(Message result) {
				// 更新界面

				if (result != null) {

					Oelement oelement = result.getBody().getOelement();

					if (ConstValue.SUCCESS.equals(oelement.getErrorcode())) 
					{
						changeNotice(result.getBody().getElements().get(0));
					} else {
						PromptManager
								.showToast(context, oelement.getErrormsg());
					}
				} else {
					PromptManager.showToast(context, "服务器忙，请稍后重试。");
				}

				super.onPostExecute(result);
			}

		}.executeProxy(ConstValue.SSQ); // 彩种的标识

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
			Log.i("MyLog", "SSQ");
			break;
		case R.id.ii_hall_3d_bet:
			Log.i("MyLog", "3D");
			break;
		case R.id.ii_hall_qlc_bet:
			Log.i("MyLog", "QLC");
			break;
		}
	}

	/**
	 * 设置TextView
	 * 
	 * @param element
	 */
	protected void changeNotice(Element element) {

		CurrentIssues currentIssuesElement = (CurrentIssues) element;

		String iissue = currentIssuesElement.getISSUE();
		String lasttime = getLasttime(currentIssuesElement.getLasttime());

		String str = context.getResources().getString(
				R.string.is_hall_common_summary);

		// 替换字符。
		String text = StringUtils.replaceEach(str, new String[] { "ISSUE", "TIME" },
				new String[] { iissue, lasttime });

		tvSSQ.setText(text);
	}
	
	/**
	 * 将秒时间转换成日时分格式
	 * 
	 * @param lasttime
	 * @return
	 */
	public String getLasttime(String lasttime) {
		StringBuffer result = new StringBuffer();
		if (StringUtils.isNumericSpace(lasttime)) {
			int time = Integer.parseInt(lasttime);
			int day = time / (24 * 60 * 60);
			result.append(day).append("天");
			if (day > 0) {
				time = time - day * 24 * 60 * 60;
			}
			int hour = time / 3600;
			result.append(hour).append("时");
			if (hour > 0) {
				time = time - hour * 60 * 60;
			}
			int minute = time / 60;
			result.append(minute).append("分");
		}
		return result.toString();
	}

}

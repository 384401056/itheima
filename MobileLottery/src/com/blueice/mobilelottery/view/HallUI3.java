package com.blueice.mobilelottery.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.blueice.mobilelottery.ConstValue;
import com.blueice.mobilelottery.R;
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
public class HallUI3 extends BaseUI {

	// 第一步：加载layout（布局参数设置）
	// 第二步：初始化layout中控件
	// 第三步：设置监听

	private ListView listView;
	private MyAdapter myAdapter;
	
	public HallUI3(Context context) {
		super(context);
	}

	@Override
	public void init() {

		// 从布局文件中得到View.
		showInMiddle = (LinearLayout) View.inflate(context, R.layout.il_hall,
				null);

		listView = (ListView) findViewById(R.id.ii_hall_lottery_list);
		listButton = new ArrayList<View>();
		myAdapter = new MyAdapter();
		listView.setAdapter(myAdapter);
	}

	@Override
	public void OnResume() {

		getCurrentIssueInfo();
	}

	@Override
	public void OnPause() {

	}

	/**
	 * 获取当前销售期信息。
	 */
	private void getCurrentIssueInfo() {

		// 创建一个父类的异步任务类对象,并执行。
		new MyAsynTask<Integer>() {

			@Override
			protected Message doInBackground(Integer... params) {
				// 获取数据---业务调用。
				CommonInfoEngine engine = EngineFactory
						.Instance(CommonInfoEngine.class);
				return engine.getCurrentIssueInfo(params[0]);
			}

			@Override
			protected void onPostExecute(Message result) {
				// 更新界面

				if (result != null) {

					Oelement oelement = result.getBody().getOelement();

					if (ConstValue.SUCCESS.equals(oelement.getErrorcode())) {
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

	}

	@Override
	public int getID() {
		return ConstValue.VIEW_HELL;
	}

	@Override
	public void onClick(View v) {

	}

	/**
	 * 服务器端返回的文字信息。
	 */
	private String text;

	/**
	 * 需要被更新的控件列表。
	 */
	private List<View> listButton;

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
		text = StringUtils.replaceEach(str, new String[] { "ISSUE", "TIME" },
				new String[] { iissue, lasttime });

		// 更新数据
		// 方案一：新有Item都会被更新。
//		 myAdapter.notifyDataSetChanged();

		// 方案二：只更新想要更新的内容。
		// 获取需要更新控件的引用。needUpdate

//		TextView view = (TextView) needUpdate.get(0);
//		if (view != null) {
//			view.setText(text);
//		}

		// 方案三：
		 TextView view = (TextView) listView.findViewWithTag(0);
		 if(view!=null){
			 view.setText(text);
		 }
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

	// 资源信息
	private int[] logoResIds = new int[] { R.drawable.id_ssq, R.drawable.id_3d,
			R.drawable.id_qlc };
	private int[] titleResIds = new int[] { R.string.is_hall_ssq_title,
			R.string.is_hall_3d_title, R.string.is_hall_qlc_title };

	class MyAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return 3;
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

			ViewHolder holder = null;

			// 如果是第一次加载。
			if (convertView == null) {

				holder = new ViewHolder();

				// 从布局文件生成容器。
				convertView = View.inflate(context,
						R.layout.il_hall_lottery_item, null);

				holder.logo = (ImageView) convertView
						.findViewById(R.id.ii_hall_lottery_logo);
				holder.title = (TextView) convertView
						.findViewById(R.id.ii_hall_lottery_title);
				holder.summary = (TextView) convertView
						.findViewById(R.id.ii_hall_lottery_summary);

//				needUpdate.add(holder.summary);// 将控件加入需要更新控件列表。

				holder.bet = (ImageView) convertView
						.findViewById(R.id.ii_hall_lottery_bet);

				final int pos = position;
				holder.bet.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Log.i("MyLog", pos+"");
					}
				});

				convertView.setTag(holder); // 将holder存入Tag中。

			} else {

				holder = (ViewHolder) convertView.getTag();

			}

			// 更新Item控件上的值。
			holder.logo.setImageResource(logoResIds[position]);
			holder.title.setText(titleResIds[position]);
			
			holder.summary.setTag(position);  //给每个summary控件设置唯一标识符。

//			 if(StringUtils.isNotBlank(text)&&position==0){
//				 holder.summary.setText(text);
//			 }

			return convertView;
		}

		// 作用：将 ListView的item layout文件中的控件持久化。
		class ViewHolder {

			ImageView logo;
			TextView title;
			TextView summary;
			ImageView bet;

		}
	}

}

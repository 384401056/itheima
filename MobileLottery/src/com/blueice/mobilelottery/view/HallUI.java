package com.blueice.mobilelottery.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.R.color;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
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
import com.blueice.mobilelottery.GlobalParams;
import com.blueice.mobilelottery.R;
import com.blueice.mobilelottery.engine.CommonInfoEngine;
import com.blueice.mobilelottery.net.protocal.Element;
import com.blueice.mobilelottery.net.protocal.Message;
import com.blueice.mobilelottery.net.protocal.Oelement;
import com.blueice.mobilelottery.net.protocal.element.CurrentIssues;
import com.blueice.mobilelottery.utils.EngineFactory;
import com.blueice.mobilelottery.utils.PromptManager;
import com.blueice.mobilelottery.view.manager.BottomManager;
import com.blueice.mobilelottery.view.manager.IGameUICommonMethod;
import com.blueice.mobilelottery.view.manager.MiddleManager;

/**
 * 购彩大厅的界面。
 *
 */
public class HallUI extends BaseUI {

	// 第一步：加载layout（布局参数设置）
	// 第二步：初始化layout中控件
	// 第三步：设置监听

	private ListView listView;
	private MyAdapter myAdapter;

	/**
	 * ViewPager
	 */
	private ViewPager viewPager;
	private List<View> pagers;
	private ImageView cursor;

	private TextView fc;
	private TextView tc;
	private TextView gpc;

	private int offset; // 偏移量
	private int bmpW; // 图片宽度

	private Bundle ssqbundle;

	public HallUI(Context context) {
		super(context);
	}

	@Override
	public void init() {

		// 从布局文件中得到View.
		showInMiddle = (LinearLayout) View.inflate(context, R.layout.il_hall,
				null);

		viewPager = (ViewPager) findViewById(R.id.ii_viewpager);
		pagers = new ArrayList<View>();
		cursor = (ImageView) findViewById(R.id.ii_category_selector);

		fc = (TextView) findViewById(R.id.ii_category_fc);
		tc = (TextView) findViewById(R.id.ii_category_tc);
		gpc = (TextView) findViewById(R.id.ii_category_gpc);

		// listView = (ListView) findViewById(R.id.ii_hall_lottery_list);
		listView = new ListView(context);
		listButton = new ArrayList<View>();
		
		myAdapter = new MyAdapter();
		listView.setAdapter(myAdapter);
		listView.setFadingEdgeLength(0);// 设置lsitview的边缘。

		initPager();

		viewPager.setAdapter(new MyPagerAdapter());

		// 初始化选项卡下划线。
		initCursor();

	}

	/**
	 * 初始化选择项的下划线数据。
	 */
	private void initCursor() {

		// 获取小图片宽度。
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.id_category_selector);
		// 获取偏移量。
		bmpW = bitmap.getWidth();
		offset = (GlobalParams.WIN_WIDTH / pagers.size() - bmpW) / 2;

	}

	/**
	 * 初始化pagers
	 */
	private void initPager() {

		pagers.add(listView);

		TextView tv = new TextView(context);
		tv.setText("体彩");
		tv.setBackgroundColor(Color.GREEN);
		pagers.add(tv);

		tv = new TextView(context);
		tv.setText("高频彩");
		tv.setBackgroundColor(Color.YELLOW);
		pagers.add(tv);
	}

	@Override
	public void OnResume() {
		getCurrentIssueInfo();
		Log.i("MyLog", "OnResume");
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
		fc.setOnClickListener(this);
		tc.setOnClickListener(this);
		gpc.setOnClickListener(this);

		// viewPager的监听事件。
		viewPager.setOnPageChangeListener(new MyPagerChangerListener());
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();

		switch (id) {
		case R.id.ii_category_fc:
			viewPager.setCurrentItem(0, true);
			break;
		case R.id.ii_category_tc:
			viewPager.setCurrentItem(1, true);
			break;
		case R.id.ii_category_gpc:
			viewPager.setCurrentItem(2, true);
			break;
		}
	}

	@Override
	public int getID() {
		return ConstValue.VIEW_HELL;
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

		String issue = currentIssuesElement.getISSUE();
		String lasttime = getLasttime(currentIssuesElement.getLasttime());

		String str = context.getResources().getString(
				R.string.is_hall_common_summary);

		// 替换字符。
		text = StringUtils.replaceEach(str, new String[] { "ISSUE", "TIME" },
				new String[] { issue, lasttime });

		// 更新数据
		// 方案一：新有Item都会被更新。
		// myAdapter.notifyDataSetChanged();

		// 方案二：只更新想要更新的内容。
		// 获取需要更新控件的引用。needUpdate

		// TextView view = (TextView) needUpdate.get(0);
		// if (view != null) {
		// view.setText(text);
		// }

		// 方案三：在MyAdapter中使用Tag来标识想要更新的ListView下的UI。然后通过findViewWithTag()方法找到要更新的UI.
		TextView view = (TextView) listView.findViewWithTag(0);
		if (view != null) {
			view.setText(text);
		}

	//  设置需要传输的数据
		ssqbundle = new Bundle();
		ssqbundle.putString("issue", issue);

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

	/**
	 * ListView的Adapter.
	 *
	 */
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
		public View getView(final int position, View convertView, ViewGroup parent) {

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

				holder.bet = (ImageView) convertView
						.findViewById(R.id.ii_hall_lottery_bet);

				convertView.setTag(holder); // 将holder存入Tag中。

			} else {

				holder = (ViewHolder) convertView.getTag();
			}

			// 更新Item控件上的值。
			holder.logo.setImageResource(logoResIds[position]);
			holder.title.setText(titleResIds[position]);
			
			holder.summary.setTag(position); // 给每个summary控件设置唯一标识符。

			holder.bet.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					switch (position) {
					case 0:
						MiddleManager.getInstance().changeUI(PlaySSQ.class,ssqbundle);
						break;
					case 1:

						break;
					case 2:

						break;
					}
				}
			});

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

	/**
	 * ViewPager的Adapter.
	 */
	class MyPagerAdapter extends PagerAdapter {

		// 添加View
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = pagers.get(position);
			container.addView(view);
			return view;
		}

		// 删除View
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			View view = pagers.get(position);
			container.removeView(view);
		}

		// 指定View的数量。
		@Override
		public int getCount() {
			return pagers.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}

	/**
	 * viewPager的事件类。
	 */
	class MyPagerChangerListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		/**
		 * 当页面在滑动的时候会调用此方法，在滑动被停止之前，此方法回一直得到调用。 其中三个参数的含义分别为： arg0
		 * :当前页面，及你点击滑动的到的页面序号 arg1 :当前页面偏移的百分比 arg2 :当前页面偏移的像素位置
		 */
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			Matrix matrix = new Matrix();

			// offset为算好的偏移量。bmpW为图片的宽度。
			switch (arg0) {
			case 0:
				matrix.setTranslate(offset, 0); // 设置当选中面板0时的ImageView的初始位置
				break;
			case 1:
				matrix.setTranslate(offset * 3 + bmpW, 0); // 设置当选中面板1时的ImageView的初始位置
				break;
			case 2:
				matrix.setTranslate(offset * 5 + bmpW * 2, 0); // 设置当选中面板2时的ImageView的初始位置
				break;
			default:
				break;
			}

			// 在滑动的过程中，计算出激活条应该要滑动的距离
			float t = (offset * 2 + bmpW) * arg1;

			// 使用post追加数值
			matrix.postTranslate(t, 0);

			cursor.setImageMatrix(matrix); // 在布局文件中ImageView要设置
											// android:scaleType="matrix"

		}

		@Override
		public void onPageSelected(int position) {

		}

	}

}

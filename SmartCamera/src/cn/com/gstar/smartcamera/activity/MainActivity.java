package cn.com.gstar.smartcamera.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import cn.com.gstar.smartcamera.R;
import cn.com.gstar.smartcamera.service.MinaService;
import cn.com.gstar.smartcamera.utils.PromptManager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MainActivity extends Activity implements View.OnClickListener {
	private Context context;

	@ViewInject(R.id.btn_start)
	private Button btn_start;
	@ViewInject(R.id.btn_stop)
	private Button btn_stop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.context = MainActivity.this;
		ViewUtils.inject(this);
		init();
	}

	private void init() {
		btn_start.setOnClickListener(this);
		btn_stop.setOnClickListener(this);
	}


	private void startMyServer() {
		Intent startIntent = new Intent(context, MinaService.class);
		startService(startIntent);
		PromptManager.showToast(context,"StartServer!!!--------");
	}

	private void stopMyServer() {
		Intent stopIntent = new Intent(context, MinaService.class);
		stopService(stopIntent);
		PromptManager.showToast(context,"stop!!!--------");
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.btn_start:
				startMyServer();
				break;
			case R.id.btn_stop:
				stopMyServer();
				break;
			default:
				break;
		}
	}
}

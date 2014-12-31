package com.blueice.mobilesafe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.blueice.mobilesafe.beam.UpdateVersion;
import com.blueice.mobilesafe.utils.HttpClientUtils;
import com.blueice.mobilesafe.utils.NetUtils;
import com.blueice.mobilesafe.utils.PromptManager;

public class SplashActivity extends Activity {

	protected static final int ENTER_HOME = 0;

	protected static final int SHOW_UPDATE_DIALOG = 1;

	protected static final int NETWORK_ERROR = 2;

	protected static final int JSONE_ERROR = 3;

	private TextView tvVersion;
	private TextView tvUpdatePrgs;

	private UpdateVersion version;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		/**
		 * 启动界面的功能： 1.显示Logo 2.应用程序数据初始化。 3.检查版本升级。 4.检查网络。 5.检查版权。
		 */
		
		/**
		 * 获取屏幕宽度、高度，存入全局变量类。
		 */
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		GlobalParams.WIN_WIDTH = metrics.widthPixels;
		GlobalParams.WIN_HEIGHT = metrics.heightPixels;

		initDB();

		init();

	}

	/**
	 * 初始化数据库. 数据库文件虽然已经放到raw文件下，但是还是必须复制到
	 * data/data/com.blueice.mobilesafe/...的目录下才能被SQLiteDatabase找到。
	 */
	private void initDB() {

		// 创建一个文件。
		File file = new File(getFilesDir(), "address.db");

		// 如果文件已经存在，并且大小不为0，则不再进行复制操作。
		if (file.exists() && file.length() != 0) {
			Log.i("MyLog", "DBFile exists!!");
			return;
			
		} else {
			try {
				InputStream is = getAssets().open("address.db");
				// 用文件输入流,来向file中写入数据
				FileOutputStream fos = new FileOutputStream(file);

				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}

				is.close();
				fos.close();
				
				Log.i("MyLog", "DBFile is ok!");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 初始化,检查网络，检查版权。
	 */
	private void init() {

		tvVersion = (TextView) findViewById(R.id.splash_version);
		tvUpdatePrgs = (TextView) findViewById(R.id.update);

		// 存储配置文件。
		GlobalParams.sp = getSharedPreferences("setting", MODE_PRIVATE);
		tvVersion.setText("版本：" + getVersion());

		// 如果SharedPreferences中的update为true,则执行版本检查。
		if (GlobalParams.sp.getBoolean("update", true)) {
			checkUpdate();
		} else {
			// 延迟2秒再进入主页面。
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					enterHome();
				}
			}, 2000);

		}
	}

	/**
	 * 获取更新信息。
	 */
	private void checkUpdate() {

		if (NetUtils.checkNet(this)) {

			new AsyncTask<String, Integer, Message>() {

				@Override
				protected Message doInBackground(String... params) {

					Message msg = Message.obtain();

					Long startTime = System.currentTimeMillis();

					try {

						HttpClientUtils clientUtils = new HttpClientUtils();
						InputStream is = clientUtils.sendRequest(
								ConstValue.URI, params[0]);

						parseJson(streamToString(is));

						if (getVersion().equals(version.getVersion())) {
							// 如果版本一致，直接进入主页面。
							msg.what = ENTER_HOME;
						} else {
							// 否则，弹出升级窗口。
							msg.what = SHOW_UPDATE_DIALOG;
						}

					} catch (IOException e) {
						msg.what = NETWORK_ERROR;
						e.printStackTrace();
					} catch (JSONException e) {
						msg.what = JSONE_ERROR;
						e.printStackTrace();
					} finally {

						Long endTiem = System.currentTimeMillis();

						Long dTiem = endTiem - startTime;

						if (dTiem < 2000) {

							try {
								Thread.sleep(2000 - dTiem);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						return msg;
					}
				}

				@Override
				protected void onPostExecute(Message msg) {

					switch (msg.what) {
					case ENTER_HOME:
						enterHome();
						break;
					case SHOW_UPDATE_DIALOG:
						Log.i("MyLog", version.getVersion());
						Log.i("MyLog", version.getDescription());
						Log.i("MyLog", version.getApkurl());
						showUpdateDialog();
						break;
					case NETWORK_ERROR:
						Log.i("MyLog", "网络出错");
						enterHome();
						break;
					case JSONE_ERROR:
						Log.i("MyLog", "Json解析出错");
						enterHome();
						break;
					}
				}

			}.execute("CheckVersion");

		} else {
			// 延迟2秒再进入主页面。
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					enterHome();
				}
			}, 2000);

		}

	}

	/**
	 * 显示升级对话框。
	 */
	protected void showUpdateDialog() {
		AlertDialog.Builder dialog = new Builder(this);
		// dialog.setCancelable(true); //不能按返回键来取消。更好的做法是点返回键时继续运行。
		dialog.setTitle("");
		dialog.setMessage(version.getDescription());
		dialog.setPositiveButton("立刻升级", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 下载升级apk.

				// 1.判断SD卡是否存在。
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// 如果存在则开始下载。使用afinal框架。

					FinalHttp finalHttp = new FinalHttp();
					finalHttp.download(version.getApkurl(), Environment
							.getExternalStorageDirectory().getAbsolutePath()
							+ "/mobilesafe2.0.apk", new AjaxCallBack<File>() {

						/**
						 * 开始下载时调用的方法
						 */
						@Override
						public void onStart() {
							super.onStart();
							tvUpdatePrgs.setVisibility(View.VISIBLE);// 显示进度TextView.
						}

						/**
						 * 下载的过程中将不断调用此方法。
						 */
						@Override
						public void onLoading(long count, long current) {
							super.onLoading(count, current);
							int progress = (int) (current * 100 / count);
							tvUpdatePrgs.setText("下载进度：" + progress + "%"); // 改变进度条。

						}

						/**
						 * 下载失败时调用此方法。
						 * 
						 * t:为异常。
						 */
						@Override
						public void onFailure(Throwable t, int errorNo,
								String strMsg) {
							t.printStackTrace();
							PromptManager.showToast(getApplicationContext(),
									"下载失败。");
							super.onFailure(t, errorNo, strMsg);
						}

						/**
						 * 下载成功后调用此方法。
						 */
						@Override
						public void onSuccess(File t) {
							PromptManager.showToast(getApplicationContext(),
									"下载成功。");
							super.onSuccess(t);

							installApk(t);
						}

					});

				} else {
					PromptManager.showToast(getApplicationContext(),
							"没有找到SD卡，请安装好再试。");
				}

			}
		});

		dialog.setNegativeButton("下次再说", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();
				enterHome();
			}
		});

		/**
		 * 用户点击对话框外，或者点击返回键后执行的事件。
		 */
		dialog.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				dialog.dismiss();
				enterHome();
			}
		});

		dialog.show();

	}

	/**
	 * 进入主页。
	 */
	private void enterHome() {

		Intent intent = new Intent(SplashActivity.this, Home.class);
		startActivity(intent);
		this.finish();// 删除启动界面，让其无法被返回。
	}

	/**
	 * 安装APK文件。
	 * 
	 * @param file
	 *            apk文件
	 */
	private void installApk(File file) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");

		startActivity(intent);

		// 删除应用的意图设置
		// Intent intent = new Intent();
		// intent.setAction("android.intent.action.VIEW");
		// intent.addCategory("android.intent.category.DEFAULT");
		// intent.setData(Uri.parse("package:"+<应用包名>));
		// startActivity(intent);

	}

	/**
	 * 解析Json数据。
	 * 
	 * @param result
	 * @throws JSONException
	 */
	private void parseJson(String result) throws JSONException {

		version = new UpdateVersion();

		JSONObject json = new JSONObject(result);

		version.setVersion(json.getString("version"));
		version.setDescription(json.getString("description"));
		version.setApkurl(json.getString("apkurl"));

	}

	/**
	 * InputStream转为String.
	 * 
	 * @param is
	 * @return String
	 */
	private String streamToString(InputStream is) {

		byte[] buffer = new byte[2048];
		int len = 0;
		StringBuffer sBuffer = new StringBuffer();

		try {
			while ((len = is.read(buffer)) != -1) {
				sBuffer.append(new String(buffer, 0, len));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return sBuffer.toString();
	}

	/**
	 * 获取软件版本号。
	 * 
	 * @return String
	 */
	private String getVersion() {

		PackageManager pm = getPackageManager();
		try {

			PackageInfo info = pm.getPackageInfo(getPackageName(), 0);

			return info.versionName;

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}
}

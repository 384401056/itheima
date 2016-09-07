package cn.com.gstar.smartcamera.utils;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.widget.Toast;
import cn.com.gstar.smartcamera.R;


/**
 * 提示信息的管理
 */

public class PromptManager {
	private static ProgressDialog dialog;

	public static void showProgressDialog(Context context,int icon) {
		dialog = new ProgressDialog(context);
		dialog.setIcon(icon);
		dialog.setTitle(R.string.app_name);
		dialog.setCancelable(false);
		dialog.setMessage("请等候，数据加载中……");
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);/* 圆形进度条 */
		dialog.show();

	}

	
	public static ProgressDialog showProgressDialogH(Context context,int icon,String title) {
		dialog = new ProgressDialog(context);
		dialog.setIcon(icon);
		dialog.setTitle(title);
		dialog.setCancelable(false);
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);/* 水平进度条 */
		return dialog;

	}
	

	public static void closeProgressDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
			dialog=null;
		}
	}
	
	public static boolean isShow(){
		return dialog!=null?true:false;
	}

	/**
	 * 当判断当前手机没有网络时使用
	 * 
	 * @param context
	 */
//	public static void showNoNetWork(final Context context) {
//		AlertDialog.Builder builder = new Builder(context);
//		builder.setIcon(R.drawable.ic_launcher)//
//				.setTitle(R.string.app_name)//
//				.setMessage("当前无网络").setPositiveButton("设置", new OnClickListener() {
//
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						// 跳转到系统的网络设置界面
//						Intent intent = new Intent();
//						intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
//						context.startActivity(intent);
//					}
//				}).setNegativeButton("知道了", null).show();
//	}

	

	public static void showRelogin(final Context context,int icon){
		Builder builder = new Builder(context);
		builder.setIcon(icon)//
				.setCancelable(false)//点击对话框之处，不能取消对话框。
				.setTitle(R.string.app_name)//
				.setMessage("连接超时，请重新开启应用。").setPositiveButton("确定", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						android.os.Process.killProcess(android.os.Process.myPid());
					}
				}).show();
	}
	
	public static void changePwdRelogin(final Context context,int icon){
		Builder builder = new Builder(context);
		builder.setIcon(icon)//
		.setCancelable(false)//点击对话框之处，不能取消对话框。
		.setTitle(R.string.app_name)//
		.setMessage("密码修改成功，请重新开启应用，并使用新密码登陆。").setPositiveButton("确定", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		}).show();
	}
	
	/**
	 * 当判断当前手机没有网络时使用
	 * 
	 * @param context
	 */
	public static void showNoNetWork(final Context context,int icon) {
		Builder builder = new Builder(context);
		builder.setIcon(icon)//
		.setTitle(R.string.app_name)//
		.setMessage("当前无网络").setPositiveButton("设置", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 跳转到系统的网络设置界面
				Intent intent=null;
                //判断手机系统的版本  即API大于10 就是3.0或以上版本 
                if(android.os.Build.VERSION.SDK_INT>10){
                    intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                }else{
                    intent = new Intent();
                    ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
                    intent.setComponent(component);
                    intent.setAction("android.intent.action.VIEW");
                }
                context.startActivity(intent);
			}
		}).setNegativeButton("退出", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		}).show();
	}
	
	/**
	 * 判断当前手机没有网络，并直接退出
	 * 
	 * @param context
	 */
	public static void showNoNetWork_exit(final Context context,int icon) {
		Builder builder = new Builder(context);
		builder.setIcon(icon)
		.setTitle(R.string.app_name)
		.setMessage("当前无网络,请连接网络后再试。")
		.setCancelable(false)//不能取消此圣诞框。
		.setNegativeButton("退出", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		}).show();
	}
	
	/**
	 * 数据库中无用户，提示联网重试。
	 * @param context
	 * @param icon
	 */
	public static void showNoUser_exit(final Context context,int icon) {
		Builder builder = new Builder(context);
		builder.setIcon(icon)
		.setTitle(R.string.app_name)
		.setMessage("   无法更新用户列表，请连接网络后重试。")
		.setCancelable(false)//不能取消此提示框。
		.setNegativeButton("退出", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		}).show();
	}
	
	/**
	 * 判断当前手机没有GPS，并直接退出
	 * 
	 * @param context
	 */
	public static void showNoGPS_exit(final Context context,int icon) {
		Builder builder = new Builder(context);
		builder.setIcon(icon)
		.setTitle(R.string.app_name)
		.setMessage("GSP当前已禁用，请在您的系统设置中启动。")
		.setCancelable(false)//不能取消此圣诞框。
		.setNegativeButton("退出", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		}).show();
	}
	
	
	/**
	 * 判断当前手机没有网络，不退出
	 * 
	 * @param context
	 */
	public static void showNoNetWork_cancle(final Context context,int icon) {
		Builder builder = new Builder(context);
		builder.setIcon(icon)
		.setTitle(R.string.app_name)
		.setMessage("当前无网络,连接后重试。")
		.setCancelable(false)//不能取消此圣诞框。
		.setNegativeButton("取消",null).show();
	}


	/**
	 * 退出系统
	 * 
	 * @param context
	 */
	public static void showExitSystem(Context context,int icon) {
		Builder builder = new Builder(context);
		builder.setIcon(icon)//
				.setTitle(R.string.app_name)//
				.setMessage("确定要退出?").setPositiveButton("确定", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						android.os.Process.killProcess(android.os.Process.myPid());
						// 多个Activity——懒人听书：没有彻底退出应用
						// 将所有用到的Activity都存起来，获取全部，干掉
						// BaseActivity——onCreated——放到容器中
					}
				})//
				.setNegativeButton("取消", null)//
				.show();

	}
	
	
	
	/**
	 * 显示错误提示框
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showErrorDialog(Context context, String msg) {
		new Builder(context)//
//				.setIcon(R.drawable.alarm_icon)//
				.setTitle("错误")//
				.setMessage(msg)//
				.setNegativeButton("确定", null)//
				.show();
	}

	public static void showToast(Context context, String msg) {
		Toast.makeText(context, msg,Toast.LENGTH_SHORT).show();
	}

	public static void showToast(Context context, int msgResId) {
		Toast.makeText(context, msgResId, Toast.LENGTH_SHORT).show();
	}

	// 当测试阶段时true
	private static final boolean isShow = true;

	/**
	 * 测试用 在正式投入市场：删
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showToastTest(Context context, String msg) {
		if (isShow) {
			Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
		}
	}

}

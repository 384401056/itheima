package cn.com.gstar.smartcamera.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import cn.com.gstar.smartcamera.R;
import cn.com.gstar.smartcamera.activity.MainActivity;
import cn.com.gstar.smartcamera.utils.HttpTools;
import com.lidroid.xutils.util.LogUtils;

/**
 * Created by Administrator on 2016/9/2.
 */
public class HeartBeatService extends Service {

    private static final int NOTIFICATION_FLAG = 1;
    private MyHandler handler = new MyHandler();
    private SendHeartBeatTask sendHeartBeatTask = null;

    @Override
    public void onCreate() {
        super.onCreate();
        startSendHeartBeat();
        LogUtils.i("onCreate.....");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
        LogUtils.i("onDestroy.....");
    }

    private void startSendHeartBeat() {
        handler.sendMessage(new Message());
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(new ComponentName(this, MainActivity.class));//用ComponentName得到class对象
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);// 关键的一步，设置启动模式，两种情况

        PendingIntent pendingIntent3 = PendingIntent.getActivity(this, 0, intent, 0);// 通过Notification.Builder来创建通知，注意API Level// API16之后才支持

        Notification notify3 = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setTicker("服务已启动...")
                .setContentTitle("服务名称")
                .setContentText("请不要关闭此服务...")
                .setOngoing(true)
                .setContentIntent(pendingIntent3).setNumber(1).build(); // 需要注意build()是在API
        // level16及之后增加的，API11可以使用getNotificatin()来替代
//        notify3.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
        manager.notify(NOTIFICATION_FLAG, notify3);// 步骤4：通过通知管理器来发起通知。如果id不同，则每click，在status哪里增加一个提示
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.i("onStartCommand.....");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class SendHeartBeatTask extends AsyncTask<Void,Void,Integer>{
        @Override
        protected Integer doInBackground(Void... voids) {
            String result = HttpTools.getJsonByUrl("/hb");
            if(result!=null){
                return Integer.parseInt(result);
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if(result==1){
                LogUtils.i("SendHeartBeat Success!");
            }else{
                LogUtils.i("Send Fail!!!");
            }
            handler.sendMessageDelayed(new Message(),1000);
        }
    }

    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            sendHeartBeatTask = new HeartBeatService.SendHeartBeatTask();
            sendHeartBeatTask.execute();
        }
    }

}





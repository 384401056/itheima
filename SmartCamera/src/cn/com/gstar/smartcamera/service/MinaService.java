package cn.com.gstar.smartcamera.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import cn.com.gstar.smartcamera.R;
import cn.com.gstar.smartcamera.activity.MainActivity;
import com.lidroid.xutils.util.LogUtils;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by Administrator on 2016/9/2.
 */
public class MinaService extends Service {

    private static final int NOTIFICATION_FLAG = 1;
    private static int PORT = 4444;
    private Thread serverThread = null;
    private IoAcceptor acceptor;
    @Override
    public void onCreate() {
        super.onCreate();
        startMinaServer();
        LogUtils.i("onCreate.....");
    }

    private void startMinaServer() {
        initNotify();
        serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    //创建一个非阻塞的server端的Socket
                    acceptor = new NioSocketAcceptor();
                    //设置过滤器（使用mina提供的文本换行符编解码器）
                    acceptor.getFilterChain().addLast(
                            "codec", new ProtocolCodecFilter(
                                    new TextLineCodecFactory(
                                            Charset.forName("UTF-8"),
                                            LineDelimiter.WINDOWS.getValue(),
                                            LineDelimiter.WINDOWS.getValue()
                                    )
                            )
                    );
                    //自定义的编解码器
                    //acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new CharsetCodecFactory()));
                    //设置读取数据的换从区大小
                    acceptor.getSessionConfig().setReadBufferSize(2048);
                    //读写通道10秒内无操作进入空闲状态
                    acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
                    //为接收器设置管理服务
                    acceptor.setHandler(new ServerHandler());
                    //绑定端口
                    acceptor.bind(new InetSocketAddress(PORT));
                    LogUtils.i("服务器启动成功...    端口号："+PORT);
                }catch (Exception e){
                    LogUtils.e("服务器异常.."+e);
                }
            }
        });
        serverThread.start();
    }


    private void initNotify() {
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
    public void onDestroy() {
        super.onDestroy();
        acceptor.unbind();
        LogUtils.i("onDestroy.....");
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

}





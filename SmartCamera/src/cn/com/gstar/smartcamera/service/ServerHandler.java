package cn.com.gstar.smartcamera.service;

import android.os.AsyncTask;
import cn.com.gstar.smartcamera.models.ReceiveData;
import cn.com.gstar.smartcamera.utils.HttpTools;
import cn.com.gstar.smartcamera.utils.JsonParser;
import com.lidroid.xutils.util.LogUtils;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.util.Date;

/**
 * Created by Administrator on 2016/9/6.
 */
public class ServerHandler extends IoHandlerAdapter {

    private ReceiveData receiveData;
    //从端口接受消息，会响应此方法来对消息进行处理
    @Override
    public void messageReceived(final IoSession session, final Object message) throws Exception {
        final String msg = message.toString();
        LogUtils.i("收到来自 "+session.getRemoteAddress().toString()+" 的信息："+msg);
        receiveData = new ReceiveData();
        receiveData.setIp(session.getRemoteAddress().toString());
        receiveData.setNum(msg);
        receiveData.setDate(new Date().toLocaleString());

        if("exit".equals(msg)){
            //如果客户端发来exit，则关闭该连接
            session.closeNow();
        }else{
            //发送数据到nodejs
            new AsyncTask<Void,Void,Integer>(){
                @Override
                protected Integer doInBackground(Void... voids) {

//                    String result = HttpTools.getJsonByUrl("/insert?ip="+session.getRemoteAddress().toString()+"&num="+msg+"&date="+new Date().toLocaleString());

                    String result = HttpTools.postJson("/insert", JsonParser.objectToJson(receiveData));
                    if(result!=null){
                        return Integer.parseInt(result);
                    }
                    return 0;
                }
                @Override
                protected void onPostExecute(Integer result) {
                    Date date = new Date();
                    if(result!=0){
                        //向客户端发送消息
                        session.write("Save Scucess!!:"+date);
                    }else{
                        session.write("Save Fail...........:"+date);
                    }
                }
            }.execute();
        }
    }

    //向客服端发送消息后会调用此方法
    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
//        LogUtils.i("服务器发送消息成功...");
        super.messageSent(session, message);
    }

    //关闭与客户端的连接时会调用此方法
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        LogUtils.i("服务器与客户端断开连接...");
        super.sessionClosed(session);
    }

    //服务器与客户端创建连接
    @Override
    public void sessionCreated(IoSession session) throws Exception {
//        LogUtils.i("服务器与客户端创建连接...");
        super.sessionCreated(session);
    }

    //服务器与客户端连接打开
    @Override
    public void sessionOpened(IoSession session) throws Exception {
//        LogUtils.i("服务器与客户端连接打开...");
        super.sessionOpened(session);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status)
            throws Exception {
//        LogUtils.i("服务器进入空闲状态...");
        super.sessionIdle(session, status);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {
        LogUtils.i("服务器发送异常"+cause);
//        super.exceptionCaught(session, cause);
    }
}

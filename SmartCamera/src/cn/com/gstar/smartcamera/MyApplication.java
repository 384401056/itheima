package cn.com.gstar.smartcamera;

import android.app.Application;
import com.lidroid.xutils.util.LogUtils;

import java.util.logging.Logger;

/**
 * Created by Administrator on 2016/9/2.
 */
public class MyApplication extends Application {

    private static Logger myLog;
    public void onCreate() {
        super.onCreate();
        LogUtils.customTagPrefix = "MyLog";
        LogUtils.allowI = true;
//        myLog = Logger.getLogger(String.valueOf(MinaServer.class));
//        try {
//            PatternLayout patternLayout = new PatternLayout();
//            patternLayout.setConversionPattern("%d{yyyy-MM-dd HH:mm:ss} %m %n");
//            FileAppender fileAppender = new FileAppender(patternLayout, "D://log4j_info.log" );
//            myLog.addAppender(fileAppender);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

//    private static String getOutputPath(Context context, String name) {
//        String rootPath = getSdcardPath(context);
//
//        StringBuilder fileName = new StringBuilder();
//        // 手机型号
//        fileName.append(android.os.Build. MODEL);
//        fileName.append( "-");
//
//        // 系统版本
//        fileName.append( "Android_");
//        fileName.append(android.os.Build.VERSION.RELEASE );
//        // SDK版本
//        fileName.append( "_");
//        fileName.append(android.os.Build.VERSION. SDK);
//        fileName.append( "-");
//        String path = rootPath + "/" + fileName.toString() + name;
//        return path;
//    }
//
//
//    private static String getSdcardPath(Context context) {
//        // SD卡是否存在
//        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment. MEDIA_MOUNTED)) {
//            // SD卡存在，返回SD卡根目录
//            return Environment.getExternalStorageDirectory().getPath();
//        } else {
//            return context.getFilesDir().getPath();
//        }
//    }
}

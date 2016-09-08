package cn.com.gstar.smartcamera;

import android.app.Application;
import android.os.Environment;
import com.lidroid.xutils.util.LogUtils;
import de.mindpipe.android.logging.log4j.LogConfigurator;
import org.apache.log4j.Level;

import java.io.File;

/**
 * Created by Administrator on 2016/9/2.
 */
public class MyApplication extends Application {

    public void onCreate() {
        super.onCreate();
        LogUtils.customTagPrefix = "MyLog";
        LogUtils.allowI = true;
    }

    public static void configure() {
        final LogConfigurator logConfigurator = new LogConfigurator();
//        Date nowtime = new Date();
//        String needWriteMessage = myLogSdf.format(nowtime);
        //日志文件路径地址:SD卡下myc文件夹log文件夹的test文件
        String fileName = Environment.getExternalStorageDirectory()
                + File.separator + "SmartCamera" + File.separator + "log"
                + File.separator + "Server.log";
        //设置文件名
        logConfigurator.setFileName(fileName);
        //设置root日志输出级别 默认为DEBUG
        logConfigurator.setRootLevel(Level.DEBUG);
        // 设置日志输出级别
        logConfigurator.setLevel("org.apache", Level.INFO);
        //设置 输出到日志文件的文字格式 默认 %d %-5p [%c{2}]-[%L] %m%n
        logConfigurator.setFilePattern("%d %-5p [%c{2}]-[%L] %m%n");
        //设置输出到控制台的文字格式 默认%m%n
        logConfigurator.setLogCatPattern("%m%n");
        //设置总文件大小
//        logConfigurator.setMaxFileSize(1024 * 1024 * 5);
//        //设置最大产生的文件个数
//        logConfigurator.setMaxBackupSize(100);

        //设置所有消息是否被立刻输出 默认为true,false 不输出
        logConfigurator.setImmediateFlush(true);
        //是否本地控制台打印输出 默认为true ，false不输出
        logConfigurator.setUseLogCatAppender(true);
        //设置是否启用文件附加,默认为true。false为覆盖文件
        logConfigurator.setUseFileAppender(true);
        //设置是否重置配置文件，默认为true
        logConfigurator.setResetConfiguration(true);
        //是否显示内部初始化日志,默认为false
        logConfigurator.setInternalDebugging(false);

        logConfigurator.configure();

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

package cn.com.gstar.smartcamera.models;

/**
 * Created by Administrator on 2016/9/8.
 */
public class ReceiveData {
    private String ip;
    private String msg;
    private String date;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNum() {
        return msg;
    }

    public void setNum(String num) {
        this.msg = num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

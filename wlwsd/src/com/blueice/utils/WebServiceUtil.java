package com.blueice.utils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.blueice.domain.HistoryData;
import com.blueice.wlwsd.R;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class WebServiceUtil {

    // 定义webservice的命名空间
    public static final String SERVICE_NAMESPACE = "http://gstar.com/";
    // 定义webservice提供服务的url
    public static final String SERVICE_URL = "http://121.42.44.149:801/ThmsApiV1.asmx";

    /*
     * 用户登陆
     */
    public static String userLogin(String userName, String pwd,String authentication) {

        String methodName = "login";
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);

        ht.debug = true;
        SoapObject soapObject = new SoapObject(SERVICE_NAMESPACE, methodName);

        // 传入参数。
        soapObject.addProperty("account", userName);
        soapObject.addProperty("password", pwd);
        soapObject.addProperty("authentication", authentication);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.bodyOut = soapObject;
        envelope.dotNet = true;

        // 调用webservice
        try {
            ht.call(SERVICE_NAMESPACE + methodName, envelope);

            if (envelope.getResponse() != null) {
                // 获取服务器响应返回的SOAP消息
                SoapObject result = (SoapObject) envelope.bodyIn;
                return result.getProperty(methodName + "Result").toString();
            }

        } catch (SoapFault e) {
            System.out.println("SoapFault:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException:" + e.getMessage());
        } catch (XmlPullParserException e) {
            System.out.println("XmlPullParserException:" + e.getMessage());
        }

        return null;
    }

    /*
     * 获取用户SensorIds
     */
    public static List<String> getUserSensorIds(String apiToken) {

        ArrayList<String> resultList = new ArrayList<String>();

        String methodName = "getUserSensorIds";
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;
        SoapObject soapObject = new SoapObject(SERVICE_NAMESPACE, methodName);
        // 传入参数。
        soapObject.addProperty("apiToken", apiToken);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;

        try {
            ht.call(SERVICE_NAMESPACE + methodName, envelope); // 调用webservice

            if (envelope.getResponse() != null) {
                // 获取服务器响应返回的SOAP消息
                soapObject = (SoapObject) envelope.bodyIn;
                SoapObject soap1 = (SoapObject) soapObject.getProperty(0);
                SoapObject soap2 = (SoapObject) soap1.getProperty(1);

                for (int i = 0; i < soap2.getPropertyCount(); i++) {
                    SoapObject soap3 = (SoapObject) soap2.getProperty(i);
                    for (int j = 0; j < soap3.getPropertyCount(); j++) {
                        SoapObject soap4 = (SoapObject) soap3.getProperty(j);

                        resultList.add(soap4.getProperty("Id").toString());
                    }
                }

                return resultList;
            }

        } catch (SoapFault e) {
            System.out.println("SoapFault:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException:" + e.getMessage());
        } catch (XmlPullParserException e) {
            System.out.println("XmlPullParserException:" + e.getMessage());
        }

        return null;
    }

    /*
     * 根据探头号获取RealSignal
     */
    public static HashMap<String, Object> getRealSignalById(String apiToken,int id) {
    	
        HashMap<String, Object> resMap = new HashMap<String, Object>();
        String methodName = "getRealSignalById";
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;
        SoapObject soapObject = new SoapObject(SERVICE_NAMESPACE, methodName);
        // 传入参数。
        soapObject.addProperty("apiToken", apiToken);
        soapObject.addProperty("id", id);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;

        try {
            ht.call(SERVICE_NAMESPACE + methodName, envelope); // 调用webservice

            if (envelope.getResponse() != null) {
                // 获取服务器响应返回的SOAP消息
                soapObject = (SoapObject) envelope.bodyIn;
                SoapObject soap1 = (SoapObject) soapObject.getProperty(0);
                SoapObject soap2 = (SoapObject) soap1.getProperty(1);

                for (int i = 0; i < soap2.getPropertyCount(); i++) {
                    SoapObject soap3 = (SoapObject) soap2.getProperty(i);
                    for (int j = 0; j < soap3.getPropertyCount(); j++) {
                        SoapObject soap4 = (SoapObject) soap3.getProperty(j);

                        resMap.put("Id", soap4.getProperty("Id").toString());
                        resMap.put("ElectricPower",
                                soap4.getProperty("ElectricPower").toString()
                                        + "%");
                        resMap.put("Temperature",
                                soap4.getProperty("Temperature").toString()
                                        + "℃");
                        resMap.put("Humidity", soap4.getProperty("Humidity")
                                .toString() + "%");

                        String revData = soap4.getProperty("RecvTime").toString()
                                .substring(0, 10);
                        String revTiem = soap4.getProperty("RecvTime").toString()
                                .substring(11, 19);

                        resMap.put("RecvTime", revData + " " + revTiem);

//						resMap.put("State", R.drawable.sensor_1);
                    }
                }
            }
        } catch (SoapFault e) {
            System.out.println("SoapFault:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException:" + e.getMessage());
        } catch (XmlPullParserException e) {
            System.out.println("XmlPullParserException:" + e.getMessage());
        }
        return resMap;
    }

    /*
     * 根据探头号获取AlarmSignal
     */
    public static HashMap<String, Object> getAlarmSignal(String apiToken,int id, String begin, String end) {
        HashMap<String, Object> resMap = new HashMap<String, Object>();
        String methodName = "getAlarmSignal";
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;
        SoapObject soapObject = new SoapObject(SERVICE_NAMESPACE, methodName);
        // 传入参数。
        soapObject.addProperty("apiToken", apiToken);
        soapObject.addProperty("id", id);
        soapObject.addProperty("begin", begin);
        soapObject.addProperty("end", end);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;

        try {
            ht.call(SERVICE_NAMESPACE + methodName, envelope); // 调用webservice

            if (envelope.getResponse() != null) {
                // 获取服务器响应返回的SOAP消息
                soapObject = (SoapObject) envelope.bodyIn;
                SoapObject soap1 = (SoapObject) soapObject.getProperty(0);
                SoapObject soap2 = (SoapObject) soap1.getProperty(1);

                for (int i = 0; i < soap2.getPropertyCount(); i++) {
                    SoapObject soap3 = (SoapObject) soap2.getProperty(i);

                    // for (int j=0;j<soap3.getPropertyCount();j++){

                    // 只取最后一条。
                    SoapObject soap4 = (SoapObject) soap3.getProperty(soap3
                            .getPropertyCount() - 1);

                    // for(int k=0;k<soap4.getPropertyCount();k++){

                    resMap.put("Id", soap4.getProperty("Id").toString());
                    resMap.put("LowElectricPower",
                            soap4.getProperty("LowElectricPower").toString());
                    resMap.put("RealElectricPower",
                            soap4.getProperty("RealElectricPower").toString()
                                    + "%");

                    resMap.put("LowTemperature",
                            soap4.getProperty("LowTemperature").toString());
                    resMap.put("HighTemperature",
                            soap4.getProperty("HighTemperature").toString());
                    resMap.put("RealTemperature",
                            soap4.getProperty("RealTemperature").toString()
                                    + "℃");

                    resMap.put("LowHumidity", soap4.getProperty("LowHumidity")
                            .toString());
                    resMap.put("HighHumidity", soap4
                            .getProperty("HighHumidity").toString());
                    resMap.put("RealHumidity", soap4
                            .getProperty("RealHumidity").toString() + "%");

                    String revData = soap4.getProperty("RecvTime").toString()
                            .substring(0, 10);
                    String revTiem = soap4.getProperty("RecvTime").toString()
                            .substring(11, 19);
                    resMap.put("RecvTime", revData + " " + revTiem);
                    resMap.put("State", R.drawable.sensor_alarm);

                    // }
                    // }
                }
            }
        } catch (SoapFault e) {
            System.out.println("SoapFault:" + e.getMessage());
            return null;
        } catch (IOException e) {
            System.out.println("IOException:" + e.getMessage());
            return null;
        } catch (XmlPullParserException e) {
            System.out.println("XmlPullParserException:" + e.getMessage());
            return null;
        }
        return resMap;
    }

    /*
     * 获取历史图表数据.
     */
    public static List<HistoryData> getHistorySignal(String apiToken, int id,String begin, String end) {
        List<HistoryData> res = new ArrayList<HistoryData>();

        String methodName = "getHistorySignal";
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;
        SoapObject soapObject = new SoapObject(SERVICE_NAMESPACE, methodName);
        // 传入参数。
        soapObject.addProperty("apiToken", apiToken);
        soapObject.addProperty("id", id);
        soapObject.addProperty("begin", begin);
        soapObject.addProperty("end", end);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;

        try {
            ht.call(SERVICE_NAMESPACE + methodName, envelope); // 调用webservice

            if (envelope.getResponse() != null) {
                // 获取服务器响应返回的SOAP消息
                soapObject = (SoapObject) envelope.bodyIn;
                SoapObject soap1 = (SoapObject) soapObject.getProperty(0);
                SoapObject soap2 = (SoapObject) soap1.getProperty(1);

                for (int i = 0; i < soap2.getPropertyCount(); i++) {
                    SoapObject soap3 = (SoapObject) soap2.getProperty(i);

                    String currentStr;
                    Date lastDate = null;

                    for (int j = 0; j < soap3.getPropertyCount(); j++) {
                        SoapObject soap4 = (SoapObject) soap3.getProperty(j);

                        // System.out.println("Soap4="+j+"=ID=====>"+soap4.getProperty("Id").toString());
                        // System.out.println("Soap4="+j+"=ElectricPower=====>"+soap4.getProperty("ElectricPower").toString());
                        // System.out.println("Soap4="+j+"=Temperature=====>"+soap4.getProperty("Temperature").toString());
                        // System.out.println("Soap4="+j+"=Humidity=====>"+soap4.getProperty("Humidity").toString());
                        // System.out.println("Soap4="+j+"=RecvTime=====>"+soap4.getProperty("RecvTime").toString());

                        currentStr = (soap4.getProperty("RecvTime").toString().substring(0, 10)+" "+soap4.getProperty("RecvTime").toString().substring(11, 19));

                        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date currentDate;
                        try {

                            currentDate = fmt.parse(currentStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                            return null;
                        }

                        if (lastDate == null) {

                            lastDate = currentDate;

                            HistoryData data = new HistoryData();
                            data.setElectricPower(Double.parseDouble(soap4
                                    .getProperty("ElectricPower").toString()));
                            data.setTemperature(Double.parseDouble(soap4
                                    .getProperty("Temperature").toString()));
                            data.setHumidity(Double.parseDouble(soap4.getProperty(
                                    "Humidity").toString()));
                            data.setRecvTime(soap4.getProperty("RecvTime")
                                    .toString());

                            res.add(data);

                        }


                        if (compareDate(currentDate, lastDate) == 0) {
                            continue;
                        } else {

                            HistoryData data = new HistoryData();
                            data.setElectricPower(Double.parseDouble(soap4
                                    .getProperty("ElectricPower").toString()));
                            data.setTemperature(Double.parseDouble(soap4
                                    .getProperty("Temperature").toString()));
                            data.setHumidity(Double.parseDouble(soap4
                                    .getProperty("Humidity").toString()));
                            data.setRecvTime(soap4.getProperty("RecvTime")
                                    .toString());

                            res.add(data);
                            lastDate = currentDate;
                        }
                    }
                }
            }

        } catch (SoapFault e) {
            System.out.println("SoapFault:" + e.getMessage());
            return null;
        } catch (IOException e) {
            System.out.println("IOException:" + e.getMessage());
            return null;
        } catch (XmlPullParserException e) {
            System.out.println("XmlPullParserException:" + e.getMessage());
            return null;
        }

        System.out.println("res.size===>"+res.size());
        return res;
    }


    /**
     * 比较时间。
     * @param currentDate
     * @param lastDate
     * @return
     */
    private static int compareDate(Date currentDate, Date lastDate) {
        long diffrent = (currentDate.getTime() -lastDate.getTime())/(60*60*1000);

        if(diffrent>=1){
            return 1;
        }

        return 0;
    }

}

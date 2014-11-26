package com.blueice.domain;

/**
 * 历史数据类
 */
public class HistoryData {

    private int Id;
    private double ElectricPower;
    private double Temperature;
    private double Humidity;
    private String RecvTime;

    public int getId() {
        return Id;
    }
    public void setId(int id) {
        Id = id;
    }
    public double getElectricPower() {
        return ElectricPower;
    }
    public void setElectricPower(double electricPower) {
        ElectricPower = electricPower;
    }
    public double getTemperature() {
        return Temperature;
    }
    public void setTemperature(double temperature) {
        Temperature = temperature;
    }
    public double getHumidity() {
        return Humidity;
    }
    public void setHumidity(double humidity) {
        Humidity = humidity;
    }
    public String getRecvTime() {
        return RecvTime;
    }
    public void setRecvTime(String recvTime) {
        RecvTime = recvTime;
    }



}


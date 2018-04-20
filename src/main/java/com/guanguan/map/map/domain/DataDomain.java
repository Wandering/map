package com.guanguan.map.map.domain;

import java.io.Serializable;

/**
 * @program: map
 * @description: 一句话说明该类
 * @author: 杨永平
 * @create: 2018年01月23日 15:46
 **/
public class DataDomain implements Serializable{
    private String temperature;
    private String humidity;
    private String longitude;
    private String latitude;
    private String acceleration;
    private String obliquity;
    private String angularAcc;

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(String acceleration) {
        this.acceleration = acceleration;
    }

    public String getObliquity() {
        return obliquity;
    }

    public void setObliquity(String obliquity) {
        this.obliquity = obliquity;
    }

    public String getAngularAcc() {
        return angularAcc;
    }

    public void setAngularAcc(String angularAcc) {
        this.angularAcc = angularAcc;
    }
}

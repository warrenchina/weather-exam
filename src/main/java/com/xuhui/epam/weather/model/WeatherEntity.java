package com.xuhui.epam.weather.model;

import java.io.Serializable;

public class WeatherEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 城市名称
     */
    private String city;
    /**
     * 城市id
     */
    private String cityid;
    /**
     * 温度
     */
    private String temp;
    /**
     * 风向
     */
    private String WD;
    /**
     * 风速
     */
    private String WS;

    /**
     * 湿度
     */
    private String SD;

    /**
     * 气压
     */
    private String AP;
    /**
     * 能见度
     */
    private String njd;

    private String WSE;
    /**
     * 时间
     */
    private String time;
    private String sm;
    private String isRadar;
    private String Radar;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getWD() {
        return WD;
    }

    public void setWD(String WD) {
        this.WD = WD;
    }

    public String getWS() {
        return WS;
    }

    public void setWS(String WS) {
        this.WS = WS;
    }

    public String getSD() {
        return SD;
    }

    public void setSD(String SD) {
        this.SD = SD;
    }

    public String getAP() {
        return AP;
    }

    public void setAP(String AP) {
        this.AP = AP;
    }

    public String getNjd() {
        return njd;
    }

    public void setNjd(String njd) {
        this.njd = njd;
    }

    public String getWSE() {
        return WSE;
    }

    public void setWSE(String WSE) {
        this.WSE = WSE;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSm() {
        return sm;
    }

    public void setSm(String sm) {
        this.sm = sm;
    }

    public String getIsRadar() {
        return isRadar;
    }

    public void setIsRadar(String isRadar) {
        this.isRadar = isRadar;
    }

    public String getRadar() {
        return Radar;
    }

    public void setRadar(String radar) {
        Radar = radar;
    }
}

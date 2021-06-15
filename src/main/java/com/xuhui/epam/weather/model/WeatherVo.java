package com.xuhui.epam.weather.model;

import java.io.Serializable;

public class WeatherVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private WeatherEntity weatherinfo;

    public WeatherEntity getWeatherinfo() {
        return weatherinfo;
    }

    public void setWeatherinfo(WeatherEntity weatherinfo) {
        this.weatherinfo = weatherinfo;
    }
}

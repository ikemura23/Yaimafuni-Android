package com.ikmr.banbara23.yaeyama_liner_checker.model.weather;

import com.google.gson.annotations.SerializedName;

/**
 * 時間毎の天気
 */
public class Table {

    @SerializedName("hour")
    private String hour;
    @SerializedName("weather")
    private String weather;
    @SerializedName("windBlow")
    private String windBlow;
    @SerializedName("windSpeed")
    private String windSpeed;

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getHour() {
        return this.hour;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWeather() {
        return this.weather;
    }

    public void setWindBlow(String windBlow) {
        this.windBlow = windBlow;
    }

    public String getWindBlow() {
        return this.windBlow;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindSpeed() {
        return this.windSpeed;
    }

    @Override
    public String toString() {
        return "Table{" +
                "hour='" + hour + '\'' +
                ", weather='" + weather + '\'' +
                ", windBlow='" + windBlow + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                '}';
    }
}
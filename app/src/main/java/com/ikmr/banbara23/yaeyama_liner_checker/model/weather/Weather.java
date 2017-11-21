package com.ikmr.banbara23.yaeyama_liner_checker.model.weather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 天気 一日分
 */
public class Weather {

    @SerializedName("date")
    private String date;
    @SerializedName("table")
    private List<HourTimeTable> mHourTimes;
    @SerializedName("temperature")
    private Temperature temperature;
    @SerializedName("wave")
    private String wave;
    @SerializedName("weather")
    private String weather;
    @SerializedName("wind")
    private String wind;

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }

    public List<HourTimeTable> getHourTimes() {
        return mHourTimes;
    }

    public void setHourTimes(List<HourTimeTable> hourTimes) {
        mHourTimes = hourTimes;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Temperature getTemperature() {
        return this.temperature;
    }

    public void setWave(String wave) {
        this.wave = wave;
    }

    public String getWave() {
        return this.wave;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWeather() {
        return this.weather;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getWind() {
        return this.wind;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "date='" + date + '\'' +
                ", mHourTimes=" + mHourTimes +
                ", temperature=" + temperature +
                ", wave='" + wave + '\'' +
                ", weather='" + weather + '\'' +
                ", wind='" + wind + '\'' +
                '}';
    }
}

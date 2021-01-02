package com.ikmr.banbara23.yaeyama_liner_checker.model.weather;

import java.util.List;

/**
 * 天気 一日分
 */
public class Weather {

    private String date;
    private List<Table> table;
    private Temperature temperature;
    private String wave;
    private String weather;
    private String wind;

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Table> getTable() {
        return table;
    }

    public void setTable(List<Table> table) {
        this.table = table;
    }

    public Temperature getTemperature() {
        return this.temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public String getWave() {
        return this.wave;
    }

    public void setWave(String wave) {
        this.wave = wave;
    }

    public String getWeather() {
        return this.weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWind() {
        return this.wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    @Override
    public String toString() {
        return "Weather{" +
               "date='" + date + '\'' +
               ", table=" + table +
               ", temperature=" + temperature +
               ", wave='" + wave + '\'' +
               ", weather='" + weather + '\'' +
               ", wind='" + wind + '\'' +
               '}';
    }
}

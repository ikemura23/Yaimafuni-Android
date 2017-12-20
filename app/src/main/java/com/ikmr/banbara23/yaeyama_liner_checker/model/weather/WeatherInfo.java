package com.ikmr.banbara23.yaeyama_liner_checker.model.weather;

/**
 * 天気ルート
 */
public class WeatherInfo {
    private Weather today;

    private Weather tomorrow;

    public Weather getToday() {
        return today;
    }

    public void setToday(Weather today) {
        this.today = today;
    }

    public Weather getTomorrow() {
        return tomorrow;
    }

    public void setTomorrow(Weather tomorrow) {
        this.tomorrow = tomorrow;
    }

    @Override
    public String toString() {
        return "WeatherInfo{" +
                "today=" + today +
                ", tomorrow=" + tomorrow +
                '}';
    }
}

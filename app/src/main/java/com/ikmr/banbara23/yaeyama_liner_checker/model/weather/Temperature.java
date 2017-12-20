package com.ikmr.banbara23.yaeyama_liner_checker.model.weather;

/**
 * 気温
 */
public class Temperature {

    private String hight;
    private String low;

    public void setHight(String hight) {
        this.hight = hight;
    }

    public String getHight() {
        return this.hight;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getLow() {
        return this.low;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "hight='" + hight + '\'' +
                ", low='" + low + '\'' +
                '}';
    }
}

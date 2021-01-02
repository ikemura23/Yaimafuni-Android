package com.ikmr.banbara23.yaeyama_liner_checker.model.weather;

/**
 * 気温
 */
public class Temperature {

    private String hight;
    private String low;

    public String getHight() {
        return this.hight;
    }

    public void setHight(String hight) {
        this.hight = hight;
    }

    public String getLow() {
        return this.low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    @Override
    public String toString() {
        return "Temperature{" +
               "hight='" + hight + '\'' +
               ", low='" + low + '\'' +
               '}';
    }
}

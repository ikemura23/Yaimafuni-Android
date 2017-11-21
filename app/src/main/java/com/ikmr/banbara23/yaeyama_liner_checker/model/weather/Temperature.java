package com.ikmr.banbara23.yaeyama_liner_checker.model.weather;

import com.google.gson.annotations.SerializedName;

/**
 * 気温
 */
public class Temperature {

    @SerializedName("hight")
    private String hight;
    @SerializedName("low")
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

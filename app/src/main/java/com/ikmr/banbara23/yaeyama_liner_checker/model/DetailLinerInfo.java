package com.ikmr.banbara23.yaeyama_liner_checker.model;

import com.google.gson.annotations.SerializedName;

/**
 * 運行ステータス以外の情報
 */
public class DetailLinerInfo {

    @SerializedName("driving_time")
    private String driving_time;
    @SerializedName("adult_one_way_fare")
    private int adult_one_way_fare;
    @SerializedName("adult_round_trip_fare")
    private int adult_round_trip_fare;
    @SerializedName("child_one_way_fare")
    private int child_one_way_fare;
    @SerializedName("child_round_trip_fare")
    private int child_round_trip_fare;
    @SerializedName("disability_one_way_fare")
    private int disability_one_way_fare;
    @SerializedName("disability_round_trip_fare")
    private int disability_round_trip_fare;
    @SerializedName("url")
    private String url;
    @SerializedName("tell")
    private String tell;

    public String getDriving_time() {
        return driving_time;
    }

    public void setDriving_time(String driving_time) {
        this.driving_time = driving_time;
    }

    public int getAdult_one_way_fare() {
        return adult_one_way_fare;
    }

    public void setAdult_one_way_fare(int adult_one_way_fare) {
        this.adult_one_way_fare = adult_one_way_fare;
    }

    public int getAdult_round_trip_fare() {
        return adult_round_trip_fare;
    }

    public void setAdult_round_trip_fare(int adult_round_trip_fare) {
        this.adult_round_trip_fare = adult_round_trip_fare;
    }

    public int getChild_one_way_fare() {
        return child_one_way_fare;
    }

    public void setChild_one_way_fare(int child_one_way_fare) {
        this.child_one_way_fare = child_one_way_fare;
    }

    public int getChild_round_trip_fare() {
        return child_round_trip_fare;
    }

    public void setChild_round_trip_fare(int child_round_trip_fare) {
        this.child_round_trip_fare = child_round_trip_fare;
    }

    public int getDisability_one_way_fare() {
        return disability_one_way_fare;
    }

    public void setDisability_one_way_fare(int disability_one_way_fare) {
        this.disability_one_way_fare = disability_one_way_fare;
    }

    public int getDisability_round_trip_fare() {
        return disability_round_trip_fare;
    }

    public void setDisability_round_trip_fare(int disability_round_trip_fare) {
        this.disability_round_trip_fare = disability_round_trip_fare;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTell() {
        return tell;
    }

    public void setTell(String tell) {
        this.tell = tell;
    }

    @Override
    public String toString() {
        return "DetailLinerInfo{" +
                "driving_time='" + driving_time + '\'' +
                ", adult_one_way_fare=" + adult_one_way_fare +
                ", adult_round_trip_fare=" + adult_round_trip_fare +
                ", child_one_way_fare=" + child_one_way_fare +
                ", child_round_trip_fare=" + child_round_trip_fare +
                ", disability_one_way_fare=" + disability_one_way_fare +
                ", disability_round_trip_fare=" + disability_round_trip_fare +
                ", url='" + url + '\'' +
                ", tell='" + tell + '\'' +
                '}';
    }
}

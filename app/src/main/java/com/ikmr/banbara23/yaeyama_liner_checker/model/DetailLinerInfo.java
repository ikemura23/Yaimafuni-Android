package com.ikmr.banbara23.yaeyama_liner_checker.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 運行ステータス以外の情報
 */
public class DetailLinerInfo implements Parcelable {

    private String drivingTime;
    private int adultOneWayFare;
    private int adultRoundTripFare;
    private int childOneWayFare;
    private int childRoundTripFare;
    private int disabilityOneWayFare;
    private int disabilityRoundTripFare;
    private String url;
    private String tell;

    public String getDrivingTime() {
        return drivingTime;
    }

    public void setDrivingTime(String drivingTime) {
        this.drivingTime = drivingTime;
    }

    public int getAdultOneWayFare() {
        return adultOneWayFare;
    }

    public void setAdultOneWayFare(int adultOneWayFare) {
        this.adultOneWayFare = adultOneWayFare;
    }

    public int getAdultRoundTripFare() {
        return adultRoundTripFare;
    }

    public void setAdultRoundTripFare(int adultRoundTripFare) {
        this.adultRoundTripFare = adultRoundTripFare;
    }

    public int getChildOneWayFare() {
        return childOneWayFare;
    }

    public void setChildOneWayFare(int childOneWayFare) {
        this.childOneWayFare = childOneWayFare;
    }

    public int getChildRoundTripFare() {
        return childRoundTripFare;
    }

    public void setChildRoundTripFare(int childRoundTripFare) {
        this.childRoundTripFare = childRoundTripFare;
    }

    public int getDisabilityOneWayFare() {
        return disabilityOneWayFare;
    }

    public void setDisabilityOneWayFare(int disabilityOneWayFare) {
        this.disabilityOneWayFare = disabilityOneWayFare;
    }

    public int getDisabilityRoundTripFare() {
        return disabilityRoundTripFare;
    }

    public void setDisabilityRoundTripFare(int disabilityRoundTripFare) {
        this.disabilityRoundTripFare = disabilityRoundTripFare;
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
                "drivingTime='" + drivingTime + '\'' +
                ", adultOneWayFare=" + adultOneWayFare +
                ", adultRoundTripFare=" + adultRoundTripFare +
                ", childOneWayFare=" + childOneWayFare +
                ", childRoundTripFare=" + childRoundTripFare +
                ", disabilityOneWayFare=" + disabilityOneWayFare +
                ", disabilityRoundTripFare=" + disabilityRoundTripFare +
                ", url='" + url + '\'' +
                ", tell='" + tell + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.drivingTime);
        dest.writeInt(this.adultOneWayFare);
        dest.writeInt(this.adultRoundTripFare);
        dest.writeInt(this.childOneWayFare);
        dest.writeInt(this.childRoundTripFare);
        dest.writeInt(this.disabilityOneWayFare);
        dest.writeInt(this.disabilityRoundTripFare);
        dest.writeString(this.url);
        dest.writeString(this.tell);
    }

    public DetailLinerInfo() {
    }

    protected DetailLinerInfo(Parcel in) {
        this.drivingTime = in.readString();
        this.adultOneWayFare = in.readInt();
        this.adultRoundTripFare = in.readInt();
        this.childOneWayFare = in.readInt();
        this.childRoundTripFare = in.readInt();
        this.disabilityOneWayFare = in.readInt();
        this.disabilityRoundTripFare = in.readInt();
        this.url = in.readString();
        this.tell = in.readString();
    }

    public static final Parcelable.Creator<DetailLinerInfo> CREATOR = new Parcelable.Creator<DetailLinerInfo>() {
        @Override
        public DetailLinerInfo createFromParcel(Parcel source) {
            return new DetailLinerInfo(source);
        }

        @Override
        public DetailLinerInfo[] newArray(int size) {
            return new DetailLinerInfo[size];
        }
    };
}

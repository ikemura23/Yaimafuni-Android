package com.ikmr.banbara23.yaeyama_liner_checker.model.time_table;

import android.os.Parcel;
import android.os.Parcelable;

import com.ikmr.banbara23.yaeyama_liner_checker.model.Status;

public class Item implements Parcelable {
    private String memo;
    private Status status;
    private String time;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.memo);
        dest.writeParcelable(this.status, flags);
        dest.writeString(this.time);
    }

    public Item() {
    }

    protected Item(Parcel in) {
        this.memo = in.readString();
        this.status = in.readParcelable(Status.class.getClassLoader());
        this.time = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Left{" +
                "memo='" + memo + '\'' +
                ", status=" + status +
                ", time='" + time + '\'' +
                '}';
    }
}
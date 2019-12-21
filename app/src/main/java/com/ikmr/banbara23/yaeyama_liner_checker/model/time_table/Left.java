package com.ikmr.banbara23.yaeyama_liner_checker.model.time_table;

import android.os.Parcel;
import android.os.Parcelable;

import com.ikmr.banbara23.yaeyama_liner_checker.model.Status;

public class Left implements Parcelable {
    public static final Parcelable.Creator<Left> CREATOR = new Parcelable.Creator<Left>() {
        @Override
        public Left createFromParcel(Parcel source) {
            return new Left(source);
        }

        @Override
        public Left[] newArray(int size) {
            return new Left[size];
        }
    };
    public String memo;
    public Status status;
    public String time;

    public Left() {
    }

    protected Left(Parcel in) {
        this.memo = in.readString();
        this.status = in.readParcelable(Status.class.getClassLoader());
        this.time = in.readString();
    }

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

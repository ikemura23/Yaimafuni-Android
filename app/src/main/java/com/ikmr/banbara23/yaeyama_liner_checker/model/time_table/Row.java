package com.ikmr.banbara23.yaeyama_liner_checker.model.time_table;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Row implements Parcelable {
    @SerializedName("left")
    public Left left;
    @SerializedName("right")
    public Right right;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.left, flags);
        dest.writeParcelable(this.right, flags);
    }

    public Row() {
    }

    protected Row(Parcel in) {
        this.left = in.readParcelable(Left.class.getClassLoader());
        this.right = in.readParcelable(Right.class.getClassLoader());
    }

    public static final Parcelable.Creator<Row> CREATOR = new Parcelable.Creator<Row>() {
        @Override
        public Row createFromParcel(Parcel source) {
            return new Row(source);
        }

        @Override
        public Row[] newArray(int size) {
            return new Row[size];
        }
    };

    public Left getLeft() {
        return left;
    }

    public void setLeft(Left left) {
        this.left = left;
    }

    public Right getRight() {
        return right;
    }

    public void setRight(Right right) {
        this.right = right;
    }
}